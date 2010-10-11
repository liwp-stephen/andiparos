/*
 * Copyright (C) 2010, Axel Neumann <axel.neumann@csnc.ch>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/copyleft/
 * 
 */

package org.parosproxy.paros.view;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.parosproxy.paros.extension.ExtensionPopupMenu;
import org.parosproxy.paros.model.SiteMap;
import org.parosproxy.paros.model.SiteNode;
import org.parosproxy.paros.network.HttpMessage;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PopupCopyUrlToClipboardMenu extends ExtensionPopupMenu {

	private static final long serialVersionUID = 2282358266003940700L;
	
	private Component invoker = null;

	/**
	 * This method initializes
	 * 
	 */
	public PopupCopyUrlToClipboardMenu() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setText("Copy URI to Clipboard");
		this.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (invoker.getName().equals("treeSite")) {
					
					JTree tree = (JTree) invoker;
					TreePath[] paths = tree.getSelectionPaths();
					SiteMap map = (SiteMap) tree.getModel();
					for (int i = 0; i < paths.length; i++) {
						SiteNode node = (SiteNode) paths[i].getLastPathComponent();
						
						// Andiparos: Root should not be copied to Clipboard
						if (!node.isRoot()) {
							copyToClipboard(node);
						}
					}
				}

			}
		});

	}

	public boolean isEnableForComponent(Component invoker) {
		if (invoker.getName() != null && invoker.getName().equals("treeSite")) {
			this.invoker = invoker;
			return true;
		} else {
			this.invoker = null;
			return false;
		}

	}

	private static void copyToClipboard(SiteNode node) {
		
		try {
			HttpMessage msg = node.getHistoryReference().getHttpMessage();
			if (msg != null) {
				String tmp = msg.getRequestHeader().getURI().toString();
				setClipboard(tmp);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	private static void setClipboard(String str) {
		StringSelection sStr = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sStr, null);
	} 
	
}
