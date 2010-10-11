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

package org.parosproxy.paros.extension.encoder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import org.parosproxy.paros.extension.AbstractDialog;
import org.parosproxy.paros.extension.ViewDelegate;
import org.parosproxy.paros.utils.FontHelper;


public class EncoderDialog extends AbstractDialog {

	private static final long serialVersionUID = 2733002714754868517L;
	
	private JPanel jPanel = null;
	private JPanel jPanelEncodeText = null;
	private JPanel jPanelDecodeText = null;
	private JPanel jPanelEncodeButtons = null;
	
	private JScrollPane jScrollPaneEncode = null;
	private JScrollPane jScrollPaneDecode = null;
	private JTextArea txtEncode = null;
	private JTextArea txtDecode = null;
	private JButton btnMD5Hash = null;
	private JButton btnURLEncode = null;
	private JButton btnBase64Encode = null;
	private JButton btnSHA1Hash = null;
	private JButton btnURLDecode = null;
	private JButton btnBase64Decode = null;
	private JButton btnIllegalUTF8Encode = null;
	private JPanel jPanelEncode = null;
	private JPanel jPanelDecodeButtons = null;
	private JPanel jPanelUTF8EncodeButtons = null;
	private JPanel jPanelDecode = null;
	private JComboBox cboxIllegalUTF8Bytes = null;

	private Encoder encoder = null;
	private ViewDelegate view = null;


	public EncoderDialog() {
		super();
		initialize();
	}

