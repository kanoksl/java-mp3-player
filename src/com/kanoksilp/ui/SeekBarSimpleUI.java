/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

import static com.kanoksilp.JMPApplication.R;

/**
 * @author Kanoksilp
 */
public class SeekBarSimpleUI extends BasicProgressBarUI {

	private Color color;

	@Override
	protected void installDefaults() {
		this.color = Color.decode(R.getString("mainWindow.seekBarColor"));
	}

	@Override
	protected void paintDeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth();
		int barRectHeight = progressBar.getHeight();

		if (barRectWidth <= 0 || barRectHeight <= 0) {
			return; // nothing to draw
		}

		// amount of progress to draw
		int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);

		// draw the cells
		if (amountFull > 0) {
			// set g2's stroke to have width = progressBar's height
			g2.setStroke(new BasicStroke((float) barRectHeight,
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			// draw a line from the point middle of left edge
			// to a point that represent the amount
			g2.drawLine(b.left, (barRectHeight / 2) + b.top,
					amountFull + b.left, (barRectHeight / 2) + b.top);

		}
	}
}
