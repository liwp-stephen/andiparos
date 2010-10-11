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

import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

public class TestMiscExternalRedirect extends AbstractAppParamPlugin {

	private String redirect1 = "http://www.google.com";
	private String redirect2 = "www.google.com";

	public int getId() {
		return 60001;
	}

	public String getName() {
		return "External redirect";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "Arbitrary external redirection can be. A phishing email can make use of this to entice "
			+ "readers to click on the link to redirect readers to bogus sites.";
		return msg;
	}

	public int getCategory() {
		return Category.MISC;
	}

	public String getSolution() {
		String msg = "Only allow redirection within the same web sites; or only allow redirection to designated "
			+ "external URLs.";
		return msg;
	}

	public String getReference() {
		String msg = "Sorry, no references.";
		return msg;
	}
	
	public void init() {

	}

	public void scan(HttpMessage msg, String param, String value) {

		String locationHeader = null;
		String locationHeader2 = null;
		String redirect = "";

		URI uri = null;

		msg = getNewMsg();
		try {
			sendAndReceive(msg, false);
			if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.MOVED_PERMANENTLY
					&& msg.getResponseHeader().getStatusCode() != HttpStatusCode.FOUND) {
				// not redirect page, return;
				return;
			}

			locationHeader = msg.getResponseHeader().getHeader(
					HttpHeader.LOCATION);
			if (locationHeader == null) {
				return;
			}

			if (locationHeader.compareToIgnoreCase(value) == 0) {
				// URI found in param
				redirect = redirect1;
			} else if (locationHeader.compareToIgnoreCase(getURLDecode(value)) == 0) {
				redirect = getURLEncode(redirect1);
			}

			if (redirect != null) {
				uri = new URI(locationHeader, true);
				locationHeader2 = uri.getPathQuery();
				if (locationHeader2.compareToIgnoreCase(value) == 0) {
					// path and query found in param
					redirect = redirect2;
				} else if (locationHeader2
						.compareToIgnoreCase(getURLDecode(value)) == 0) {
					redirect = getURLEncode(redirect2);
				}
			}

			if (redirect == null) {
				return;
			}

		} catch (Exception e) {

		}

		msg = getNewMsg();
		setParameter(msg, param, redirect);
		try {
			sendAndReceive(msg, false);
			if (checkResult(msg, param + "=" + redirect)) {
				return;
			}

		} catch (Exception e) {

		}

	}

	private boolean checkResult(HttpMessage msg, String query) {
		if (msg.getResponseHeader().getStatusCode() != HttpStatusCode.MOVED_PERMANENTLY
				&& msg.getResponseHeader().getStatusCode() != HttpStatusCode.FOUND) {
			// not redirect page, return;
			return false;
		}

		String locationHeader = msg.getResponseHeader().getHeader(
				HttpHeader.LOCATION);
		if (locationHeader != null && locationHeader.startsWith(redirect1)) {
			bingo(Alert.RISK_MEDIUM, Alert.WARNING, null, query, "", msg);
			return true;
		}

		return false;

	}

	/*
	public boolean isVisible() {
		return Constant.isSP();
	}*/

}