	public EncoderDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	private void initialize() {
		this.setTitle("Encode / Decode / Hash");
		this.setContentPane(getJPanel());
		this.setPreferredSize(new Dimension(800,600));
		this.setMinimumSize(this.getPreferredSize());
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			
			GridBagConstraints gbcTextEncode = new GridBagConstraints();
			GridBagConstraints gbcTextDecode = new GridBagConstraints();
			
			gbcTextDecode.gridx = 0;
			gbcTextDecode.gridy = 0;
			gbcTextDecode.weightx = 1.0D;
			gbcTextDecode.weighty = 0.5D;
			gbcTextDecode.insets = new Insets(2, 2, 2, 2);
			gbcTextDecode.anchor = GridBagConstraints.NORTHWEST;
			gbcTextDecode.fill = GridBagConstraints.BOTH;
			
			gbcTextEncode.gridx = 0;
			gbcTextEncode.gridy = 1;
			gbcTextEncode.weightx = 1.0D;
			gbcTextEncode.weighty = 0.5D;
			gbcTextEncode.insets = new Insets(2, 2, 2, 2);
			gbcTextEncode.anchor = GridBagConstraints.NORTHWEST;
			gbcTextEncode.fill = GridBagConstraints.BOTH;
			
			jPanel.add(getJPanelEncodeText(), gbcTextDecode);
			jPanel.add(getJPanelDecodeText(), gbcTextEncode);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEncodeText() {
		if (jPanelEncodeText == null) {
			jPanelEncodeText = new JPanel();
			jPanelEncodeText.setLayout(new GridBagLayout());
			jPanelEncodeText.setBorder(BorderFactory.createTitledBorder(
					null, "Enter plain text below to be encoded/hashed",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.PLAIN, 11),
					Color.black));
			
			GridBagConstraints gbcEncodeScrollPane = new GridBagConstraints();
			GridBagConstraints gbcButtonPlaceholder = new GridBagConstraints();
			
			gbcEncodeScrollPane.weightx = 1.0;
			gbcEncodeScrollPane.weighty = 1.0;
			gbcEncodeScrollPane.fill = GridBagConstraints.BOTH;
			gbcEncodeScrollPane.anchor = GridBagConstraints.NORTHWEST;
			gbcEncodeScrollPane.gridheight = 1;
			gbcEncodeScrollPane.gridx = 0;
			gbcEncodeScrollPane.gridy = 0;
			gbcEncodeScrollPane.insets = new Insets(2, 2, 2, 2);
			
			gbcButtonPlaceholder.anchor = GridBagConstraints.NORTHWEST;
			gbcButtonPlaceholder.gridx = 1;
			gbcButtonPlaceholder.gridy = 0;
			gbcButtonPlaceholder.insets = new Insets(2, 2, 2, 2);
			
			jPanelEncodeText.add(getJScrollPaneEncode(), gbcEncodeScrollPane);
			jPanelEncodeText.add(getJPanelEncode(), gbcButtonPlaceholder);
		}
		return jPanelEncodeText;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDecodeText() {
		if (jPanelDecodeText == null) {
			jPanelDecodeText = new JPanel();
			jPanelDecodeText.setLayout(new GridBagLayout());
			jPanelDecodeText.setBorder(BorderFactory.createTitledBorder(
				null, "Enter text below to be decoded",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("Dialog", Font.PLAIN, 11),
				Color.black));
			
			GridBagConstraints gbcDecodeScrollPane = new GridBagConstraints();
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			
			gbcDecodeScrollPane.gridx = 0;
			gbcDecodeScrollPane.gridy = 0;
			gbcDecodeScrollPane.weightx = 1.0;
			gbcDecodeScrollPane.weighty = 1.0;
			gbcDecodeScrollPane.fill = GridBagConstraints.BOTH;
			gbcDecodeScrollPane.insets = new Insets(2, 2, 2, 2);
			gbcDecodeScrollPane.anchor = GridBagConstraints.NORTHWEST;
			gbcDecodeScrollPane.gridheight = 1;
			
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints9.anchor = GridBagConstraints.NORTHWEST;
			
			jPanelDecodeText.add(getJScrollPaneDecode(), gbcDecodeScrollPane);
			jPanelDecodeText.add(getJPanelDecode(), gridBagConstraints9);
		}
		return jPanelDecodeText;
	}


	private JScrollPane getJScrollPaneEncode() {
		if (jScrollPaneEncode == null) {
			jScrollPaneEncode = new JScrollPane();
			jScrollPaneEncode.setViewportView(getTxtEncode());
			jScrollPaneEncode.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return jScrollPaneEncode;
	}


	private JScrollPane getJScrollPaneDecode() {
		if (jScrollPaneDecode == null) {
			jScrollPaneDecode = new JScrollPane();
			jScrollPaneDecode.setViewportView(getTxtDecode());
		}
		return jScrollPaneDecode;
	}


	private JTextArea getTxtEncode() {
		if (txtEncode == null) {
			txtEncode = new JTextArea();
			txtEncode.setLineWrap(true);
			txtEncode.setFont(FontHelper.getCodeFont());
			txtEncode.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
						// right mouse button
						view.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
					}
				}

			});

		}
		return txtEncode;
	}


	private JTextArea getTxtDecode() {
		if (txtDecode == null) {
			txtDecode = new JTextArea();
			txtDecode.setLineWrap(true);
			txtDecode.setFont(FontHelper.getCodeFont());
		}
		return txtDecode;
	}


	private JButton getBtnMD5Hash() {
		if (btnMD5Hash == null) {
			btnMD5Hash = new JButton();
			btnMD5Hash.setText("MD5 Hash");
			btnMD5Hash.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDecode.setText("");
					try {
						String result = getEncoder().getHexString(
										getEncoder().getHashMD5(
										getEncoder().getBytes(
										txtEncode.getText())));
						
						txtDecode.setText(result);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}

				}
			});

		}
		return btnMD5Hash;
	}

	private JButton getBtnURLEncode() {
		if (btnURLEncode == null) {
			btnURLEncode = new JButton();
			btnURLEncode.setText("URL Encode");
			btnURLEncode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDecode.setText("");
					txtDecode.setText(getEncoder().getURLEncode(txtEncode.getText()));
				}
			});
		}
		return btnURLEncode;
	}

	private JButton getBtnBase64Encode() {
		if (btnBase64Encode == null) {
			btnBase64Encode = new JButton();
			btnBase64Encode.setText("Base64 Encode");
			btnBase64Encode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDecode.setText("");
					txtDecode.setText(getEncoder().getBase64Encode(txtEncode.getText()));

				}
			});
		}
		return btnBase64Encode;
	}


	private JButton getBtnSHA1Hash() {
		if (btnSHA1Hash == null) {
			btnSHA1Hash = new JButton();
			btnSHA1Hash.setText("SHA1 Hash");
			btnSHA1Hash.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDecode.setText("");
					try {
						String result = getEncoder().getHexString(
										getEncoder().getHashSHA1(
										getEncoder().getBytes(
										txtEncode.getText())));
						
						txtDecode.setText(result);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnSHA1Hash;
	}


	private JButton getBtnURLDecode() {
		if (btnURLDecode == null) {
			btnURLDecode = new JButton();
			btnURLDecode.setText("URL Decode");
			btnURLDecode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtEncode.setText("");
					String result = getEncoder().getURLDecode(txtDecode.getText());
					txtEncode.setText(result);
				}
			});
		}
		return btnURLDecode;
	}


	private JButton getBtnBase64Decode() {
		if (btnBase64Decode == null) {
			btnBase64Decode = new JButton();
			btnBase64Decode.setText("Base64 Decode");
			btnBase64Decode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtEncode.setText("");
					txtEncode.setText(getEncoder().getBase64Decode(txtDecode.getText()));
				}
			});
		}
		return btnBase64Decode;
	}
	

	private JButton getBtnIllegalUTF8Encode() {
		if (btnIllegalUTF8Encode == null) {
			btnIllegalUTF8Encode = new JButton();
			btnIllegalUTF8Encode.setText("Illegal UTF8");
			btnIllegalUTF8Encode.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDecode.setText("");
					Integer bytesToEncode = (Integer) cboxIllegalUTF8Bytes.getSelectedItem();
					txtDecode.setText(getEncoder().getIllegalUTF8Encode(txtEncode.getText(), bytesToEncode));
				}
			});
		}
		return btnIllegalUTF8Encode;
	}
	
	private JComboBox getCboxIllegalUTF8Bytes() {
		if (cboxIllegalUTF8Bytes == null) {
			Integer [] bytesCount = {2,3,4};
			cboxIllegalUTF8Bytes = new JComboBox(bytesCount);
			cboxIllegalUTF8Bytes.setSelectedIndex(0);
			cboxIllegalUTF8Bytes.setMaximumSize(cboxIllegalUTF8Bytes.getPreferredSize()); 
		}
		return cboxIllegalUTF8Bytes;
	}


	private JPanel getJPanelEncode() {
		if (jPanelEncode == null) {
			GridBagLayout gbl = new GridBagLayout();
			
			jPanelEncode = new JPanel();
			jPanelEncode.setLayout(gbl);
			jPanelEncode.setPreferredSize(new Dimension(200, 200));
			
			GridBagConstraints gbcButtons = new GridBagConstraints();
			GridBagConstraints gbcSpace = new GridBagConstraints();
			
			gbcButtons.gridx = 0;
			gbcButtons.gridy = 0;
			gbcButtons.weightx = 1.0;
			gbcButtons.weighty = 1.0;
			gbcButtons.insets = new Insets(2, 2, 2, 2);
			gbcButtons.anchor = GridBagConstraints.NORTHWEST;
			gbcButtons.fill = GridBagConstraints.HORIZONTAL;
			
			gbcSpace.gridx = 0;
			gbcSpace.gridy = 1;
			gbcSpace.insets = new Insets(2, 2, 2, 2);
			gbcSpace.anchor = GridBagConstraints.NORTHWEST;
			gbcSpace.fill = GridBagConstraints.BOTH;
			
			
			JLabel spaceLabel = new JLabel();
	        spaceLabel.setText(" ");
	        
	        jPanelEncode.add(getJPanelEncodeButtons(), gbcButtons);
	        jPanelEncode.add(spaceLabel, gbcSpace);
		}
		return jPanelEncode;
	}
	
	private JPanel getJPanelEncodeButtons() {
		if (jPanelEncodeButtons == null) {
			jPanelEncodeButtons = new JPanel();
			GridLayout gridLayout6 = new GridLayout();
			jPanelEncodeButtons.setLayout(gridLayout6);
			
			gridLayout6.setRows(5);
			gridLayout6.setColumns(1);
			gridLayout6.setVgap(3);
			gridLayout6.setHgap(3); 
			
			jPanelEncodeButtons.add(getBtnURLEncode(), null);
			jPanelEncodeButtons.add(getBtnBase64Encode(), null);
			jPanelEncodeButtons.add(getJPanelEncodeUTF8Buttons(), null);
			jPanelEncodeButtons.add(getBtnSHA1Hash(), null);
			jPanelEncodeButtons.add(getBtnMD5Hash(), null);
		}
		return jPanelEncodeButtons;
	}

	private JPanel getJPanelEncodeUTF8Buttons() {
		if (jPanelUTF8EncodeButtons == null) {
			jPanelUTF8EncodeButtons = new JPanel();
			
			jPanelUTF8EncodeButtons.setLayout(new BoxLayout(jPanelUTF8EncodeButtons, BoxLayout.X_AXIS));
			jPanelUTF8EncodeButtons.setPreferredSize(new Dimension(200,200));
			
			jPanelUTF8EncodeButtons.add(getCboxIllegalUTF8Bytes(), null);
			jPanelUTF8EncodeButtons.add(getBtnIllegalUTF8Encode(), null);
			
		}
		return jPanelUTF8EncodeButtons;
	}
	
	private JPanel getJPanelDecodeButtons() {
		if (jPanelDecodeButtons == null) {
			jPanelDecodeButtons = new JPanel();
			GridLayout gl = new GridLayout();
			jPanelDecodeButtons.setLayout(gl);
			
			gl.setRows(2);
			gl.setColumns(1);
			gl.setVgap(3);
			gl.setHgap(3); 
			
			jPanelDecodeButtons.add(getBtnURLDecode(), null);
			jPanelDecodeButtons.add(getBtnBase64Decode(), null);
		}
		return jPanelDecodeButtons;
	}
	
	private JPanel getJPanelDecode() {
		if (jPanelDecode == null) {
			GridBagLayout gbl = new GridBagLayout();
			
			jPanelDecode = new JPanel();
			jPanelDecode.setLayout(gbl);
			jPanelDecode.setPreferredSize(new Dimension(200, 200));
			
			GridBagConstraints gbcButtons = new GridBagConstraints();
			GridBagConstraints gbcSpace = new GridBagConstraints();
			
			gbcButtons.gridx = 0;
			gbcButtons.gridy = 0;
			gbcButtons.weightx = 1.0;
			gbcButtons.weighty = 1.0;
			gbcButtons.insets = new Insets(2, 2, 2, 2);
			gbcButtons.anchor = GridBagConstraints.NORTHWEST;
			gbcButtons.fill = GridBagConstraints.HORIZONTAL;
			
			
			gbcSpace.gridx = 0;
			gbcSpace.gridy = 1;
			gbcSpace.insets = new Insets(2, 2, 2, 2);
			gbcSpace.anchor = GridBagConstraints.NORTHWEST;
			gbcSpace.fill = GridBagConstraints.BOTH;
			
			JLabel spaceLabel = new JLabel();
	        spaceLabel.setText(" ");
	        
	        jPanelDecode.add(getJPanelDecodeButtons(), gbcButtons);
	        jPanelDecode.add(spaceLabel, gbcSpace);
		}
		return jPanelDecode;
	}

	private Encoder getEncoder() {
		if (encoder == null) {
			encoder = new Encoder();
		}
		return encoder;
	}

	void setView(ViewDelegate view) {
		this.view = view;
	}
}
