/*
 * Created on May 18, 2004
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
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

import org.zaproxy.zap.view.MainToolbarPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Font;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class MainFrame extends AbstractFrame {
	private static final long serialVersionUID = -1430550461546083192L;
	
	private JPanel paneContent = null;
	private JLabel txtStatus = null;
	private org.parosproxy.paros.view.WorkbenchPanel paneStandard = null;
	private org.parosproxy.paros.view.MainMenuBar mainMenuBar = null;
	private JPanel paneDisplay = null;
	
	private MainToolbarPanel mainToolbarPanel = null;
	private JToolBar footerToolbarPanel = null;
	private JLabel alertHigh = null;
	private JLabel alertMedium = null;
	private JLabel alertLow = null;
	private JLabel alertInfo = null;
	
	// ZAP Added footer

	/**
	 * This method initializes
	 * 
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getPaneContent());

		this.setSize(1000, 800);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				getMainMenuBar().getMenuFileControl().exit();
			}
		});

		this.setVisible(false);
	}

	/**
	 * 
	 * This method initializes paneContent
	 * 
	 * @return JPanel
	 */
	private JPanel getPaneContent() {
		if (paneContent == null) {

			paneContent = new JPanel();
			paneContent.setLayout(new BoxLayout(getPaneContent(), BoxLayout.Y_AXIS));
			paneContent.setEnabled(true);
			paneContent.setPreferredSize(new Dimension(800, 600));
			paneContent.setFont(new Font("Dialog", Font.PLAIN, 12));
			// ZAP: Add MainToolbar
			paneContent.add(getMainToolbarPanel(), null);
			
			paneContent.add(getPaneDisplay(), null);
			// ZAP: Remove the status line - its not really used and takes up space
			//paneContent.add(getTxtStatus(), null);
			paneContent.add(getFooterToolbarPanel(), null);
			
		}
		return paneContent;
	}
	
	private JLabel getAlertHigh(int alert) {
		if (alertHigh == null) {
			alertHigh = new JLabel();
			alertHigh.setToolTipText("High priority alerts");

		}
		alertHigh.setText("" + alert);
		return alertHigh;
	}
	
	public void setAlertHigh (int alert) {
		getAlertHigh(alert);
	}

	private JLabel getAlertMedium(int alert) {
		if (alertMedium == null) {
			alertMedium = new JLabel();
			alertMedium.setToolTipText("Medium priority alerts");
		}
		alertMedium.setText("" + alert);
		return alertMedium;
	}
	
	public void setAlertMedium (int alert) {
		getAlertMedium(alert);
	}

	private JLabel getAlertLow(int alert) {
		if (alertLow == null) {
			alertLow = new JLabel();
			alertLow.setToolTipText("Low priority alerts");
		}
		alertLow.setText("" + alert);
		return alertLow;
	}
	
	public void setAlertLow (int alert) {
		getAlertLow(alert);
	}

	private JLabel getAlertInfo(int alert) {
		if (alertInfo == null) {
			alertInfo = new JLabel();
			alertInfo.setToolTipText("Informational alerts");
		}
		alertInfo.setText("" + alert);
		return alertInfo;
	}
	
	public void setAlertInfo (int alert) {
		getAlertInfo(alert);
	}

	private JToolBar getFooterToolbarPanel() {
		if (this.footerToolbarPanel == null) {
			this.footerToolbarPanel = new JToolBar();
			this.footerToolbarPanel.setFloatable(false);
			this.footerToolbarPanel.setEnabled(true);
			
			this.footerToolbarPanel.add(new JLabel("<html>&nbsp;Alerts&nbsp;</html>"));
			
			this.footerToolbarPanel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>"));
			
			JLabel flagHigh = new JLabel("<html>:&nbsp;</html>");
			flagHigh.setToolTipText("High priority alerts");
			ImageIcon iconHigh = new ImageIcon(getClass().getResource("/resource/icons/flag_red.png"));
			flagHigh.setIcon(iconHigh);
			this.footerToolbarPanel.add(flagHigh);
			this.footerToolbarPanel.add(this.getAlertHigh(0));

			this.footerToolbarPanel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>"));

			JLabel flagMedium = new JLabel("<html>:&nbsp;</html>");
			flagMedium.setToolTipText("Medium priority alerts");
			ImageIcon iconMedium = new ImageIcon(getClass().getResource("/resource/icons/flag_orange.png"));
			flagMedium.setIcon(iconMedium);
			this.footerToolbarPanel.add(flagMedium);
			this.footerToolbarPanel.add(this.getAlertMedium(0));

			this.footerToolbarPanel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>"));

			JLabel flagLow = new JLabel("<html>:&nbsp;</html>");
			flagLow.setToolTipText("Low priority alerts");
			ImageIcon iconLow = new ImageIcon(getClass().getResource("/resource/icons/flag_yellow.png"));
			flagLow.setIcon(iconLow);
			this.footerToolbarPanel.add(flagLow);
			this.footerToolbarPanel.add(this.getAlertLow(0));

			this.footerToolbarPanel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>"));

			JLabel flagInfo = new JLabel("<html>:&nbsp;</html>");
			flagInfo.setToolTipText("Informational alerts");
			ImageIcon iconInfo = new ImageIcon(getClass().getResource("/resource/icons/flag_blue.png"));
			flagInfo.setIcon(iconInfo);
			this.footerToolbarPanel.add(flagInfo);
			this.footerToolbarPanel.add(this.getAlertInfo(0));

		}
		return this.footerToolbarPanel;
	}

	/**
	 * 
	 * This method initializes txtStatus
	 * 
	 * 
	 * 
	 * @return JLabel
	 */
	private JLabel getTxtStatus() {
		if (txtStatus == null) {
			txtStatus = new JLabel();
			txtStatus.setName("txtStatus");
			txtStatus.setText("Initializing...");
			txtStatus.setHorizontalAlignment(SwingConstants.LEFT);
			txtStatus.setHorizontalTextPosition(SwingConstants.LEFT);
			txtStatus.setPreferredSize(new Dimension(800, 18));
		}
		return txtStatus;
	}

	/**
	 * 
	 * This method initializes paneStandard
	 * 
	 * 
	 * 
	 * @return com.proofsecure.paros.view.StandardPanel
	 */
	org.parosproxy.paros.view.WorkbenchPanel getWorkbench() {
		if (paneStandard == null) {
			paneStandard = new org.parosproxy.paros.view.WorkbenchPanel();
			paneStandard.setLayout(new CardLayout());
			paneStandard.setName("paneStandard");
		}
		return paneStandard;
	}

	/**
	 * 
	 * This method initializes mainMenuBar
	 * 
	 * 
	 * 
	 * @return com.proofsecure.paros.view.MenuDisplay
	 */
	public org.parosproxy.paros.view.MainMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new org.parosproxy.paros.view.MainMenuBar();
		}
		return mainMenuBar;
	}

	public void setStatus(final String msg) {
		if (EventQueue.isDispatchThread()) {
			txtStatus.setText(msg);
			return;
		}
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					txtStatus.setText(msg);
				}
			});
		} catch (Exception e) {
		}
	}

	/**
	 * This method initializes paneDisplay
	 * 
	 * @return JPanel
	 */
	public JPanel getPaneDisplay() {
		if (paneDisplay == null) {
			paneDisplay = new JPanel();
			paneDisplay.setLayout(new CardLayout());
			paneDisplay.setName("paneDisplay");
			paneDisplay.add(getWorkbench(), getWorkbench().getName());
		}
		return paneDisplay;
	}
	
	// ZAP: Added main toolbar panel
	public MainToolbarPanel getMainToolbarPanel() {
		if (mainToolbarPanel == null) {
			mainToolbarPanel = new MainToolbarPanel();
		}
		return mainToolbarPanel;
	}
}
