/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.util;

/**
 * Useful codes for testing purposes.
 *
 * @author Kanoksilp
 */
public class TestingUtil {

	/**
	 * Equivalent to: System.out.println(message);
	 *
	 * @param message
	 */
	public static void out(Object message) {
		System.out.println(message);
	}

	/**
	 * Equivalent to: System.out.printf(format, args);
	 *
	 * @param format
	 * @param args
	 */
	public static void out(String format, Object... args) {
		System.out.printf(format, args);
	}

	/**
	 * Equivalent to: System.out.println(message); with leading tabs
	 *
	 * @param indent  Number of leading tabs.
	 * @param message
	 */
	public static synchronized void out(int indent, Object message) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.println(message);
	}

	/**
	 * Equivalent to: System.err.println(message);
	 *
	 * @param message
	 */
	public static void err(Object message) {
		System.err.println(message);
	}

	/**
	 * Equivalent to: System.err.printf(format, args);
	 *
	 * @param format
	 * @param args
	 */
	public static void err(String format, Object... args) {
		System.err.printf(format, args);
	}
}
