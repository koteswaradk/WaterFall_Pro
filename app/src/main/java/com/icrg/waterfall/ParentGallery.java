package com.icrg.waterfall;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

public class ParentGallery extends Gallery implements SpinnerAdapter
{
	private Context ctxt;
	private int mgallerybackground, position;
	private ImageView img = null;
	private Integer[] HorizontalScrollImages={R.drawable.theme, R.drawable.animation, R.drawable.ambiance, 
            R.drawable.instrumental_musics, R.drawable.mode};
	private Integer[] HorizontalScrollImages_hvr={R.drawable.theme_hvr, R.drawable.animation_hvr, R.drawable.ambiance_hvr, 
                R.drawable.instrumental_musics_hvr, R.drawable.mode};
	private int pos = 0;
	private static final int SWIPE_MIN_DISTANCE = 20;
    private static final int SWIPE_MAX_OFF_PATH = 50;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	public ParentGallery(Context context, int posn) 
	{
		super(context);
		ctxt = context;
		img = new ImageView(ctxt);
		position = pos ;
		/*TypedArray typedArray = obtainStyledAttributes(R.styleable.HelloGallery);
		mgallerybackground = typedArray.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
		typedArray.recycle();*/
		// TODO Auto-generated constructor stub
	}
	
		@Override
	  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	 {
	    float velMax = 500f;
	    float velMin = 100f;
	    float velX = Math.abs(velocityX);
	    if (velX > velMax) 
	    {
	      velX = velMax;
	    } 
	    else if (velX < velMin) 
	    {
	      velX = velMin;
	    }
	    velX -= 1600;
	    int k = 500000;
	    int speed = (int) Math.floor(1f / velX * k);
	    setAnimationDuration(speed);

	    int kEvent;
	    if (isScrollingLeft(e1, e2)) 
	    {
	      // Check if scrolling left
	      kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
	    } 
	    else 
	    {
	      // Otherwise scrolling right
	      kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
	    }
	    onKeyDown(kEvent, null);
	    return true;
	  }

	
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		setAnimationDuration(600);
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	

	@Override
	public View getDropDownView(int position, View convertView,	ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return HorizontalScrollImages.length;
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return HorizontalScrollImages.length;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return HorizontalScrollImages.length;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		ImageView img = new ImageView(ctxt);
		img.setBackgroundResource(HorizontalScrollImages[position]);		
		return img;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub			
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub			
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
}
