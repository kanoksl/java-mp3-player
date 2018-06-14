/*
 * 11/26/04		Buffer size modified to support JRE 1.5 optimizations.
 *              (CPU usage < 1% under P4/2Ghz, RAM < 12MB).
 *              jlayer@javazoom.net
 * 11/19/04		1.0 moved to LGPL.
 * 06/04/01		Too fast playback fixed. mdm@techie.com
 * 29/01/00		Initial version. mdm@techie.com
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package com.kanoksilp.player.upgradedjlayer;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDeviceBase;

import javax.sound.sampled.*;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * The <code>JavaSoundAudioDevice</code> implements an audio
 * device by using the JavaSound API.
 *
 * @author Mat McGowan
 * @since 0.0.8
 */
public class JavaSoundAudioDevice extends AudioDeviceBase {
	
	// <editor-fold defaultstate="collapsed" desc="Original Source Code from JLayer Library">
	private SourceDataLine source = null;

	private AudioFormat fmt = null;

	private byte[] byteBuf = new byte[4096];

	protected void setAudioFormat(AudioFormat fmt0) {
		fmt = fmt0;
	}

	protected AudioFormat getAudioFormat() {
		if (fmt == null) {
			Decoder decoder = getDecoder();
			fmt = new AudioFormat(decoder.getOutputFrequency(),
					16,
					decoder.getOutputChannels(),
					true,
					false);
		}
		return fmt;
	}

	protected DataLine.Info getSourceLineInfo() {
		AudioFormat fmt = getAudioFormat();
		//DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt, 4000);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt);
		return info;
	}

	public void open(AudioFormat fmt) throws JavaLayerException {
		if (!isOpen()) {
			setAudioFormat(fmt);
			openImpl();
			setOpen(true);
		}
	}

	@Override
	protected void openImpl()
			throws JavaLayerException {
	}


	// createSource fix.
	protected void createSource() throws JavaLayerException {
		Throwable t = null;
		try {
			Line line = AudioSystem.getLine(getSourceLineInfo());
			if (line instanceof SourceDataLine) {
				source = (SourceDataLine) line;
				//source.open(fmt, millisecondsToBytes(fmt, 2000));
				source.open(fmt);
				
				// set initial volume
				if (this.usePercentage) {
					this.setLineGain(iniGainP);
				} else {
					this.setLineGain(iniGain);
				}
				
				source.start();

			}
		} catch (RuntimeException ex) {
			t = ex;
		} catch (LinkageError ex) {
			t = ex;
		} catch (LineUnavailableException ex) {
			t = ex;
		}
		if (source == null)
			throw new JavaLayerException("cannot obtain source audio line", t);
	}

	public int millisecondsToBytes(AudioFormat fmt, int time) {
		return (int) (time * (fmt.getSampleRate() * fmt.getChannels() * fmt.getSampleSizeInBits()) / 8000.0);
	}

	@Override
	protected void closeImpl() {
		if (source != null) {
			source.close();
		}
	}

	@Override
	protected void writeImpl(short[] samples, int offs, int len)
			throws JavaLayerException {
		if (source == null)
			createSource();

		byte[] b = toByteArray(samples, offs, len);
		source.write(b, 0, len * 2);
	}

	protected byte[] getByteArray(int length) {
		if (byteBuf.length < length) {
			byteBuf = new byte[length + 1024];
		}
		return byteBuf;
	}

	protected byte[] toByteArray(short[] samples, int offs, int len) {
		byte[] b = getByteArray(len * 2);
		int idx = 0;
		short s;
		while (len-- > 0) {
			s = samples[offs++];
			b[idx++] = (byte) s;
			b[idx++] = (byte) (s >>> 8);
		}
		return b;
	}

	@Override
	protected void flushImpl() {
		if (source != null) {
			source.drain();
		}
	}

	@Override
	public int getPosition() {
		int pos = 0;
		if (source != null) {
			pos = (int) (source.getMicrosecondPosition() / 1000);
		}
		return pos;
	}

	/**
	 * Runs a short test by playing a short silent sound.
	 */
	public void test()
			throws JavaLayerException {
		try {
			open(new AudioFormat(22050, 16, 1, true, false));
			short[] data = new short[22050 / 10];
			write(data, 0, data.length);
			flush();
			close();
		} catch (RuntimeException ex) {
			throw new JavaLayerException("Device test failed: " + ex);
		}

	}
	// </editor-fold>
	
	private boolean usePercentage = true;
	public void setInitialGainMode(boolean usePercentage) {
		this.usePercentage = usePercentage;
	}
	private float iniGain = 0;
	public void setInitialGain(float iniGain) {
		this.iniGain = iniGain;
	}
	private double iniGainP = 0.8;
	public void setInitialGain(double percentage) {
		this.iniGainP = percentage;
	}
	

	// add a method for setting volume (gain)
	// http://stackoverflow.com/questions/648107/

	public void setLineGain(float gain) {
		// set the gain from the specified float value
		if (source != null) {
			// get the volume control from DataLine
			FloatControl volControl = (FloatControl)
					source.getControl(FloatControl.Type.MASTER_GAIN);

			// check if the gain is within range
			float newGain = gain;
			if (gain < volControl.getMinimum())
				newGain = volControl.getMinimum();
			if (gain > volControl.getMaximum())
				newGain = volControl.getMaximum();

			//// out("JavaSoundAudioDevice.setLineGain(gain=" + gain + "), newGain=" + newGain);

			// set the gain
			volControl.setValue(newGain);
		} else {
			err("JavaSoundAudioDevice.setLineGain() : source == null");
		}
	}

	public void setLineGain(double percentage) {
		// set the gain by percentage, with some additional calculations
		if (source != null) {
			// get the volume control from DataLine
			FloatControl volControl = (FloatControl)
					source.getControl(FloatControl.Type.MASTER_GAIN);

			// calculate the value of gain to set
			float min = 0.5f * volControl.getMinimum();
			float max = volControl.getMaximum();
			float newGain = max + (float) (Math.log10(percentage) * (max - min));
			if (newGain < volControl.getMinimum())
				newGain = volControl.getMinimum();

			//// out("JavaSoundAudioDevice.setLineGain(percentage=" + percentage + "), newGain=" + newGain);

			// set the gain
			volControl.setValue(newGain);
		} else {
			err("JavaSoundAudioDevice.setLineGain() : source == null");
		}
	}
}
