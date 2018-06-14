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
public interface ServerLogger {

	/**
	 * Append a text to the end of current log.
	 * @param log Text to append.
	 */
	void append(String log);

	/**
	 * Append a text to the end of current log and add a new line.
	 * @param line Text to append as a line.
	 */
	void appendln(String line);

}
