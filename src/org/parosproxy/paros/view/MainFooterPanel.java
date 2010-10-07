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

package org.parosproxy.paros.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MainFooterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JToolBar footerToolbar = null;
	private JLabel flagHigh = null;
	private JLabel flagMedium = null;
	private JLabel flagLow = null;
	private JLabel flagInfo = null;
	private JLabel alertHigh = null;
	private JLabel alertMedium = null;
	private JLabel alertLow = null;
	private JLabel alertInfo = null;
	
	public MainFooterPanel () {
		super();
		initialise();
	}
	
	public void initialise () {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(getMaximumSize().width, 20));
		setMaximumSize(new Dimension(getMaximumSize().width, 20));
		
		this.setBorder(BorderFactory.createEtchedBorder());

		GridBagConstraints gbcToolbar = new GridBagConstraints();
		GridBagConstraints gbcDummyToFillSpace = new GridBagConstraints();

		gbcToolbar.gridx = 0;
		gbcToolbar.gridy = 0;
		gbcToolbar.insets = new Insets(0, 5, 0, 0); 
		gbcToolbar.anchor = GridBagConstraints.WEST;
		
		gbcDummyToFillSpace.gridx = 1;
		gbcDummyToFillSpace.gridy = 0;
		gbcDummyToFillSpace.weightx = 1.0;
		gbcDummyToFillSpace.weighty = 1.0;
		gbcDummyToFillSpace.anchor = GridBagConstraints.EAST;
		gbcDummyToFillSpace.fill = GridBagConstraints.HORIZONTAL;

		JLabel dummyLabel = new JLabel();
		
		add(getToolbar(), gbcToolbar);
		add(dummyLabel, gbcDummyToFillSpace);

		footerToolbar.add(getAlertFlagHigh());
		footerToolbar.add(getAlertHigh(0));
		
		footerToolbar.add(getAlertFlagMedium());
		footerToolbar.add(getAlertMedium(0));
		
		footerToolbar.add(getAlertFlagLow());
		footerToolbar.add(getAlertLow(0));
		
		footerToolbar.add(getAlertFlagInfo());
		footerToolbar.add(getAlertInfo(0));
		
		//footerToolbar.addSeparator();
		
	}
	
	private JToolBar getToolbar() {
		if (footerToolbar == null) {
			footerToolbar = new JToolBar();
			footerToolbar.setEnabled(true);
			footerToolbar.setFloatable(false);
			footerToolbar.setRollover(true);
			footerToolbar.setFont(new Font("Dialog", Font.PLAIN, 12));
			footerToolbar.setName("Footer Toolbar");
			footerToolbar.setBorder(BorderFactory.createEmptyBorder());
		}
		return footerToolbar;
	}
	
	public void addAlertFlag (JButton button) {
		getToolbar().add(button);
	}


	public void addSeparator() {
		getToolbar().addSeparator();
	}

	private JLabel getAlertFlagHigh() {
		if (flagHigh == null) {
			flagHigh = new JLabel();
			flagHigh.setToolTipText("High priority alerts");
			ImageIcon iconHigh = new ImageIcon(getClass().getResource("/resource/icons/flag_red.png"));
			flagHigh.setIcon(iconHigh);
		}
		return flagHigh;
	}
	
	private JLabel getAlertFlagMedium() {
		if (flagMedium == null) {
			flagMedium = new JLabel();
			flagMedium.setToolTipText("Medium priority alerts");
			ImageIcon iconMedium = new ImageIcon(getClass().getResource("/resource/icons/flag_orange.png"));
			flagMedium.setIcon(iconMedium);
		}
		return flagMedium;
	}
	
	private JLabel getAlertFlagLow() {
		if (flagLow == null) {
			flagLow = new JLabel();
			flagLow.setToolTipText("Low priority alerts");
			ImageIcon iconLow = new ImageIcon(getClass().getResource("/resource/icons/flag_yellow.png"));
			flagLow.setIcon(iconLow);
		}
		return flagLow;
	}
	
	private JLabel getAlertFlagInfo() {
		if (flagInfo == null) {
			flagInfo = new JLabel();
			flagInfo.setToolTipText("Infos");
			ImageIcon iconLow = new ImageIcon(getClass().getResource("/resource/icons/flag_blue.png"));
			flagInfo.setIcon(iconLow);
		}
		return flagInfo;
	}
	
	private JLabel getAlertHigh(int alert) {
		if (alertHigh == null) {
			alertHigh = new JLabel();
		}
		//FIXME Fix this ugly hack for adding some space between the icons
		alertHigh.setText("" + alert + "  ");
		return alertHigh;
	}
	
	public void setAlertHigh (int alert) {
		getAlertHigh(alert);
	}

	private JLabel getAlertMedium(int alert) {
		if (alertMedium == null) {
			alertMedium = new JLabel();
		}
		//FIXME Fix this ugly hack for adding some space between the icons
		alertMedium.setText("" + alert + "  ");
		return alertMedium;
	}
	
	public void setAlertMedium (int alert) {
		getAlertMedium(alert);
	}

	private JLabel getAlertLow(int alert) {
		if (alertLow == null) {
			alertLow = new JLabel();
		}
		//FIXME Fix this ugly hack for adding some space between the icons
		alertLow.setText("" + alert + "  ");
		return alertLow;
	}
	
	public void setAlertLow (int alert) {
		getAlertLow(alert);
	}

	private JLabel getAlertInfo(int alert) {
		if (alertInfo == null) {
			alertInfo = new JLabel();
		}
		//FIXME Fix this ugly hack for adding some space between the icons
		alertInfo.setText("" + alert + "  ");
		return alertInfo;
	}
	
	public void setAlertInfo (int alert) {
		getAlertInfo(alert);
	}

}
