package com.icrg.waterfall.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.icrg.waterfall.R;

public class Binaural 
{
	public Context ctxt = null;
	public MediaPlayer alpha = null, beta = null, delta = null, meditation = null, powernap = null, beach1 = null, theta = null, sleep = null;
	public int[] raw_binauralfiles = {R.raw.alpha, R.raw.beta, R.raw.delta, R.raw.meditation, R.raw.powernap, R.raw.beach1, R.raw.theata, R.raw.sleep};
	public MediaPlayer[] binaural_files = {alpha, beta, delta, meditation, powernap, beach1, theta, sleep};
	
	public Binaural(Context context) 
	{
		ctxt = context;		
	}
	
	public void create_binaural_sound(int id)
	{
		binaural_files[id] = MediaPlayer.create(ctxt, raw_binauralfiles[id]);  
	}
	
	public void pause_binaural(int id)
	{
		if((binaural_files != null && binaural_files[id] != null) && binaural_files[id].isPlaying())
		{
			binaural_files[id].pause();			
		}
	}
	
	public void start_binaural(int id)
	{
		if((binaural_files != null && binaural_files[id] != null) && !binaural_files[id].isPlaying())
		{
			binaural_files[id].setLooping(true);
			binaural_files[id].start();			
		}
	}
	
	public void release_binaural_sound(int id)
	{
		if(binaural_files != null && binaural_files[id] != null)
		{
			binaural_files[id].stop();
			binaural_files[id].reset();
			binaural_files[id].release();
			binaural_files[id] = null;
		}
	}

}
