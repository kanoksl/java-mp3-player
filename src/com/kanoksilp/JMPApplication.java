/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp;

import com.kanoksilp.network.MediaControlServer;
import com.kanoksilp.network.ServerLogger;
import com.kanoksilp.ui.MainWindow;

import java.util.ResourceBundle;

/**
 * @author Kanoksilp
 */
public class JMPApplication {

	// a resource bundle for referencing various UI-related values
	public static final ResourceBundle R
			= ResourceBundle.getBundle("com/kanoksilp/ui/resource/Resources");

	private static final MainWindow mainWindow = new MainWindow();

	public static MainWindow getMainWindow() {
		return mainWindow;
	}
	
	public static void main(String[] args) {
		// display the main window
		mainWindow.loadDefaultPlaylist();
		mainWindow.setVisible(true);
	}

	// <editor-fold desc="Server-Related Codes">
	private static final String MCS_THREADNAME = "MediaControlServerThread";
	private static final int MCS_PORT_NUMBER = 10010;
	private static MediaControlServer mcs;

	public static void createServer() {
		// create a new server object
		mcs = new MediaControlServer(MCS_THREADNAME, MCS_PORT_NUMBER);
		mcs.setCommandListener(mainWindow);
		mcs.setServerStatusWatcher(mainWindow);
	}

	public static void startServer() {
		mcs.start();	// tell the server to start
	}

	public static void stopServer() {
		mcs.stop();		// tell the server to stop
	}

	public static void setServerLogger(ServerLogger logger) {
		mcs.setServerLogger(logger);
	}
	// </editor-fold>
}
