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
package org.parosproxy.paros.view;

import java.awt.Cursor;


public class AboutWindow extends AbstractFrame {

	private static final long serialVersionUID = 8137677407478367715L;
	
	private AboutPanel aboutPanel = null;

	public AboutWindow() {
		super();
		initialize();
	}

	private void initialize() {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		this.setResizable(false);
		this.setContentPane(getAboutPanel());
		this.pack();
		centerFrame();

	}

	private AboutPanel getAboutPanel() {
		if (aboutPanel == null) {
			aboutPanel = new AboutPanel();
		}
		return aboutPanel;
	}
}
