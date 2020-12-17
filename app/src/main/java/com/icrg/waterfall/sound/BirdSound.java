package com.icrg.waterfall.sound;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.icrg.waterfall.R;

public class BirdSound 
{
	public MediaPlayer bird1 = null, bird2 = null, bird3 = null, bird4 = null, bird5 = null, bird6 = null, bird7 = null, bird8 = null, bird9 = null, bird10 = null,
				       bird11 = null, bird12 = null, bird13 = null, bird14 = null, bird15 = null, bird16 = null, bird17 = null, bird18 = null, bird19 = null, bird20 = null;
	public int[] raw_birdfiles = {R.raw.bird1, R.raw.bird2, R.raw.bird3, R.raw.bird4, R.raw.bird5, R.raw.bird6, R.raw.bird7, R.raw.bird8, R.raw.bird9, R.raw.bird10, 
			                      R.raw.bird11, R.raw.bird12, R.raw.bird13, R.raw.bird14, R.raw.bird15, R.raw.bird16, R.raw.bird17, R.raw.bird18, R.raw.bird19, R.raw.bird20};
	public MediaPlayer[] bird_files = {bird1, bird2, bird3, bird4, bird5, bird6, bird7, bird8, bird9, bird10, 
									   bird11, bird12, bird13, bird14, bird15, bird16, bird17, bird18, bird19, bird20};
	public Random random = null;
	public int rand = 0;
	public boolean isTrue = true;
	public Context ctxt = null;
	public Timer timer = null;
	public float leftVolume, rightVolume;
	
	public BirdSound(Context context) 
	{
		ctxt = context;
		random = new Random();
		rand = random.nextInt(19);
		timer = new Timer();
//		System.out.println(" Random number generated in BirdSound class is ++++++++++++++++>>>>>>>>>>>>>>>>" + rand);		
	}
	
	public int create_bird_sound(int id)
	{		
		bird_files[id] = MediaPlayer.create(ctxt, raw_birdfiles[id]);		
		return 1;    	
	}
	
	public int stop_bird(int id)
	{
//		System.out.println("came inside stop_bird--------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rand);
		if(bird_files != null && bird_files[id] != null)
		{
//			System.out.println("stopping bird sound --------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rand);
			bird_files[id].stop();
			bird_files[id].reset();
			bird_files[id].release();
			bird_files[id] = null;
		}		
		return 1;
	}	
	
	public void playSound(float left, float right)
	{
		leftVolume = left;
		rightVolume = right;
		/*new Thread(new Runnable() {				
			@Override
			public void run() {*/
				create_bird_sound(rand);
				
				bird_files[rand].setVolume(leftVolume, rightVolume);
				bird_files[rand].start();
				bird_files[rand].setLooping(false);
				bird_files[rand].setOnCompletionListener(new OnCompletionListener() {			
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub	
						
						stop_bird(rand);
						
						rand = random.nextInt(9);					
//						System.out.println(" Random number generated in setOnCompletionListener is  ++++++++++++++++++++++++++++++++ >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+rand);
						if(timer != null)
						{
							timer.schedule(new TimerTask() {								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									playSound(leftVolume, rightVolume);
								}
							}, 500);
						}
					}
				});
			/*}df
		}).start();*/				
	}	
	
	public void setBirdVolume(float left, float right)	
	{
		leftVolume = left;
		rightVolume = right;
		if(bird_files[rand] != null && bird_files[rand].isPlaying())
		{
			bird_files[rand].setVolume(left, right);
		}
	}
	
	public void pauseSound()
	{
		if(bird_files[rand] != null && bird_files[rand].isPlaying())
		{
			bird_files[rand].pause();
		}
	}
	
	public void stopSound()
	{
		if(bird_files[rand] != null && bird_files[rand].isPlaying())
		{			
			bird_files[rand].stop();
			bird_files[rand].reset();
			bird_files[rand].release();
			bird_files[rand] = null;
		}
	}
	
	public void stopAllBirdSound()
	{
//		Thread.currentThread().interrupt();
		if(timer != null)
		{
			/*timer.cancel();
			timer = null;*/
		}
		for(int i=0; i<bird_files.length; i++)
		{
			if(bird_files[i] != null && bird_files[i].isPlaying())
			{			
				bird_files[i].stop();
				bird_files[i].reset();
				bird_files[i].release();
				bird_files[i] = null;
			}
		}
	}
}
