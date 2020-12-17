package com.icrg.waterfall;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class DefaultSound extends Service 
{
	private MediaPlayer mp = null;
	private AudioManager audioManager_all = null;
	private int maxVolume = 0, curVolume = 0;

	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mp = MediaPlayer.create(DefaultSound.this, R.raw.flute);
		mp.setLooping(true);
		audioManager_all  = (AudioManager)getSystemService(Context.AUDIO_SERVICE); 
    	
    	maxVolume  = audioManager_all.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        curVolume = audioManager_all.getStreamVolume(AudioManager.STREAM_MUSIC);
        
        mp.setVolume(curVolume, curVolume);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if(mp != null && !mp.isPlaying())
		{
			mp.start();			
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mp != null && mp.isPlaying())
		{
			mp.stop();
			mp.reset();
			mp.release();
			mp = null;
		}
	}
	

}
