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


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.control.MenuFileControl;
import org.parosproxy.paros.control.MenuToolsControl;


public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = 2161880506665813883L;
	
	private JMenu menuEdit = null;
	private JMenu menuTools = null;
	private JMenu menuView = null;
	private JMenuItem menuToolsOptions = null;
	private JMenu menuFile = null;
	private JMenuItem menuFileNewSession = null;
	private JMenuItem menuFileOpen = null;
	private JMenuItem menuFileSaveAs = null;
	private JMenuItem menuFileExit = null;
	private JMenuItem menuFileProperties = null;
	private JMenuItem menuFileSave = null;
	private JMenu menuHelp = null;
	private JMenuItem menuHelpAbout = null;
	private JMenu menuAnalyse = null;
	// ZAP: Added standard report menu
	private JMenu menuReport = null;

	/**
	 * This method initializes
	 * 
	 */
	public MainMenuBar() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.add(getMenuFile());
		this.add(getMenuEdit());
		this.add(getMenuView());
		this.add(getMenuAnalyse());
		this.add(getMenuReport());
		this.add(getMenuTools());
		this.add(getMenuHelp());

	}

	/**
	 * This method initializes menuEdit
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuEdit() {
		if (menuEdit == null) {
			menuEdit = new JMenu();
			menuEdit.setText("Edit");
			menuEdit.setMnemonic(KeyEvent.VK_E);
		}
		return menuEdit;
	}

	/**
	 * This method initializes menuTools
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuTools() {
		if (menuTools == null) {
			menuTools = new JMenu();
			menuTools.setText("Tools");
			menuTools.setMnemonic(KeyEvent.VK_T);
			menuTools.addSeparator();
			menuTools.add(getMenuToolsOptions());
		}
		return menuTools;
	}

	/**
	 * This method initializes menuView
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuView() {
		if (menuView == null) {
			menuView = new JMenu();
			menuView.setText("View");
			menuView.setMnemonic(KeyEvent.VK_V);
		}
		return menuView;
	}

	/**
	 * This method initializes menuToolsOptions
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuToolsOptions() {
		if (menuToolsOptions == null) {
			menuToolsOptions = new JMenuItem();
			menuToolsOptions.setText("Options...");
			menuToolsOptions.setMnemonic(KeyEvent.VK_O);
			menuToolsOptions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuToolsControl().options();
				}
			});
		}
		return menuToolsOptions;
	}

	/**
	 * This method initializes menuFile
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenu();
			menuFile.setText("File");
			menuFile.setMnemonic(KeyEvent.VK_F);
			menuFile.add(getMenuFileNewSession());
			menuFile.add(getMenuFileOpen());
			menuFile.addSeparator();
			// ZAP: Removed the Save option, as it doesnt really do anything now
			//menuFile.add(getMenuFileSave());
			menuFile.add(getMenuFileSaveAs());
			menuFile.addSeparator();
			menuFile.add(getMenuFileProperties());
			menuFile.addSeparator();
			menuFile.addSeparator();
			menuFile.add(getMenuFileExit());
		}
		return menuFile;
	}

	/**
	 * This method initializes menuFileNewSession
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuFileNewSession() {
		if (menuFileNewSession == null) {
			menuFileNewSession = new JMenuItem();
			menuFileNewSession.setText("New Session");
			menuFileNewSession.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getMenuFileControl().newSession(true);
						getMenuFileSave().setEnabled(false);
					} catch (Exception e1) {
						View.getSingleton().showWarningDialog("Error creating new session");
						e1.printStackTrace();
					}
				}
			});
			// ZAP Added New Session accelerator
			menuFileNewSession.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK, false));
		}
		return menuFileNewSession;
	}

	/**
	 * This method initializes menuFileOpen
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuFileOpen() {
		if (menuFileOpen == null) {
			menuFileOpen = new JMenuItem();
			menuFileOpen.setText("Open Session...");
			menuFileOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().openSession();
					getMenuFileSave().setEnabled(true);
				}
			});
			// ZAP Added Open Session accelerator
			menuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK, false));
		}
		return menuFileOpen;
	}

	/**
	 * This method initializes menuFileSaveAs
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuFileSaveAs() {
		if (menuFileSaveAs == null) {
			menuFileSaveAs = new JMenuItem();
			menuFileSaveAs.setText("Save As...");
			menuFileSaveAs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().saveAsSession();
				}
			});
		}
		return menuFileSaveAs;
	}

	/**
	 * This method initializes menuFileExit
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuFileExit() {
		if (menuFileExit == null) {
			menuFileExit = new JMenuItem();
			menuFileExit.setText("Exit");
			menuFileExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().exit();
				}
			});

		}
		return menuFileExit;
	}

	/**
	 * This method initializes menuFileControl
	 * 
	 * @return com.proofsecure.paros.view.MenuFileControl
	 */
	public MenuFileControl getMenuFileControl() {
		return Control.getSingleton().getMenuFileControl();
	}

	/**
	 * This method initializes menuToolsControl
	 * 
	 * @return com.proofsecure.paros.view.MenuToolsControl
	 */
	private MenuToolsControl getMenuToolsControl() {
		return Control.getSingleton().getMenuToolsControl();
	}

	/**
	 * This method initializes menuFileProperties
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuFileProperties() {
		if (menuFileProperties == null) {
			menuFileProperties = new JMenuItem();
			menuFileProperties.setText("Properties...");
			menuFileProperties.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().properties();
				}
			});
		}
		return menuFileProperties;
	}

	/**
	 * This method initializes menuFileSave
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getMenuFileSave() {
		if (menuFileSave == null) {
			menuFileSave = new JMenuItem();
			menuFileSave.setText("Save");
			menuFileSave.setEnabled(false);
			menuFileSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMenuFileControl().saveSession();
				}
			});
		}
		return menuFileSave;
	}

	/**
	 * This method initializes menuHelp
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuHelp() {
		if (menuHelp == null) {
			menuHelp = new JMenu();
			menuHelp.setText("Help");
			menuHelp.setMnemonic(KeyEvent.VK_H);
			menuHelp.add(getMenuHelpAbout());
		}
		return menuHelp;
	}

	 // ZAP: Added standard report menu
	public JMenu getMenuReport() {
		if (menuReport == null) {
			menuReport = new JMenu();
			menuReport.setText("Report");
		}
		return menuReport;
	}
	
	/**
	 * This method initializes menuHelpAbout
	 * 
	 * @return JMenuItem
	 */
	private JMenuItem getMenuHelpAbout() {
		if (menuHelpAbout == null) {
			menuHelpAbout = new JMenuItem();
			menuHelpAbout.setText("About " + Constant.PROGRAM_NAME);
			menuHelpAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AboutDialog dialog = new AboutDialog(View.getSingleton().getMainFrame(), true);
					dialog.setVisible(true);
				}
			});
		}
		return menuHelpAbout;
	}

	/**
	 * This method initializes jMenu1
	 * 
	 * @return JMenu
	 */
	public JMenu getMenuAnalyse() {
		if (menuAnalyse == null) {
			menuAnalyse = new JMenu();
			menuAnalyse.setText("Analyse");
			menuAnalyse.setMnemonic(KeyEvent.VK_A);
		}
		return menuAnalyse;
	}
}
