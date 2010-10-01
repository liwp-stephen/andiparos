/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2006 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.parosproxy.paros.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpBody;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

public class TableHistory extends AbstractTable {

	private static final String HISTORYID = "HISTORYID";
	private static final String SESSIONID = "SESSIONID";
	private static final String HISTTYPE = "HISTTYPE";
	private static final String METHOD = "METHOD";
	private static final String URI = "URI";
	private static final String STATUSCODE = "STATUSCODE";
	private static final String TIMESENTMILLIS = "TIMESENTMILLIS";
	private static final String TIMEELAPSEDMILLIS = "TIMEELAPSEDMILLIS";
	private static final String REQHEADER = "REQHEADER";
	private static final String REQBODY = "REQBODY";
	private static final String RESHEADER = "RESHEADER";
	private static final String RESBODY = "RESBODY";
	private static final String TAG = "TAG";
	private static final String FLAG = "FLAG";
	// ZAP: Added NOTE field to history table
    private static final String NOTE = "NOTE";

	private PreparedStatement psRead = null;
	private PreparedStatement psWrite1 = null;
	private CallableStatement psWrite2 = null;
	private PreparedStatement psDelete = null;
	private PreparedStatement psDeleteTemp = null;
	private PreparedStatement psContainsURI = null;
	private PreparedStatement psUpdateTag = null;
	private PreparedStatement psUpdateFlag = null;
	private PreparedStatement psUpdateNote = null;

	private static boolean isExistStatusCode = false;
	
	// ZAP: Added logger
    private static Log log = LogFactory.getLog(TableHistory.class);

	public TableHistory() {
	}

