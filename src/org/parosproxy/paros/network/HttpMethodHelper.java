/*
 * Created on May 26, 2004
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
package org.parosproxy.paros.network;

import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class HttpMethodHelper {

	private static final String OPTIONS = "OPTIONS";
	private static final String GET = "GET";
	private static final String HEAD = "HEAD";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";
	private static final String TRACE = "TRACE";

	private static final String CRLF = "\r\n";
	private static final String LF = "\n";
	private static final Pattern patternCRLF = Pattern.compile("\\r\\n", Pattern.MULTILINE);
	private static final Pattern patternLF = Pattern.compile("\\n",	Pattern.MULTILINE);

	private String mUserAgent = "";

	public void setUserAgent(String userAgent) {
		mUserAgent = userAgent;
	}

	// Not used - all abstract using Generic method but GET cannot be used.
	public HttpMethod createRequestMethodNew(HttpRequestHeader header,
			HttpBody body) throws URIException {
		HttpMethod httpMethod = null;

		String method = header.getMethod();
		URI uri = header.getURI();
		String version = header.getVersion();

		httpMethod = new GenericMethod(method);

		httpMethod.setURI(uri);
		HttpMethodParams httpParams = httpMethod.getParams();
		// default to use HTTP 1.0
		httpParams.setVersion(HttpVersion.HTTP_1_0);
		if (version.equalsIgnoreCase(HttpHeader.HTTP11)) {
			httpParams.setVersion(HttpVersion.HTTP_1_1);
		}

		// set various headers
		int pos = 0;
		// ZAP: FindBugs fix - always initialise pattern
		Pattern pattern = patternCRLF;
		String delimiter = CRLF;

		String msg = header.getHeadersAsString();
		if ((pos = msg.indexOf(CRLF)) < 0) {
			if ((pos = msg.indexOf(LF)) < 0) {
				delimiter = LF;
				pattern = patternLF;
			}
		} else {
			delimiter = CRLF;
			pattern = patternCRLF;
		}

		String[] split = pattern.split(msg);
		String token = null;
		String name = null;
		String value = null;
		String host = null;

		for (int i = 0; i < split.length; i++) {
			token = split[i];
			if (token.equals("")) {
				continue;
			}

			if ((pos = token.indexOf(":")) < 0) {
				return null;
			}
			name = token.substring(0, pos).trim();
			value = token.substring(pos + 1).trim();
			httpMethod.addRequestHeader(name, value);

		}

		// set body if post method or put method
		if (body != null && body.length() > 0) {
			EntityEnclosingMethod generic = (EntityEnclosingMethod) httpMethod;
			// generic.setRequestEntity(new
			// StringRequestEntity(body.toString()));
			generic
					.setRequestEntity(new ByteArrayRequestEntity(body
							.getBytes()));

		}

		httpMethod.setFollowRedirects(false);
		return httpMethod;

	}

	// This is the currently in use method.
	// may be replaced by the New method - however the New method is not yet
	// fully tested so this is still used.
	public HttpMethod createRequestMethod(HttpRequestHeader header,
			HttpBody body) throws URIException {
		HttpMethod httpMethod = null;

		String method = header.getMethod();
		URI uri = header.getURI();
		String version = header.getVersion();

		if (method.equalsIgnoreCase(GET)) {
			httpMethod = new GetMethod();
		} else if (method.equalsIgnoreCase(POST)) {
			httpMethod = new PostMethod();
		} else if (method.equalsIgnoreCase(DELETE)) {
			httpMethod = new DeleteMethod();
		} else if (method.equalsIgnoreCase(PUT)) {
			httpMethod = new PutMethod();
		} else if (method.equalsIgnoreCase(HEAD)) {
			httpMethod = new HeadMethod();
		} else if (method.equalsIgnoreCase(OPTIONS)) {
			httpMethod = new OptionsMethod();
		} else if (method.equalsIgnoreCase(TRACE)) {
			httpMethod = new TraceMethod(uri.toString());
		} else {
			httpMethod = new GenericMethod(method);
		}

		httpMethod.setURI(uri);
		HttpMethodParams httpParams = httpMethod.getParams();
		// default to use HTTP 1.0
		httpParams.setVersion(HttpVersion.HTTP_1_0);
		if (version.equalsIgnoreCase(HttpHeader.HTTP11)) {
			httpParams.setVersion(HttpVersion.HTTP_1_1);
		}

		// set various headers
		int pos = 0;
		Pattern pattern = null;
		String delimiter = CRLF;

		String msg = header.getHeadersAsString();
		if ((pos = msg.indexOf(CRLF)) < 0) {
			if ((pos = msg.indexOf(LF)) < 0) {
				delimiter = LF;
				pattern = patternLF;
			}
		} else {
			delimiter = CRLF;
			pattern = patternCRLF;
		}

		String[] split = pattern.split(msg);
		String token = null;
		String name = null;
		String value = null;

		for (int i = 0; i < split.length; i++) {
			token = split[i];
			if (token.equals("")) {
				continue;
			}

			if ((pos = token.indexOf(":")) < 0) {
				return null;
			}
			name = token.substring(0, pos).trim();
			value = token.substring(pos + 1).trim();
			httpMethod.addRequestHeader(name, value);

		}

		// set body if post method or put method
		if (body != null
				&& body.length() > 0
				&& (httpMethod instanceof PostMethod || httpMethod instanceof PutMethod)) {
			EntityEnclosingMethod post = (EntityEnclosingMethod) httpMethod;
			// post.setRequestEntity(new StringRequestEntity(body.toString()));
			post.setRequestEntity(new ByteArrayRequestEntity(body.getBytes()));

		}

		httpMethod.setFollowRedirects(false);
		return httpMethod;

	}

	public static void updateHttpRequestHeaderSent(HttpRequestHeader req,
			HttpMethod httpMethodSent) {
		StringBuffer sb = new StringBuffer(200);
		String name = null;
		String value = null;

		// Not used yet, no need to update request.
		if (!httpMethodSent.hasBeenUsed()) {
			return;
		}

		// add status line
		sb.append(req.getPrimeHeader() + CRLF);

		Header[] header = httpMethodSent.getRequestHeaders();
		for (int i = 0; i < header.length; i++) {
			name = header[i].getName();
			value = header[i].getValue();
			sb.append(name + ": " + value + CRLF);
		}

		sb.append(CRLF);
		try {
			req.setMessage(sb.toString());
		} catch (HttpMalformedHeaderException e) {
			e.printStackTrace();
		}
	}

	private static String getHttpResponseHeaderAsString(HttpMethod httpMethod) {
		StringBuffer sb = new StringBuffer(200);
		String name = null;
		String value = null;

		// add status line
		sb.append(httpMethod.getStatusLine().toString() + CRLF);

		Header[] header = httpMethod.getResponseHeaders();
		for (int i = 0; i < header.length; i++) {
			name = header[i].getName();
			value = header[i].getValue();
			sb.append(name + ": " + value + CRLF);
		}

		sb.append(CRLF);
		return sb.toString();
	}

	public static HttpResponseHeader getHttpResponseHeader(HttpMethod httpMethod)
			throws HttpMalformedHeaderException {
		return new HttpResponseHeader(getHttpResponseHeaderAsString(httpMethod));
	}

	/*
	 * public static String getHttpRequestHeaderAsString(HttpMethod httpMethod)
	 * { StringBuffer sb = new StringBuffer(200); String name = null; String
	 * value = null;
	 * 
	 * // add status line try { sb.append(httpMethod.getName() + " " +
	 * httpMethod.getURI().toString() + " " +
	 * httpMethod.getParams().getVersion() + CRLF); } catch (URIException e) {
	 * 
	 * }
	 * 
	 * Header[] header = httpMethod.getRequestHeaders(); for (int i=0;
	 * i<header.length; i++) { name = header[i].getName(); value =
	 * header[i].getValue(); sb.append(name + ": " + value + CRLF); }
	 * 
	 * sb.append(CRLF); return sb.toString(); }
	 */

}
