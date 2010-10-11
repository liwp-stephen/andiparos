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
package org.parosproxy.paros.extension.scanner;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;

import org.parosproxy.paros.core.scanner.HostProcess;

public class HostProgressMeter extends JPanel {

	private static final long serialVersionUID = 8560609413240466630L;

	private JLabel txtHost = null;
	private JProgressBar barProgress = null;
	private JButton btnStop = null;
	private JButton btnSkip = null;
	private JLabel txtDisplay = null;
	private HostProcess hostProcess = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanelButtons = null;

	/**
	 * This is the default constructor
	 */
	public HostProgressMeter() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

		JLabel jLabel = new JLabel();

		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

		this.setLayout(new GridBagLayout());
		this.setSize(380, 76);
		this.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new Insets(2, 2, 2, 5);
		gridBagConstraints1.anchor = GridBagConstraints.NORTH;
		
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.insets = new Insets(2, 5, 2, 2);
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.weightx = 1.0D;
		gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints2.gridwidth = 2;
		
		jLabel.setText("Host:");
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 0;
		gridBagConstraints4.insets = new Insets(2, 5, 2, 5);
		gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
		
		gridBagConstraints5.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints5.gridx = 2;
		gridBagConstraints5.gridy = 1;
		gridBagConstraints5.insets = new Insets(2, 2, 2, 5);

		
		gridBagConstraints12.weightx = 1.0;
		gridBagConstraints12.weighty = 0.0D;
		gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints12.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints12.gridwidth = 2;
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.gridy = 2;
		gridBagConstraints12.insets = new Insets(2, 5, 2, 5);
		
		this.add(jLabel, gridBagConstraints4);
		this.add(getTxtHost(), gridBagConstraints1);
		this.add(getJPanelButtons(), gridBagConstraints5);
		this.add(getBarProgress(), gridBagConstraints2);
		this.add(getJScrollPane(), gridBagConstraints12);
	}

	/**
	 * This method initializes jPanelButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			GridLayout gl = new GridLayout();
			jPanelButtons.setLayout(gl);
			
			gl.setRows(1);
			gl.setColumns(2);
			gl.setVgap(3);
			gl.setHgap(3);
			
			jPanelButtons.add(getBtnSkip(), null);
			jPanelButtons.add(getBtnStop(), null);
			
		}
		return jPanelButtons;
	}
	
	/**
	 * This method initializes txtHost
	 * 
	 * @return JTextField
	 */
	JLabel getTxtHost() {
		if (txtHost == null) {
			txtHost = new JLabel("    ");
		}
		return txtHost;
	}

	/**
	 * This method initializes barProgress
	 * 
	 * @return JProgressBar
	 */
	private JProgressBar getBarProgress() {
		if (barProgress == null) {
			barProgress = new JProgressBar();
			barProgress.setPreferredSize(new Dimension(150, 20));
		}
		return barProgress;
	}

	/**
	 * This method initializes btnStop
	 * 
	 * @return JButton
	 */
	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton();
			btnStop.setText("Stop");
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hostProcess != null) {
						hostProcess.stop();
					}
					btnStop.setEnabled(false);
				}
			});
		}
		return btnStop;
	}
	
	/**
	 * This method initializes btnSkip
	 * 
	 * @return JButton
	 */
	private JButton getBtnSkip() {
		if (btnSkip == null) {
			btnSkip = new JButton();
			btnSkip.setText("Skip plugin");
			btnSkip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (hostProcess != null) {
						hostProcess.skipPlugin();
					}
				}
			});
		}
		return btnSkip;
	}


	void setProgress(String msg, int percentage) {
		getBarProgress().setValue(percentage);
		getTxtDisplay().setText(msg);
	}

	void setHostProcess(HostProcess hostThread) {
		this.hostProcess = hostThread;
	}

	/**
	 * This method initializes txtDisplay
	 * 
	 * @return JTextField
	 */
	private JLabel getTxtDisplay() {
		if (txtDisplay == null) {
			txtDisplay = new JLabel("    ");
		}
		return txtDisplay;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtDisplay());
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jScrollPane;
	}
}