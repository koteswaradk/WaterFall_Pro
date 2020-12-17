package com.icrg.waterfall;

import java.io.File;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.icrg.waterfall.alarm.AlarmClock;

public class Settings extends Activity implements OnClickListener, OnTimeSetListener, OnSeekBarChangeListener
{
	private static final String AMBIANCE_BUTTON_STRING = "ambience";
	private static final String BINAURAL_BUTTON_STRING = "binaural_sound";
	private static final String INSTRUMENTAL_BUTTON_STRING = "instrumental_sound";
	private static final String WATER_BUTTON_STRING = "water";
	
	private static final String AMBIANCE_SLIDER_SETTINGS = "ambience_slider_settings";
	private static final String BINAURAL_SLIDER_SETTINGS = "ambience_slider_settings";
	private static final String INSTRUMENTAL_SLIDER_SETTINGS = "ambience_slider_settings";
	private static final String WATER_SLIDER_SETTINGS = "ambience_slider_settings";
	
	private static final String AMBIANCE_SLIDER_FEELIT = "ambience_progress";
	private static final String BINAURAL_SLIDER_FEELIT = "binaural_progress";
	private static final String INSTRUMENTAL_SLIDER_FEELIT = "instrumental_progress";
	private static final String WATER_SLIDER_FEELIT = "water_progress";
	
	private boolean water_shared, ambiance_shared, binaural_shared, instrumental_shared;
	private int water_slider_settings, ambiance_slider_settings, binaural_slider_settings, instrumental_slider_settings; 
	private int water_slider_feelit, ambiance_slider_feelit, binaural_slider_feelit, instrumental_slider_feelit;
	
	private static final String STOPCLOCK_STRING = "stop_clock";
	private static final String SLIDESHOW_STRING = "slideshow";
	
