package com.icrg.waterfall.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.icrg.waterfall.R;

public class Ambience 
{
	public Context ctxt = null;
	public MediaPlayer morning = null, bird = null, insect_cricket = null, rain_ambience_sound = null, cicidas = null, beach = null, thunder = null, frog = null, river = null, wind = null;
	public int[] raw_ambiencefiles = {R.raw.beach1, R.raw.bird4, R.raw.morning, R.raw.cricket1, R.raw.rain_1, R.raw.frog1, R.raw.wind, R.raw.river, R.raw.thunder, R.raw.cicidas1};
	public MediaPlayer[] ambience_files = {beach, bird, morning, insect_cricket, rain_ambience_sound, frog, wind, river, thunder, cicidas};
	
	public Ambience(Context context) 
	{
		ctxt = context;		
	}	
	
	public void create_ambience_sound(int id)
	{		
		ambience_files[id] = MediaPlayer.create(ctxt, raw_ambiencefiles[id]);    	
	}	
	
	public void pause_ambience()
	{
		for(int i=0; i<ambience_files.length; i++)
		{
			if((ambience_files != null && ambience_files[i] != null) && ambience_files[i].isPlaying())
				ambience_files[i].pause();				
		}
	}
	
	public void start_ambience(int id)
	{
		if((ambience_files != null && ambience_files[id] != null) && !ambience_files[id].isPlaying())
		{
			ambience_files[id].setLooping(true);			
			ambience_files[id].start();			
		}
	}
	
	public void stop_ambience(int id)
	{
		if((ambience_files != null && ambience_files[id] != null) && ambience_files[id].isPlaying())
		{
			ambience_files[id].stop();
			ambience_files[id].reset();
			ambience_files[id].release();
			ambience_files[id] = null;
		}
	}
	
	public void release_Ambience_sound()
	{
		for(int i=0; i<ambience_files.length; i++)
		{
			if((ambience_files != null && ambience_files[i] != null) && ambience_files[i].isPlaying())
			{
				ambience_files[i].stop();
				ambience_files[i].reset();
				ambience_files[i].release();
				ambience_files[i] = null;
			}
		}
	}

}
