/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
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

import java.sql.SQLException;
import java.util.Vector;

import org.parosproxy.paros.core.spider.SpiderListener;


public class Database {

	private static Database database = null;

	private DatabaseServer databaseServer = null;
	private TableHistory tableHistory = null;
	private TableSession tableSession = null;
	private TableAlert tableAlert = null;
	private TableScan tableScan = null;
	// ZAP: Added TableTag
	private TableTag tableTag = null;

	private Vector<DatabaseListener> listenerList = new Vector<DatabaseListener>();

	public Database() {
		tableHistory = new TableHistory();
		tableSession = new TableSession();
		tableAlert = new TableAlert();
		tableScan = new TableScan();
		tableTag = new TableTag();
		addDatabaseListener(tableHistory);
		addDatabaseListener(tableSession);
		addDatabaseListener(tableAlert);
		addDatabaseListener(tableScan);
		addDatabaseListener(tableTag);
	}

	/**
	 * @return Returns the databaseServer.
	 */
	public DatabaseServer getDatabaseServer() {
		return databaseServer;
	}

	/**
	 * @param databaseServer
	 *            The databaseServer to set.
	 */
	private void setDatabaseServer(DatabaseServer databaseServer) {
		this.databaseServer = databaseServer;
	}

	public TableHistory getTableHistory() {
		return tableHistory;
	}

	/**
	 * @return Returns the tableSession.
	 */
	public TableSession getTableSession() {
		return tableSession;
	}

	public static Database getSingleton() {
		if (database == null) {
			database = new Database();
		}

		return database;
	}

	public void addDatabaseListener(DatabaseListener listener) {
		listenerList.add(listener);

	}

	public void removeDatabaseListener(SpiderListener listener) {
		listenerList.remove(listener);
	}

	private void notifyListenerDatabaseOpen() throws SQLException {
		DatabaseListener listener = null;

		for (int i = 0; i < listenerList.size(); i++) {
			listener = (DatabaseListener) listenerList.get(i);
			listener.databaseOpen(getDatabaseServer());
		}
	}

	public void open(String path) throws ClassNotFoundException, Exception {
		setDatabaseServer(new DatabaseServer(path));
		notifyListenerDatabaseOpen();
	}

	public void close(boolean compact) {
		if (databaseServer == null)
			return;

		try {
			// perform clean up
			getTableHistory().deleteTemporary();

			// shutdown
			getDatabaseServer().shutdown(compact);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the tableAlert.
	 */
	public TableAlert getTableAlert() {
		return tableAlert;
	}

	/**
	 * @param tableAlert
	 *            The tableAlert to set.
	 */
	public void setTableAlert(TableAlert tableAlert) {
		this.tableAlert = tableAlert;
	}

	/**
	 * @return Returns the tableScan.
	 */
	public TableScan getTableScan() {
		return tableScan;
	}

	/**
	 * @param tableScan
	 *            The tableScan to set.
	 */
	public void setTableScan(TableScan tableScan) {
		this.tableScan = tableScan;
	}
	
	/**
	 * @return Returns the tableTag.
	 */
	public TableTag getTableTag() {
		return tableTag;
	}

	/**
	 * @param tableTag
	 *            The tableTag to set.
	 */
	public void setTableTag(TableTag tableTag) {
		this.tableTag = tableTag;
	}
}
