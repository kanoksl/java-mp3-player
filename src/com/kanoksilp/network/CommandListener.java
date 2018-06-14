/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.network;

/**
 *
 * @author Kanoksilp
 */
public interface CommandListener {

	/**
	 * Called when the server has received a command sent from the remote client.
	 * @param cmd Command text
	 */
	void receiveCommand(String cmd);

}
