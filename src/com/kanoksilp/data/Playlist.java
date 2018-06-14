/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * @author Kanoksilp
 */
public class Playlist {

	private ArrayList<Track> tracks;
	private ArrayList<Track> tracks_ordered;

	private boolean repeating = false;
	private boolean shuffling = false;

	public Playlist() {
		out("[new] Playlist");
		this.tracks = new ArrayList<>();
		this.tracks_ordered = new ArrayList<>();
	}

	// how many songs do we have
	public int size() {
		return this.tracks.size();
	}

	// return the Track at the specified index i
	public Track getTrack(int i) {
		if (i < 0 || i >= this.size()) {
			return null;
		}
		return this.tracks.get(i);
	}

	public int indexOf(Track track) {
		return this.tracks.indexOf(track);
	}

	public boolean isRepeating() {
		return this.repeating;
	}

	public void setRepeating(boolean value) {
		this.repeating = value;
	}

	public boolean isShuffling() {
		return this.shuffling;
	}

	public void setShuffling(boolean value) {
		this.shuffling = value;
		if (!value) {
			this.tracks.clear();
			this.tracks.addAll(tracks_ordered);
		}
	}

	public void shuffle() {
		Collections.shuffle(this.tracks, new Random());
		out("[done] Playlist.shuffle()\n  Result: " + this);
	}
	
	// shuffle and move the chosen track to the first position in list
	public void shuffleEnhanced(int currentIndex) {
		Track cur = this.tracks.get(currentIndex);
		Collections.shuffle(this.tracks, new Random());
		this.moveTrack(this.tracks.indexOf(cur), 0);
		out("[done] Playlist.shuffleEnhanced(ci=" + currentIndex + ")\n  Result: " + this);
	}

	// remove a track at index = i
	public void remove(int i) {
		if (i > this.tracks.size()-1 || i < 0) return;

		this.tracks_ordered.remove(this.tracks.get(i));
		this.tracks.remove(i);

	}

	// remove all tracks from the playlist
	public void clear() {
		this.tracks.clear();
		this.tracks_ordered.clear();
	}

	// move a track located at index i to targetIndex
	public void moveTrack(int i, int targetIndex) {
		if (i == targetIndex) {
			return;	// no moving needed
		}

		if (i > this.tracks.size()-1 || i < 0) return;

		// fix if target index is out of bound
		targetIndex = Math.min(this.tracks.size(), targetIndex);
		targetIndex = Math.max(targetIndex, 0);

		this.tracks.add(targetIndex, this.tracks.get(i));
		this.tracks.remove(targetIndex > i ? i : i + 1);

		out("Track moved from " + i + " to " + targetIndex);
		out(this.toString());
	}

	public void saveOrdering() {
		this.tracks_ordered.clear();
		this.tracks_ordered.addAll(this.tracks);
	}

	// <editor-fold desc="File IO Methods">

	// write the current Track list to file
	public void save(String filePath)
			throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(filePath);
		for (Track t : this.tracks) {
			writer.println(t.getFilePath());
		}
		writer.close();
		out("[done] Playlist.save(filePath=" + filePath + ")");
	}
	
	public void saveRelative(String filePath)
			throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(filePath);
		Path base = Paths.get((new File(filePath)).getParent());

		for (Track t : this.tracks) {
			writer.println(base.relativize(Paths.get(t.getFilePath())));
		}
		writer.close();
		out("[done] Playlist.saveRelative(filePath=" + filePath + ")");
	}

	// clear the current Track list, and then read from file
	public void load(String filePath)
			throws FileNotFoundException {
		this.tracks.clear();
		this.tracks_ordered.clear();
		this.append(filePath);
		out("[done] Playlist.load(filePath=" + filePath + ")");
	}

	// add the Track to the end of current list
	public void append(Track track) {
		if (track != null && track.isValid()) {
			if (this.tracks.contains(track)) {
				track = this.tracks.get(this.tracks.indexOf(track));
				out(1, " [note] track already present in the playlist");
			}
			this.tracks.add(track);
			this.tracks_ordered.add(track);
			out(1, " [done] Playlist.append(track=" + track + ")");
		}
	}

	// add all the Tracks from the playlist file to the end of current list
	public void append(String filePath)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		Path base = Paths.get((new File(filePath)).getParent());

		while (scanner.hasNext()) {
			try {
				this.append(new Track(base.resolve(scanner.nextLine()).toString()));
			} catch (FileNotFoundException e) {
				err("@Playlist.append() : track failed to initiate, not added to playlist");
			}
		}
		scanner.close();
	}

	// </editor-fold>

	@Override
	public String toString() {
		String str = "Playlist{size=";
		str += this.tracks.size() + "}\n";
		str += " - Current track ordering:\n";
		for (Track t : this.tracks) {
			str += "\t > " + t.toString() + "\n";
		}
		str += " - Saved ordering: \n";
		for (Track t : this.tracks_ordered) {
			str += "\t > " + t.toString() + "\n";
		}
		return str;
	}

}
