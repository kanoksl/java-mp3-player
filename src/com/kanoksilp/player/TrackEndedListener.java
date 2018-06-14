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
public interface TrackEndedListener {

	/**
	 * Called when the current playing track has finished playing
	 * (the decoder has reached the last frame).
	 */
	void trackEnded();
}
