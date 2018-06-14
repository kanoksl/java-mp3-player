/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.network;

import com.kanoksilp.network.MediaControlServer.ServerStatus;

/**
 *
 * @author Kanoksilp
 */
public interface ServerStatusWatcher {

	/**
	 * Called when the server's status has changed.
	 * @param status Current status of the server.
	 */
	void updateServerStatus(ServerStatus status);
}
