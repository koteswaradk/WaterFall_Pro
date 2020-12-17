package com.icrg.waterfall;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SavedItemsAdapter extends CursorAdapter 
{
	private LayoutInflater lInflater = null;
	private Context ctxt = null;

	public SavedItemsAdapter(Context context, Cursor c)
	{
		super(context, c);
		// TODO Auto-generated constructor stub
		ctxt = context;
		lInflater = LayoutInflater.from(ctxt);
		
	}	

	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		// TODO Auto-generated method stub
		final Save alarm = new Save(cursor);
        TextView filename = (TextView)view.findViewById(R.id.filename_text);       
        filename.setText(alarm.filename);
        if(cursor.getPosition() == 0)
		{
//			System.out.println(" cursor.getPosition() in bindView --------------- 0 >>>>>>>>>>>>>>>> "+cursor.getPosition());
//			view.setBackgroundResource(0);
		}
		if(cursor.getPosition() == 4)
		{
//			System.out.println(" cursor.getPosition() in bindView --------------- 4 >>>>>>>>>>>>>>>> "+cursor.getPosition());
//			view.setBackgroundResource(0);
		}
		if(cursor.getPosition() == 1)
		{
//			System.out.println(" cursor.getPosition() in bindView --------------- 1 >>>>>>>>>>>>>>>> "+cursor.getPosition());
		}
		if(cursor.getPosition() == 5)
		{
//			System.out.println(" cursor.getPosition() in bindView --------------- 5 >>>>>>>>>>>>>>>> "+cursor.getPosition());
		}
//        System.out.println(" Filenames are --------------- >>>>>>>>>>>>>>>> "+alarm.filename);        
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		View view = lInflater.inflate(R.layout.save, parent, false);
		ImageView imageView = (ImageView)view.findViewById(R.id.text_bg);
		if(cursor.getPosition() == 0)
		{
//			System.out.println(" cursor.getPosition() in newView --------------- 0 >>>>>>>>>>>>>>>> "+cursor.getPosition());
			view.setBackgroundResource(0);
			imageView.setBackgroundResource(0);
		}
		if(cursor.getPosition() == 4)
		{
//			System.out.println(" cursor.getPosition() in newView --------------- 4 >>>>>>>>>>>>>>>> "+cursor.getPosition());
			view.setBackgroundResource(0);
			imageView.setBackgroundResource(0);
		}
		if(cursor.getPosition() == 1)
		{
//			System.out.println(" cursor.getPosition() in newView --------------- 1 >>>>>>>>>>>>>>>> "+cursor.getPosition());
		}
		if(cursor.getPosition() == 5)
		{
//			System.out.println(" cursor.getPosition() in newView --------------- 5 >>>>>>>>>>>>>>>> "+cursor.getPosition());
			
		}
		((TextView)view.findViewById(R.id.filename_text)).setText("filename");
		
		return view;
	}

}
