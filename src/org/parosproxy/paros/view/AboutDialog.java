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

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.parosproxy.paros.extension.AbstractDialog;

public class AboutDialog extends AbstractDialog {

	private static final long serialVersionUID = -3404620776843070107L;
	
	private JPanel jPanel = null;
	private AboutPanel aboutPanel = null;
	private JButton btnOK = null;

	
	public AboutDialog() throws HeadlessException {
		super();
		initialize();
	}

	/**
	 * @param frame
	 * @param bool
	 * @throws HeadlessException
	 */
	public AboutDialog(Frame frame, boolean bool) throws HeadlessException {
		super(frame, bool);
		initialize();
	}


	private void initialize() {
		this.setContentPane(getJPanel());
		this.setResizable(false);
		this.pack();
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			
			GridBagConstraints gbcSpace = new GridBagConstraints();
			GridBagConstraints gbcAbout = new GridBagConstraints();

			gbcAbout.gridx = 0;
			gbcAbout.gridy = 0;
			gbcAbout.weightx = 1.0D;
			gbcAbout.weighty = 1.0D;
			gbcAbout.ipady = 2;
			gbcAbout.gridwidth = 2;
			gbcAbout.insets = new Insets(0, 0, 0, 0);
			gbcAbout.anchor = GridBagConstraints.NORTHWEST;
			gbcAbout.fill = GridBagConstraints.BOTH;
			
			gbcSpace.gridx = 1;
			gbcSpace.gridy = 1;
			gbcSpace.insets = new Insets(2, 2, 2, 2);
			gbcSpace.anchor = GridBagConstraints.SOUTHEAST;
			
			jPanel.add(getAboutPanel(), gbcAbout);
			jPanel.add(getBtnOK(), gbcSpace);
		}
		return jPanel;
	}


	private AboutPanel getAboutPanel() {
		if (aboutPanel == null) {
			aboutPanel = new AboutPanel();
		}
		return aboutPanel;
	}

	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("OK");
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AboutDialog.this.dispose();
				}
			});
		}
		return btnOK;
	}
}
