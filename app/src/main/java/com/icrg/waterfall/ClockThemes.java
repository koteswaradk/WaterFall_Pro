package com.icrg.waterfall;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class ClockThemes extends Activity 
{
	private static final String ON_OFF_PREFERENCE = "onoff";
	private static final String GRID_PREFERENCE = "grid";
	private ImageButton onoff = null, done = null;
	private GridView grid = null;
	/*private Integer[] thumbs = {R.drawable.clock_1, R.drawable.clock_2, R.drawable.clock_3, R.drawable.clock_4, R.drawable.clock_5,
					            R.drawable.clock_6};
	private Integer[] thumbs_tick = {R.drawable.clock_1_tick, R.drawable.clock_2_tick, R.drawable.clock_3_tick, R.drawable.clock_4_tick, 
			                         R.drawable.clock_5_tick, R.drawable.clock_6_tick};*/
	private ImageView tick_mark = null;
	private boolean isOn = true;
	private SharedPreferences sPreferences = null;
	private Editor editor = null;
	private AllImages allImages = null;
	private int previous_selected = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.clockthemes);
		
		allImages = new AllImages(ClockThemes.this);
		
		sPreferences = PreferenceManager.getDefaultSharedPreferences(ClockThemes.this);
		editor = sPreferences.edit();
		
		previous_selected = sPreferences.getInt(GRID_PREFERENCE, 0);
		System.out.println("Saved value is ======================>>>>>>>>>>>>>>>>>>>>>>>>>>"+previous_selected);
		
		//thumbs[previous_selected] = thumbs_tick[previous_selected];
		
		grid = (GridView)findViewById(R.id.grid);
		grid.setAdapter(new GridAdapter(ClockThemes.this));
		
		onoff = (ImageButton)findViewById(R.id.onoff_clockthemes);
		if(sPreferences.getBoolean(ON_OFF_PREFERENCE, false) == true)
		{
			System.out.println("preference is true -------------------->>>>>>>>>>>>>>>>>>>>>>");
			isOn = true;
			grid.setEnabled(true);
			grid.setClickable(true);
			onoff.setBackgroundResource(R.drawable.on_image);
			WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		    layoutParams.screenBrightness = (float)0.5;
		    getWindow().setAttributes(layoutParams);			
		}
		else
		{
			System.out.println("preference is false -------------------->>>>>>>>>>>>>>>>>>>>>>");
			isOn = false;
			grid.setEnabled(false);
			grid.setClickable(false);
			grid.setSoundEffectsEnabled(false);
			onoff.setBackgroundResource(R.drawable.off_image);
			WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		    layoutParams.screenBrightness = (float) 0.1;
		    getWindow().setAttributes(layoutParams);			
		}
		done = (ImageButton)findViewById(R.id.done_clockthemes);		
		
		//grid.setGravity(Gravity.BOTTOM);
		
		tick_mark = new ImageView(ClockThemes.this);
		tick_mark.setBackgroundResource(R.drawable.tick_mark);
		
		onoff.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(v.getId() == R.id.onoff_clockthemes)
				{
					if(isOn)
					{
						v.setBackgroundResource(R.drawable.off_image);					
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
						grid.setClickable(false);
						grid.clearFocus();
						grid.setEnabled(false);
						grid.setKeepScreenOn(false);
						grid.setSaveEnabled(true);	
						editor.putBoolean(ON_OFF_PREFERENCE, false);
						editor.commit();
						WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
					    layoutParams.screenBrightness = (float) 0.1;
					    getWindow().setAttributes(layoutParams);
					}
					else
					{
						v.setBackgroundResource(R.drawable.on_image);
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
						grid.setClickable(true);
						grid.setFocusable(true);
						grid.setEnabled(true);
						grid.setKeepScreenOn(true);
						editor.putBoolean(ON_OFF_PREFERENCE, true);
						editor.commit();
						WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
					    layoutParams.screenBrightness = (float) 0.5;
					    getWindow().setAttributes(layoutParams);					    
					}
					isOn = !isOn;
				}
			}
		});
		
		done.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				finish();				
			}
		});			
		
		grid.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> adpt, View view, int position, long id) 
			{
//				// TODO Auto-generated method stub	
//				for(int i=0; i<thumbs.length; i++)
//				{
//					thumbs[i] = allImages.thumbs[i];
//					System.out.println("repleces image ------->>>>>>>>>>>"+i);
//				}
//				thumbs[previous_selected] = allImages.thumbs[previous_selected];
				System.out.println("Item selected is ----------------->>>>>>>>>>>>>>>>>++++++++++++++++++++++++++++++"+position);
				editor.putInt(GRID_PREFERENCE, position);
				editor.commit();			
			}			
		});		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//thumbs[sPreferences.getInt(GRID_PREFERENCE, 0)] = thumbs_tick[sPreferences.getInt(GRID_PREFERENCE, 0)];
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*if(keyCode == KeyEvent.KEYCODE_BACK)
			return this.moveTaskToBack(true);
		else*/
			return super.onKeyDown(keyCode, event);		
	}	
	
	public class GridAdapter extends BaseAdapter
	{		
		Context context = null;
		private int selectedPosition = -1;
		private ImageView selectedView = null;
		private Integer[] thumbs = {R.drawable.clock_1, R.drawable.clock_2, R.drawable.clock_3, R.drawable.clock_4, R.drawable.clock_5,
	            R.drawable.clock_6};
		private Integer[] thumbs_tick = {R.drawable.clock_1_tick, R.drawable.clock_2_tick, R.drawable.clock_3_tick, R.drawable.clock_4_tick, 
                     R.drawable.clock_5_tick, R.drawable.clock_6_tick};
		
		public GridAdapter(Context ctxt) 
		{			
			context = ctxt;			
		}		

		@Override
		public boolean areAllItemsEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEnabled(int position) 
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return thumbs[position];
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return thumbs.length;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return thumbs[position];
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return thumbs[position];
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) 
		{
			// TODO Auto-generated method stub
			//thumbs[previous_selected] = thumbs_tick[previous_selected];
			final ImageView img = new ImageView(context);						
			img.setOnClickListener(new OnClickListener() 
			{				
				@Override
				public void onClick(View v) 
				{
					thumbs[position] = allImages.thumbs[position];
					System.out.println("Item selected in getView is------------->>>>>>>>>>>>"+position);
					if(sPreferences.getBoolean(ON_OFF_PREFERENCE, false) == true)
					{
						img.setSoundEffectsEnabled(false);
						editor.putInt(GRID_PREFERENCE, position);
						editor.commit();					
						if (selectedPosition != -1) 
						{
			                selectedView.setImageResource(thumbs[selectedPosition]);
			            }	
						img.setImageResource(thumbs_tick[position]);
						
						selectedPosition = position;
			            selectedView = (ImageView)v;
					}
				}				
			});			
			img.setBackgroundResource(thumbs[position]);			
			return img;
		}		
	}
	
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		int value = sPreferences.getInt(GRID_PREFERENCE, 0);
		System.out.println("Item stored in sharedPreference is ---------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value);
	}

}
