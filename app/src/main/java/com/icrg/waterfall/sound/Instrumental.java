package com.icrg.waterfall.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.icrg.waterfall.R;

public class Instrumental 
{
	public Context ctxt = null;
	public MediaPlayer flute = null, xylophone = null, saxaphone = null, sitar = null, violine = null, piano = null, guitar = null;
	public int[] raw_instrumentalfiles = {R.raw.flute, R.raw.xylophone, R.raw.saxophone, R.raw.sitar, R.raw.violine, R.raw.piano, R.raw.guitar};
	public MediaPlayer[] instrumental_files = {flute, xylophone, saxaphone, sitar, violine, piano, guitar};
	
	public Instrumental(Context context) 
	{
		ctxt = context;		
	}
	
	public void create_instrumental_sound(int id)
	{
		instrumental_files[id] = MediaPlayer.create(ctxt, raw_instrumentalfiles[id]);    
	}	
	
	public void pause_instrumental(int id)
	{
		if((instrumental_files != null && instrumental_files[id] != null) && instrumental_files[id].isPlaying())
		{
			instrumental_files[id].pause();			
		}
	}
	
	public void start_instrumental(int id)
	{
		if((instrumental_files != null && instrumental_files[id] != null) && !instrumental_files[id].isPlaying())
		{
			instrumental_files[id].setLooping(true);
			instrumental_files[id].start();			
		}
	}
	
	public void release_instrumental_sound(int id)
	{
		if(instrumental_files != null && instrumental_files[id] != null)
		{
			instrumental_files[id].stop();
			instrumental_files[id].reset();
			instrumental_files[id].release();
			instrumental_files[id] = null;
		}
	}

}
