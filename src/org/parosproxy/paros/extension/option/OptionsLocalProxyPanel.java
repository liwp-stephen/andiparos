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

package org.parosproxy.paros.extension.option;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.core.proxy.ProxyParam;
import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.view.AbstractParamPanel;

public class OptionsLocalProxyPanel extends AbstractParamPanel {

	private static final long serialVersionUID = 2834386267595589743L;

	private JPanel panelProxy = null;
	private JPanel panelLocalProxy = null;
	private JPanel panelReverseProxy = null;
	
	private JTextField txtProxyIp = null;
	private JTextField txtProxyPort = null;
	
	private JCheckBox chkReverseProxy = null;
	private JTextField txtReverseProxyIp = null;
	private JTextField txtReverseProxyHttpPort = null;
	private JTextField txtReverseProxyHttpsPort = null;

	public OptionsLocalProxyPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes panelLocalProxy
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelLocalProxy() {
		if (panelLocalProxy == null) {
			
			panelLocalProxy = new JPanel();
			panelLocalProxy.setLayout(new GridBagLayout());
			panelLocalProxy.setBorder(BorderFactory.createTitledBorder(null,
					"Local proxy",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.PLAIN, 11),
					Color.black));
			
			JLabel addressLabel = new JLabel();
			addressLabel.setText("Address/Domain Name:");
			
			JLabel portLabel = new JLabel();
			portLabel.setText("Port:");

			JLabel descLabel = new JLabel();
			descLabel.setText("<html><body><br><p>Set your browser proxy setting using the above. The http port and https port must be the same port as above.</p></body></html>");
			
			
			GridBagConstraints gbcDescText = new GridBagConstraints();
			GridBagConstraints gbcPort = new GridBagConstraints();
			GridBagConstraints gbcPortText = new GridBagConstraints();
			GridBagConstraints gbcAddress = new GridBagConstraints();
			GridBagConstraints gbcAddressText = new GridBagConstraints();
			
			gbcAddressText.gridx = 0;
			gbcAddressText.gridy = 0;
			gbcAddressText.ipadx = 0;
			gbcAddressText.ipady = 0;
			gbcAddressText.weightx = 0.5D;
			gbcAddressText.insets = new Insets(2, 2, 2, 2);
			gbcAddressText.anchor = GridBagConstraints.WEST;
			//gbcAddressText.fill = GridBagConstraints.HORIZONTAL;
			
			gbcAddress.gridx = 1;
			gbcAddress.gridy = 0;
			gbcAddress.ipadx = 200;
			gbcAddress.ipady = 0;
			gbcAddress.weightx = 0.5D;
			gbcAddress.insets = new Insets(2, 2, 2, 2);
			gbcAddress.anchor = GridBagConstraints.EAST;
			gbcAddress.fill = GridBagConstraints.HORIZONTAL;
			
			gbcPortText.gridx = 0;
			gbcPortText.gridy = 1;
			gbcPortText.ipadx = 0;
			gbcPortText.ipady = 0;
			gbcPortText.weightx = 0.5D;
			gbcPortText.insets = new Insets(2, 2, 2, 2);
			gbcPortText.anchor = GridBagConstraints.WEST;
			//gbcPortText.fill = GridBagConstraints.HORIZONTAL;
						
			gbcPort.gridx = 1;
			gbcPort.gridy = 1;
			gbcPort.ipadx = 200;
			gbcPort.ipady = 0;
			gbcPort.weightx = 0.5D;
			gbcPort.insets = new Insets(2, 2, 2, 2);
			gbcPort.anchor = GridBagConstraints.EAST;
			gbcPort.fill = GridBagConstraints.HORIZONTAL;
			
			gbcDescText.gridx = 0;
			gbcDescText.gridy = 4;
			gbcDescText.weightx = 1.0D;
			gbcDescText.insets = new Insets(2, 2, 2, 2);
			gbcDescText.anchor = GridBagConstraints.NORTHWEST;
			gbcDescText.fill = GridBagConstraints.HORIZONTAL;
			gbcDescText.gridwidth = 2;
			
			panelLocalProxy.add(addressLabel, gbcAddressText);
			panelLocalProxy.add(getTxtProxyIp(), gbcAddress);
			panelLocalProxy.add(portLabel, gbcPortText);
			panelLocalProxy.add(getTxtProxyPort(), gbcPort);
			panelLocalProxy.add(descLabel, gbcDescText);
		}
		
