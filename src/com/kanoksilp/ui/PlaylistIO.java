/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.ui;

import com.kanoksilp.JMPApplication;
import com.kanoksilp.data.Playlist;
import com.kanoksilp.data.Track;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 *
 * @author Kanoksilp
 */
public class PlaylistIO {

	/**
	 * Add MP3 file(s) to a Playlist.
	 * @param playlist Target Playlist
	 */
	public static void addFromMP3Multiple(Playlist playlist) {
		// get mp3 files from the FileChooser
		for (File file : getFilesFromDialog("Add MP3 Files",
				"MP3 Audio Files (*.mp3)", "mp3", true, false)) {
			// create a Track and add it to the playlist
			Track track = null;
			try {
				track = new Track(file);
			} catch (NullPointerException e) {
				out("@PlaylistIO.addFromMP3Multiple() : no file to add.");
			} catch (FileNotFoundException e) {
				err("@PlaylistIO.addFromMP3Multiple() : file not found: " + file.getAbsolutePath());
			}
			playlist.append(track);
		}
	}
	
	private static FilenameFilter mp3filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// accept only mp3 files
				return name.toLowerCase().endsWith(".mp3");
			}
		};

	/**
	 * Add MP3 files in a selected folder to a Playlist. Not including
	 * files in sub-folders.
	 * @param playlist Target Playlist
	 */
	public static void addFromFolderSingle(Playlist playlist) {
		// create a new FileChooser and set its properties
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Add from Folder...");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File("./"));
		if (chooser.showOpenDialog(JMPApplication.getMainWindow()) 
				!= JFileChooser.APPROVE_OPTION) {
			return; // if the user cancelled
		}

		out("Add from Folder: " + chooser.getSelectedFile().toString());
		
		// for each mp3 file in selected folder
		for (File file : chooser.getSelectedFile().listFiles(mp3filter)) {
			// create a Track and add it to the playlist
			Track track = null;
			try {
				track = new Track(file);
			} catch (FileNotFoundException e) {
				err("@PlaylistIO.addFromFolderSingle() : file not found: " + file.getAbsolutePath());
			}
			playlist.append(track);
		}
	}

	/**
	 * Add MP3 files in a selected folder and its subfolder to a Playlist.
	 * @param playlist Target Playlist
	 */
	public static void addFromFolderRecursive(Playlist playlist) {
		// create a new FileChooser and set its properties
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Add from Folder...");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File("./"));
		if (chooser.showOpenDialog(JMPApplication.getMainWindow())
				!= JFileChooser.APPROVE_OPTION) {
			return; // if the user cancelled
		}

		folderRecursiveAdd(playlist, chooser.getSelectedFile());
	}

	private static void folderRecursiveAdd(Playlist playlist, File folder) {
		out("[called] PlaylistIO.folderRecursiveAdd(folder=" + folder + ")");
		
		// for each mp3 file in selected folder
		for (File file : folder.listFiles(mp3filter)) {
			// create a Track and add it to the playlist
			Track track = null;
			try {
				track = new Track(file);
			} catch (FileNotFoundException e) {
				err("@PlaylistIO.folderRecursiveAdd() : file not found: " + file.getAbsolutePath());
			}
			playlist.append(track);
		}

		for (File dir : folder.listFiles()) {
			if (dir.isDirectory()) {
				folderRecursiveAdd(playlist, dir);
			}
		}
	}

	/**
	 * Add MP3 files from selected M3U playlist(s) to a Playlist.
	 * @param playlist Target Playlist
	 */
	public static void addFromPlaylistMultiple(Playlist playlist) {
		// get playlist files from the FileChooser
		for (File file : getFilesFromDialog("Add from Playlist...",
				"M3U Playlist Files (*.m3u)", "m3u", true, false)) {
			try {
				// append each playlist to the current list
				playlist.append(file.getAbsolutePath());
			} catch (NullPointerException e) {
				out("@PlaylistIO.addFromPlaylistMultiple() : no file to add.");
			} catch (FileNotFoundException e) {
				err("@PlaylistIO.addFromPlaylistMultiple() : file not found: " + file.getAbsolutePath());
			}
		}
	}

	/**
	 * Clear the Playlist and load from an M3U file.
	 * @param playlist Target Playlist
	 */
	public static void openPlaylist(Playlist playlist) {
		try {
			// load the selected playlist file
			playlist.load(getFilesFromDialog("Open Playlist...",
					"M3U Playlist Files (*.m3u)", "m3u", false, false)[0]
					.getAbsolutePath());
		} catch (NullPointerException e) {
			out("@PlaylistIO.openPlaylist() : no playlist to open.");
		} catch (FileNotFoundException e) {
			err("@PlaylistIO.openPlaylist() : file not found.");
		}
	}

	/**
	 * Save this Playlist to an M3U file. Each line in the saved M3U is the
	 * ABSOLUTE path to the corresponding MP3 file.
	 * @param playlist Playlist to save
	 */
	public static void savePlaylistAs(Playlist playlist) {
		try {
			// save to the selected playlist file
			String saveTarget = getFilesFromDialog("Save Playlist as...",
					"M3U Playlist File (*.m3u)", "m3u", false, true)[0]
					.getAbsolutePath();
			if (!saveTarget.toLowerCase().endsWith(".m3u")) {
				saveTarget += ".m3u";
			}
			playlist.save(saveTarget);
		} catch (NullPointerException e) {
			out("@PlaylistIO.savePlaylistAs() : save cancelled.");
		} catch (IOException e) {
			err("@PlaylistIO.savePlaylistAs() : exception thrown:\n\t" + e);
		}
	}

	/**
	 * Save this Playlist to an M3U file. Each line in the saved M3U is the
	 * RELATIVE path to the corresponding MP3 file.
	 * @param playlist Playlist to save
	 */
	public static void savePlaylistAsRelative(Playlist playlist) {
		try {
			// save to the selected playlist file
			String saveTarget = getFilesFromDialog("Save Playlist as...",
					"M3U Playlist File (*.m3u)", "m3u", false, true)[0]
					.getAbsolutePath();
			if (!saveTarget.toLowerCase().endsWith(".m3u")) {
				saveTarget += ".m3u";
			}
			playlist.saveRelative(saveTarget);
		} catch (NullPointerException e) {
			out("@PlaylistIO.savePlaylistAsRelative() : save cancelled.");
		} catch (FileNotFoundException e) {
			err("@PlaylistIO.savePlaylistAsRelative() : exception thrown:\n\t" + e);
		}
	}
	
	private static File[] getFilesFromDialog(String dialogTitle,
			String filterDesc, String filterExt,
			boolean isMulti, boolean isSave) {
		
		// create a new FileChooser object
		JFileChooser chooser = new JFileChooser();
		// set its properties
		chooser.setDialogTitle(dialogTitle);
		chooser.setFileFilter(new FileNameExtensionFilter(filterDesc, filterExt));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(isMulti);
		
		chooser.setCurrentDirectory(new File("./"));
		
		// create a file array with one null for returning if user cancelled
		File[] file = new File[1];
		file[0] = null;

		if (isSave) {
			// show dialog for saving file
			if (chooser.showSaveDialog(JMPApplication.getMainWindow()) 
					!= JFileChooser.APPROVE_OPTION) {
				return file; // if user cancelled, return the null array
			}
		} else {
			// show dialog for opening file
			if (chooser.showOpenDialog(JMPApplication.getMainWindow()) 
					!= JFileChooser.APPROVE_OPTION) {
				return file; // if user cancelled, return the null array
			}
		}
		
		if (isMulti) {
			// if multiple selection is enabled, return files array
			return chooser.getSelectedFiles();
		} else {
			// return array containing a single file
			file[0] = chooser.getSelectedFile();
			return file;
		}
	}
}
