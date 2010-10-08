/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2006 Chinotec Technologies Company
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

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Cursor;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;

public class LogPanelCellRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 5235089457655708192L;

	private JLabel txtFlag = null;
	private JLabel txtId = null;
	private JLabel txtMethod = null;
	private JLabel txtURI = null;
	private JLabel txtStatus = null;
	private JLabel txtReason = null;
	private JLabel txtRTT = null;
	private JLabel txtTimestamp = null;
	private JLabel txtTag = null;
	private JLabel txtNote = null;
	
	/**
	 * This is the default constructor
	 */
	public LogPanelCellRenderer() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagConstraints gbc_txtFlag = new GridBagConstraints();
		gbc_txtFlag.anchor = GridBagConstraints.WEST;
		gbc_txtFlag.gridx = 0;
		gbc_txtFlag.gridy = 0;
		gbc_txtFlag.weightx = 0.0D;
		gbc_txtFlag.insets = new Insets(0, 0, 0, 0);
		gbc_txtFlag.ipadx = 4;
		gbc_txtFlag.ipady = 1;
		
		txtFlag = new JLabel();
		txtFlag.setName("txtFlag");
		txtFlag.setPreferredSize(new Dimension(16, 16));
		txtFlag.setMinimumSize(new Dimension(16, 16));
		txtFlag.setOpaque(true);
		
		
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.anchor = GridBagConstraints.WEST;
		gbc_txtId.gridx = 1;
		gbc_txtId.gridy = 0;
		gbc_txtId.weightx = 0.0D;
		gbc_txtId.insets = new Insets(0, 0, 0, 0);
		gbc_txtId.ipadx = 4;
		gbc_txtId.ipady = 1;
		
		txtId = new JLabel();
		txtId.setText("");
		txtId.setBackground(SystemColor.text);
		txtId.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setPreferredSize(new Dimension(40, 16));
		txtId.setMinimumSize(new Dimension(40, 16));
		txtId.setFont(new Font("Default", Font.PLAIN, 12));
		txtId.setOpaque(true);
		
		
		GridBagConstraints gbc_txtMethod = new GridBagConstraints();
		gbc_txtMethod.anchor = GridBagConstraints.WEST;
		gbc_txtMethod.gridx = 2;
		gbc_txtMethod.gridy = 0;
		gbc_txtMethod.weightx = 0.0D;
		gbc_txtMethod.insets = new Insets(0, 0, 0, 0);
		gbc_txtMethod.ipadx = 4;
		gbc_txtMethod.ipady = 1;
		gbc_txtMethod.fill = GridBagConstraints.NONE;
		
		txtMethod = new JLabel();
		txtMethod.setText("");
		txtMethod.setBackground(SystemColor.text);
		txtMethod.setHorizontalAlignment(SwingConstants.LEFT);
		txtMethod.setPreferredSize(new Dimension(45, 16));
		txtMethod.setMinimumSize(new Dimension(45, 16));
		txtMethod.setFont(new Font("Default", Font.PLAIN, 12));
		txtMethod.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txtMethod.setOpaque(true);
		
		
		GridBagConstraints gbc_txtURI = new GridBagConstraints();
		gbc_txtURI.anchor = GridBagConstraints.WEST;
		gbc_txtURI.gridx = 3;
		gbc_txtURI.gridy = 0;
		gbc_txtURI.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURI.weightx = 0.75D;
		gbc_txtURI.insets = new Insets(0, 0, 0, 0);
		gbc_txtURI.ipadx = 4;
		gbc_txtURI.ipady = 1;
		
		txtURI = new JLabel();
		txtURI.setText("");
		txtURI.setBackground(SystemColor.text);
		txtURI.setHorizontalAlignment(SwingConstants.LEFT);
		txtURI.setPreferredSize(new Dimension(420, 16));
		txtURI.setMinimumSize(new Dimension(420, 16));
		txtURI.setFont(new Font("Default", Font.PLAIN, 12));
		txtURI.setOpaque(true);
		
		
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.anchor = GridBagConstraints.WEST;
		gbc_txtStatus.gridx = 4;
		gbc_txtStatus.gridy = 0;
		gbc_txtStatus.weightx = 0.0D;
		gbc_txtStatus.ipadx = 4;
		gbc_txtStatus.ipady = 1;
		
		txtStatus = new JLabel();
		txtStatus.setText("");
		txtStatus.setBackground(SystemColor.text);
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setPreferredSize(new Dimension(30, 16));
		txtStatus.setMinimumSize(new Dimension(30, 16));
		txtStatus.setFont(new Font("Default", Font.PLAIN, 12));
		txtStatus.setOpaque(true);

		
		GridBagConstraints gbc_txtReason = new GridBagConstraints();
		gbc_txtReason.gridx = 5;
		gbc_txtReason.ipadx = 4;
		gbc_txtReason.ipady = 1;
		gbc_txtReason.weightx = 0.0D;
		gbc_txtReason.anchor = GridBagConstraints.WEST;
		gbc_txtReason.gridy = 0;
		
		txtReason = new JLabel();
		txtReason.setText("");
		txtReason.setBackground(SystemColor.text);
		txtReason.setHorizontalAlignment(SwingConstants.LEFT);
		txtReason.setPreferredSize(new Dimension(85, 16));
		txtReason.setMinimumSize(new Dimension(85, 16));
		txtReason.setFont(new Font("Default", Font.PLAIN, 12));
		txtReason.setOpaque(true);
		txtReason.setVisible(true);
		
		
		GridBagConstraints gbc_txtRTT = new GridBagConstraints();
		gbc_txtRTT.anchor = GridBagConstraints.WEST;
		gbc_txtRTT.gridx = 6;
		gbc_txtRTT.gridy = 0;
		gbc_txtRTT.weightx = 0.0D;
		gbc_txtRTT.ipadx = 4;
		gbc_txtRTT.ipady = 1;
		
		txtRTT = new JLabel();
		txtRTT.setText("");
		txtRTT.setBackground(SystemColor.text);
		txtRTT.setHorizontalAlignment(SwingConstants.RIGHT);
		txtRTT.setPreferredSize(new Dimension(55, 16));
		txtRTT.setMinimumSize(new Dimension(55, 16));
		txtRTT.setFont(new Font("Default", Font.PLAIN, 12));
		txtRTT.setOpaque(true);
		
		
		GridBagConstraints gbc_txtTimestamp = new GridBagConstraints();
		gbc_txtTimestamp.anchor = GridBagConstraints.WEST;
		gbc_txtTimestamp.gridx = 7;
		gbc_txtTimestamp.gridy = 0;
		gbc_txtTimestamp.weightx = 0.15D;
		gbc_txtTimestamp.ipadx = 10;
		gbc_txtTimestamp.ipady = 1;
		gbc_txtTimestamp.fill = GridBagConstraints.HORIZONTAL;
		
		txtTimestamp = new JLabel();
		txtTimestamp.setText("");
		txtTimestamp.setBackground(SystemColor.text);
		txtTimestamp.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimestamp.setPreferredSize(new Dimension(40, 16));
		txtTimestamp.setMinimumSize(new Dimension(40, 16));
		txtTimestamp.setFont(new Font("Default", Font.PLAIN, 12));
		txtTimestamp.setOpaque(true);
		
		
		GridBagConstraints gbc_txtTag = new GridBagConstraints();
		gbc_txtTag.anchor = GridBagConstraints.WEST;
		gbc_txtTag.gridx = 8;
		gbc_txtTag.gridy = 0;
		gbc_txtTag.weightx = 0.0D;
		gbc_txtTag.ipadx = 4;
		gbc_txtTag.ipady = 1;
		gbc_txtTag.fill = GridBagConstraints.HORIZONTAL;
		
		txtTag = new JLabel();
		txtTag.setText("");
		txtTag.setBackground(SystemColor.text);
		txtTag.setHorizontalAlignment(SwingConstants.LEFT);
		txtTag.setPreferredSize(new Dimension(70, 16));
		txtTag.setMinimumSize(new Dimension(70, 16));
		txtTag.setFont(new Font("Default", Font.PLAIN, 12));
		txtTag.setOpaque(true);
		
		
		// ZAP: Added notes image
        GridBagConstraints gbc_txtNote = new GridBagConstraints();
        gbc_txtNote.anchor = java.awt.GridBagConstraints.WEST;
        gbc_txtNote.gridx = 9;
        gbc_txtNote.gridy = 0;
        gbc_txtNote.weightx = 0.0D;
        gbc_txtNote.ipadx = 4;
        gbc_txtNote.ipady = 1;
        gbc_txtNote.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc_txtNote.anchor = java.awt.GridBagConstraints.WEST;

        txtNote = new JLabel();
        txtNote.setText("");
        txtNote.setBackground(SystemColor.text);
        txtNote.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        txtNote.setHorizontalAlignment(SwingConstants.CENTER);
        txtNote.setPreferredSize(new Dimension(20 ,16));
        txtNote.setMinimumSize(new Dimension(20 ,16));
        txtNote.setFont(new Font("Default", Font.PLAIN, 12));
        txtNote.setOpaque(true);
        

		this.setLayout(new GridBagLayout());
		this.setSize(328, 11);
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.add(txtFlag, gbc_txtFlag);
		this.add(txtId, gbc_txtId);
		this.add(txtMethod, gbc_txtMethod);
		this.add(txtURI, gbc_txtURI);
		this.add(txtStatus, gbc_txtStatus);
		this.add(txtReason, gbc_txtReason);
		this.add(txtRTT, gbc_txtRTT);
		this.add(txtTimestamp, gbc_txtTimestamp);
		this.add(txtTag, gbc_txtTag);
		this.add(txtNote, gbc_txtNote);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		HistoryReference ref = (HistoryReference) value;
		txtId.setText(Integer.toString(ref.getHistoryId()));
		
		Color markedBgColor = new Color(200,100,120);
		Color markedFgColor = new Color(255,255,255);

		final ImageIcon tag_standard = new ImageIcon(getClass().getResource("/resource/icons/tag_gray.png"));
		final ImageIcon tag_highRisk = new ImageIcon(getClass().getResource("/resource/icons/flag_red.png"));
		final ImageIcon tag_mediumRisk = new ImageIcon(getClass().getResource("/resource/icons/flag_orange.png"));
		final ImageIcon tag_lowRisk = new ImageIcon(getClass().getResource("/resource/icons/flag_yellow.png"));
		final ImageIcon tag_infoRisk = new ImageIcon(getClass().getResource("/resource/icons/flag_blue.png"));
		
		boolean isMarked = false;
		

		HttpMessage msg = new HttpMessage();
		
		try {
			msg = ref.getHttpMessage();
			txtMethod.setText(msg.getRequestHeader().getMethod());
			txtURI.setText(msg.getRequestHeader().getURI().toString());
			txtStatus.setText(Integer.toString(msg.getResponseHeader().getStatusCode()));
			txtReason.setText(msg.getResponseHeader().getReasonPhrase());
			txtRTT.setText(msg.getTimeElapsedMillis() + "ms");
			
			String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date (msg.getTimeSentMillis()));
			txtTimestamp.setText(date);
			
			// ZAP: Support for multiple tags
            StringBuffer sb = new StringBuffer();
            for (String tag : msg.getTags()) {
            	if (sb.length() > 0) {
                	sb.append(", ");
            	}
            	sb.append(tag);
            }
            txtTag.setText(sb.toString());
			
            // Andiparos: Message marking
			isMarked = msg.getFlag();
			
			// ZAP: Alert flagging
			if (ref.getAlerts().size() > 0) {
            	switch (ref.getHighestAlert()) {
            	case Alert.RISK_INFO:
            		txtFlag.setIcon(tag_infoRisk);
            		break;
            	case Alert.RISK_LOW:
            		txtFlag.setIcon(tag_lowRisk);
            		break;
            	case Alert.RISK_MEDIUM:
            		txtFlag.setIcon(tag_mediumRisk);
            		break;
            	case Alert.RISK_HIGH:
            		txtFlag.setIcon(tag_highRisk);
            		break;
            	}
            } else {
            	txtFlag.setIcon(tag_standard);
				//txtAlertFlag.setIcon(null);
			}
			

		} catch (HttpMalformedHeaderException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (isSelected) {
			if(!isMarked) {
				txtFlag.setIcon(tag_standard);
				//txtAlertFlag.setIcon(null);
			}
			txtFlag.setBackground(list.getSelectionBackground());
			txtFlag.setForeground(list.getSelectionForeground());
			txtId.setBackground(list.getSelectionBackground());
			txtId.setForeground(list.getSelectionForeground());
			txtMethod.setBackground(list.getSelectionBackground());
			txtMethod.setForeground(list.getSelectionForeground());
			txtURI.setBackground(list.getSelectionBackground());
			txtURI.setForeground(list.getSelectionForeground());
			txtStatus.setBackground(list.getSelectionBackground());
			txtStatus.setForeground(list.getSelectionForeground());
			txtReason.setBackground(list.getSelectionBackground());
			txtReason.setForeground(list.getSelectionForeground());
			txtRTT.setBackground(list.getSelectionBackground());
			txtRTT.setForeground(list.getSelectionForeground());
			txtTimestamp.setBackground(list.getSelectionBackground());
			txtTimestamp.setForeground(list.getSelectionForeground());
			txtTag.setBackground(list.getSelectionBackground());
			txtTag.setForeground(list.getSelectionForeground());
			txtNote.setBackground(list.getSelectionBackground());
			txtNote.setForeground(list.getSelectionForeground());
			
		} else if (isMarked) {
			txtFlag.setBackground(markedBgColor);
			txtFlag.setForeground(markedFgColor);
			txtId.setBackground(markedBgColor);
			txtId.setForeground(markedFgColor);
			txtMethod.setBackground(markedBgColor);
			txtMethod.setForeground(markedFgColor);
			txtURI.setBackground(markedBgColor);
			txtURI.setForeground(markedFgColor);
			txtStatus.setBackground(markedBgColor);
			txtStatus.setForeground(markedFgColor);
			txtReason.setBackground(markedBgColor);
			txtReason.setForeground(markedFgColor);
			txtRTT.setBackground(markedBgColor);
			txtRTT.setForeground(markedFgColor);
			txtTimestamp.setBackground(markedBgColor);
			txtTimestamp.setForeground(markedFgColor);
			txtTag.setBackground(markedBgColor);
			txtTag.setForeground(markedFgColor); 
			txtNote.setBackground(markedBgColor);
			txtNote.setForeground(markedFgColor); 
		
		} else {
			Color darker = new Color(list.getBackground().getRGB() & 0xFFECECEC);
			txtFlag.setBackground(list.getBackground());
			txtFlag.setForeground(list.getForeground());
			txtId.setBackground(list.getBackground());
			txtId.setForeground(list.getForeground());
			txtMethod.setBackground(darker);
			txtMethod.setForeground(list.getForeground());
			txtURI.setBackground(list.getBackground());
			txtURI.setForeground(list.getForeground());
			txtStatus.setBackground(darker);
			txtStatus.setForeground(list.getForeground());
			txtReason.setBackground(list.getBackground());
			txtReason.setForeground(list.getForeground());
			txtRTT.setBackground(darker);
			txtRTT.setForeground(list.getForeground());
			txtTimestamp.setBackground(list.getBackground());
			txtTimestamp.setForeground(list.getForeground());
			txtTag.setBackground(darker);
			txtTag.setForeground(list.getForeground());
			txtNote.setBackground(list.getBackground());
			txtNote.setForeground(list.getForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;

	}
}
