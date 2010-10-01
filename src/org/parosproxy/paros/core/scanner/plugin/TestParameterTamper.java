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
package org.parosproxy.paros.core.scanner.plugin;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.AbstractPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

public class TestParameterTamper extends AbstractAppParamPlugin {
	
	// ZAP: Added logger
    private static Log log = LogFactory.getLog(TestParameterTamper.class);

	private static String[] PARAM_LIST = { "", "", "@", "+", AbstractPlugin.getURLDecode("%00"), "|" };

	private static Pattern patternErrorJava1 = Pattern.compile(
		"javax\\.servlet\\.\\S+", PATTERN_PARAM);
	private static Pattern patternErrorJava2 = Pattern.compile(
		"invoke.+exception|exception.+invoke", PATTERN_PARAM);

	private static Pattern patternErrorVBScript = Pattern.compile(
		"Microsoft(\\s+|&nbsp)*VBScript(\\s+|&nbsp)+error", PATTERN_PARAM);
	private static Pattern patternErrorODBC1 = Pattern.compile(
		"Microsoft OLE DB Provider for ODBC Drivers.*error", PATTERN_PARAM);
	private static Pattern patternErrorODBC2 = Pattern.compile(
		"ODBC.*Drivers.*error", PATTERN_PARAM);
	private static Pattern patternErrorJet = Pattern.compile(
		"Microsoft JET Database Engine.*error", PATTERN_PARAM);
	private static Pattern patternErrorPHP = Pattern.compile(
		" on line <b>", PATTERN_PARAM);
	private static Pattern patternErrorTomcat = Pattern.compile(
		"(Apache Tomcat).*(^Caused by:|HTTP Status 500 - Internal Server Error)", PATTERN_PARAM);

	public int getId() {
		return 40005;
	}

	public String getName() {
		return "Parameter tampering";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "Certain parameter caused error page or Java stacktrace to be displayed. "
			+ "This indicated lack of exception handling and potential areas for further exploit.";
		return msg;
	}

	public int getCategory() {
		return Category.HTML_INJECTION;
	}

	public String getSolution() {
		String msg = "Identify the cause of the error and fix it. Do not trust client side input "
			+ "and enforce tight check in the server side. Besides, catch the exception properly. "
			+ "Use a generic 500 error page for internal server error.";
		return msg;
	}

	public String getReference() {
		return "";

	}


	public void init() {

	}

	public void scan(HttpMessage msg, String param, String value) {

		String bingoQuery = null;

		// always try normal query first
		HttpMessage normalMsg = getNewMsg();

		try {
			sendAndReceive(normalMsg);
		} catch (Exception e) {
			// ZAP: Log exceptions
        	log.warn(e.getMessage(), e);
			return;
		}

		if (normalMsg.getResponseHeader().getStatusCode() != HttpStatusCode.OK) {
			return;
		}

		for (int i = 0; i < PARAM_LIST.length; i++) {
			msg = getNewMsg();
			if (i == 0) {
				// remove entire parameter when i=0;
				bingoQuery = setParameter(msg, null, null);
			} else {
				bingoQuery = setParameter(msg, param, PARAM_LIST[i]);

			}
			try {
				sendAndReceive(msg);
				if (checkResult(msg, bingoQuery, normalMsg.getResponseBody()
						.toString())) {
					return;
				}

			} catch (Exception e) {
				// ZAP: Log exceptions
	        	log.warn(e.getMessage(), e);
			}

		}

	}

	private boolean checkResult(HttpMessage msg, String query,
			String normalHTTPResponse) {

		StringBuffer sb = new StringBuffer();

		if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.OK
				&& !HttpStatusCode.isServerError(msg.getResponseHeader()
						.getStatusCode())) {
			return false;
		}

		// remove false positive if parameter have no effect on output
		if (msg.getResponseBody().toString().equals(normalHTTPResponse)) {
			return false;
		}

		if (matchBodyPattern(msg, patternErrorJava1, sb)
				&& matchBodyPattern(msg, patternErrorJava2, null)) {

			bingo(Alert.RISK_MEDIUM, Alert.WARNING, null,
					(query == null || query.length() == 0) ? "nil" : query, sb
							.toString(), msg);
			return true;
		} else if (matchBodyPattern(msg, patternErrorVBScript, sb)
				|| matchBodyPattern(msg, patternErrorODBC1, sb)
				|| matchBodyPattern(msg, patternErrorODBC2, sb)
				|| matchBodyPattern(msg, patternErrorJet, sb)
				|| matchBodyPattern(msg, patternErrorTomcat, sb)
				|| matchBodyPattern(msg, patternErrorPHP, sb)) {
			bingo(Alert.RISK_MEDIUM, Alert.SUSPICIOUS, "",
					(query == null || query.length() == 0) ? "nil" : query, sb
							.toString(), msg);

			return true;
		}

		return false;

	}

}