		return panelLocalProxy;
	}

	/**
	 * This method initializes panelLocalProxySSL
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelReverseProxy() {
		if (panelReverseProxy == null) {
			
			panelReverseProxy = new JPanel();
			panelReverseProxy.setLayout(new GridBagLayout());
			panelReverseProxy.setSize(114, 132);
			panelReverseProxy.setName("Miscellenous");
			panelReverseProxy.setBorder(BorderFactory.createTitledBorder(null,
				"Reverse proxy",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("Dialog", Font.PLAIN, 11),
				Color.black));
			panelReverseProxy.setFont(new Font("Dialog",Font.PLAIN, 11));
			
			
			JLabel addressLabel = new JLabel();
			addressLabel.setText("Address (eg 192.168.0.1)");
			
			JLabel httpPortLabel = new JLabel();
			httpPortLabel.setText("HTTP Port (eg 80)");
			
			JLabel httpsPortLabel = new JLabel();
			httpsPortLabel.setText("HTTPS port (eg 443)");
			
			JLabel descLabel = new JLabel();
			descLabel.setText("<html><body><p>The address should not be \"localhost\" because a reverse proxy should be accessed by browser from another computer.</p></body></html>");
			
			
			GridBagConstraints gbcReverseProxyHttpsPort = new GridBagConstraints();
			GridBagConstraints gbcHttpsPortText = new GridBagConstraints();
			GridBagConstraints gbcDescText = new GridBagConstraints();
			GridBagConstraints gbcReverseProxyHttpPort = new GridBagConstraints();
			GridBagConstraints gbcHttpPortText = new GridBagConstraints();
			GridBagConstraints gbcReverseProxyAddress = new GridBagConstraints();
			GridBagConstraints gbcAddressText = new GridBagConstraints();

			
			gbcAddressText.gridx = 0;
			gbcAddressText.gridy = 0;
			gbcAddressText.ipadx = 0;
			gbcAddressText.ipady = 0;
			gbcAddressText.insets = new Insets(2, 2, 2, 2);
			gbcAddressText.weightx = 0.5D;
			gbcAddressText.fill = GridBagConstraints.HORIZONTAL;
			gbcAddressText.anchor = GridBagConstraints.WEST;
			
			gbcReverseProxyAddress.gridx = 1;
			gbcReverseProxyAddress.gridy = 0;
			gbcReverseProxyAddress.ipadx = 50;
			gbcReverseProxyAddress.weightx = 0.5D;
			gbcReverseProxyAddress.insets = new Insets(2, 2, 2, 2);
			gbcReverseProxyAddress.anchor = GridBagConstraints.WEST;
			gbcReverseProxyAddress.fill = GridBagConstraints.HORIZONTAL;
			
			gbcHttpPortText.gridx = 0;
			gbcHttpPortText.gridy = 1;
			gbcHttpPortText.ipadx = 0;
			gbcHttpPortText.ipady = 0;
			gbcHttpPortText.weightx = 0.5D;
			gbcHttpPortText.insets = new Insets(2, 2, 2, 2);
			gbcHttpPortText.anchor = GridBagConstraints.WEST;
			gbcHttpPortText.fill = GridBagConstraints.HORIZONTAL;
			
			gbcReverseProxyHttpPort.gridx = 1;
			gbcReverseProxyHttpPort.gridy = 1;
			gbcReverseProxyHttpPort.ipadx = 50;
			gbcReverseProxyHttpPort.weightx = 0.5D;
			gbcReverseProxyHttpPort.insets = new Insets(2, 2, 2, 2);
			gbcReverseProxyHttpPort.anchor = GridBagConstraints.WEST;
			gbcReverseProxyHttpPort.fill = GridBagConstraints.HORIZONTAL;
			
			gbcReverseProxyHttpsPort.gridx = 1;
			gbcReverseProxyHttpsPort.gridy = 2;
			gbcReverseProxyHttpsPort.ipadx = 50;
			gbcReverseProxyHttpsPort.weightx = 0.5D;
			gbcReverseProxyHttpsPort.insets = new Insets(2, 2, 2, 2);
			gbcReverseProxyHttpsPort.anchor = GridBagConstraints.EAST;
			gbcReverseProxyHttpsPort.fill = GridBagConstraints.HORIZONTAL;
			
			gbcHttpsPortText.gridx = 0;
			gbcHttpsPortText.gridy = 2;
			gbcHttpsPortText.weightx = 0.5D;
			gbcHttpsPortText.insets = new Insets(2, 2, 2, 2);
			gbcHttpsPortText.anchor = GridBagConstraints.WEST;
			gbcHttpsPortText.fill = GridBagConstraints.HORIZONTAL;
			
			gbcDescText.gridx = 0;
			gbcDescText.gridy = 3;
			gbcDescText.weightx = 1.0D;
			gbcDescText.insets = new Insets(2, 2, 2, 2);
			gbcDescText.gridwidth = 2;
			gbcDescText.anchor = GridBagConstraints.WEST;
			gbcDescText.fill = GridBagConstraints.BOTH;
			
			panelReverseProxy.add(addressLabel, gbcAddressText);
			panelReverseProxy.add(getTxtReverseProxyIp(), gbcReverseProxyAddress);
			panelReverseProxy.add(getTxtReverseProxyHttpPort(), gbcReverseProxyHttpPort);	
			panelReverseProxy.add(httpPortLabel, gbcHttpPortText);
			panelReverseProxy.add(descLabel, gbcDescText);
			panelReverseProxy.add(httpsPortLabel, gbcHttpsPortText);
			panelReverseProxy.add(getTxtReverseProxyHttpsPort(), gbcReverseProxyHttpsPort);
			panelReverseProxy.setVisible(true);
		}
		return panelReverseProxy;
	}

	/**
	 * This method initializes panelProxy
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelProxy() {
		if (panelProxy == null) {
			
			panelProxy = new JPanel();
			panelProxy.setLayout(new GridBagLayout());
			panelProxy.setName("Local Proxy");
			panelProxy.setSize(303, 177);
			panelProxy.setFont(new Font("Dialog", Font.PLAIN, 11));
			
			GridBagConstraints gbcChkReverseProxy = new GridBagConstraints();
			GridBagConstraints gbcSpace = new GridBagConstraints();
			GridBagConstraints gbcReverseProxyPanel = new GridBagConstraints();
			GridBagConstraints gbcLocalProxyPanel = new GridBagConstraints();			
			
			JLabel spaceLabel = new JLabel();
			spaceLabel.setText("");
			
			gbcLocalProxyPanel.gridx = 0;
			gbcLocalProxyPanel.gridy = 0;
			gbcLocalProxyPanel.ipadx = 2;
			gbcLocalProxyPanel.ipady = 4;
			gbcLocalProxyPanel.insets = new Insets(2, 2, 2, 2);
			gbcLocalProxyPanel.anchor = GridBagConstraints.NORTHWEST;
			gbcLocalProxyPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcLocalProxyPanel.weightx = 1.0D;
			gbcLocalProxyPanel.weighty = 0.0D;
			
			gbcReverseProxyPanel.gridx = 0;
			gbcReverseProxyPanel.gridy = 2;
			gbcReverseProxyPanel.anchor = GridBagConstraints.NORTHWEST;
			gbcReverseProxyPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcReverseProxyPanel.weightx = 1.0D;
			gbcReverseProxyPanel.weighty = 0.0D;
			gbcReverseProxyPanel.ipady = 4;
			gbcReverseProxyPanel.ipadx = 2;	
			
			gbcSpace.gridx = 0;
			gbcSpace.gridy = 2;
			gbcSpace.weightx = 1.0D;
			gbcSpace.weighty = 1.0D;
			gbcSpace.fill = GridBagConstraints.BOTH;
			
			gbcChkReverseProxy.gridx = 0;
			gbcChkReverseProxy.gridy = 1;
			gbcChkReverseProxy.weightx = 1.0D;
			gbcChkReverseProxy.insets = new Insets(2, 2, 2, 2);
			gbcChkReverseProxy.anchor = GridBagConstraints.NORTHWEST;
			gbcChkReverseProxy.fill = GridBagConstraints.HORIZONTAL;
			
			
			panelProxy.add(getPanelLocalProxy(), gbcLocalProxyPanel);
			panelProxy.add(getChkReverseProxy(), gbcChkReverseProxy);
			panelProxy.add(getPanelReverseProxy(), gbcReverseProxyPanel);
			panelProxy.add(spaceLabel, gbcSpace);
		}
		return panelProxy;
	}

	/**
	 * This method initializes txtProxyIp
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtProxyIp() {
		if (txtProxyIp == null) {
			txtProxyIp = new JTextField();
			txtProxyIp.setText("");
		}
		return txtProxyIp;
	}

	/**
	 * This method initializes txtProxyIpSSL
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtReverseProxyIp() {
		if (txtReverseProxyIp == null) {
			txtReverseProxyIp = new JTextField();
		}
		return txtReverseProxyIp;
	}

	/**
	 * This method initializes txtProxyPort
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtProxyPort() {
		if (txtProxyPort == null) {
			txtProxyPort = new JTextField();
		}
		return txtProxyPort;
	}

	/**
	 * This method initializes txtProxyPortSSL
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtReverseProxyHttpPort() {
		if (txtReverseProxyHttpPort == null) {
			txtReverseProxyHttpPort = new JTextField();
		}
		return txtReverseProxyHttpPort;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new CardLayout());
		this.setName("Local proxy");
		this.setSize(391, 320);
		this.add(getPanelProxy(), getPanelProxy().getName());

		getChkReverseProxy().setVisible(Constant.isSP());
		getPanelReverseProxy().setVisible(Constant.isSP());

	}

	public void initParam(Object obj) {
		OptionsParam optionsParam = (OptionsParam) obj;
		ProxyParam proxyParam = optionsParam.getProxyParam();

		// Set local proxy parameters
		txtProxyIp.setText(proxyParam.getProxyIp());
		txtProxyPort.setText(Integer.toString(proxyParam.getProxyPort()));

		// Set reverse proxy parameters
		txtReverseProxyIp.setText(proxyParam.getReverseProxyIp());
		txtReverseProxyHttpPort.setText(Integer.toString(proxyParam.getReverseProxyHttpPort()));
		txtReverseProxyHttpsPort.setText(Integer.toString(proxyParam.getReverseProxyHttpsPort()));

		chkReverseProxy.setSelected(proxyParam.isUseReverseProxy());
		setReverseProxyEnabled(proxyParam.isUseReverseProxy());
	}

	public void validateParam(Object obj) throws Exception {

		try {
			Integer.parseInt(txtProxyPort.getText());
		} catch (NumberFormatException nfe) {
			txtProxyPort.requestFocus();
			throw new Exception("Invalid proxy port number.");
		}

		try {
			Integer.parseInt(txtReverseProxyHttpPort.getText());
		} catch (NumberFormatException nfe) {
			txtReverseProxyHttpPort.requestFocus();
			throw new Exception("Invalid reverse proxy port number.");
		}

		try {
			Integer.parseInt(txtReverseProxyHttpsPort.getText());
		} catch (NumberFormatException nfe) {
			txtReverseProxyHttpsPort.requestFocus();
			throw new Exception("Invalid reverse proxy port number.");
		}

	}

	public void saveParam(Object obj) throws Exception {
		OptionsParam optionsParam = (OptionsParam) obj;
		ProxyParam proxyParam = optionsParam.getProxyParam();
		
		int proxyPort = 0;
		int reverseProxyHttpPort = 0;
		int reverseProxyHttpsPort = 0;

		try {
			proxyPort = Integer.parseInt(txtProxyPort.getText());
		} catch (NumberFormatException nfe) {
			txtProxyPort.requestFocus();
			throw new Exception("Invalid proxy port number.");
		}

		try {
			reverseProxyHttpPort = Integer.parseInt(txtReverseProxyHttpPort.getText());
		} catch (NumberFormatException nfe) {
			txtReverseProxyHttpPort.requestFocus();
			throw new Exception("Invalid reverse proxy port number.");
		}

		try {
			reverseProxyHttpsPort = Integer.parseInt(txtReverseProxyHttpsPort.getText());
		} catch (NumberFormatException nfe) {
			txtReverseProxyHttpsPort.requestFocus();
			throw new Exception("Invalid reverse proxy port number.");
		}

		proxyParam.setProxyIp(txtProxyIp.getText());
		proxyParam.setProxyPort(proxyPort);
		proxyParam.setReverseProxyIp(txtReverseProxyIp.getText());
		proxyParam.setReverseProxyHttpPort(reverseProxyHttpPort);
		proxyParam.setReverseProxyHttpsPort(reverseProxyHttpsPort);
		proxyParam.setUseReverseProxy(getChkReverseProxy().isSelected());

	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getChkReverseProxy() {
		if (chkReverseProxy == null) {
			chkReverseProxy = new JCheckBox();
			chkReverseProxy.setText("Use reverse proxy");
			chkReverseProxy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setReverseProxyEnabled(getChkReverseProxy().isSelected());
				}
			});
		}
		return chkReverseProxy;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtReverseProxyHttpsPort() {
		if (txtReverseProxyHttpsPort == null) {
			txtReverseProxyHttpsPort = new JTextField();
		}
		return txtReverseProxyHttpsPort;
	}

	private void setReverseProxyEnabled(boolean isEnabled) {

		txtProxyIp.setEditable(!isEnabled);
		txtProxyPort.setEditable(!isEnabled);

		txtReverseProxyIp.setEditable(isEnabled);
		txtReverseProxyHttpPort.setEditable(isEnabled);
		txtReverseProxyHttpsPort.setEditable(isEnabled);

		if (isEnabled) {
			txtProxyIp.setBackground(panelProxy.getBackground());
			txtProxyPort.setBackground(panelProxy.getBackground());

			txtReverseProxyIp.setBackground(Color.WHITE);
			txtReverseProxyHttpPort.setBackground(Color.WHITE);
			txtReverseProxyHttpsPort.setBackground(Color.WHITE);
		} else {
			txtProxyIp.setBackground(Color.WHITE);
			txtProxyPort.setBackground(Color.WHITE);

			txtReverseProxyIp.setBackground(panelProxy.getBackground());
			txtReverseProxyHttpPort.setBackground(panelProxy.getBackground());
			txtReverseProxyHttpsPort.setBackground(panelProxy.getBackground());
		}
	}
}
