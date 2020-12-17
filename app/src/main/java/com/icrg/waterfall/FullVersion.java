package com.icrg.waterfall;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FullVersion extends Activity
{
	private ImageButton back;
	private String address = "https://market.android.com/?hl=en";
	private boolean isFeelIt = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.full_version);
        
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
        	isFeelIt = bundle.getBoolean("isFeelit");
        }
        
        back=(ImageButton)findViewById(R.id.available_on_aandroidmarket);
        back.setOnClickListener(new OnClickListener() 
        {			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	            myWebLink.setData(Uri.parse(address));
	            startActivity(myWebLink);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*if(keyCode == KeyEvent.KEYCODE_BACK)
			return this.moveTaskToBack(true);
		else*/
			return super.onKeyDown(keyCode, event);		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(!isFeelIt)
			finish();
		else
		{
			startActivity(new Intent(FullVersion.this, FeelIt.class));
			finish();
		}
	}

}