	private ImageButton /*back,*/ alarm, clock_themes/*, music_library*/;
	private ImageButton ambience_onoff, instrumental_onoff, water_onoff, binaural_onoff;
	private ImageButton stopclock, slideshow;	
	private SeekBar ambience_slider, instrumental_slider, water_slider, binaural_slider;
	private boolean isAmbience_on = true, isInstrumental_on = true, isWater_on = true, isBinaural_on = true;
	private boolean isStopclock = false, isSlideshow = false; 
	private SharedPreferences sPreferences;
	private Editor editor;	
	private TextView stopclock_time, slideshow_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.settings_control);
		
		init();		
		
	}
	
	private void init() 
	{
		// TODO Auto-generated method stub
		sPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sPreferences.edit();
		
		water_shared = sPreferences.getBoolean(WATER_BUTTON_STRING, true);
		ambiance_shared = sPreferences.getBoolean(AMBIANCE_BUTTON_STRING, true);
		binaural_shared = sPreferences.getBoolean(BINAURAL_BUTTON_STRING, true);
		instrumental_shared  = sPreferences.getBoolean(INSTRUMENTAL_BUTTON_STRING, true);
		
		water_slider_settings = sPreferences.getInt(WATER_SLIDER_SETTINGS, 25);
		ambiance_slider_settings = sPreferences.getInt(AMBIANCE_SLIDER_SETTINGS, 25);
		binaural_slider_settings = sPreferences.getInt(BINAURAL_SLIDER_SETTINGS, 25);
		instrumental_slider_settings = sPreferences.getInt(INSTRUMENTAL_SLIDER_SETTINGS, 25);
		
		water_slider_feelit = sPreferences.getInt(WATER_SLIDER_FEELIT, 25);
		ambiance_slider_feelit = sPreferences.getInt(AMBIANCE_SLIDER_FEELIT, 25);
		binaural_slider_feelit = sPreferences.getInt(BINAURAL_SLIDER_FEELIT, 25);
		instrumental_slider_feelit = sPreferences.getInt(INSTRUMENTAL_SLIDER_FEELIT, 25);
		
		stopclock_time=(TextView)findViewById(R.id.stopclock_time);
		stopclock_time.setText(sPreferences.getString(STOPCLOCK_STRING, "MM:SS"));
		slideshow_time=(TextView)findViewById(R.id.slideshow_time);
		slideshow_time.setText(sPreferences.getString(SLIDESHOW_STRING, "MM:SS"));
		
		//back=(ImageButton)findViewById(R.id.settings_back);
		alarm=(ImageButton)findViewById(R.id.Alarm);
		clock_themes=(ImageButton)findViewById(R.id.Clock_themes);
		//music_library=(ImageButton)findViewById(R.id.Music_library);
		
		stopclock=(ImageButton)findViewById(R.id.stopclock_settings);
		slideshow=(ImageButton)findViewById(R.id.slideshow_settings);
			
		stopclock.setOnClickListener(this);
		slideshow.setOnClickListener(this);
		
		ambience_onoff=(ImageButton)findViewById(R.id.ambience_settings_onoff);
		if(ambiance_shared)
		{
			isAmbience_on = true;
			ambience_onoff.setBackgroundResource(R.drawable.on_image);
		}
		else
		{
			isAmbience_on = false;
			ambience_onoff.setBackgroundResource(R.drawable.off_image);
		}
		
		instrumental_onoff=(ImageButton)findViewById(R.id.instrumental_settings_onoff);
		if(instrumental_shared)
		{
			isInstrumental_on = true;
			instrumental_onoff.setBackgroundResource(R.drawable.on_image);
		}
		else
		{
			isInstrumental_on = false;
			instrumental_onoff.setBackgroundResource(R.drawable.off_image);
		}
		
		water_onoff = (ImageButton)findViewById(R.id.water_settings_onoff);
		if(water_shared)
		{
			isWater_on = true;
			water_onoff.setBackgroundResource(R.drawable.on_image);
		}
		else
		{
			isWater_on = false;
			water_onoff.setBackgroundResource(R.drawable.off_image);
		}
		
		binaural_onoff=(ImageButton)findViewById(R.id.binaural_settings_onoff);
		if(binaural_shared)
		{
			isBinaural_on = true;
			binaural_onoff.setBackgroundResource(R.drawable.on_image);
		}
		else
		{
			isBinaural_on = false;
			binaural_onoff.setBackgroundResource(R.drawable.off_image);
		}
		
		//back.setOnClickListener(this);
		alarm.setOnClickListener(this);
		clock_themes.setOnClickListener(this);
		//music_library.setOnClickListener(this);
		
		ambience_onoff.setOnClickListener(this);
		instrumental_onoff.setOnClickListener(this);
		water_onoff.setOnClickListener(this);
		binaural_onoff.setOnClickListener(this);
		
		ambience_slider=(SeekBar)findViewById(R.id.ambience_slider_settings);
		ambience_slider.setProgress(ambiance_slider_feelit);
		ambience_slider.setOnSeekBarChangeListener(this); 
				
		instrumental_slider=(SeekBar)findViewById(R.id.instrumental_slider_settings);
		instrumental_slider.setProgress(instrumental_slider_feelit);
		instrumental_slider.setOnSeekBarChangeListener(this);
		
		water_slider=(SeekBar)findViewById(R.id.water_slider_settings);
		water_slider.setProgress(water_slider_feelit);
		water_slider.setOnSeekBarChangeListener(this);
		
		binaural_slider=(SeekBar)findViewById(R.id.binaural_slider_settings);
		binaural_slider.setProgress(binaural_slider_feelit);
		binaural_slider.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
		/*case R.id.settings_back:
			 finish();
		   	 break;*/
		   	 
		case R.id.ambience_settings_onoff:
			 if(v.getId() == R.id.ambience_settings_onoff)
			 {
				 if(isAmbience_on)
				 {
					 v.setBackgroundResource(R.drawable.off_image);
					 editor.putBoolean(AMBIANCE_BUTTON_STRING, false);
					 editor.commit();					 
				 }
				 else
				 {
					 v.setBackgroundResource(R.drawable.on_image);
					 editor.putBoolean(AMBIANCE_BUTTON_STRING, true);
					 editor.commit();					 
				 }
				 isAmbience_on = !isAmbience_on;
			 }
		   	 break;
		   	 
		case R.id.instrumental_settings_onoff:
			 if(v.getId() == R.id.instrumental_settings_onoff)
			 {
				 if(isInstrumental_on)
				 {
					 v.setBackgroundResource(R.drawable.off_image);
					 editor.putBoolean(INSTRUMENTAL_BUTTON_STRING, false);
					 editor.commit();					 
				 }
				 else
				 {
					 v.setBackgroundResource(R.drawable.on_image);
					 editor.putBoolean(INSTRUMENTAL_BUTTON_STRING, true);
					 editor.commit();					 
				 }
				 isInstrumental_on = !isInstrumental_on;
			 }
		   	 break; 
		   	 
		case R.id.water_settings_onoff:
			 if(v.getId() == R.id.water_settings_onoff)
			 {
				 if(isWater_on)
				 {
					 v.setBackgroundResource(R.drawable.off_image);
					 editor.putBoolean(WATER_BUTTON_STRING, false);
					 editor.commit();					 
				 }
				 else
				 {
					 v.setBackgroundResource(R.drawable.on_image);
					 editor.putBoolean(WATER_BUTTON_STRING, true);
					 editor.commit();					 
				 }
				 isWater_on = !isWater_on;
			 }
		   	 break;
		   	 
		case R.id.binaural_settings_onoff:
			 if(v.getId() == R.id.binaural_settings_onoff)
			 {
				 if(isBinaural_on)
				 {
					 v.setBackgroundResource(R.drawable.off_image);
					 editor.putBoolean(BINAURAL_BUTTON_STRING, false);
					 editor.commit();					 
				 }
				 else
				 {
					 v.setBackgroundResource(R.drawable.on_image);
					 editor.putBoolean(BINAURAL_BUTTON_STRING, true);
					 editor.commit();					 
				 }
				 isBinaural_on = !isBinaural_on;
			 }
		   	 break; 
		   	 
		case R.id.stopclock_settings:			 
			 isSlideshow = false;
			 isStopclock = true;
			 new TimePickerDialog(this, this, 0, 10, true).show();
		   	 break;
		   	 
		case R.id.slideshow_settings:			 
			 isStopclock = false;
			 isSlideshow = true;
			 new TimePickerDialog(this, this, 0, 10, true).show();
		   	 break;   	 
		   	 
		case R.id.Alarm:
			 File alarm = new File("/sdcard/alarms");
			 alarm.mkdirs();			 
			 Intent in = new Intent(Settings.this, AlarmClock.class);
			 startActivity(in);			 
		   	 break;  
		   	 
		case R.id.Clock_themes:			 	
			 Intent in1 = new Intent(Settings.this, ClockThemes.class);
			 startActivity(in1);
		   	 break;
		   	 
		/*case R.id.Music_library:
			 Intent intent = new Intent(Intent.ACTION_ALL_APPS);
             intent.setType("audio/music_player");
             startActivity(intent);			 			 
		   	 break;*/		   	 
		}		
	}	

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
	{
		// TODO Auto-generated method stub
		if(isStopclock)
		{
			stopclock_time.setText(new StringBuilder().append(pad(hourOfDay)).append(":").append(pad(minute)));
			editor.putString(STOPCLOCK_STRING, stopclock_time.getText().toString());
			editor.putInt("min", hourOfDay);
			editor.commit();
			editor.putInt("sec", minute);
			editor.commit();
			editor.putBoolean("set", true);
			editor.commit();
		}
		else if(isSlideshow)
		{
			slideshow_time.setText(new StringBuilder().append(pad(hourOfDay)).append(":").append(pad(minute)));
			editor.putString(SLIDESHOW_STRING, slideshow_time.getText().toString());
			editor.commit();
			editor.putInt("min_slideshow", hourOfDay);
			editor.commit();
			editor.putInt("sec_slideshow", minute);
			editor.commit();
		}
	}	
	
	private String pad(int c) 
	{		
		if(c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) 
		{
			case R.id.ambience_slider_settings:
				 editor.putInt(AMBIANCE_SLIDER_SETTINGS, ambience_slider.getProgress());			 
				 editor.putInt("ambience_progress", ambience_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.instrumental_slider_settings:
				 editor.putInt(INSTRUMENTAL_SLIDER_SETTINGS, instrumental_slider.getProgress());
				 editor.putInt("instrumental_progress", instrumental_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.water_slider_settings:
				 editor.putInt(WATER_SLIDER_SETTINGS, water_slider.getProgress());
				 editor.putInt("water_progress", water_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.binaural_slider_settings:
				 editor.putInt(BINAURAL_SLIDER_SETTINGS, binaural_slider.getProgress());
				 editor.putInt("binaural_progress", binaural_slider.getProgress());
				 editor.commit();
				 break;
		}	
			
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser)
		{
			switch (seekBar.getId()) 
			{
				case R.id.ambience_slider_settings:
					 editor.putInt(AMBIANCE_SLIDER_SETTINGS, progress);
					 editor.putInt("ambience_progress", progress);
					 editor.commit();
					 break;
					 
				case R.id.instrumental_slider_settings:
					 editor.putInt(INSTRUMENTAL_SLIDER_SETTINGS, progress);
					 editor.putInt("instrumental_progress", progress);
					 editor.commit();
					 break;
					 
				case R.id.water_slider_settings:
					 editor.putInt(WATER_SLIDER_SETTINGS, progress);
					 editor.putInt("water_progress", progress);
					 editor.commit();
					 break;
					 
				case R.id.binaural_slider_settings:
					 editor.putInt(BINAURAL_SLIDER_SETTINGS, progress);
					 editor.putInt("binaural_progress", progress);
					 editor.commit();
					 break;			
			}
		}
	}	

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) 
		{
			case R.id.ambience_slider_settings:
				 editor.putInt(AMBIANCE_SLIDER_SETTINGS, ambience_slider.getProgress());
				 editor.putInt("ambience_progress", ambience_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.instrumental_slider_settings:
				 editor.putInt(INSTRUMENTAL_SLIDER_SETTINGS, instrumental_slider.getProgress());
				 editor.putInt("instrumental_progress", instrumental_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.water_slider_settings:
				 editor.putInt(WATER_SLIDER_SETTINGS, water_slider.getProgress());
				 editor.putInt("water_progress", water_slider.getProgress());
				 editor.commit();
				 break;
				 
			case R.id.binaural_slider_settings:
				 editor.putInt(BINAURAL_SLIDER_SETTINGS, binaural_slider.getProgress());
				 editor.putInt("binaural_progress", binaural_slider.getProgress());
				 editor.commit();
				 break;
		}
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
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
//		System.out.println("Ambience progress at last is --------------------->>>>>>>>>>>>>>>>>>"+ambience_slider.getProgress());
		editor.putInt("ambience_progress", ambience_slider.getProgress());
		editor.putInt("binaural_progress", binaural_slider.getProgress());
		editor.putInt("instrumental_progress", instrumental_slider.getProgress());
		editor.putInt("water_progress", water_slider.getProgress());
		editor.commit();
	}

}
