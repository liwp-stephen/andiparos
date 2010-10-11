/*
 * Copyright (C) 2010, Andiparos Project, Axel Neumann
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

package org.parosproxy.paros.extension.scanner;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;

import org.parosproxy.paros.extension.ExtensionPopupMenu;
import org.parosproxy.paros.model.SiteNode;


public class PopupMenuScan extends ExtensionPopupMenu {

	private static final long serialVersionUID = 7713033401025997706L;

	private ExtensionScanner extension = null;
    private JTree treeSite = null;
    

    public PopupMenuScan() {
        super();
 		initialize();
    }

    /**
     * @param label
     */
    public PopupMenuScan(String label) {
        super(label);
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setText("Scan this site...");
        this.addActionListener(new ActionListener() {       	
        	public void actionPerformed(ActionEvent e) {    
        		if (treeSite != null) {
        		    SiteNode node = (SiteNode) treeSite.getLastSelectedPathComponent();
	                if (node.isRoot()) {
	                	// Placeholder for adding a warning
	                	extension.startScan(node);
	                } else {
	        			extension.startScan(node);     
	                }
        		}
        	}
        });
	}

	public boolean isEnableForComponent(Component invoker) {
		treeSite = getTree(invoker);
		if (treeSite != null) {
			SiteNode node = (SiteNode) treeSite.getLastSelectedPathComponent();
			if (node != null) {
				this.setEnabled(true);
			} else {
				this.setEnabled(false);
			}
			return true;
		}
		return false;
	}

	private JTree getTree(Component invoker) {
		if (invoker instanceof JTree) {
			JTree tree = (JTree) invoker;
			if (tree.getName().equals("treeSite")) {
				return tree;
			}
		}

		return null;
	}
	
	void setExtension(ExtensionScanner extension) {
        this.extension = extension;
    }
}
