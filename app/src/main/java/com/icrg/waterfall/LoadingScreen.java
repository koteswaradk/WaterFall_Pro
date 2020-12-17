package com.icrg.waterfall;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class LoadingScreen extends Activity 
{
	//Introduce an delay
	private final int WAIT_TIME = 500;	
	private MyPBar pBar;
	Timer timer;
	private static ProgressDialog dialog;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);        
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			setContentView(R.layout.loading_screen);
			timer = new Timer();
			pBar = (MyPBar)findViewById(R.id.progressBar);
			pBar.startAnimation();
			new Handler().postDelayed(new Runnable(){ 
				@Override 
				public void run() { 
					timer.schedule(new TimerTask() {						
						@Override
						public void run() {
							// TODO Auto-generated method stub							
							/* Create an Intent that will start the ProfileData-Activity. */	        				
							Intent mainIntent = new Intent(LoadingScreen.this, FeelIt.class);							
							overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);					
							startActivity(mainIntent);							
							finish();
							pBar.dismiss();	
						}
					}, 2500);					
				} 
			}, WAIT_TIME);
		}
		
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			stopService(new Intent(LoadingScreen.this, DefaultSound.class));
		}		
}
