/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------

 * Based on: http://thiscouldbebetter.wordpress.com/2011/07/04/
 *                  pausing-an-mp3-file-using-jlayer/
 *           21-jul-2012 by Arthur Assuncao
 */

package com.kanoksilp.player;

import com.kanoksilp.data.Track;
import com.kanoksilp.player.ImprovedPlayerComponent.PlaybackAdapter;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * @author Modified by Kanoksilp
 */
public class ImprovedPlayerController implements Runnable {

	// audio track to play
	private Track track;

	// ImprovedPlayerComponent (actual player) fields
	private ImprovedPlayerComponent playerComponent;
	private PlaybackListener playbackListener = new PlaybackListener();
	private TrackEndedNotifier trackEndedNotifier;

	// threading fields
	private Thread thread;
	private String threadName;

	// ProgressChecker fields
	private ProgressChecker progressChecker = new ProgressChecker();
	private Thread progressCheckerThread;
	private boolean continueChecking = false;
	private boolean needProgressChecker = true;
	
	private JProgressBar seekBar;
	private JLabel progressLabel;
	
	private double iniGain = -1;

	/**
	 * Create a new ImprovedPlayer object to play an audio track.
	 * @param track An audio track to play.
	 */
	public ImprovedPlayerController(Track track) {
		out("[new] IPController()");

		this.track = track;
		this.threadName = "IPController[" + track.getFileName() + "]";
	}
	
	public Track getTrack() {
		return this.track;
	}

	public void setTrackEndedNotifier(TrackEndedNotifier trackEndedNotifier) {
		this.trackEndedNotifier = trackEndedNotifier;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.seekBar = progressBar;
	}
	public void setProgressLabel(JLabel label) {
		this.progressLabel = label;
	}
	
	public void setInitialGain(double iniGain) {
		out(2, "[called] IPController.setInitialGain(gain=" + iniGain + ")");
		this.iniGain = iniGain;
	}

	private void playerInitialize() {
		out(2, "[called] IPController.playerInitialize()");
		this.playerComponent = new ImprovedPlayerComponent(this.track.getFilePath());
		this.playerComponent.setPlaybackListener(this.playbackListener);
		this.playerComponent.setTrackEndedNotifier(this.trackEndedNotifier);
		this.playerComponent.setInitialGain(this.iniGain);
	}

	// start the playback
	public void play() {
		out(2, "[called] IPController.play()");

		if (this.playerComponent == null) {
			// set up playerComponent
			this.playerInitialize();
		} else if (!this.playerComponent.isPaused()
				|| this.playerComponent.isComplete()
				|| this.playerComponent.isStopped()) {
			// if the audio is playing, or already finished playing,
			// or has been stopped, reset playerComponent
			this.stop();
			this.playerInitialize();
		} else {
			this.playerComponent.setInitialGain(this.iniGain);
		}

		// create a new thread for audio-playing
		if (this.thread == null) {
			this.thread = new Thread(this, threadName);
			this.thread.setDaemon(true);
			this.thread.start();
		}

		// we start the playback, so we tell the Checker
		// to continue checking progress
		continueChecking = true;

		// create a new thread for progress-checking if needed
		if (needProgressChecker) {
			this.progressCheckerThread = new Thread(
					progressChecker, "ProgressChecker." + threadName);
			this.progressCheckerThread.setDaemon(true);
			this.progressCheckerThread.start();
		}
	}

	// pause the playback
	public void pause() {
		out(2, "[called] IPController.pause()");

		if (this.playerComponent != null) {
			this.playerComponent.pause();
			this.thread = null;
			this.progressCheckerThread = null;
			continueChecking = false;
		}
	}

	// play or pause toggling
	public void pauseToggle() {
		out(2, "[called] IPController.pauseToggle()");

		if (this.playerComponent != null) {
//			if (this.playerComponent.isPaused() &&
//					!this.playerComponent.isStopped()) {
			if (this.playerComponent.isPaused() ||
					this.playerComponent.isStopped()) {
				this.play();
			} else {
				this.pause();
			}
		}
	}

	// stop the playback
	public void stop() {
		out(2, "[called] IPController.stop()");

		if (this.playerComponent != null) {
			
			this.playerComponent.stop();
			this.thread = null;
			this.progressCheckerThread = null;
			continueChecking = false;
		}
	}

	// seek: start the playback at targetFrame
	public synchronized void seek(int targetFrame) {
		out(2, "[called] IPController.seek(targetFrame=" + targetFrame + ")");

		this.pause();
		
		this.playerComponent.setFrameIndexCurrent(targetFrame);
		this.play();
	}
	
