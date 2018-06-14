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

import com.kanoksilp.player.upgradedjlayer.FactoryRegistry;
import com.kanoksilp.player.upgradedjlayer.JavaSoundAudioDevice;

import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kanoksilp.util.TestingUtil.out;

/**
 *
 * @author Modified by Kanoksilp
 */
public class ImprovedPlayerComponent {

	// path of the audio file
	private String filePath;

	// JLayer components
	private Bitstream bitstream;
	private Decoder decoder;
	private AudioDevice audioDevice;

	// playback statuses
	private boolean closed;
	private boolean complete;
	private boolean paused;
	private boolean stopped;

	// event listeners
	private PlaybackListener listener;
	private TrackEndedNotifier trackEndedNotifier;

	private int frameIndexCurrent;		// current decoding frame
	private final int lostFrames = 20;	// number of frames lost after pausing
	private int approxFrameCount;		// total number of frames of the file
	
	private double iniGain = -1;

	public ImprovedPlayerComponent(String filePath) {
		out("[new] IPComponent()");

		this.filePath = filePath;
		//setPlaybackListener(new PlaybackAdapter());
		calculateApproxFrameCount();
	}

	public void setPlaybackListener(PlaybackListener playbackListener) {
		/*if (playbackListener != null) {
			this.listener = playbackListener;
		} else {
			throw new NullPointerException("PlaybackListener is null");
		}*/
		this.listener = playbackListener;
		// can be null, no exception thrown
	}

	public void setTrackEndedNotifier(TrackEndedNotifier trackEndedNotifier) {
		out(3, "[set] IPComponent.trackEndedNotifier");

		this.trackEndedNotifier = trackEndedNotifier;
		// can be null, no exception thrown
	}

	public int getCurrentFrame() {
		return this.frameIndexCurrent;
	}
	
	public void setFrameIndexCurrent(int frame) {
		this.frameIndexCurrent = frame - this.lostFrames;
	}

