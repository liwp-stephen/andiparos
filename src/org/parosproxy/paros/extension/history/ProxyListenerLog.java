/*
 * Created on Jun 17, 2004
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
package org.parosproxy.paros.extension.history;

import java.awt.EventQueue;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.extension.ViewDelegate;
import org.parosproxy.paros.model.HistoryList;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpStatusCode;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ProxyListenerLog implements ProxyListener {

	private ViewDelegate view = null;
	private Model model = null;
	private HistoryList historyList = null;
	private Pattern pattern = null;
	// TODO Remove
	private Pattern uriFilterPattern = null;
	private Pattern methodFilterPattern = null;
	private boolean uriFilterPatternInverse = false;
	
	private boolean isFirstAccess = true;
	// ZAP: filter log using a HistoryFilter
	private HistoryFilter historyFilter = null;
	
	// ZAP: Added logger
    private static Log log = LogFactory.getLog(ProxyListenerLog.class);

	public ProxyListenerLog(Model model, ViewDelegate view, HistoryList historyList) {
		this.model = model;
		this.view = view;
		this.historyList = historyList;
	}

	public void setFilter(String filter) {
		if (filter == null || filter.equals("")) {
			pattern = null;
		} else {
			pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		}
	}
	
	public void setHistoryFilter (HistoryFilter historyFilter) {
		this.historyFilter = historyFilter;
	}

	// AXEL: New HTTP method and URI filter
	
	public void setUriFilter(String[] filter, boolean uriFilterInverse) {
		// HTTP method filter
		if (filter[0] == "ALL" || filter[0].equals("")) {
			methodFilterPattern = null;
		} else {
			methodFilterPattern = Pattern.compile(filter[0]);
		}
		
		// URI filter
		if (filter[1] == null || filter[1].equals("")) {
			uriFilterPattern = null;
		} else {
			uriFilterPattern = Pattern.compile(filter[1], Pattern.CASE_INSENSITIVE);
		}

		// Pattern inversion
		if (uriFilterInverse == true) {
			uriFilterPatternInverse = true;
		} else {
			uriFilterPatternInverse = false;
		}
	}

	public void onHttpRequestSend(HttpMessage msg) {

		HttpMessage existingMsg = model.getSession().getSiteTree().pollPath(msg);

		// check if a msg of the same type exist
		if (existingMsg != null && !existingMsg.getResponseHeader().isEmpty()) {
			if (HttpStatusCode.isSuccess(existingMsg.getResponseHeader().getStatusCode())) {
				// exist, no modification necessary
				return;
			}
		}

		// if not, make sure a new copy will be obtained
		if (msg.getRequestHeader().getHeader(HttpHeader.IF_MODIFIED_SINCE) != null) {
			msg.getRequestHeader().setHeader(HttpHeader.IF_MODIFIED_SINCE, null);
		}

		if (msg.getRequestHeader().getHeader(HttpHeader.IF_NONE_MATCH) != null) {
			msg.getRequestHeader().setHeader(HttpHeader.IF_NONE_MATCH, null);
		}
	}

	public void onHttpResponseReceive(final HttpMessage msg) {
		int type = HistoryReference.TYPE_MANUAL;
		if (isSkipImage(msg.getRequestHeader()) || isSkipImage(msg.getResponseHeader())) {
			if (msg.getResponseHeader().getStatusCode() == HttpStatusCode.OK) {
				type = HistoryReference.TYPE_HIDDEN;
			} else {
				return;
			}
		}

		final int finalType = type;
		Thread t = new Thread(new Runnable() {
			public void run() {
				addHistory(msg, finalType);
			}
		});
		t.start();
	}

	public boolean isSkipImage(HttpHeader header) {
		if (header.isImage() && !model.getOptionsParam().getViewParam().isProcessImages()) {
			return true;
		}

		return false;

	}

	private void addHistory(HttpMessage msg, int type) {
		HistoryReference historyRef = null;
		try {
			historyRef = new HistoryReference(model.getSession(), type, msg);
		} catch (Exception e) {
			// ZAP: Log exceptions
        	log.warn(e.getMessage(), e);
			return;
		}

		if (type != HistoryReference.TYPE_MANUAL && type != HistoryReference.TYPE_HIDDEN) {
			return;
		}

		// add history to list (log panel). Must use event queue because this
		// proxylistener may not be run from event queue.
		// TODO: AXEL... This code is really dirty... please cleanup
		synchronized (historyList) {
			if (type == HistoryReference.TYPE_MANUAL) {
				
				if (pattern == null && historyFilter == null) {
                    addHistoryInEventQueue(historyRef);
                } else if (historyFilter != null) {
                	if (historyFilter.matches(historyRef)) {
                        addHistoryInEventQueue(historyRef);
                	}
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(msg.getRequestHeader().toString());
                    sb.append(msg.getRequestBody().toString());
                    if (!msg.getResponseHeader().isEmpty()) {
                        sb.append(msg.getResponseHeader().toString());
                        sb.append(msg.getResponseBody().toString());
                        
                    }
                    if (pattern.matcher(sb.toString()).find()) {
                        addHistoryInEventQueue(historyRef);
                    }
                }
				
				//TODO: Old Andiparos Code
				/*
				String uri = msg.getRequestHeader().getURI().toString();
				if (uriFilterPattern != null && pattern != null) {
					if (uriFilterPattern.matcher(uri).find() && ifHeaderPatternMatches(msg, historyRef)) {
						addHistoryInEventQueue(historyRef);
					}
				} else if (uriFilterPattern != null) {
					if (uriFilterPattern.matcher(uri).find()) {
						addHistoryInEventQueue(historyRef);
					}
				} else if (pattern != null) {
					if (ifHeaderPatternMatches(msg, historyRef)) {
						addHistoryInEventQueue(historyRef);
					}
				} else {
					addHistoryInEventQueue(historyRef);
				}*/
				
			}
		}

		// add history to site panel. Must use event queue because this
		// proxylistener may not be run from event queue.
		final HistoryReference ref = historyRef;
		final HttpMessage finalMsg = msg;
		if (EventQueue.isDispatchThread()) {
			model.getSession().getSiteTree().addPath(ref, msg);
			if (isFirstAccess) {
				isFirstAccess = false;
				view.getSiteTreePanel().expandRoot();
			}
		} else {
			try {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						model.getSession().getSiteTree().addPath(ref, finalMsg);
						if (isFirstAccess) {
							isFirstAccess = false;
							view.getSiteTreePanel().expandRoot();
						}
					}
				});
			} catch (Exception e) {
				// ZAP: Log exceptions
            	log.warn(e.getMessage(), e);
			}
		}
	}

	private void addHistoryInEventQueue(final HistoryReference ref) {
		if (EventQueue.isDispatchThread()) {
			historyList.addElement(ref);
		} else {
			try {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						historyList.addElement(ref);
					}
				});
			} catch (Exception e) {
				// ZAP: Log exceptions
            	log.warn(e.getMessage(), e);
			}
		}
	}

	private boolean ifHeaderPatternMatches(HttpMessage msg, HistoryReference historyRef) {
		StringBuffer sb = new StringBuffer();
		sb.append(msg.getRequestHeader().toString());
		sb.append(msg.getRequestBody().toString());
		if (!msg.getResponseHeader().isEmpty()) {
			sb.append(msg.getResponseHeader().toString());
			sb.append(msg.getResponseBody().toString());
		}

		if (pattern.matcher(sb.toString()).find()) {
			return true;
		} else {
			return false;
		}
	}

}