	public void seek(double percentage) {
		this.seek((int) Math.round(this.getApproxFrameCount()*percentage));
	}

	public void setGain(float gain) {
		this.playerComponent.setGain(gain);
	}

	public void setGain(double percentage) {
		this.playerComponent.setGain(percentage);
	}

	public int getCurrentFrame() {
		return this.playerComponent.getCurrentFrame();
	}

	public int getApproxFrameCount() {
		return this.playerComponent.getApproxFrameCount();
	}

	// number of instances of ImprovedPlayerController
	private static int instanceCount = 0;

	@Override
	public void run() {
		out(1, "[begin] IPController.run()");

		if (instanceCount > 0) {
			err("@IPController : an instance is already running");
			return;
		}
		instanceCount++;

		try {
			// try resuming the playback
			this.playerComponent.resume();
		} catch (JavaLayerException ex) {
			ex.printStackTrace();
		}

		instanceCount--;
		out(1, "[end] IPController.run()");
	}

	private class PlaybackListener extends PlaybackAdapter {

		@Override
		public void playbackStarted(ImprovedPlayerComponent.PlaybackEvent playbackEvent) {
			out("PlaybackListener.PlaybackStarted()");
		}

		@Override
		public void playbackPaused(ImprovedPlayerComponent.PlaybackEvent playbackEvent) {
			out("PlaybackListener.PlaybackPaused()");
		}

		@Override
		public void playbackFinished(ImprovedPlayerComponent.PlaybackEvent playbackEvent) {
			out("PlaybackListener.PlaybackStopped()");
		}
	}

	public class ProgressChecker implements Runnable {

		// interval between each report, in milliseconds
		private int interval;
		private static final int DEFAULT_INTERVAL = 1000;

		private int prevFrame = 0;

		public ProgressChecker() {
			this(DEFAULT_INTERVAL);
		}

		public ProgressChecker(int interval) {
			out("[new] ProgressChecker(interval=" + interval + ")");
			this.interval = interval;
		}

		// get the current playback position, in seconds
		private int getCurrentTrackTime() {
			return (int) Math.round((double) track.getTrackLength() *
					getCurrentFrame() / getApproxFrameCount());
		}

		// get the current playback position as HH:MM:SS string
		private String getCurrentTrackTimeString() {
			int trackTime = getCurrentTrackTime();
			if (trackTime >= 3600) {
				// if the trackTime is longer than 1 hour, return HH:MM:SS
				return String.format("%02d:%02d:%02d", trackTime/3600,
						(trackTime%3600)/60, trackTime%60);
			} else {
				// trackTime is less than 1 hour, return MM:SS
				return String.format("%02d:%02d", trackTime/60, trackTime%60);
			}
		}

		private int getFrameDiff() {
			int d = getCurrentFrame() - prevFrame;
			prevFrame = getCurrentFrame();
			return d;
		}

		// print the progress in the specified format:
		// FRAME/FRAME_COUNT | FRAME_DIFF | PROGRESS% | TIME / TRACK_LENGTH
		public void displayProgress() {
			System.out.printf("%50d/%-7d d=%-4d %5.2f%-3s %s / %s\n",
					getCurrentFrame(), getApproxFrameCount(),
					getFrameDiff(),
					(100. * getCurrentFrame() / getApproxFrameCount()), "%",
					getCurrentTrackTimeString(), track.getTrackLengthString());
		}

		// check current playback progress
		private void check() {
			displayProgress();
			updateGUI();

			// when playback *almost* reach the end,
			// we don't need to check progress anymore
			if (track.getTrackLength() - getCurrentTrackTime()
					< Math.ceil(interval /1000)) {
				out(2, "ProgressChecker.check() : set continueChecking = false");
				continueChecking = false;
			}
		}
		
		private void updateGUI() {
			if (seekBar != null) {
				seekBar.setMaximum(getApproxFrameCount());
				seekBar.setValue(getCurrentFrame());
			}
			if (progressLabel != null) {
				progressLabel.setText(getCurrentTrackTimeString() + " / " +
						track.getTrackLengthString());
			}
		}

		@Override
		public void run() {
			out(1, "[begin] ProgressChecker.run()");

			// this Checker is running, we don't need another one
			needProgressChecker = false;

			while (continueChecking) {
				try {
					// call the check() method, which will decide what to do
					// and then let the thread sleep for a while
					this.check();
					Thread.sleep(this.interval);
				} catch (InterruptedException e) {
					err("@ProgressChecker.run() : thread sleep has been interrupted.");
				}
			}

			// this Checker has finished its task, we need a new one
			needProgressChecker = true;

			out(1, "[end] ProgressChecker.run()");
		}
	}

}
