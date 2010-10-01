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
package org.parosproxy.paros.extension.trap;

import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;


import org.parosproxy.paros.Constant;
import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.view.HttpPanel;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TrapPanel extends HttpPanel {

	private static final long serialVersionUID = 6510788884698514729L;

	private JPanel panelCommand = null;
	private JCheckBox chkTrapRequest = null;
	private JCheckBox chkTrapRequestOnce = null;
	private JCheckBox chkTrapResponse = null;
	
	private JButton btnContinue = null;
	private JButton btnDrop = null;
	private JButton btnContinueAndTag = null;
	
	private boolean isContinue = false;
	private boolean flagPackage = false;
	
    public TrapPanel() {
        super();
 		initialize();
    }

    /**
     * @param isEditable
     */
    public TrapPanel(boolean isEditable) {
        super(isEditable);
 		initialize();
    }
	
	/**
	 * @return Returns the isContinue.
	 */
	public boolean isContinue() {
		return isContinue;
	}
	/**
	 * @param isContinue The isContinue to set.
	 */
	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}
	
    /**
     * @return Returns the flagPackage.
     */
    public boolean flagPackage() {
        return flagPackage;
    }

    /**
     * @param flagPackage The flagPackage to set.
     */
    public void setFlagPackage(boolean flagPackage) {
        this.flagPackage = flagPackage;
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		getPanelOption().add(getPanelCommand(), "");
	}
	
	/**
	 * This method initializes panelCommand	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelCommand() {
		if (panelCommand == null) {
			
			panelCommand = new JPanel();
			panelCommand.setLayout(new BoxLayout(panelCommand,BoxLayout.X_AXIS));
			panelCommand.setName("Command");
			
			Box box = Box.createHorizontalBox();
			box.add(getChkTrapRequest());
			box.add(getChkTrapResponse());
			box.add(getChkTrapRequestOnce());
			box.add(Box.createGlue());
			box.add(getBtnContinueAndTag());
			box.add(getBtnContinue());
			box.add(getBtnDrop());
			
			panelCommand.add(box);
			
		}
		return panelCommand;
	}

	public JCheckBox getChkTrapRequestOnce() {
		if (chkTrapRequestOnce == null) {
			chkTrapRequestOnce = new JCheckBox();
			chkTrapRequestOnce.setText(Constant.messages.getString("trap.methods.one_request"));
			chkTrapRequestOnce.addItemListener(new ItemListener() { 
				public void itemStateChanged(ItemEvent e) {    

					if (!chkTrapRequest.isSelected() && !chkTrapResponse.isSelected() && !chkTrapRequestOnce.isSelected()) {
					    Control.getSingleton().getProxy().setSerialize(false);
					} else {
					    Control.getSingleton().getProxy().setSerialize(true);
					}
					
				}
			});
		}
		return chkTrapRequestOnce;
	}

	/**
	 * This method initializes chkTrapRequest	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	public JCheckBox getChkTrapRequest() {
		if (chkTrapRequest == null) {
			chkTrapRequest = new JCheckBox();
			chkTrapRequest.setText(Constant.messages.getString("trap.methods.request"));
			chkTrapRequest.addItemListener(new ItemListener() { 
				public void itemStateChanged(ItemEvent e) {    

					if (!chkTrapRequest.isSelected() && !chkTrapResponse.isSelected() && !chkTrapRequestOnce.isSelected()) {
					    Control.getSingleton().getProxy().setSerialize(false);
					} else {
					    Control.getSingleton().getProxy().setSerialize(true);
					}
					
				}
			});
		}
		return chkTrapRequest;
	}

	/**
	 * This method initializes chkTrapResponse	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	public JCheckBox getChkTrapResponse() {
		if (chkTrapResponse == null) {
			chkTrapResponse = new JCheckBox();
			chkTrapResponse.setText(Constant.messages.getString("trap.methods.response"));
			chkTrapResponse.addItemListener(new ItemListener() { 

				public void itemStateChanged(ItemEvent e) {    

					if (!chkTrapRequest.isSelected() && !chkTrapResponse.isSelected() && !chkTrapRequestOnce.isSelected()) {
					    Control.getSingleton().getProxy().setSerialize(false);
					} else {
					    Control.getSingleton().getProxy().setSerialize(true);
					}							
				}
			});
		}
		return chkTrapResponse;
	}

	/**
	 * This method initializes btnContinue	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnContinue() {
		if (btnContinue == null) {
			btnContinue = new JButton();
			btnContinue.setText(Constant.messages.getString("trap.actions.continue"));
			btnContinue.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent ae) {  
					setContinue(true);
				}				
			});
		}
		return btnContinue;
	}

	/**
     * This method initializes btnContinue  
     *  
     * @return javax.swing.JButton  
     */
	private JButton getBtnContinueAndTag() {
		if (btnContinueAndTag == null) {
			btnContinueAndTag = new JButton();
			btnContinueAndTag.setText(Constant.messages.getString("trap.actions.mark"));
			btnContinueAndTag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					setFlagPackage(true);
					setContinue(true);
				}
			});
		}
		return btnContinueAndTag;
	}
	
	/**
	 * This method initializes btnDrop	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnDrop() {
		if (btnDrop == null) {
			btnDrop = new JButton();
			btnDrop.setText(Constant.messages.getString("trap.actions.drop"));
			btnDrop.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
				    TrapPanel.this.setMessage("","", false);
				    setContinue(true);
				}
			});
		}
		return btnDrop;
	}
  }
