/*
 * 11/19/04		1.0 moved to LGPL.
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

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.AudioDeviceFactory;

/**
 * This class is responsible for creating instances of the
 * JavaSoundAudioDevice. The audio device implementation is loaded
 * and tested dynamically as not all systems will have support
 * for JavaSound, or they may have the incorrect version. 
 */
public class JavaSoundAudioDeviceFactory extends AudioDeviceFactory
{

	// change the value to our modified class
	private static final String DEVICE_CLASS_NAME = 
			"com.kanoksilp.player.upgradedjlayer.JavaSoundAudioDevice";
	
	// <editor-fold defaultstate="collapsed" desc="Original Source Code from JLayer Library">
	private boolean tested = false;

	@Override
	public synchronized AudioDevice createAudioDevice()
		throws JavaLayerException
	{
		if (!tested)
		{			
			testAudioDevice();
			tested = true;
		}
		
		try
		{			
			return createAudioDeviceImpl();
		}
		catch (Exception ex)
		{
			throw new JavaLayerException("unable to create JavaSound device: "+ex);
		}
		catch (LinkageError ex)
		{
			throw new JavaLayerException("unable to create JavaSound device: "+ex);
		}
	}
	
	protected JavaSoundAudioDevice createAudioDeviceImpl()
		throws JavaLayerException
	{
		ClassLoader loader = getClass().getClassLoader();
		try
		{
			JavaSoundAudioDevice dev = (JavaSoundAudioDevice)instantiate(loader, DEVICE_CLASS_NAME);
			return dev;
		}
		catch (Exception ex)
		{
			throw new JavaLayerException("Cannot create JavaSound device", ex);
		}
		catch (LinkageError ex)
		{
			throw new JavaLayerException("Cannot create JavaSound device", ex);
		}
		
	}
	
	public void testAudioDevice() throws JavaLayerException
	{
		JavaSoundAudioDevice dev = createAudioDeviceImpl();
		dev.test();
	}
	// </editor-fold>
}
