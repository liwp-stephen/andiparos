/*
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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Vector;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.AbstractPanel;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.view.HttpPanel;
import org.parosproxy.paros.view.View;


public class LogPanel extends AbstractPanel implements Runnable {

	private static final long serialVersionUID = 5013184985828986669L;

	private JScrollPane scrollLog = null;
	private JList listLog = null;

	private HttpPanel requestPanel = null;
	private HttpPanel responsePanel = null;
	private JPanel jPanel = null;
	private JLabel uriFilterLabel = null;
	private JTextField uriFilter = null;
	private JLabel methodLabel = null;
	private JComboBox methodList = null;
	private JCheckBox filterInverse = null;
	private ExtensionHistory extension = null;


	/**
	 * This is the default constructor
	 */
	public LogPanel() {
		super();
		initialize();
	}

	private void initialize() {
		GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

		this.setLayout(new GridBagLayout());
		this.setSize(600, 200);
		gridBagConstraints0.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints0.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints0.gridx = 0;
		gridBagConstraints0.gridy = 0;
		gridBagConstraints0.weightx = 1.0;
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.ipadx = 0;
		gridBagConstraints1.ipady = 0;

		this.add(getJPanel(), gridBagConstraints0);
		this.add(getScrollLog(), gridBagConstraints1);

	}

	void setExtension(ExtensionHistory extension) {
		this.extension = extension;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			
			methodLabel = new JLabel("HTTP Method: ");
			String[] methods = {"ALL", "POST", "GET", "HEAD"};
			methodList = new JComboBox(methods);
			methodList.setSelectedIndex(0);
			
			uriFilterLabel = new JLabel("URI: ");
			uriFilter = new JTextField();
			uriFilter.setColumns(50);
			filterInverse = new JCheckBox("Filter inverse");
			JButton jButton = new JButton("Filter");

			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			
			jPanel.add(methodLabel);
			jPanel.add(methodList);
			jPanel.add(uriFilterLabel);
			jPanel.add(uriFilter);
			jPanel.add(filterInverse);
			jPanel.add(jButton);

			
			// HTTP Method Filter
			methodList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String filter[] = { (String)methodList.getSelectedItem(), uriFilter.getText() };
					boolean inverseFilter = filterInverse.isSelected();
					extension.getProxyListenerLog().setUriFilter(filter, inverseFilter);
					extension.searchHistoryByURI(filter, inverseFilter);
				}
			});
			
			
			// URI Filter
			uriFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String filter[] = { (String)methodList.getSelectedItem(), uriFilter.getText() };
					boolean inverseFilter = filterInverse.isSelected();
					extension.getProxyListenerLog().setUriFilter(filter, inverseFilter);
					extension.searchHistoryByURI(filter, inverseFilter);
				}
			});
			
			// Filter inversion
			filterInverse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String filter[] = { (String)methodList.getSelectedItem(), uriFilter.getText() };
					boolean inverseFilter = filterInverse.isSelected();
					extension.getProxyListenerLog().setUriFilter(filter, inverseFilter);
					extension.searchHistoryByURI(filter, inverseFilter);
				}
			});
			
			// Filter Button
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String filter[] = { (String)methodList.getSelectedItem(), uriFilter.getText() };
					boolean inverseFilter = filterInverse.isSelected();
					extension.getProxyListenerLog().setUriFilter(filter, inverseFilter);
					extension.searchHistoryByURI(filter, inverseFilter);
				}
			});
			
			
			

		}
		return jPanel;
	}

	public String getFilterString() {
		String filterString = uriFilter.getText();
		return filterString;
	}

	/**
	 * This method initializes scrollLog
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getScrollLog() {
		if (scrollLog == null) {
			scrollLog = new JScrollPane();
			scrollLog.setViewportView(getListLog());
			scrollLog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollLog.setPreferredSize(new Dimension(800, 200));
			scrollLog.setName("scrollLog");
		}
		return scrollLog;
	}

	/**
	 * This method initializes listLog
	 * 
	 * @return JList
	 */
	public JList getListLog() {
		if (listLog == null) {
			listLog = new JList();
			listLog.setDoubleBuffered(true);
			listLog.setCellRenderer(getLogPanelCellRenderer());
			listLog.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listLog.setName("ListLog");
			listLog.setFont(new Font("Default", Font.PLAIN, 12));
			listLog.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
						// right mouse button
						View.getSingleton().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
						return;
					}

					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0 && e.getClickCount() > 1) {
						// double click
						requestPanel.setTabFocus();
						return;
					}
				}
			});

			listLog.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (listLog.getSelectedValue() == null) {
						return;
					}

					final HistoryReference historyRef = (HistoryReference) listLog.getSelectedValue();
					readAndDisplay(historyRef);
				}
			});

			listLog.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (listLog.getSelectedValue() == null) {
						return;
					}

					if (e.getKeyCode() == KeyEvent.VK_T) {
						if (isMetaCtrlDown(e)) {
							HistoryReference ref = (HistoryReference) listLog.getSelectedValue();
							HttpMessage msg = null;
							try {
								msg = ref.getHttpMessage();
								boolean currentFlag = msg.getFlag();

								if (currentFlag == false) {
									ref.setFlag(true);
								} else {
									ref.setFlag(false);
								}

								extension.getHistoryList().notifyItemChanged(ref);

							} catch (HttpMalformedHeaderException ex) {
								ex.printStackTrace();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			});

		}
		return listLog;
	}

	public boolean isMetaCtrlDown(KeyEvent ke) {
		boolean keyDown;
		if (Constant.isOSX()) {
			keyDown = ke.isMetaDown();
		} else {
			keyDown = ke.isControlDown();
		}
		return keyDown;
	}
	
	private Vector<HistoryReference> displayQueue = new Vector<HistoryReference>();
	private Thread thread = null;
	private LogPanelCellRenderer logPanelCellRenderer = null;

	private void readAndDisplay(final HistoryReference historyRef) {

		synchronized (displayQueue) {
			if (!ExtensionHistory.isEnableForNativePlatform()) {
				// truncate queue if browser dialog is displayed to have better response
				if (displayQueue.size() > 0) {
					// replace all display queue because the newest display
					// overrides all previous one pending to be rendered.
					displayQueue.clear();
				}
			}

			displayQueue.add(historyRef);

		}

		if (thread != null && thread.isAlive()) {
			return;
		}

		thread = new Thread(this);

		thread.setPriority(Thread.NORM_PRIORITY);
		thread.start();
	}

	public void setDisplayPanel(HttpPanel requestPanel, HttpPanel responsePanel) {
		this.requestPanel = requestPanel;
		this.responsePanel = responsePanel;

	}

	private void displayMessage(HttpMessage msg) {

		if (msg.getRequestHeader().isEmpty()) {
			requestPanel.setMessage(null, true);
		} else {
			requestPanel.setMessage(msg, true);
		}

		if (msg.getResponseHeader().isEmpty()) {
			responsePanel.setMessage(null, false);
		} else {
			responsePanel.setMessage(msg, false);
		}
	}

	public void run() {
		HistoryReference ref = null;
		int count = 0;

		do {
			synchronized (displayQueue) {
				count = displayQueue.size();
				if (count == 0) {
					break;
				}

				ref = (HistoryReference) displayQueue.get(0);
				displayQueue.remove(0);
			}

			try {
				final HttpMessage msg = ref.getHttpMessage();
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						displayMessage(msg);
						listLog.requestFocus();
					}
				});

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// wait some time to allow another selection event to be triggered
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		} while (true);

	}

	/**
	 * This method initializes logPanelCellRenderer
	 * 
	 * @return org.parosproxy.paros.extension.history.LogPanelCellRenderer
	 */
	private LogPanelCellRenderer getLogPanelCellRenderer() {
		if (logPanelCellRenderer == null) {
			logPanelCellRenderer = new LogPanelCellRenderer();
			logPanelCellRenderer.setSize(new Dimension(328, 21));
			logPanelCellRenderer.setBackground(Color.white);
			logPanelCellRenderer.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
		}
		return logPanelCellRenderer;
	}
}