	private void calculateApproxFrameCount() {
		// using JAudioTagger to calculate MP3 file's length
		// get the length (number of frames) of the MP3 from its header data
		try {
			MP3AudioHeader header = new MP3AudioHeader(new File(this.filePath));
			this.approxFrameCount = (int) header.getNumberOfFrames();
		} catch (IOException | InvalidAudioFrameException ex) {
			Logger.getLogger(ImprovedPlayerComponent.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}

	public int getApproxFrameCount() {
		return this.approxFrameCount;
	}

	public void setGain(float gain) {
		out(2, "[called] IPComponent.setGain(gain=" + gain + ")");
		if (this.audioDevice instanceof JavaSoundAudioDevice) {
			JavaSoundAudioDevice js = (JavaSoundAudioDevice) this.audioDevice;
			js.setLineGain(gain);
		}
	}

	public void setGain(double percentage) {
		out(2, "[called] IPComponent.setGain(percentage=" + percentage + ")");
		if (percentage < 0 || percentage > 1) {
			this.setGain(0f);
		} else if (this.audioDevice instanceof JavaSoundAudioDevice) {
			out(2, "IPComponent.audioDevice is an instance of JavaSoundAudioDevice");
			((JavaSoundAudioDevice) this.audioDevice).setLineGain(percentage);
		}
	}
	
	public void setInitialGain(double percentage) {
		this.iniGain = percentage;
	}
	
	private void setAudioDeviceInitialGain() {
		out(2, "[called] IPComponent.setAudioDeviceInitialGain(iniGain=" + this.iniGain + ")");
		if (this.audioDevice instanceof JavaSoundAudioDevice) {
			//// out(2, "ImprovedPlayerComponent.audioDevice is an instance of JavaSoundAudioDevice");
			((JavaSoundAudioDevice) this.audioDevice).setInitialGain(this.iniGain);
		}
	}

	public boolean play()
			throws JavaLayerException {
		return this.play(0);
	}

	public boolean play(int frameIndexStart)
			throws JavaLayerException {
		return this.play(frameIndexStart, -1, lostFrames);
	}

	public boolean play(int frameIndexStart, int frameIndexFinal,
			int correctionFactorInFrames)
			throws JavaLayerException {
		try {
			this.bitstream = new Bitstream(new FileInputStream(this.filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
		this.decoder = new Decoder();
		this.audioDevice.open(this.decoder);

		this.setAudioDeviceInitialGain();

		boolean shouldContinueReadingFrames = true;

		this.paused = false;
		this.stopped = false;
		this.frameIndexCurrent = 0;

		while (shouldContinueReadingFrames &&
				this.frameIndexCurrent < frameIndexStart - correctionFactorInFrames) {
			shouldContinueReadingFrames = this.skipFrame();
			this.frameIndexCurrent++;
		}

		if (this.listener != null) {
			this.listener.playbackStarted(new PlaybackEvent(this,
					PlaybackEvent.EventType.Instances.Started,
					this.audioDevice.getPosition()));
		}

		if (frameIndexFinal < 0) {
			frameIndexFinal = Integer.MAX_VALUE;
		}
		
		
		
		while (shouldContinueReadingFrames &&
				this.frameIndexCurrent < frameIndexFinal) {
			if (this.paused || this.stopped) {
				shouldContinueReadingFrames = false;
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				shouldContinueReadingFrames = this.decodeFrame();
				this.frameIndexCurrent++;
			}
		}

		// last frame, ensure all data flushed to the audio device.
		if (this.audioDevice != null && !this.paused) {
			this.audioDevice.flush();

			synchronized (this) {
				this.complete = (!this.closed);
				this.close();
			}

			// report to listener
			if (this.listener != null) {
				PlaybackEvent playbackEvent = new PlaybackEvent(
						this, PlaybackEvent.EventType.Instances.Stopped,
						this.getAudioDevicePosition());
				this.listener.playbackFinished(playbackEvent);
			}
		}

		//out("Try setting gain to iniGain=" + iniGain);
		//this.setGain(this.iniGain);
		
		return shouldContinueReadingFrames;
	}

	public boolean resume()
			throws JavaLayerException {
		return this.play(this.frameIndexCurrent);
	}

	public synchronized void close() {
		if (this.audioDevice != null) {
			this.closed = true;

			this.audioDevice.close();

			this.audioDevice = null;

			try {
				this.bitstream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// return AudioDevice's position if available, -1 if null
	private int getAudioDevicePosition() {
		return (this.audioDevice != null) ? 
			this.audioDevice.getPosition() : -1;
	}
	

	protected boolean decodeFrame()
			throws JavaLayerException {
		boolean returnValue = false;
		if (this.stopped) { //nothing for decode
			return false;
		}

		try {
			if (this.audioDevice != null) {
				Header header = this.bitstream.readFrame();
				if (header != null) {
					// sample buffer set when decoder constructed
					SampleBuffer output = (SampleBuffer)
							this.decoder.decodeFrame(header, this.bitstream);

					synchronized (this) {
						if (this.audioDevice != null) {
							this.audioDevice.write(
									output.getBuffer(), 0,
									output.getBufferLength());
						}
					}
					this.bitstream.closeFrame();
					returnValue = true;
				} else {
					out(2, "IPComponent.decodeFrame() : reached end of file");
					// end of file, report to the Notifier
					if (this.trackEndedNotifier != null)
						this.trackEndedNotifier.trackEnded();
					returnValue = false;
				}
			}
		} catch (RuntimeException ex) {
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		return returnValue;
	}

	public void pause() {
		if (!this.stopped) {
			this.paused = true;
			if (this.listener != null) {
				this.listener.playbackPaused(new PlaybackEvent(
						this, PlaybackEvent.EventType.Instances.Paused,
						this.getAudioDevicePosition()));
			}
			this.close();
		}
	}

	protected boolean skipFrame() throws JavaLayerException {
		boolean returnValue = false;

		Header header;

		try {
			header = this.bitstream.readFrame();
			if (header != null) {
				this.bitstream.closeFrame();
				returnValue = true;
			}
		} catch (BitstreamException e) {
			e.printStackTrace();

		}

		return returnValue;
	}

	public void stop() {
		if (!this.stopped) {
			
			if (!this.closed) {
				if (this.listener != null) {
					this.listener.playbackFinished(new PlaybackEvent(
							this, PlaybackEvent.EventType.Instances.Stopped,
							this.getAudioDevicePosition()));
				}
				this.close();
			} else if (this.paused) {
				if (this.listener != null) {
					this.listener.playbackFinished(new PlaybackEvent(
							this, PlaybackEvent.EventType.Instances.Stopped,
							this.getAudioDevicePosition()));
				}
			}
			
			this.stopped = true;
		}
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isComplete() {
		return complete;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isStopped() {
		return stopped;
	}

	public static class PlaybackEvent {

		public ImprovedPlayerComponent source;
		public EventType eventType;
		public int frameIndex;

		public PlaybackEvent(ImprovedPlayerComponent source,
				EventType eventType, int frameIndex) {
			this.source = source;
			this.eventType = eventType;
			this.frameIndex = frameIndex;
		}

		public static class EventType {

			public String name;

			public EventType(String name) {
				this.name = name;
			}

			public static class Instances {

				public static EventType Started = new EventType("Started");
				public static EventType Paused = new EventType("Paused");
				public static EventType Stopped = new EventType("Stopped");
			}
		}
	}

	public static class PlaybackAdapter implements PlaybackListener {

		@Override
		public void playbackStarted(PlaybackEvent event) {
			System.err.println("Playback started");
		}

		@Override
		public void playbackPaused(PlaybackEvent event) {
			System.err.println("Playback paused");
		}

		@Override
		public void playbackFinished(PlaybackEvent event) {
			System.err.println("Playback stopped");
		}
	}

	public static interface PlaybackListener {

		public void playbackStarted(PlaybackEvent event);

		public void playbackPaused(PlaybackEvent event);

		public void playbackFinished(PlaybackEvent event);
	}

}
