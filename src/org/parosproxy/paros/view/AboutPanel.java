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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.parosproxy.paros.Constant;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class AboutPanel extends JPanel {

	private static final long serialVersionUID = 8893525515508199864L;
	
	private static final String VERSION = "Version " + Constant.PROGRAM_VERSION;
	private static final String COPYRIGHT = "<html><body>"
				+ "<p>Copyright (C) 2007-2010 Andiparos Project</p>"
				+ "<p>Copyright (C) 2007-2010 Compass Security AG</p>"
				+ "<p>Copyright (C) 2003-2005 Chinotec Technologies Company</p></body></html>";
	
	private static final String DISCLAIMER = "<html><body><p>Disclaimer: You should only use this software to "
				+ "test the security of your own web application or those you are authorized to do so. "
				+ "The creators of this software do not take responsibility for any problems "
				+ "in relation to running Andiparos against any applications or machines.<p></body></html>";
	
	private static final String LICENSE_DETAIL = "<html><body><p>"
		+ "This program is free software; you can redistribute it and/or "
		+ "modify it under the terms of the GNU General Public License "
		+ "as published by the Free Software Foundation; either version 2 "
		+ "of the License, or (at your option) any later version." + "</p></p>"
		+ "This program is distributed in the hope that it will be useful, "
		+ "but WITHOUT ANY WARRANTY; without even the implied warranty of "
		+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the "
		+ "GNU General Public License for more details."
		+ "</p><p>"
		+ "You should have received a copy of the GNU General Public License "
		+ "along with this program; if not, write to the Free Software Foundation"
		+ ", Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA."
		+ "</p></body></html>";

	private static final String OTHER_LICENSE = "<html><body>"
		+ "<p>This product is based on Paros Proxy that was developed by "
		+ "Chinotec Technologies Company which is licensed under the Clarified "
		+ "Artistic License."
		+ "</p><p>"
		+ "Moreover, it also contains code of WebScarab that was developed by "
		+ "R. Dawes, licensed under the GPLv2."
		+ "</p><p>"
		+ "It also includes Software of the Apache Software Foundation "
		+ "<a>http://www.apache.org</a> licensed under Apache License 2.0. "
		+ "HSQLDB is licensed under BSD license."
		+ "</p><p>"
		+ "Andiparos also contains BeanShell, which is lisenced unter LGPL."
		+ "</p><p>"
		+ "The Copyrights of these software belong to their respective owners."
		+ "</p></body></html>";
	

	public AboutPanel() {
		super();
		initialize();
	}

	public AboutPanel(boolean arg0) {
		super(arg0);
		initialize();
	}

	public AboutPanel(LayoutManager arg0) {
		super(arg0);
		initialize();
	}


	public AboutPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		
		JLabel lblDisclaimer = new JLabel();
		JLabel lblCopyright = new JLabel();
		JLabel lblOtherCopyright = new JLabel();
		JLabel lblVersion = new JLabel();
		JLabel lblCopyrightDetail = new JLabel();
		JLabel lblLogo = new JLabel();

		this.setLayout(gbl);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setPreferredSize(new Dimension(470, 550));
		
		
		lblLogo.setText("");
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/resource/logos/andiparos-logo.png")));
		lblLogo.setName("lblLogo");
		
		lblVersion.setText(VERSION);
		lblVersion.setFont(new Font("Default", Font.PLAIN, 18));
		lblVersion.setName("lblVersion");
		lblVersion.setBackground(Color.white);
		
		lblCopyright.setText(COPYRIGHT);
		lblCopyright.setFont(new Font("Default", Font.PLAIN, 11));
		lblCopyright.setName("lblCopyright");
		lblCopyright.setBackground(Color.white);
		
		lblDisclaimer.setText(DISCLAIMER);
		lblDisclaimer.setName("lblDisclaimer");
		lblDisclaimer.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblDisclaimer.setBackground(Color.white);
		
		lblCopyrightDetail.setText(LICENSE_DETAIL);
		lblCopyrightDetail.setFont(new Font("Default", Font.PLAIN, 11));
		lblCopyrightDetail.setName("lblCopyrightDetail");
		lblCopyrightDetail.setBackground(Color.white);
		
		lblOtherCopyright.setText(OTHER_LICENSE);
		lblOtherCopyright.setName("lblOtherCopyright");
		lblOtherCopyright.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblOtherCopyright.setBackground(Color.white);
		
		
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblLogo, gridBagConstraints1);
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblVersion, gridBagConstraints1);
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblCopyright, gridBagConstraints1);
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 3;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblDisclaimer, gridBagConstraints1);
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 4;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblCopyrightDetail, gridBagConstraints1);
		
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 5;
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.insets = new Insets(5, 15, 5, 15);
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		this.add(lblOtherCopyright, gridBagConstraints1);
		
	}
}
