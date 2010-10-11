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
package org.parosproxy.paros.extension.manualrequest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.apache.commons.httpclient.URI;
import org.apache.log4j.Logger;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.parosproxy.paros.extension.history.ManualRequestEditorDialog;
import org.parosproxy.paros.model.HistoryList;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;


public class ExtensionManualRequestEditor extends ExtensionAdaptor {

	private ManualRequestEditorDialog manualRequestEditorDialog = null;
	private JMenuItem menuManualRequestEditor = null;
	private HistoryList historyList = null;
	// ZAP Added logger
	private Logger logger = Logger.getLogger(ExtensionManualRequestEditor.class);
	
	public ExtensionManualRequestEditor() {
		super();
		initialize();
	}

	public ExtensionManualRequestEditor(String name) {
		super(name);
	}

	private void initialize() {
		this.setName("ExtensionManualRequest");
	}

	public HistoryList getHistoryList() {
		if (historyList == null) {
			historyList = new HistoryList();
		}
		return historyList;
	}
	
	
	public void hook(ExtensionHook extensionHook) {
		super.hook(extensionHook);
		if (getView() != null) {
			extensionHook.getHookView();
			extensionHook.getHookMenu().addToolsMenuItem(getMenuManualRequestEditor());
		}
	}

	private JMenuItem getMenuManualRequestEditor() {
		if (menuManualRequestEditor == null) {
			menuManualRequestEditor = new JMenuItem();
			menuManualRequestEditor.setText("Manual Request Editor...");
			menuManualRequestEditor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ManualRequestEditorDialog dialog = getManualRequestEditorDialog();
					if (dialog.getRequestPanel().getTxtHeader().getText().equals("")) {
						HttpMessage msg = new HttpMessage();
						try {
							URI uri = new URI("http://www.example.com/path", true);
							msg.setRequestHeader(new HttpRequestHeader(HttpRequestHeader.GET, uri, HttpHeader.HTTP10));
							dialog.getRequestPanel().setMessage(msg, true);
						} catch (Exception e1) {
							// ZAP: Log the exception
                        	logger.error(e1.getMessage(), e1);
						}
					}
					dialog.setVisible(true);
				}
			});
		}
		return menuManualRequestEditor;
	}

	ManualRequestEditorDialog getManualRequestEditorDialog() {
		if (manualRequestEditorDialog == null) {
			manualRequestEditorDialog = new ManualRequestEditorDialog(getView().getMainFrame(), false, true, this);
			manualRequestEditorDialog.setSize(500, 600);
			manualRequestEditorDialog.setTitle("Manual Request Editor");
		}
		return manualRequestEditorDialog;
	}
	
	

}
