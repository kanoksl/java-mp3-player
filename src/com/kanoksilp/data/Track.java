/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.data;

import org.imgscalr.Scalr;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * @author Kanoksilp
 */
public class Track {

	// file-related fields
	private String filePath;
	private String fileName;

	private AudioFile audioFile = null;

	// tag-related fields
	private String title;
	private String artist;
	private String album;
	private String year;

	// header-related fields
	private int trackLength;	// seconds
	private int bitRate;		// Kbps
	private int sampleRate;		// Hz
	private String channels;
	private String encoding;

	// is this Track exist and can be played?
	private boolean valid;
	
	// create a new Track object from a file
	public Track(String filePath)
			throws FileNotFoundException {
		//File file = new File(filePath);
		this(new File(filePath));
	}
	
	public Track(File file) 
		throws FileNotFoundException {
		if (file.exists()) {
			// if the file exists, continue creating Track
			this.filePath = file.getAbsolutePath();
			this.fileName = file.getName();
			
			out(1, "[new] " + this);
			
			// try creating an AudioFile object (from JAudioTagger library)
			// and then read MP3 tags and header data
			try {
				this.audioFile = AudioFileIO.read(new File(filePath));
				
				this.readTagData();
				this.readHeaderData();
				
				this.valid = true;
			} catch (CannotReadException | IOException |
					TagException | ReadOnlyFileException |
					InvalidAudioFrameException ex) {
				err("@Track() : failed initializing " + this + "\n" + ex);
				this.valid = false;
			}
		} else {
			// file doesn't exist, throw an exception
			this.valid = false;
			err("@Track() : cannot initialize a track : file doesn't exist (" + file + ")");
			throw new FileNotFoundException();
		}
	}
	
	public boolean isValid() {
		return this.valid;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	private static final String NULL_ARTIST = "Unknown Artist";
	private static final String NULL_ALBUM = "Unknown Album";

	private void readTagData() {
		//// out(1, "[called] " + this + ".readTagData()");

		Tag tag = audioFile.getTag();
		
		// tag might be null if the file doesn't contain any
		if (tag == null) {
			this.title = this.fileName;
			this.artist = NULL_ARTIST;
			this.album = NULL_ALBUM;
			return;
		}

		// read each field and store the values to instance variables
		this.title = tag.getFirst(FieldKey.TITLE);
		this.artist = tag.getFirst(FieldKey.ARTIST);
		this.album = tag.getFirst(FieldKey.ALBUM);
		this.year = tag.getFirst(FieldKey.YEAR);
		
		//// tag = null;
	}

	private void readHeaderData() {
		//// out(1, "[called] " + this + ".readHeaderData()");
		
		// get data from file's header
		this.trackLength = this.audioFile.getAudioHeader().getTrackLength();
		this.bitRate = (int) this.audioFile.getAudioHeader().getBitRateAsNumber();
		this.sampleRate = this.audioFile.getAudioHeader().getSampleRateAsNumber();
		this.channels = this.audioFile.getAudioHeader().getChannels();
		this.encoding = this.audioFile.getAudioHeader().getEncodingType();
	}

	public int getTrackLength() {
		return this.trackLength;
	}

	public String getTrackLengthString() {
		if (this.trackLength >= 3600) {
			// if the trackLength is longer than 1 hour, return HH:MM:SS
			return String.format("%02d:%02d:%02d",
					this.trackLength/3600, 			// hours
					(this.trackLength%3600)/60, 	// minutes
					this.trackLength%60);			// seconds
		} else {
			// trackLength is less than 1 hour, return MM:SS
			return String.format("%02d:%02d",
					this.trackLength/60, 	// minutes
					this.trackLength%60);	//seconds
		}
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public String getAlbumAndYearString() {
		if (this.year != null) {
			return this.album + " (" + this.year + ")";
		} else {
			return this.album;
		}
	}
	
	// a collection for icon cache, use icon's size (width/height) as key
	private Map<Integer, Icon> artworkIconCache = new HashMap<>();
	
	private void generateArtworkIconCache(int size) {
		// read the Artwork image from the file
		// then resize it to the specified size, and store in the cache
		try {
			Image image = (Image) audioFile.getTag().getFirstArtwork().getImage();
			// using highest quality settings
			Icon icon = new ImageIcon(Scalr.resize((BufferedImage) image,
					Scalr.Method.QUALITY, size, Scalr.OP_ANTIALIAS));
			// for low quality, use:
			//// Icon icon = new ImageIcon(Scalr.resize((BufferedImage) image, Scalr.Method.SPEED, size));
			this.artworkIconCache.put(size, icon);
			//// image.flush();
			//// image = null;
			out(2, "[called] Track.generateArtworkIconCache(size=" + size + ") in " + this);
			
		} catch (IOException ex) {
			Logger.getLogger(Track.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NullPointerException e) {
			this.artworkIconCache.put(size, null);
		}
	}
	
	public Icon getArtworkIcon(int size) {
		// if the needed icon size has been generated before, return that
		// else, generate a new one, store it for later uses, and return that
		if (!this.artworkIconCache.containsKey(size))
			this.generateArtworkIconCache(size);
		return this.artworkIconCache.get(size);
	}


	public int getBitRate() {
		return this.bitRate;
	}

	public int getSampleRate() {
		return this.sampleRate;
	}

	public String getChannels() {
		return this.channels;
	}

	public String getEncoding() {
		return this.encoding;
	}
	
	
	@Override
	public String toString() {
		return "Track{" + "fileName=" + this.fileName + '}';
	}
	
	@Override
	public boolean equals(Object o) {
		// two Tracks are equal if their filePath are the same
		return (o instanceof Track) &&
				(this.filePath.equals(((Track) o).filePath));
	}

}
