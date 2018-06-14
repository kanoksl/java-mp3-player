/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.player;

/**
 *
 * @author Kanoksilp
 */
public class TrackEndedNotifier {

	private TrackEndedListener listener;

	public TrackEndedNotifier(TrackEndedListener listener) {
		this.listener = listener;
	}

	public void trackEnded() {
		listener.trackEnded();
	}

}
