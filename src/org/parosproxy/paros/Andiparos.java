/*
 * Created on May 19, 2004
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
package org.parosproxy.paros;

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.model.Model;
import org.parosproxy.paros.network.HttpSender;
import org.parosproxy.paros.network.SSLConnector;
import org.parosproxy.paros.view.AboutWindow;
import org.parosproxy.paros.view.LicenseFrame;
import org.parosproxy.paros.view.View;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Andiparos {

	static {

		// set SSLConnector as socketfactory in HttpClient.
		ProtocolSocketFactory sslFactory = null;
		try {
			Protocol protocol = Protocol.getProtocol("https");
			sslFactory = protocol.getSocketFactory();
		} catch (Exception e) {
			// ZAP: Print the exception - log not yet initialised
	    	e.printStackTrace();
		}
		if (sslFactory == null || !(sslFactory instanceof SSLConnector)) {
			Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) new SSLConnector(), 443));
		}
	}

	private static Log log = null;

	public static void main(String[] args) throws Exception {
		Andiparos andiparos = new Andiparos();
		andiparos.init(args);
		Constant.getInstance();
		String msg = Constant.PROGRAM_NAME + " " + Constant.PROGRAM_VERSION + " started.";
		log = LogFactory.getLog(Andiparos.class);
		log.info(msg);

		try {
			andiparos.run();
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw e;
		}

	}

	private CommandLine cmdLine = null;

	/**
	 * Initialization without dependence on any data model nor view creation.
	 * 
	 * @param args
	 */
	private void init(String[] args) {
		
		try {
			cmdLine = new CommandLine(args);
		} catch (Exception e) {
			System.out.println(CommandLine.getHelpGeneral());
			System.exit(1);
		}

		Locale.setDefault(Locale.ENGLISH);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		// Andiparos: Use Nimbus if it is enforced and installed
		if(Constant.useNimbus) {
			try {
		        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		            if ("Nimbus".equals(info.getName())) {
		                UIManager.setLookAndFeel(info.getClassName());
		                break;
		            }
		        }
		    } catch (UnsupportedLookAndFeelException e) {
		        // handle exception
		    } catch (ClassNotFoundException e) {
		        // handle exception
		    } catch (InstantiationException e) {
		        // handle exception
		    } catch (IllegalAccessException e) {
		        // handle exception
		    }
		}
	}

	private void run() throws Exception {
		Model.getSingleton().init();
		
		String userAgent = Model.getSingleton().getOptionsParam().getHttpHeaderParam().getCustomUserAgent();
		HttpSender.setUserAgent(userAgent);
		
		boolean showSplash = Model.getSingleton().getOptionsParam().getViewParam().isShowSplash();

		AboutWindow aboutWindow = null;
		
		if (cmdLine.isGUI()) {
			showLicense();
			if (showSplash) {
				aboutWindow = new AboutWindow();
				aboutWindow.setVisible(true);
				Thread.sleep(1000);
			}
		}

		Model.getSingleton().getOptionsParam().setGUI(cmdLine.isGUI());

		if (Model.getSingleton().getOptionsParam().isGUI()) {
			runGUI();
			
			if (showSplash) {	
				aboutWindow.dispose();
			}
		} else {
			runCommandLine();
		}

	}

	private void runCommandLine() {
		int rc = 0;
		String help = "";

		Control.initSingletonWithoutView();
		Control control = Control.getSingleton();

		// no view initialization

		try {
			control.getExtensionLoader().hookCommandLineListener(cmdLine);
			if (cmdLine.isEnabled(CommandLine.HELP) || cmdLine.isEnabled(CommandLine.HELP2)) {
				help = cmdLine.getHelp();
				System.out.println(help);
			} else {

				control.runCommandLineNewSession(cmdLine.getArgument(CommandLine.NEW_SESSION));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			rc = 0;
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e.getMessage());
			rc = 1;
		} finally {
			control.shutdown(false);
			log.info(Constant.PROGRAM_TITLE + " terminated.");
		}
		System.exit(rc);
	}

	private void runGUI() throws ClassNotFoundException, Exception {

		Control.initSingletonWithView();
		Control control = Control.getSingleton();
		View view = View.getSingleton();
		view.postInit();
		view.getMainFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
		view.getMainFrame().setVisible(true);
		view.setStatus("");

		control.getMenuFileControl().newSession(false);

	}

	private void showLicense() {
		if (!(new File(Constant.getInstance().ACCEPTED_LICENSE)).exists()) {

			LicenseFrame license = new LicenseFrame();
			license.setVisible(true);
			while (!license.isAccepted()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}

		try {
			FileWriter fo = new FileWriter(Constant.getInstance().ACCEPTED_LICENSE);
			fo.close();
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(new JFrame(), "Unknown Error. Please report this to the project.");
			System.exit(1);
		}
	}
}
