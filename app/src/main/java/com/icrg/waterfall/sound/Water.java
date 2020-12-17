package com.icrg.waterfall.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.icrg.waterfall.R;

public class Water 
{
	public Context ctxt = null;
	public MediaPlayer fall1 = null, fall2 = null, /*fall3 = null, fall4 = null,*/ fall5 = null, fall6 = null, fall7 = null;
	public int[] raw_waterfiles = {R.raw.theme1aandroid, R.raw.theme2aandroid, R.raw.theme4aandroid, R.raw.theme5aandroid};
	public MediaPlayer[] fall_files = {fall1, fall2, fall5, fall6, fall7};
	
	public Water(Context context) 
	{
		ctxt = context;	
	}
	
	public void create_fall_sound(int id)
	{		
		fall_files[id] = MediaPlayer.create(ctxt, raw_waterfiles[id]);    	
	}	
	
	public void pause_water(int id)
	{
		if((fall_files != null && fall_files[id] != null) && fall_files[id].isPlaying())
		{
			fall_files[id].pause();			
		}
	}
	
	public void start_water(int id)
	{
		if((fall_files != null && fall_files[id] != null) && !fall_files[id].isPlaying())
		{
			fall_files[id].setLooping(true);			
			fall_files[id].start();			
		}
	}
	
	public void release_fall_sound(int id)
	{		
		if(fall_files != null && fall_files[id] != null)
		{
			fall_files[id].stop();
			fall_files[id].reset();
			fall_files[id].release();
			fall_files[id] = null;
		}
	}
}
