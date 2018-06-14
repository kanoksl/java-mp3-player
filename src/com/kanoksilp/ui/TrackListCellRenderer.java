/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.ui;

import com.kanoksilp.data.Track;

import javax.swing.*;
import java.awt.*;

import static com.kanoksilp.JMPApplication.R;

/**
 * @author Kanoksilp
 */
public class TrackListCellRenderer extends JPanel implements ListCellRenderer {

	private final JLabel imageLabel;
	
	private final JPanel textPanel;
	
	private final JLabel titleLabel;
	private final JLabel infoLabel;
	
	// width/height of the album artwork thumbnails
	private static final int ARTWORK_SIZE = 48;

	private static final Color selectedBg = Color.decode(
			R.getString("listCellRenderer.selectionBg"));
	private static final Color selectedFg = Color.decode(
			R.getString("listCellRenderer.selectionFg"));

	public TrackListCellRenderer() {

		imageLabel = new JLabel();
		textPanel = new JPanel();
		titleLabel = new JLabel();
		infoLabel = new JLabel();

		//setMinimumSize(new java.awt.Dimension(118, 52));
		//setPreferredSize(new java.awt.Dimension(400, 52));
		this.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		imageLabel.setMinimumSize(new Dimension(ARTWORK_SIZE, ARTWORK_SIZE));
		imageLabel.setPreferredSize(new Dimension(ARTWORK_SIZE, ARTWORK_SIZE));

		textPanel.setLayout(new java.awt.GridLayout(2, 0, 0, 4));
		textPanel.setOpaque(true);

		titleLabel.setFont(new java.awt.Font(R.getString("font.list"), 0, 12));
		titleLabel.setText("Song Title");
		titleLabel.setOpaque(true);
		textPanel.add(titleLabel);

		infoLabel.setFont(new java.awt.Font(R.getString("font.list"), 0, 10));
		infoLabel.setText("Artist");
		infoLabel.setOpaque(true);
		textPanel.add(infoLabel);

		this.add(imageLabel);
		this.add(textPanel);

		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
												  int index, boolean isSelected,
												  boolean cellHasFocus) {
		
		// cast the value to a Track object and get its properties
		titleLabel.setText(((Track) value).getTitle());
		infoLabel.setText(String.format("[%s]  %s :: %s",
				((Track) value).getTrackLengthString(),
				((Track) value).getArtist(),
				((Track) value).getAlbum()));
		// set the artwork thumbnail
		imageLabel.setIcon(((Track) value).getArtworkIcon(ARTWORK_SIZE));
		if (imageLabel.getIcon() == null) {    // default thumbnail if no artwork
			imageLabel.setIcon(new ImageIcon(getClass().getResource(
					"/com/kanoksilp/ui/resource/track48.png")));
		}

		if (isSelected) {
			// if this cell is selected, set bg and fg
			this.adjustColors(selectedBg, selectedFg,
					this, textPanel, titleLabel, infoLabel);
		} else {
			// if not selected, set to default colors
			this.adjustColors(list.getBackground(), list.getForeground(),
					this, textPanel, titleLabel, infoLabel);
			// set the info text color
			infoLabel.setForeground(Color.GRAY);
		}

		return this;
	}

	private void adjustColors(Color bg, Color fg, Component... components) {
		for (Component c : components) {
			c.setForeground(fg);
			c.setBackground(bg);
		}
	}

}