	protected void reconnect(Connection conn) throws SQLException {
		psRead = conn.prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE " + HISTORYID + " = ?");
		psDelete = conn.prepareStatement("DELETE FROM HISTORY WHERE " + HISTORYID + " = ?");
		psDeleteTemp = conn.prepareStatement("DELETE FROM HISTORY WHERE " + HISTTYPE + " = " + HistoryReference.TYPE_TEMPORARY);
		psContainsURI = conn.prepareStatement("SELECT TOP 1 HISTORYID FROM HISTORY WHERE URI = ? AND  METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND HISTTYPE = ?");

		isExistStatusCode = false;
		ResultSet rs = conn.getMetaData().getColumns(null, null, "HISTORY", "STATUSCODE");
		if (rs.next()) {
			isExistStatusCode = true;
		}
		rs.close();
		
		// ZAP: Added support for the tag when creating a history record
		// Andiparos: Previously used FLAG only. There was no tag. Switched position of STATUSCODE and TAG
		if (isExistStatusCode) {
			psWrite1 = conn.prepareStatement("INSERT INTO HISTORY ("
					+ SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + ","
					+ TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + ","
					+ REQHEADER + "," + REQBODY + "," + RESHEADER + ","
					+ RESBODY + "," + STATUSCODE + "," + TAG + "," + FLAG
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		} else {
			psWrite1 = conn.prepareStatement("INSERT INTO HISTORY ("
					+ SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + ","
					+ TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + ","
					+ REQHEADER + "," + REQBODY + "," + RESHEADER + ","
					+ RESBODY + "," + TAG + "," + FLAG
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		}
		
		
		/*if (isExistStatusCode) {
			psWrite1 = conn.prepareStatement("INSERT INTO HISTORY ("
					+ SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + ","
					+ TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + ","
					+ REQHEADER + "," + REQBODY + "," + RESHEADER + ","
					+ RESBODY + "," + STATUSCODE + "," + FLAG
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		} else {
			psWrite1 = conn.prepareStatement("INSERT INTO HISTORY ("
					+ SESSIONID + "," + HISTTYPE + "," + TIMESENTMILLIS + ","
					+ TIMEELAPSEDMILLIS + "," + METHOD + "," + URI + ","
					+ REQHEADER + "," + REQBODY + "," + RESHEADER + ","
					+ RESBODY + "," + FLAG
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		}*/
		
		
		
		
		psWrite2 = conn.prepareCall("CALL IDENTITY();");

		rs = conn.getMetaData().getColumns(null, null, "HISTORY", "TAG");
		if (!rs.next()) {
			PreparedStatement stmt = conn.prepareStatement("ALTER TABLE HISTORY ADD COLUMN TAG VARCHAR DEFAULT ''");
			stmt.execute();
		}
		rs.close();

		psUpdateTag = conn.prepareStatement("UPDATE HISTORY SET TAG = ? WHERE HISTORYID = ?");
		
		// Andiparos: Flag support
		psUpdateFlag = conn.prepareStatement("UPDATE HISTORY SET FLAG = ? WHERE HISTORYID = ?");
		
		// ZAP: Add the NOTE column to the db if necessary
        rs = conn.getMetaData().getColumns(null, null, "HISTORY", "NOTE");
        if (!rs.next()) {
            PreparedStatement stmt = conn.prepareStatement("ALTER TABLE HISTORY ADD COLUMN NOTE VARCHAR DEFAULT ''");
            stmt.execute();
        }
        rs.close();

       	psUpdateNote = conn.prepareStatement("UPDATE HISTORY SET NOTE = ? WHERE HISTORYID = ?");

	}

// TODO Support multiple tags

	public synchronized RecordHistory read(int historyId) throws HttpMalformedHeaderException, SQLException {
		psRead.setInt(1, historyId);
		psRead.execute();
		ResultSet rs = psRead.getResultSet();
		RecordHistory result = build(rs);
		rs.close();

		return result;
	}

	public synchronized RecordHistory write(long sessionId, int histType, HttpMessage msg) throws HttpMalformedHeaderException, SQLException {

		String reqHeader = "";
		String reqBody = "";
		String resHeader = "";
		String resBody = "";
		String method = "";
		String uri = "";
		Boolean flag = false;
		int statusCode = 0;

		if (!msg.getRequestHeader().isEmpty()) {
			reqHeader = msg.getRequestHeader().toString();
			reqBody = msg.getRequestBody().toString(HttpBody.STORAGE_CHARSET);
			method = msg.getRequestHeader().getMethod();
			uri = msg.getRequestHeader().getURI().toString();
			flag = msg.getFlag();
		}

		if (!msg.getResponseHeader().isEmpty()) {
			resHeader = msg.getResponseHeader().toString();
			resBody = msg.getResponseBody().toString(HttpBody.STORAGE_CHARSET);
			statusCode = msg.getResponseHeader().getStatusCode();
			flag = msg.getFlag();
		}

		//return write(sessionId, histType, msg.getTimeSentMillis(), msg.getTimeElapsedMillis(), method, uri, statusCode, reqHeader, reqBody, resHeader, resBody, flag);
		//return write(sessionId, histType, msg.getTimeSentMillis(), msg.getTimeElapsedMillis(), method, uri, statusCode, reqHeader, reqBody, resHeader, resBody, flag, msg.getTag());
		return write(sessionId, histType, msg.getTimeSentMillis(), msg.getTimeElapsedMillis(), method, uri, statusCode, reqHeader, reqBody, resHeader, resBody, null, flag);

	}

	
	private synchronized RecordHistory write(long sessionId, int histType,
			long timeSentMillis, int timeElapsedMillis, String method, String uri,
			int statusCode, String reqHeader, String reqBody, String resHeader,
			String resBody, String tag, Boolean flag) throws HttpMalformedHeaderException, SQLException {

		psWrite1.setLong(1, sessionId);
		psWrite1.setInt(2, histType);
		psWrite1.setLong(3, timeSentMillis);
		psWrite1.setInt(4, timeElapsedMillis);
		psWrite1.setString(5, method);
		psWrite1.setString(6, uri);
		psWrite1.setString(7, reqHeader);
		psWrite1.setString(8, reqBody);
		psWrite1.setString(9, resHeader);
		psWrite1.setString(10, resBody);
		
		// Andiparos: Handling for Flag and Tag
		if (isExistStatusCode) {
			psWrite1.setInt(11, statusCode);
			psWrite1.setString(12, tag);
			psWrite1.setBoolean(13, flag);
		} else {
			psWrite1.setString(11, tag);
			psWrite1.setBoolean(12, flag);
		}
		
		psWrite1.executeUpdate();

		psWrite2.executeQuery();
		ResultSet rs = psWrite2.getResultSet();
		rs.next();
		int id = rs.getInt(1);
		rs.close();
		return read(id);
	}

	private RecordHistory build(ResultSet rs) throws HttpMalformedHeaderException, SQLException {
		RecordHistory history = null;
		if (rs.next()) {
			history = new RecordHistory(
					rs.getInt(HISTORYID),
					rs.getInt(HISTTYPE),
					rs.getLong(SESSIONID),
					rs.getLong(TIMESENTMILLIS),
					rs.getInt(TIMEELAPSEDMILLIS),
					rs.getString(REQHEADER),
					rs.getString(REQBODY),
					rs.getString(RESHEADER),
					rs.getString(RESBODY),
					rs.getString(TAG),
					rs.getBoolean(FLAG), // Andiparos: Added Flag
					rs.getString(NOTE)	 // ZAP: Added note
			);
		}
		return history;

	}

	public Vector<Integer> getHistoryList(long sessionId, int histType) throws SQLException {
		PreparedStatement psReadSession = getConnection().prepareStatement(
				"SELECT " + HISTORYID + " FROM HISTORY WHERE " + SESSIONID + " = ? AND " + HISTTYPE + " = ? ORDER BY " + HISTORYID);

		Vector<Integer> v = new Vector<Integer>();
		psReadSession.setLong(1, sessionId);
		psReadSession.setInt(2, histType);
		psReadSession.executeQuery();
		ResultSet rs = psReadSession.getResultSet();

		while (rs.next()) {
			int last = rs.getInt(HISTORYID);
			v.add(new Integer(last));
		}
		rs.close();
		psReadSession.close();

		return v;
	}

	public List<Integer> getHistoryList(long sessionId, int histType, String filter, boolean isRequest) throws SQLException {
		PreparedStatement psReadSearch = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE " + SESSIONID + " = ? AND " + HISTTYPE + " = ? ORDER BY " + HISTORYID);

		Pattern pattern = Pattern.compile(filter, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;

		Vector<Integer> v = new Vector<Integer>();
		psReadSearch.setLong(1, sessionId);
		psReadSearch.setInt(2, histType);
		psReadSearch.executeQuery();
		ResultSet rs = psReadSearch.getResultSet();
		while (rs.next()) {
			if (isRequest) {
				matcher = pattern.matcher(rs.getString(REQHEADER));
				if (matcher.find()) {
					v.add(new Integer(rs.getInt(HISTORYID)));
					continue;
				}
				matcher = pattern.matcher(rs.getString(REQBODY));
				if (matcher.find()) {
					v.add(new Integer(rs.getInt(HISTORYID)));
					continue;
				}
			} else {
				matcher = pattern.matcher(rs.getString(RESHEADER));
				if (matcher.find()) {
					v.add(new Integer(rs.getInt(HISTORYID)));
					continue;
				}
				matcher = pattern.matcher(rs.getString(RESBODY));
				if (matcher.find()) {
					v.add(new Integer(rs.getInt(HISTORYID)));
					continue;
				}
			}

		}
		rs.close();
		psReadSearch.close();
		return v;
	}

	/* Andiparos: URI filter */
	public List<Integer> getFilteredHistoryList(long sessionId, int histType, String methodFilter, String uriFilter, boolean uriFilterInverse) throws SQLException {
		PreparedStatement psReadSearch = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE " + SESSIONID + " = ? AND " + HISTTYPE + " = ? ORDER BY " + HISTORYID);

		Pattern uriPattern = Pattern.compile(uriFilter, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher uriMatcher = null;
		
		Pattern methodPattern = Pattern.compile(methodFilter, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher methodMatcher = null;
		
		Vector<Integer> v = new Vector<Integer>();
		psReadSearch.setLong(1, sessionId);
		psReadSearch.setInt(2, histType);
		psReadSearch.executeQuery();

		ResultSet rs = psReadSearch.getResultSet();
		while (rs.next()) {
			methodMatcher = methodPattern.matcher(rs.getString(METHOD));
			uriMatcher = uriPattern.matcher(rs.getString(URI));

			// Check if ALL methods should be displayed
			if (methodFilter.equals("ALL") || methodFilter.equals("")) {
				
				if (uriFilterInverse == true) {
					if (!uriMatcher.find()) {
						v.add(new Integer(rs.getInt(HISTORYID)));
						continue;
					}
				} else {
					if (uriMatcher.find()) {
						v.add(new Integer(rs.getInt(HISTORYID)));
						continue;
					}
				}
			
			} else { // In this case, a certain HTTP method has been selected
				if (uriFilterInverse == true) {
					if (!uriMatcher.find() && methodMatcher.find()) {
						v.add(new Integer(rs.getInt(HISTORYID)));
						continue;
					}
				} else {
					if (uriMatcher.find() && methodMatcher.find()) {
						v.add(new Integer(rs.getInt(HISTORYID)));
						continue;
					}
				}
			}

		}

		return v;
	}

	public void deleteHistorySession(long sessionId) throws SQLException {
		Statement stmt = getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM HISTORY WHERE " + SESSIONID + " = " + sessionId);
		stmt.close();
	}

	public void deleteHistoryType(long sessionId, int historyType) throws SQLException {
		Statement stmt = getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM HISTORY WHERE " + SESSIONID + " = " + sessionId + " AND " + HISTTYPE + " = " + historyType);
		stmt.close();
	}

	public void delete(int historyId) throws SQLException {
		psDelete.setInt(1, historyId);
		psDelete.executeUpdate();

	}

	public void deleteTemporary() throws SQLException {
		psDeleteTemp.execute();
	}

	public boolean containsURI(long sessionId, int historyType, String method, String uri, String body) throws SQLException {
		psContainsURI.setString(1, uri);
		psContainsURI.setString(2, method);
		psContainsURI.setString(3, body);
		psContainsURI.setLong(4, sessionId);
		psContainsURI.setInt(5, historyType);
		psContainsURI.executeQuery();
		ResultSet rs = psContainsURI.getResultSet();
		if (rs.next()) {
			return true;
		}
		rs.close();
		return false;

	}

	public RecordHistory getHistoryCache(HistoryReference ref, HttpMessage reqMsg) throws SQLException, HttpMalformedHeaderException {

		// get the cache from provided reference.
		// naturally, the obtained cache should be AFTER AND NEARBY to the given reference.
		// - historyId up to historyId+200
		// - match sessionId
		// - history type can be MANUEL or hidden (hidden is used by images not explicitly stored in history)
		// - match URI
		PreparedStatement psReadCache = null;

		if (isExistStatusCode) {
			psReadCache = getConnection().prepareStatement(
					"SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ? AND STATUSCODE != 304");

		} else {
			psReadCache = getConnection().prepareStatement(
					"SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND " + HISTORYID + " >= ? AND " + HISTORYID + " <= ? AND SESSIONID = ?)");

		}
		
		psReadCache.setString(1, reqMsg.getRequestHeader().getURI().toString());
		psReadCache.setString(2, reqMsg.getRequestHeader().getMethod());
		psReadCache.setString(3, reqMsg.getRequestBody().toString(HttpBody.STORAGE_CHARSET));

		psReadCache.setInt(4, ref.getHistoryId());
		psReadCache.setInt(5, ref.getHistoryId() + 200);
		psReadCache.setLong(6, ref.getSessionId());

		psReadCache.executeQuery();
		ResultSet rs = psReadCache.getResultSet();
		RecordHistory rec = null;

		try {
			do {
				rec = build(rs);
				// for retrieval from cache, the message requests nature must be the same.
				// and the result should NOT be NOT_MODIFIED for rendering by browser
				if (rec != null && rec.getHttpMessage().equals(reqMsg) && rec.getHttpMessage().getResponseHeader().getStatusCode() != HttpStatusCode.NOT_MODIFIED) {
					return rec;
				}

			} while (rec != null);

		} finally {
			try {
				rs.close();
				psReadCache.close();
			} catch (Exception e) {
				// ZAP: Log exceptions
            	log.warn(e.getMessage(), e);
			}
		}

		// if cache not exist, probably due to NOT_MODIFIED,
		// lookup from cache BEFORE the given reference

		if (isExistStatusCode) {
			psReadCache = getConnection().prepareStatement("SELECT TOP 1 * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ? AND STATUSCODE != 304");
		} else {
			psReadCache = getConnection().prepareStatement("SELECT * FROM HISTORY WHERE URI = ? AND METHOD = ? AND REQBODY = ? AND SESSIONID = ?");
		}
		
		psReadCache.setString(1, reqMsg.getRequestHeader().getURI().toString());
		psReadCache.setString(2, reqMsg.getRequestHeader().getMethod());
		psReadCache.setString(3, reqMsg.getRequestBody().toString(HttpBody.STORAGE_CHARSET));
		psReadCache.setLong(4, ref.getSessionId());

		psReadCache.executeQuery();
		rs = psReadCache.getResultSet();
		rec = null;

		try {
			do {
				rec = build(rs);
				if (rec != null && rec.getHttpMessage().equals(reqMsg) && rec.getHttpMessage().getResponseHeader().getStatusCode() != HttpStatusCode.NOT_MODIFIED) {
					return rec;
				}

			} while (rec != null);

		} finally {
			try {
				rs.close();
				psReadCache.close();
			} catch (Exception e) {
				// ZAP: Log exceptions
            	log.warn(e.getMessage(), e);
			}

		}

		return null;
	}

	public void updateTag(int historyId, String tag) throws SQLException {
		psUpdateTag.setString(1, tag);
		psUpdateTag.setInt(2, historyId);
		psUpdateTag.execute();
	}

	public void updateFlag(int historyId, boolean flag) throws SQLException {
		psUpdateFlag.setBoolean(1, flag);
		psUpdateFlag.setInt(2, historyId);
		psUpdateFlag.execute();
	}
	
	public void updateNote(int historyId, String note) throws SQLException {
        psUpdateNote.setString(1, note);
        psUpdateNote.setInt(2, historyId);
        psUpdateNote.execute();
    }

}
