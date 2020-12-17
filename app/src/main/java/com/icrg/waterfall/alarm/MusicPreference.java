package com.icrg.waterfall.alarm;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class MusicPreference extends ListPreference 
{

	public MusicPreference(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		String[] values = new String[] 
		{"Bird", "Classic", "Cuckoo", "Spring"};
		
		setEntries(values);
		setEntryValues(values);
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		if(positiveResult)
		{
			setSummary("summary");
		}
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		// TODO Auto-generated method stub
		super.onPrepareDialogBuilder(builder);
		CharSequence[] entries = getEntries();
		builder.setMultiChoiceItems(entries, null, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
