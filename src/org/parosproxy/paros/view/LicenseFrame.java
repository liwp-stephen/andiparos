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

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.parosproxy.paros.Constant;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class LicenseFrame extends AbstractFrame {

	private static final long serialVersionUID = -8463527680384145499L;
	
	private static String TITLE = "<html><body><font size=+1><p>"
		+ Constant.PROGRAM_NAME + " " + Constant.PROGRAM_VERSION
		+ "</p><p>"
		+ "License Agreement: " + Constant.PROGRAM_LICENSE 
		+ "</p></font><p>"
		+ "For the other libraries included in " + Constant.PROGRAM_NAME
		+ ", please refer to respective licenses of the libraries enclosed "
		+ "with this package."
		+ "</p></body></html>";
	
	private JPanel jPanel = null;
	private JTextPane txtLicense = null;
	private JPanel jPanel1 = null;
	private JButton btnAccept = null;
	private JButton btnDecline = null;
	private JScrollPane jScrollPane = null;

	private int currentPage = 0;
	private boolean accepted = false;

	private JPanel jPanel2 = null;

	/**
     * 
     */
	public LicenseFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setSize(500, 375);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				btnDecline.doClick();
			}
		});

		showLicense(currentPage);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			
			JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridwidth = 1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.weighty = 1.0D;
			jLabel.setText(TITLE);
			gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 2;
			jPanel.add(jLabel, gridBagConstraints11);
			jPanel.add(getJPanel1(), gridBagConstraints1);
			jPanel.add(getJPanel2(), gridBagConstraints12);
		}
		return jPanel;
	}


	private JTextPane getTxtLicense() {
		if (txtLicense == null) {
			txtLicense = new JTextPane();
			txtLicense.setName("txtLicense");
			txtLicense.setEditable(false);
		}
		return txtLicense;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new CardLayout());
			jPanel1.add(getJScrollPane(), getJScrollPane().getName());
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnAccept
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAccept() {
		if (btnAccept == null) {
			btnAccept = new JButton();
			btnAccept.setText("Accept");
			btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accepted = true;
					LicenseFrame.this.dispose();
				}
			});

		}
		return btnAccept;
	}

	private JButton getBtnDecline() {
		if (btnDecline == null) {
			btnDecline = new JButton();
			btnDecline.setText("Decline");
			btnDecline.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accepted = false;
					System.exit(1);
				}
			});

		}
		return btnDecline;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtLicense());
			jScrollPane.setName("jScrollPane");
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return jScrollPane;
	}

	private void showLicense(int page) {

		String localUrl = null;
		switch (page) {
		case 0:
			localUrl = "file:" + System.getProperty("user.dir")
					+ Constant.FILE_SEPARATOR
					+ "license/gpl-2.0.txt";
			break;
		}
		try {
			txtLicense.setPage(localUrl);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(),
					"Error: setting file is missing. Program will exit.");
			System.exit(0);
		}
	}

	public void setVisible(boolean show) {
		centerFrame();
		super.setVisible(show);
	}

	public boolean isAccepted() {
		return accepted;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.add(getBtnAccept(), null);
			jPanel2.add(getBtnDecline(), null);
		}
		return jPanel2;
	}
}
