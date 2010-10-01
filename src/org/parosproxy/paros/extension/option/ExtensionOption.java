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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBoxMenuItem;

import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;


public class ExtensionOption extends ExtensionAdaptor {

	private JCheckBoxMenuItem menuViewImage = null;
	private OptionsConnectionPanel optionsConnectionPanel = null;
	private OptionsAuthenticationPanel optionsAuthenticationPanel = null;
	private OptionsCertificatePanel optionsCertificatePanel = null;
	private OptionsHttpHeaderPanel optionsHttpHeaderPanel = null;
	private OptionsLocalProxyPanel optionsLocalProxyPanel = null;
	private OptionsViewPanel optionsViewPanel = null;
	//TODO: ZAP options

    /**
     * 
     */
    public ExtensionOption() {
        super();
 		initialize();
    }

    /**
     * @param name
     */
    public ExtensionOption(String name) {
        super(name);
    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setName("ExtensionViewOption");
	}
	

	public void hook(ExtensionHook extensionHook) {
	    super.hook(extensionHook);
	    if (getView() != null) {
	        extensionHook.getHookMenu().addViewMenuItem(getMenuViewImage());
	        
	        extensionHook.getHookView().addOptionPanel(getOptionsConnectionPanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsLocalProxyPanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsAuthenticationPanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsCertificatePanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsViewPanel());
	        extensionHook.getHookView().addOptionPanel(getOptionsHttpHeaderPanel());
	    }
	}

	private JCheckBoxMenuItem getMenuViewImage() {
		if (menuViewImage == null) {
			menuViewImage = new JCheckBoxMenuItem();
			menuViewImage.setText("Enable Image in History");
			menuViewImage.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					getModel().getOptionsParam().getViewParam().setProcessImages(getMenuViewImage().getState() ? 1 : 0);
				}
			});
		}
		return menuViewImage;
	}
	 
	private OptionsConnectionPanel getOptionsConnectionPanel() {
		if (optionsConnectionPanel == null) {
			optionsConnectionPanel = new OptionsConnectionPanel();
		}
		return optionsConnectionPanel;
	}
  
	private OptionsAuthenticationPanel getOptionsAuthenticationPanel() {
		if (optionsAuthenticationPanel == null) {
			optionsAuthenticationPanel = new OptionsAuthenticationPanel();
		}
		return optionsAuthenticationPanel;
	}
  
	private OptionsCertificatePanel getOptionsCertificatePanel() {
		if (optionsCertificatePanel == null) {
			optionsCertificatePanel = new OptionsCertificatePanel();
		}
		return optionsCertificatePanel;
	}
  
	private OptionsLocalProxyPanel getOptionsLocalProxyPanel() {
		if (optionsLocalProxyPanel == null) {
			optionsLocalProxyPanel = new OptionsLocalProxyPanel();
		}
		return optionsLocalProxyPanel;
	}
 
	private OptionsViewPanel getOptionsViewPanel() {
		if (optionsViewPanel == null) {
			optionsViewPanel = new OptionsViewPanel();
		}
		return optionsViewPanel;
	}
	  
	private OptionsHttpHeaderPanel getOptionsHttpHeaderPanel() {
		if (optionsHttpHeaderPanel == null) {
			optionsHttpHeaderPanel = new OptionsHttpHeaderPanel();
		}
		return optionsHttpHeaderPanel;
	}
}
