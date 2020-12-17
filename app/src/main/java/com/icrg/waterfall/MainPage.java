package com.icrg.waterfall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainPage extends Activity implements OnClickListener 
{
	private ImageButton moreapps = null, help = null, settings, full_version, feel_it;
	private SharedPreferences sPreferences;
	private Editor editor;
	private ProgressDialog dialog = null;
	private boolean isFeelitPaused = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.main);
		
		sPreferences = PreferenceManager.getDefaultSharedPreferences(MainPage.this);
		editor = sPreferences.edit();	
		
		moreapps = (ImageButton)findViewById(R.id.moreapps);
		help = (ImageButton)findViewById(R.id.help);
		settings = (ImageButton)findViewById(R.id.settings);
		full_version = (ImageButton)findViewById(R.id.full_version);
		feel_it = (ImageButton)findViewById(R.id.feelit);
		
		moreapps.setOnClickListener(this);
		help.setOnClickListener(this);
		settings.setOnClickListener(this);
		full_version.setOnClickListener(this);
		feel_it.setOnClickListener(this);
		
//		System.out.println(" set value is ---------------------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + sPreferences.getBoolean("set", false));
			
	}	
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
			case R.id.moreapps:
				 v.setBackgroundResource(R.drawable.more_apps_new);
				 Intent in1 = new Intent(MainPage.this, MoreApps.class);
				 startActivity(in1);
				 break;
				 
			case R.id.help:
				 v.setBackgroundResource(R.drawable.help_new);
				 Intent in2 = new Intent(MainPage.this, Help.class);
				 startActivity(in2);
				 break;
				 
			case R.id.settings:
				 v.setBackgroundResource(R.drawable.settings_new);
				 Intent in3 = new Intent(MainPage.this, Settings.class);
				 startActivity(in3);
				 break;			
				 
			case R.id.full_version:
				 v.setBackgroundResource(R.drawable.full_version_new);
				 Intent in4 = new Intent(MainPage.this, FullVersion.class);
				 startActivity(in4);
				 break;
				 
			case R.id.feelit:
				 feel_it.setClickable(false);
				 v.setBackgroundResource(R.drawable.feelit_over_new);
				 /*Intent in5 = new Intent(MainPage.this, LoadingScreen.class);
				 startActivity(in5);*/
				 feelIt();
				 //new UploadTask().execute();
				 break;					 
		}
		
	}
	
	private void feelIt() 
	{
		// TODO Auto-generated method stub
		//progressDialog = ProgressDialog.show(MainPage.this, "Loading", "Please wait...");
		/*new Thread(new Runnable() {			
			@Override
			public void run() {	*/			
				 Intent feel_It = new Intent(MainPage.this, FeelIt.class);
				 startActivityForResult(feel_It, 1);
			/*}
		}).start();*/
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//startService(new Intent(MainPage.this, DefaultSound.class));
	}
	
	Handler start_handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			dialog = ProgressDialog.show(MainPage.this, "Loading",	"Please wait...", true);
		};
	};
	
	Handler end_handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(dialog != null && dialog.isShowing())
				dialog.dismiss();
		};
	};
	
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();		
		//stopService(new Intent(MainPage.this, DefaultSound.class));
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- MainPage paused -------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}


	@Override
	protected void onRestart() 
	{
		// TODO Auto-generated method stub
		super.onRestart();
		feel_it.setClickable(true);
		moreapps.setBackgroundResource(R.drawable.more_apps_over_new);
		help.setBackgroundResource(R.drawable.help_over_new);
		settings.setBackgroundResource(R.drawable.setting_over_new);
		full_version.setBackgroundResource(R.drawable.full_version_over_new);
		feel_it.setBackgroundResource(R.drawable.feelit_new);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		// TODO Auto-generated method stub
		/*if(keyCode == KeyEvent.KEYCODE_BACK)
			return this.moveTaskToBack(true);
		else*/
			return super.onKeyDown(keyCode, event);		
	}
	
	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		//stopService(new Intent(MainPage.this, DefaultSound.class));
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- MainPage stopped -------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		end_handler.sendEmptyMessage(0);
	}
	
	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		//stopService(new Intent(MainPage.this, DefaultSound.class));
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- MainPage destroyed -------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		editor.putBoolean("isAppClosed", true);
		editor.commit();
		for(int i=0; i<7; i++)
		{			
			editor.putBoolean("anim_bool"+i, false);
			editor.commit();			
		}		
		
		for(int i=0; i<10; i++)
		{
			editor.putBoolean("amb_bool"+i, false);
			editor.commit();			
		}
		
		editor.putInt("bin_pos", 0);
		editor.putInt("instr_pos", 0);
		editor.commit();
		editor.putString("bin_string", "true");
		editor.putString("instr_string", "true");
		editor.commit();
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- In MainPage onBackPressed -------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		editor.putBoolean("isAppClosed", true);
		editor.commit();
		
		for(int i=0; i<7; i++)
		{			
			editor.putBoolean("anim_bool"+i, false);
			editor.commit();			
		}		
		
		for(int i=0; i<10; i++)
		{
			editor.putBoolean("amb_bool"+i, false);
			editor.commit();			
		}
		
		editor.putInt("bin_pos", 0);
		editor.putInt("instr_pos", 0);
		editor.commit();
		editor.putString("bin_string", "true");
		editor.putString("instr_string", "true");
		editor.commit();
		finishActivity(1);
		System.runFinalization();
		System.runFinalizersOnExit(true);
		System.exit(0);
		finish();
	}
	
	
	class UploadTask extends AsyncTask<Void, Void, String> 
	{
		@Override
		protected String doInBackground(Void... unsued) 
		{
			try 
			{				
				feelIt();
			} 
			catch (Exception e) 
			{
				if (dialog.isShowing())
				{
					dialog.dismiss();					
				}
				Log.e(e.getClass().getName(), e.getMessage(), e);
				return null;
			}
			return null;
		}		

		@Override
		protected void onProgressUpdate(Void... unsued) 
		{
			
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String sResponse) {
			try 
			{
				if(dialog.isShowing())
					dialog.dismiss();								
			} 
			catch (Exception e) 
			{
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- inside onSaveInstanceState in MainPage ----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>> ");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- inside onRestoreInstanceState in MainPage ----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>> ");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- inside onActivityResult in MainPage ----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>> "+" requestCode -> "+requestCode+" resultCode -> "+resultCode);
		if(resultCode == Activity.RESULT_OK)
		{
			isFeelitPaused = true;
//			System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------------- inside onActivityResult in MainPage RESULT_OK ----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>> ");
		}
	}

}
