package com.icrg.waterfall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity
{

	private boolean active = true;
	private int splashtime = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.splash);		
		Thread splash=new Thread()
		{
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				super.run();
				try
				{
					int waitid = 0;
					while(active && (waitid < splashtime))
					{
						sleep(100);
						if(active)
						{
							waitid += 100;
						}
					}
				}
				catch (InterruptedException e) 
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				finally
				{
					finish();
					startActivity(new Intent("com.droidnova.android.splashscreen.MyAppWaterfall"));					
				}
				
			}
		};
		splash.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			active = false;
		}
		return true;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
