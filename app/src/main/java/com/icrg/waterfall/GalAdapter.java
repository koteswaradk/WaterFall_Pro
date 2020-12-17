package com.icrg.waterfall;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GalAdapter extends BaseAdapter
{
	private int[] IMAGES = new int[] 
       {
     	R.drawable.theme,
       	R.drawable.animation,
       	R.drawable.ambiance,
       	R.drawable.instrumental_musics,
       	R.drawable.mode,
       };
	
	private Context ctxt;

	public GalAdapter(Context context) 
	{
		ctxt = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return IMAGES.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return IMAGES[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return IMAGES[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView img = new ImageView(ctxt);
		img.setBackgroundResource(IMAGES[position]);
		return img;
	}
	
}


