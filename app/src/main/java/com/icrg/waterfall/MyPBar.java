package com.icrg.waterfall;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyPBar extends LinearLayout implements Runnable {

	private Context context;
	//private ArrayList<ImageView> imageHolders;
	private ArrayList<String> images;
	private Thread animationThread;
	private boolean stopped = true;
	/*Integer[] image_id = { R.drawable.water_fluid_0, R.drawable.water_fluid_1, R.drawable.water_fluid_2, R.drawable.water_fluid_3,
						  R.drawable.water_fluid_4, R.drawable.water_fluid_5, R.drawable.water_fluid_6, R.drawable.water_fluid_7, R.drawable.water_fluid_8,
						  R.drawable.water_fluid_9, R.drawable.water_fluid_10, R.drawable.water_fluid_11, R.drawable.water_fluid_12,
						  R.drawable.water_fluid_13, R.drawable.water_fluid_14, R.drawable.water_fluid_15, R.drawable.water_fluid_16,						  
						  R.drawable.water_fluid_17, R.drawable.water_fluid_18, R.drawable.water_fluid_19, R.drawable.water_fluid_20,
						  R.drawable.water_fluid_21, R.drawable.water_fluid_22, R.drawable.water_fluid_23, R.drawable.water_fluid_24,
						  R.drawable.water_fluid_25, R.drawable.water_fluid_26, R.drawable.water_fluid_27, R.drawable.water_fluid_28,
						  R.drawable.water_fluid_29, R.drawable.water_fluid_30, R.drawable.water_fluid_31, R.drawable.water_fluid_32,
						  R.drawable.water_fluid_33, R.drawable.water_fluid_34, R.drawable.water_fluid_35, R.drawable.water_fluid_36,
						  R.drawable.water_fluid_37, R.drawable.water_fluid_38, R.drawable.water_fluid_39, R.drawable.water_fluid_40,
						  R.drawable.water_fluid_41, R.drawable.water_fluid_42, R.drawable.water_fluid_43, R.drawable.water_fluid_44,
						  R.drawable.water_fluid_45, R.drawable.water_fluid_46, R.drawable.water_fluid_47, R.drawable.water_fluid_48,
						  R.drawable.water_fluid_49, R.drawable.water_fluid_50, R.drawable.water_fluid_51, R.drawable.water_fluid_52,
						  R.drawable.water_fluid_53, R.drawable.water_fluid_54, R.drawable.water_fluid_55, R.drawable.water_fluid_56,
						  R.drawable.water_fluid_57, R.drawable.water_fluid_58, R.drawable.water_fluid_59, R.drawable.water_fluid_60,
						  R.drawable.water_fluid_61, R.drawable.water_fluid_62, R.drawable.water_fluid_63, R.drawable.water_fluid_64,
						  R.drawable.water_fluid_65, R.drawable.water_fluid_66, R.drawable.water_fluid_67, R.drawable.water_fluid_68,
						  R.drawable.water_fluid_69, R.drawable.water_fluid_70, R.drawable.water_fluid_71, R.drawable.water_fluid_72,
						  R.drawable.water_fluid_73, R.drawable.water_fluid_74, R.drawable.water_fluid_75, R.drawable.water_fluid_76,
						  R.drawable.water_fluid_77, R.drawable.water_fluid_78, R.drawable.water_fluid_79, R.drawable.water_fluid_80,
						  R.drawable.water_fluid_81, R.drawable.water_fluid_82, R.drawable.water_fluid_83, R.drawable.water_fluid_84,
						  R.drawable.water_fluid_85, R.drawable.water_fluid_86, R.drawable.water_fluid_87, R.drawable.water_fluid_88,
						  R.drawable.water_fluid_89 R.drawable.water_fluid_90};*/

	public MyPBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		prepareLayout();
	}

	public MyPBar(Context context) {
		super(context);
		this.context = context;
		prepareLayout();
	}

	/**
	 * This is called when you want the dialog to be dismissed
	 */
	public void dismiss() {
		stopped = false;
		//setVisibility(View.GONE);
	}

	/**
	 * Loads the layout and sets the initial set of images
	 */
	private void prepareLayout() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.mypbar, null);
		addView(view);

		//imageHolders = new ArrayList<ImageView>();
		//imageHolders.add((ImageView) view.findViewById(R.id.imgOne));
		/*imageHolders.add((ImageView) view.findViewById(R.id.imgTwo));
		imageHolders.add((ImageView) view.findViewById(R.id.imgThree));*/

		// Prepare an array list of images to be animated
		images = new ArrayList<String>();

		
	}

	/**
	 * Starts the animation thread
	 */
	public void startAnimation() {
		setVisibility(View.VISIBLE);
		stopped = true;
		animationThread = new Thread(this, "Pgress");
		animationThread.start();
	}

	@Override
	public void run() {
		while (stopped) {
			try {
				// Sleep for some time and then change the images
				Thread.sleep(100);
				handler.sendEmptyMessage(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int currentImage = 0;
			int nextImage = 0;
			// Logic to change the images
			/*for (ImageView imageView : imageHolders) 
			{
				currentImage = Integer.parseInt(imageView.getTag().toString());
				if (currentImage < 14) 
				{
					nextImage = currentImage + 1;
				} 
				else 
				{
					nextImage = 1;
				}
				imageView.setTag("" + nextImage);				
				imageView.setImageResource(image_id[nextImage - 1]);
						
			}*/
			super.handleMessage(msg);
		}

	};

}
