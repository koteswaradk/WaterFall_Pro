package com.icrg.waterfall;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.icrg.waterfall.animations.BirdFlyAnimClass;
import com.icrg.waterfall.animations.ButterFlyAnimClass;
import com.icrg.waterfall.animations.CollorNightMode;
import com.icrg.waterfall.animations.FlowerSnow;
import com.icrg.waterfall.animations.LightiningAnimClass;
import com.icrg.waterfall.animations.NightMode;
import com.icrg.waterfall.animations.RainAnimClass;
import com.icrg.waterfall.animations.Rainbow;
import com.icrg.waterfall.animations.Snow;
import com.icrg.waterfall.dragview.DragController;
import com.icrg.waterfall.dragview.DragLayer;
import com.icrg.waterfall.sound.Ambience;
import com.icrg.waterfall.sound.BirdSound;
import com.icrg.waterfall.sound.Instrumental;
import com.icrg.waterfall.sound.Water;

public class FeelIt extends Activity implements OnClickListener, OnSeekBarChangeListener, OnItemLongClickListener, CCTouchDelegateProtocol, OnTouchListener
{
	public static float WIDTH;
	public static float HEIGHT;
	private int parent_pos = 0, fall_pos = 0, instrumental_position = 0, instrumental_position_save = -1;	
	private SharedPreferences sPreferences = null;
	private Editor editor = null;	
	private AllImages all_Images = null;
	
	private Water water_soundclass = null;
	private Ambience ambience_soundclass = null;
	private Instrumental instrumental_soundclass = null;
	
	private Timer timer = null, animationTimer = null, delay_timer = null;
	private Animation fadeIn = null, fadeOut = null, fadeIn_fall = null, fadeOut_fall = null;
	private static String themeName = null, sResponse1;
	private static final int THEME = 0;
	private static final int ANIMATION = 1;
	private static final int AMBIENCE = 2;
	private static final int INSTRUMENTAL_MUSIC = 3;
	private static final int MODES = 4;
	private CustomGallery parentGallery = null;
	private FrameLayout child_gallery = null;
	private LinearLayout parent_gallery = null, animatin_layout = null, ambience_layout = null, instrumental_layout = null, modes_layout = null;
	private LinearLayout controls = null, controls_volume = null, digital_clock = null, horizontal_theme_layout = null;
	private RelativeLayout disappear = null, main_layout = null, show_saved = null, allimage_layout = null;
	
	private Button slideshow = null, reset = null, open = null, save = null, mute = null, volume = null /*, back*/;
	private ImageButton done = null, edit = null;
	private boolean isShow = true, isShow_controls_volume = true, isMute = true, water_shared = false, ambiance_shared, instrumental_shared;
	private boolean isMoved = false, isTouched = false,  isSlideshowOn = false, isDisappearedForever = false, isSavedItemSelected = false;
	private static boolean isSlideshow = true, isInonTouch;
	private HorizontalScrollView horizontal_themes = null;	
	
	private ImageButton theme1 = null, theme2 = null, theme3 = null, theme4 = null;
	private ImageButton bird_animation = null, butterfly_animation = null, rain_animation = null, rainbow_animation = null, snow_animation = null, snow_animation1 = null, thunder_animation = null;
	private ImageButton morning_ambience = null, bird_ambience = null, evening = null, cricket = null, rain_ambience = null, jungle_ambience = null, wind_ambience = null, river_ambience = null, thunder_ambience = null, cicidas = null;	
	private ImageButton flute_instrumental = null, xylophone_instrumental = null, saxophone = null, sitar_instrumental = null, violin = null, piano = null, guitar_instrumental = null;
	private ImageButton day_modes = null, night_modes = null, night_mode_color = null;
//	private ImageButton default_bird = null, default_snow = null, default_lightning = null;
	
	private static boolean isBird_animation = false, isButterfly_animation = false, isRain_animation = false, isSnow_animation = false, isRainbow_animation = false, isSunray = false, isThunder_animation = false;
	private static boolean[] animation_boolean = {isBird_animation, isButterfly_animation, isRain_animation, isSnow_animation, isRainbow_animation, isSunray, isThunder_animation};
	private static boolean[] modes_boolean = {false, false, false, false};
	
	private boolean isMorning_ambience = false, isBird_ambience = false, isEvening_ambience = false, isCricket = false, isRain_ambience = false, isJungle = false, isWind = false, isRiver = false, isThunder_ambience = false, isNight = false;
	private boolean[] ambience_boolean = {isMorning_ambience, isBird_ambience, isEvening_ambience, isCricket, isRain_ambience, isJungle, isWind, isRiver, isThunder_ambience, isNight};
	private boolean isMist = true, isReset = false;
	private boolean isAnimation = true, isAmbience = true, isInstrumental = true, isFall = true, isFall_firstTime = false;
	
	private boolean isAnimationPaused = false, isAmbiencePaused = false, isInstrumentalPaused = false, isFallPaused = false;
	
	private AnalogClock /*analogClock = null,*/ analogClock_1 = null, analogClock_2 = null, analogClock_3 = null, analogClock_4 = null, analogClock_5 = null, analogClock_6 = null;
	private ArrayList<AnalogClock> clockTheme_array = null;	
	private ArrayList<ImageButton> theme_buttons = null, animation_buttons = null, ambience_buttons = null, instrumental_buttons = null;
	
	private SeekBar ambience_slider = null, instrumental_slider = null, water_slider = null;
	
	private boolean isTouchedDown, temp_gettimertime = false, isDown;
	
	private LocalFadeInAnimationListener myFadeInAnimationListener = new LocalFadeInAnimationListener();
    private LocalFadeOutAnimationListener myFadeOutAnimationListener = new LocalFadeOutAnimationListener();   
    
    private Handler handle_fall = null, handle_disappear = null, disappear_handler = null, disappear_forever_handler = null, handle_animations = null, reset_handler = null;  
    
    private String[] instrumentalBoolean = {"true", "true", "true", "true", "true", "true", "true"};
    
	private Cursor mCursor = null;    
    public static float SCREEN_RATIO_X = 1, THEME_SCALE_X =1;
	public static float SCREEN_RATIO_Y = 1, THEME_SCALE_Y = 1;
	public static int THEMECOUNT = 1 , ThemeImageCount = 9, previousThemeImageCount = 0, pListCount = 0;
	static CCSprite globalTheme = null;

	static CCSprite globalThemePatch = null;
    static final String PREFERENCES = "AlarmClock";
    private ListView save_list = null;
    private int instr_id = 0;
	private TextView minute = null, second = null;
	private ImageView clock_image = null;
	private EditText edit_text = null;
	private CountDown countDown = null;
	private long startTime = 0;
	private long interval = 1000;    
    public static boolean isRainSelected = false, isFirstClearSprites = true, isfirstinoncreate = false, isInOnClick = false;
	public static boolean isLightningSelected = false;
	public static CCSpriteSheet birdFlySpriteSheet = null;
	public static CCSprite birdFlySprite = null;
	static CCSprite spriteBirdFly = null, spriteButterFly = null;
	static ArrayList<CCSpriteFrame> animFrames = null, animButterFlyFrames = null;
	static CCAnimate actionRain = null ,actionWaterfall= null, actionLightning = null;
	static CCSprite rainSprite = null;
	static CCAnimation animationRain = null;
	static CCAnimation animationWaterfall = null;
	static CCLayer layerBGWaterFall = null;
	static CCLayer layerRain = null;
	static CCLayer layerBirdFly = null;
	static CCLayer layerButterFly = null;
	static CCLayer layerLightning = null, layerRainbow = null, layerMist = null;
	static CCLayer layerFlower = null, layerSnow = null;
	static CCLayer layerNightMode = null;
	static CCLayer layerCollorNightMode = null;
	static CCLayer layerEveningMode = null;
	private CCLayer animation_array[] = {layerBirdFly, layerButterFly, layerButterFly, layerRain, layerSnow, layerRainbow, layerFlower, layerLightning};
	public static int PREVIOUSTHEME = 0;	
	static CCAnimate action = null;
	static CCSequence action_sequence = null;
	public static CCScene scene = null;
	private int minute_int = 0, slideshow_min = 0, total_slideshow_time = 0, slideshow_sec = 0, amb_progress = 0, bin_progress = 0, instr_progress = 0, water_progress = 0, grid_value = 0;
	private String pad_int = " ";
	private CCGLSurfaceView mGlSurfaceView = null;
	private PowerManager pManager = null;
	private PowerManager.WakeLock wl = null;
	private Display display = null;
	private BirdSound bSound = null;
	private int /*bucket_x = 0, bucket_y = 0,*/ gal_position = 0;	
	private boolean isAppClosed = false;
	public static int[] Red = null;
    public static int[] Green = null;
    public static int[] Blue = null;
    public static String resPath = "";
	public static String sResponse = null;
	
	private DragController mDragController;   // Object that sends out drag-drop events while a view is being moved.
	private DragLayer mDragLayer;             // The ViewGroup that supports drag-drop.

	public static final boolean Debugging = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
//        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- In Feelit onCreate method ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(FeelIt.this, 1, false);
        mGlSurfaceView = new CCGLSurfaceView(this);        
       
        setContentView(mGlSurfaceView); 
        
        bSound = new BirdSound(FeelIt.this);
        
        water_soundclass = new Water(FeelIt.this);
        ambience_soundclass = new Ambience(FeelIt.this);
        instrumental_soundclass = new Instrumental(FeelIt.this);                
        
        pManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wl = pManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "FeelIt");
        wl.acquire();
        
        display = getWindowManager().getDefaultDisplay(); 
        WIDTH = display.getWidth();
        HEIGHT = display.getHeight();
        
//    	System.out.println(" Screen WIDTH --->> "+ WIDTH +" Screen HEIGHT --->>> "+HEIGHT);
    	if(WIDTH >= 480 || HEIGHT >= 800)
        {
    		resPath = "480X800";
         	SCREEN_RATIO_X = WIDTH / 320;
         	SCREEN_RATIO_Y = HEIGHT / 480;
//    		System.out.println(" 11111111111 SCREEN_RATIO_X -->>> "+ SCREEN_RATIO_X+" SCREEN_RATIO_Y --->>> "+SCREEN_RATIO_Y);
    		THEME_SCALE_X = SCREEN_RATIO_X;
    		THEME_SCALE_Y = SCREEN_RATIO_Y;    		
        }
        else if(WIDTH >= 320 || HEIGHT >= 480)
        {
        	resPath = "320X480";
         	SCREEN_RATIO_X = WIDTH / 320;
         	SCREEN_RATIO_Y = HEIGHT / 480;
//    		System.out.println(" 22222222222 SCREEN_RATION_X -->>> "+ SCREEN_RATIO_X+"SCREEN_RATION_Y --->>> "+SCREEN_RATIO_Y);
    		THEME_SCALE_X = SCREEN_RATIO_X;
    		THEME_SCALE_Y = SCREEN_RATIO_Y;
        } 		
        else if(WIDTH >= 240 || HEIGHT >= 320)
        {
        	resPath = "240X320";
        	SCREEN_RATIO_X = WIDTH / 320;
        	SCREEN_RATIO_Y = HEIGHT / 480;
//	   		System.out.println(" 22222222222 SCREEN_RATION_X -->>> "+ SCREEN_RATIO_X+"SCREEN_RATION_Y --->>> "+SCREEN_RATIO_Y);
	   		THEME_SCALE_X = SCREEN_RATIO_X;
	   		THEME_SCALE_Y = SCREEN_RATIO_Y;
        }
        LayoutInflater inflater = getLayoutInflater();
        getWindow().addContentView(inflater.inflate(R.layout.feelit, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//        getWindow().addContentView(inflater.inflate(R.layout.analogclock_layout, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        isfirstinoncreate = true;
        
        init(); 
        
        CCDirector.sharedDirector().attachInView(mGlSurfaceView);
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
    	CCDirector.sharedDirector().setDisplayFPS(false);   	
    	CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
//    	System.out.println(" CCDirector.sharedDirector().getRunningScene() -> "+CCDirector.sharedDirector().getRunningScene());            	
    	if(CCDirector.sharedDirector().getRunningScene() == null)
    		CCDirector.sharedDirector().runWithScene(GameScene.scene());
    	else if(CCDirector.sharedDirector().getRunningScene().isRunning())
    		CCDirector.sharedDirector().replaceScene(GameScene.scene());
    	
    	mDragController = new DragController(this);
    	setupViews();
    	
    }
    
    private void setupViews() 
    {
        DragController dragController = mDragController;

        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController(dragController);
        dragController.addDropTarget (mDragLayer);
    }
    
    @Override
    protected void onStart() 
    {
    	// TODO Auto-generated method stub
    	super.onStart(); 
//    	System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- In Feelit onStart method ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
		
		/*CCDirector.sharedDirector().attachInView(mGlSurfaceView);
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
    	CCDirector.sharedDirector().setDisplayFPS(false);   	
    	CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
    	System.out.println(" CCDirector.sharedDirector().getRunningScene() -> "+CCDirector.sharedDirector().getRunningScene());            	
    	if(CCDirector.sharedDirector().getRunningScene() == null)
    		CCDirector.sharedDirector().runWithScene(GameScene.scene());
    	else if(CCDirector.sharedDirector().getRunningScene().isRunning())
    		CCDirector.sharedDirector().replaceScene(GameScene.scene());*/
    	
    }    
    
    @Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();	
		isAppClosed = sPreferences.getBoolean("isAppClosed", false);
		if(mGlSurfaceView != null)
		{
			mGlSurfaceView.onResume();
			CCDirector.sharedDirector().resume();
		}
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- In Feelit onResume method ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
		/*new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
*/
//		GameSceneHanmdler.sendEmptyMessage(0);
			/*}
		}).start();*/
		
		delay_timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(delay_hanHandler != null)
					delay_hanHandler.sendEmptyMessage(0);
			}
		}, 1000);
		
		reset_handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) 
			{
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				isReset = true;
				mute.setBackgroundResource(R.drawable.mute_bt);
				isMute = true;
				pause_sound();
				reset_animation();
				reset_ambience();
				reset_instrumental();
				isReset = false;
			}
		};
	}    
    
    @Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- inside onSaveInstanceState in FeelIt ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
	}
    
    @Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- inside onRestoreInstanceState in FeelIt ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
	}
    
    Handler delay_hanHandler = new Handler()
    {
    	public void handleMessage(Message msg) 
    	{
//    		System.out.println(" --- inside delay_hanHandler handleMessage --- ");
    		resumed();
    	};
    };
    
    /*Handler GameSceneHanmdler = new Handler()
    {
    	public void handleMessage(Message msg) 
    	{
    		if(mGlSurfaceView != null)
    		{
            	CCDirector.sharedDirector().attachInView(mGlSurfaceView);
            	CCDirector.sharedDirector().setDisplayFPS(false);   	
            	CCDirector.sharedDirector().setAnimationInterval(1.0f/60);
            	System.out.println(" CCDirector.sharedDirector().getRunningScene() -> "+CCDirector.sharedDirector().getRunningScene());            	
            	if(CCDirector.sharedDirector().getRunningScene() == null)
            		CCDirector.sharedDirector().runWithScene(GameScene.scene());
            	else if(CCDirector.sharedDirector().getRunningScene().isRunning())
            		CCDirector.sharedDirector().replaceScene(GameScene.scene());		        		
    		}
    	};
    };*/
    
	public int init() 
	{		
		all_Images = new AllImages(FeelIt.this);
        
		sPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	editor = sPreferences.edit();
    	
    	slideshow_min = sPreferences.getInt("min_slideshow", 0);
    	slideshow_min = slideshow_min * 60 * 1000;
    	slideshow_sec = sPreferences.getInt("sec_slideshow", 10);
    	slideshow_sec = slideshow_sec * 1000;
    	total_slideshow_time = slideshow_min + slideshow_sec; 	
    	
    	new Thread(new Runnable() 
    	{			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				for(int i=0; i<animation_boolean.length; i++)
				{
					animation_boolean[i] = sPreferences.getBoolean("anim_bool"+i, false);
					all_Images.isAnimationSelected.set(i, animation_boolean[i]);
					all_Images.animationActual_boolean.set(i, !animation_boolean[i]);
//					System.out.println(" animation values after restoring are --->>> "+animation_boolean[i]);
				}		
				for(int i=0; i<ambience_boolean.length; i++)
				{
					ambience_boolean[i] = sPreferences.getBoolean("amb_bool"+i, false);
					all_Images.isAmbienceSelected.set(i, sPreferences.getBoolean("amb_bool"+i, false));					
//					System.out.println(" ambiance values after restoring are --->>> "+ambience_boolean[i]);
				}
			}
		}).start();    	
		
		instrumental_position = sPreferences.getInt("instr_pos", 0);
		instrumentalBoolean[instrumental_position] = sPreferences.getString("instr_string", "true");
//		System.out.println(" instrumental_position value after restoring is --->>> "+instrumental_position+" booleaan value "+instrumentalBoolean[instrumental_position]);
		
    	water_shared = sPreferences.getBoolean("water", true);
    	ambiance_shared = sPreferences.getBoolean("ambience", true);
    	instrumental_shared = sPreferences.getBoolean("instrumental_sound", true);        
    	
    	controls = (LinearLayout)findViewById(R.id.controls);        
        controls_volume = (LinearLayout)findViewById(R.id.controls_volume);
        digital_clock = (LinearLayout)findViewById(R.id.digital_clock);
        horizontal_theme_layout = (LinearLayout)findViewById(R.id.horizontal_theme_layout);
        digital_clock.setVisibility(View.INVISIBLE);  
        
        horizontal_theme_layout.setOnTouchListener(FeelIt.this);
    	
        minute = (TextView)findViewById(R.id.min);
        minute_int = sPreferences.getInt("min", 1);
        if(minute_int == 0)
        	pad_int = "00";
        minute.setText(pad(minute_int));
        second = (TextView)findViewById(R.id.sec);
        clock_image = (ImageView)findViewById(R.id.clock_image);

        startTime = sPreferences.getInt("sec", 0);         
        temp_gettimertime = sPreferences.getBoolean("set", false); 
        
        if(temp_gettimertime)
        {        	
        	countDown = new CountDown(startTime * 1000, interval);
        	digital_clock.setVisibility(View.VISIBLE);
        	countDown.start();
        }
        
        mCursor = Saved.getSavedCursor(getContentResolver());
//        System.out.println(" --- mCursor.getCount() --- "+mCursor.getCount()+" mCursor.getColumnCount() -> "+mCursor.getColumnCount()+" Save.Columns.FILENAME -> "+Save.Columns.FILENAME);
        save_list = (ListView)findViewById(android.R.id.list);
        save_list.setOnItemLongClickListener(FeelIt.this);        
        save_list.setOnCreateContextMenuListener(FeelIt.this);
        
        save_list.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> adpt, View view, int position, long id) 
			{
				// TODO Auto-generated method stub
				isSavedItemSelected = true;
//				System.out.println(" --- position clciked in save_list.setOnItemClickListener --- "+position);				
				retrieve_saved(view, id);	
				play_default(position);
				//isSavedItemSelected = false;
			}			
		});       
        
        timer = new Timer(); 
        animationTimer = new Timer();
        delay_timer = new Timer();
        
        horizontal_themes = (HorizontalScrollView)findViewById(R.id.horizontal_theme);
                    
        parentGallery = ((CustomGallery)findViewById(R.id.HorizontalGal1));       
        child_gallery = (FrameLayout)findViewById(R.id.child_gallery);
        
        child_gallery.setOnTouchListener(FeelIt.this);
       
        Initialise();       
                  	
    	analogClock_1 = (AnalogClock)findViewById(R.id.analogclock_1);analogClock_1.setOnTouchListener(this);
    	analogClock_2 = (AnalogClock)findViewById(R.id.analogclock_2);analogClock_2.setOnTouchListener(this);
    	analogClock_3 = (AnalogClock)findViewById(R.id.analogclock_3);analogClock_3.setOnTouchListener(this);
    	analogClock_4 = (AnalogClock)findViewById(R.id.analogclock_4);analogClock_4.setOnTouchListener(this);
    	analogClock_5 = (AnalogClock)findViewById(R.id.analogclock_5);analogClock_5.setOnTouchListener(this);
    	analogClock_6 = (AnalogClock)findViewById(R.id.analogclock_6);analogClock_6.setOnTouchListener(this);
    	
    	parent_gallery = (LinearLayout)findViewById(R.id.parent_gallery);
    	
    	clockTheme_array = addClockThemes();    	  	    	
    	    	    	
    	disappear = (RelativeLayout)findViewById(R.id.disappear); 
    	main_layout = (RelativeLayout)findViewById(R.id.main_layout_feelit);
    	show_saved = (RelativeLayout)findViewById(R.id.show_saved);
    	allimage_layout = (RelativeLayout)findViewById(R.id.allimage_layout);
    	ambience_slider = (SeekBar)findViewById(R.id.ambience_slider_feelit);
    	instrumental_slider = (SeekBar)findViewById(R.id.instrumental_slider_feelit);
    	water_slider = (SeekBar)findViewById(R.id.water_slider_feelit);    	
    	   	
    	fadeIn = AnimationUtils.loadAnimation(FeelIt.this, R.anim.fadein);
    	fadeOut = AnimationUtils.loadAnimation(FeelIt.this, R.anim.fadeout);
    	
    	parent_gallery.startAnimation(fadeIn); 
    	
    	slideshow = (Button)findViewById(R.id.slideshow); slideshow.setOnClickListener(FeelIt.this);
    	reset = (Button)findViewById(R.id.reset); reset.setOnClickListener(FeelIt.this);
    	open = (Button)findViewById(R.id.open); open.setOnClickListener(FeelIt.this);
    	save = (Button)findViewById(R.id.save); save.setOnClickListener(FeelIt.this);
    	mute = (Button)findViewById(R.id.mute); mute.setOnClickListener(FeelIt.this);
    	volume = (Button)findViewById(R.id.volume); volume.setOnClickListener(FeelIt.this);  
    	done = (ImageButton)findViewById(R.id.done_save); done.setOnClickListener(FeelIt.this);
    	edit = (ImageButton)findViewById(R.id.edit_save); edit.setOnClickListener(FeelIt.this);        
        
        theme_buttons = addThemeButtons();
        animation_buttons = addAnimationButtons();
        ambience_buttons = addAmbienceButtons();       
        
        instrumental_buttons = addInstrumentalButtons();          
        
        amb_progress = sPreferences.getInt("ambience_progress", 25);
    	ambience_slider.setProgress(amb_progress);
    	ambience_slider.setOnSeekBarChangeListener(FeelIt.this);     	    	    	
    	
    	instr_progress = sPreferences.getInt("instrumental_progress", 25);
    	instrumental_slider.setProgress(instr_progress);
    	instrumental_slider.setOnSeekBarChangeListener(FeelIt.this);        
    	
    	water_progress = sPreferences.getInt("water_progress", 25);
    	water_slider.setProgress(water_progress);
    	water_slider.setOnSeekBarChangeListener(FeelIt.this);      
         
        timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//disAppear_forever();
				disAppear();
			}
		}, 8000);        
    	 
         parentGallery.setAdapter(new GalAdapter(FeelIt.this));    	 
         parentGallery.setOnItemSelectedListener(new OnItemSelectedListener() 
    	 {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) 
			{
				// TODO Auto-generated method stub
				view.setBackgroundResource(all_Images.tabtheme_hover[position]);
				parent_pos = position; 
				showChild(arg0, view, parent_pos, id);
				show_hide(parent_pos);
 				 				 				 				 								
 				isMoved = true;
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});  
         
        show_clocktheme();
         
		return 1;         
	}
	
	private int retrieve_saved(View view, long id) 
	{
		Save save = Saved.getSave(getContentResolver(), (int)id);
		
		show_saved.setVisibility(View.GONE);
		main_layout.setVisibility(View.VISIBLE);
		
		/*for(int i=0; i<save.animation_boolean.length; i++)
		{
//			System.out.println("Animation booleans saved are --->>> "+save.animation_boolean[i]);
		}*/
		
		for(int i=0; i<all_Images.isAnimationSelected.size(); i++)
		{
			all_Images.isAnimationSelected.set(i, save.animation_boolean[i]);
			all_Images.animationActual_boolean.set(i, animation_boolean[i]);
			animation_boolean[i] = save.animation_boolean[i];
		}
		reset_animation();
		play_animations();
		for(int i=0; i<7; i++)
		{
			if(animation_array[i] != null)
				ambience_boolean[i] = true;
		}
		for(int i=0; i<all_Images.isAmbienceSelected.size(); i++)
		{
			all_Images.isAmbienceSelected.set(i, save.ambiance_boolean[i]);
			all_Images.ambienceActual_boolean.set(i, ambience_boolean[i]);
//			System.out.println(" ambienceActual_boolean value is -------------->>>>>>>>>>>>> "+all_Images.ambienceActual_boolean.get(i));
			ambience_boolean[i] = save.ambiance_boolean[i];
//			System.out.println(" ambience_boolean value is -------------->>>>>>>>>>>>> "+ambience_boolean[i]);
		}		
		play_ambience_afterpauseORsave();		
		
		if(save.instrumental_position != -1)
		{
			stop_instrumentall(instrumental_position);
//			System.out.println("Retrieved instrumental_position is ------------------+++++++++++++++++++++ "+save.instrumental_position);
			play_instrumental(save.instrumental_position, view);			
		}
		isSavedItemSelected = false;
		return 1;
	}
	
	private void play_default(int id) 
	{
//		System.out.println(" --- inside play_default --- "+id);
		if(id == 1)
		{
//			System.out.println(" --- inside 1st if --- "+id);
			if(bSound != null)
			{
//				System.out.println(" --- inside 2nd if --- "+bSound);
				bSound.playSound((float)ambience_slider.getProgress()/100, (float)ambience_slider.getProgress()/100);
				bird_ambience.setBackgroundResource(R.drawable.bird_icon_hvr);
				ambience_boolean[0] = !ambience_boolean[0];
			}
		}
		else if(id == 3)
		{
			play_ambience(8);
			thunder_ambience.setBackgroundResource(R.drawable.thunder_icon_hvr);
			bird_ambience.setBackgroundResource(R.drawable.bird_icon);
			ambience_boolean[8] = !ambience_boolean[8];
		}
	}

	private ArrayList<ImageButton> addAnimationButtons() 
	{
		ArrayList<ImageButton> arrbut = new ArrayList<ImageButton>();
    	arrbut.add(bird_animation);
    	arrbut.add(butterfly_animation);
    	arrbut.add(rain_animation);
    	arrbut.add(snow_animation);
    	arrbut.add(rainbow_animation);
    	arrbut.add(snow_animation1);
    	arrbut.add(thunder_animation);
    	
		return arrbut;
	}	

	private ArrayList<AnalogClock> addClockThemes() 
	{
		ArrayList<AnalogClock> arrbut = new ArrayList<AnalogClock>();
    	arrbut.add(analogClock_1);
    	arrbut.add(analogClock_2);
    	arrbut.add(analogClock_3);
    	arrbut.add(analogClock_4);
    	arrbut.add(analogClock_5);
    	arrbut.add(analogClock_6);    	
    	
		return arrbut;
	}

	private void show_clocktheme() 
	{		
		grid_value = sPreferences.getInt("grid", 0);
		switch (grid_value) 
		{
			case 0:
				 clockTheme_invisible(0);
				 analogClock_1.setVisibility(View.VISIBLE);
				 break;
				
			case 1:
				 clockTheme_invisible(1);
				 analogClock_2.setVisibility(View.VISIBLE);
				 break;	
				
			case 2:
				 clockTheme_invisible(2);
				 analogClock_3.setVisibility(View.VISIBLE);
				 break;	
				
			case 3:
				 clockTheme_invisible(3);
				 analogClock_4.setVisibility(View.VISIBLE);
				 break;
				
			case 4:
				 clockTheme_invisible(4);
				 analogClock_5.setVisibility(View.VISIBLE);
				 break;
				
			case 5:
				 clockTheme_invisible(5);
				 analogClock_6.setVisibility(View.VISIBLE);
				 break;			
		}
	}
	
	/* <<<<<<<<<<<<<<<<<<------------------------ To make previously selected Clock theme invisible before selecting new one ------------------------>>>>>>>>>>>>>>>>>>>>>>>> */

	private void clockTheme_invisible(int pos) 
	{
		for(int i=0; i<clockTheme_array.size(); i++)
		{
			if(i != pos)
				clockTheme_array.get(i).setVisibility(View.GONE);
		}
	}

	private ArrayList<ImageButton> addThemeButtons() 
	{
		ArrayList<ImageButton> arrbut = new ArrayList<ImageButton>();
    	arrbut.add(theme1);
    	arrbut.add(theme2);
    	arrbut.add(theme3);
    	arrbut.add(theme4);
    	
		return arrbut;
	}

	private ArrayList<ImageButton> addInstrumentalButtons() 
    {
    	ArrayList<ImageButton> arrbut = new ArrayList<ImageButton>();
    	arrbut.add(flute_instrumental);
    	arrbut.add(xylophone_instrumental);
    	arrbut.add(saxophone);
    	arrbut.add(sitar_instrumental);
    	arrbut.add(violin);
    	arrbut.add(piano);
    	arrbut.add(guitar_instrumental);
    	
		return arrbut;
	}	

	private ArrayList<ImageButton> addAmbienceButtons() 
    {
    	ArrayList<ImageButton> arrbut = new ArrayList<ImageButton>();
    	arrbut.add(morning_ambience);
    	arrbut.add(bird_ambience);
    	arrbut.add(evening);
    	arrbut.add(cricket);
    	arrbut.add(rain_ambience);
    	arrbut.add(jungle_ambience);
    	arrbut.add(wind_ambience);
    	arrbut.add(river_ambience);
    	arrbut.add(thunder_ambience);
    	arrbut.add(cicidas);
		return arrbut;
	}

	private int Initialise() 
	{
		animatin_layout = (LinearLayout)findViewById(R.id.animation_layout);animatin_layout.setOnTouchListener(FeelIt.this);
		ambience_layout = (LinearLayout)findViewById(R.id.ambience_layout);ambience_layout.setOnTouchListener(FeelIt.this);		
		instrumental_layout = (LinearLayout)findViewById(R.id.instrumental_layout);instrumental_layout.setOnTouchListener(FeelIt.this);
		modes_layout = (LinearLayout)findViewById(R.id.modes_layout);modes_layout.setOnTouchListener(FeelIt.this);				
		
		theme1 = (ImageButton)findViewById(R.id.theme1);theme1.setOnClickListener(FeelIt.this);theme1.setOnTouchListener(FeelIt.this);
        theme2 = (ImageButton)findViewById(R.id.theme2);theme2.setOnClickListener(FeelIt.this);theme2.setOnTouchListener(FeelIt.this);
        theme3 = (ImageButton)findViewById(R.id.theme3);theme3.setOnClickListener(FeelIt.this);theme3.setOnTouchListener(FeelIt.this);
        theme4 = (ImageButton)findViewById(R.id.theme4);theme4.setOnClickListener(FeelIt.this);theme4.setOnTouchListener(FeelIt.this); 
        
        bird_animation = (ImageButton)findViewById(R.id.bird);bird_animation.setOnClickListener(FeelIt.this);bird_animation.setOnTouchListener(FeelIt.this);
        butterfly_animation = (ImageButton)findViewById(R.id.butterfly);butterfly_animation.setOnClickListener(FeelIt.this);butterfly_animation.setOnTouchListener(FeelIt.this);
        rain_animation = (ImageButton)findViewById(R.id.rain);rain_animation.setOnClickListener(FeelIt.this);rain_animation.setOnTouchListener(FeelIt.this);
        snow_animation = (ImageButton)findViewById(R.id.snow);snow_animation.setOnClickListener(FeelIt.this);snow_animation.setOnTouchListener(FeelIt.this);
        rainbow_animation = (ImageButton)findViewById(R.id.rainbow);rainbow_animation.setOnClickListener(FeelIt.this);rainbow_animation.setOnTouchListener(FeelIt.this);
        snow_animation1 = (ImageButton)findViewById(R.id.flower); snow_animation1.setOnClickListener(FeelIt.this);snow_animation1.setOnTouchListener(FeelIt.this);
        thunder_animation = (ImageButton)findViewById(R.id.thunder);thunder_animation.setOnClickListener(FeelIt.this);thunder_animation.setOnTouchListener(FeelIt.this);
        
        morning_ambience = (ImageButton)findViewById(R.id.morning);morning_ambience.setOnClickListener(FeelIt.this);morning_ambience.setOnTouchListener(FeelIt.this);
        bird_ambience = (ImageButton)findViewById(R.id.bird_ambience);bird_ambience.setOnClickListener(FeelIt.this);bird_ambience.setOnTouchListener(FeelIt.this);
        evening = (ImageButton)findViewById(R.id.evening);evening.setOnClickListener(FeelIt.this);evening.setOnTouchListener(FeelIt.this);
        cricket = (ImageButton)findViewById(R.id.cricket);cricket.setOnClickListener(FeelIt.this);cricket.setOnTouchListener(FeelIt.this);
        rain_ambience = (ImageButton)findViewById(R.id.rain_ambience);rain_ambience.setOnClickListener(FeelIt.this);rain_ambience.setOnTouchListener(FeelIt.this);
        jungle_ambience = (ImageButton)findViewById(R.id.frog);jungle_ambience.setOnClickListener(FeelIt.this);jungle_ambience.setOnTouchListener(FeelIt.this);
        wind_ambience = (ImageButton)findViewById(R.id.wind);wind_ambience.setOnClickListener(FeelIt.this);wind_ambience.setOnTouchListener(FeelIt.this);
        river_ambience = (ImageButton)findViewById(R.id.river);river_ambience.setOnClickListener(FeelIt.this);river_ambience.setOnTouchListener(FeelIt.this);
        thunder_ambience = (ImageButton)findViewById(R.id.thunder_ambience);thunder_ambience.setOnClickListener(FeelIt.this);thunder_ambience.setOnTouchListener(FeelIt.this);
        cicidas = (ImageButton)findViewById(R.id.cicidas);cicidas.setOnClickListener(FeelIt.this);cicidas.setOnTouchListener(FeelIt.this);       
        
        flute_instrumental = (ImageButton)findViewById(R.id.flute);flute_instrumental.setOnClickListener(FeelIt.this);flute_instrumental.setOnTouchListener(FeelIt.this); 
        xylophone_instrumental = (ImageButton)findViewById(R.id.xylophone);xylophone_instrumental.setOnClickListener(FeelIt.this);xylophone_instrumental.setOnTouchListener(FeelIt.this); 
        saxophone = (ImageButton)findViewById(R.id.saxophone);saxophone.setOnClickListener(FeelIt.this);saxophone.setOnTouchListener(FeelIt.this); 
        sitar_instrumental = (ImageButton)findViewById(R.id.sitar);sitar_instrumental.setOnClickListener(FeelIt.this);sitar_instrumental.setOnTouchListener(FeelIt.this); 
        violin = (ImageButton)findViewById(R.id.violin);violin.setOnClickListener(FeelIt.this);violin.setOnTouchListener(FeelIt.this); 
        piano = (ImageButton)findViewById(R.id.piano);piano.setOnClickListener(FeelIt.this);piano.setOnTouchListener(FeelIt.this); 
        guitar_instrumental = (ImageButton)findViewById(R.id.guitar);guitar_instrumental.setOnClickListener(FeelIt.this);guitar_instrumental.setOnTouchListener(FeelIt.this);    
        
        
        day_modes = (ImageButton)findViewById(R.id.day_modes);day_modes.setOnClickListener(FeelIt.this);day_modes.setBackgroundResource(R.drawable.day_icon_hvr);day_modes.setOnTouchListener(FeelIt.this);
        night_modes = (ImageButton)findViewById(R.id.night_modes);night_modes.setOnClickListener(FeelIt.this);night_modes.setOnTouchListener(FeelIt.this);
        night_mode_color = (ImageButton)findViewById(R.id.night_modes_color);night_mode_color.setOnClickListener(FeelIt.this);night_mode_color.setOnTouchListener(FeelIt.this);
        
        /*default_bird = (ImageButton)findViewById(R.id.default_bird);default_bird.setOnTouchListener(FeelIt.this);
        default_snow = (ImageButton)findViewById(R.id.default_snow);default_snow.setOnTouchListener(FeelIt.this);
        default_lightning = (ImageButton)findViewById(R.id.default_lightning);default_lightning.setOnTouchListener(FeelIt.this);*/
        
		return 1;        
	}
	
	public void onLowMemory() 
    {
    	//dalvik.system.VMRuntime.getRuntime().setMinimumHeapSize(32 * 1024 * 1024);
    };
    
    /* <<<<<<<<<<<<<<<<<<<<--------------------- To set child views for respective view in Parent gallery ------------------------>>>>>>>>>>>>>>>>>>>>>>> */

	private int showChild(AdapterView<?> arg0, View view, int position, long id) 
	{
		switch(parent_pos) 
		{
			case THEME:
				 break;
	
			case ANIMATION:	
				 animatin_layout.setVisibility(View.VISIBLE);
				 animatin_layout.bringToFront();
				 ambience_layout.setVisibility(View.GONE);				 
				 instrumental_layout.setVisibility(View.GONE);
				 modes_layout.setVisibility(View.GONE);
				 break;
				
			case AMBIENCE:		
				 animatin_layout.setVisibility(View.GONE);
				 ambience_layout.setVisibility(View.VISIBLE);
				 ambience_layout.bringToFront();
				 instrumental_layout.setVisibility(View.GONE);
				 modes_layout.setVisibility(View.GONE);
				 break;			
				
			case INSTRUMENTAL_MUSIC:	
				 animatin_layout.setVisibility(View.GONE);
				 ambience_layout.setVisibility(View.GONE);			
				 instrumental_layout.setVisibility(View.VISIBLE);
				 instrumental_layout.bringToFront();
				 modes_layout.setVisibility(View.GONE);
				 break;
				
			case MODES:
				 animatin_layout.setVisibility(View.GONE);
				 ambience_layout.setVisibility(View.GONE);			
				 instrumental_layout.setVisibility(View.GONE);
				 modes_layout.setVisibility(View.VISIBLE);
				 break;						
		}
		return 1;		
	}	

	/*<<<<<<<<<<<<<<<-------------------- To run respective water theme Animation --------------------->>>>>>>>>>>>>>>>>> */
	
	public int themeSelection(int pos, View v)
	{
		isFall = true;
		fall_pos = pos;
		editor.putInt("fall_id", pos);
		editor.commit();
		if(isSlideshowOn == false)
		{
			v.setBackgroundResource(all_Images.themeThumbs_hvr[pos]);
			v.setEnabled(false);
		}
		if((water_soundclass != null && water_soundclass.fall_files[THEMECOUNT - 1] != null) && water_soundclass.fall_files[THEMECOUNT - 1].isPlaying())
		{
			water_soundclass.release_fall_sound(THEMECOUNT - 1);
		  	theme_buttons.get(THEMECOUNT - 1).setBackgroundResource(R.drawable.thumbs_3);
		}
		return 1; 		
	}
    
    /* <<<<<<<<<<<<<<<---------------- To enable and disable other views based on Parent gallery view ---------------->>>>>>>>>>>>>>>>>> */
	
	private void show_hide(int count) 
	{
		grid_value = sPreferences.getInt("grid", 0);
		switch (count) 
		{
			case THEME:
				 if(isMoved && parent_pos < 1)
				 {
					 child_gallery.startAnimation(fadeOut);
					 child_gallery.setVisibility(View.INVISIBLE);
					 controls.startAnimation(fadeOut);
					 controls.setVisibility(View.INVISIBLE);				 
					 clockTheme_array.get(grid_value).startAnimation(fadeOut);
					 clockTheme_array.get(grid_value).setVisibility(View.INVISIBLE);
					 if(!isShow_controls_volume)
					 {
						 controls_volume.startAnimation(fadeOut);
						 controls_volume.setVisibility(View.INVISIBLE);
					 }
					 controls_volume.setVisibility(View.INVISIBLE);					 
					 isShow_controls_volume = true;
				 }	
				
				 horizontal_themes.bringToFront();
				 horizontal_themes.startAnimation(fadeIn);
				 horizontal_themes.setVisibility(View.VISIBLE);			 
				 child_gallery.setVisibility(View.INVISIBLE);
				 controls.setVisibility(View.INVISIBLE);	
				 clockTheme_array.get(grid_value).startAnimation(fadeOut);
				 clockTheme_array.get(grid_value).setVisibility(View.INVISIBLE);				 
				 break;
				 
			case ANIMATION:
				 if(!child_gallery.isShown())
				 {
					 child_gallery.startAnimation(fadeIn);
					 child_gallery.setVisibility(View.VISIBLE);
					 controls.startAnimation(fadeIn);
					 controls.setVisibility(View.VISIBLE);				 
					 clockTheme_array.get(grid_value).startAnimation(fadeIn);
					 clockTheme_array.get(grid_value).setVisibility(View.VISIBLE);									 
					 horizontal_themes.startAnimation(fadeOut);
					 horizontal_themes.setVisibility(View.INVISIBLE);
				 }
				 if(parent_pos < 1)
				 {
					 horizontal_themes.startAnimation(fadeOut);
					 horizontal_themes.setVisibility(View.INVISIBLE);
				 }			 
				 break;
				 
			case AMBIENCE:			 
				 child_gallery.setVisibility(View.VISIBLE);
				 break;
				 
			case INSTRUMENTAL_MUSIC:			 
				 child_gallery.setVisibility(View.VISIBLE);
				 break;
				 
			case MODES:			 
				 child_gallery.setVisibility(View.VISIBLE);
				 break;			 
					
		}
	}
	
	private int resumed() 
	{	
		isAppClosed = sPreferences.getBoolean("isAppClosed", true);	// keeps info about whether you came to this Activity 1st time or not
		
		gal_position = sPreferences.getInt("parent_pos", 0);
		parentGallery.setSelection(gal_position);
		
		/* **************************** Checks whether this Activity paused and restarted or destroyed and created ******************************** */		
		
		if(isAppClosed)
		{
//			System.out.println(" <<<<<<<<<<<<<<<<<<<<<< @@@@@@@@@@@@@@@@@@@@ STARTING DEFAULT @@@@@@@@@@@@@@@@@@@@ >>>>>>>>>>>>>>>>>>>>>>>>>>> ");
			THEMECOUNT = 1;		
			theme_buttons.get(fall_pos).setBackgroundResource(all_Images.themeThumbs_hvr[fall_pos]);
			play_fall_afterpause();
			play_animations();
			play_ambience_afterpauseORsave();
			play_instrumental_afterpauseORsave();
			
			Thread thr = new Thread(new Runnable() 
	        {			
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					if((water_soundclass.fall_files[fall_pos] != null && !water_soundclass.fall_files[fall_pos].isPlaying()))
					{
//						theme_buttons.get(fall_pos).setBackgroundResource(all_Images.themeThumbs_hvr[fall_pos]);
						if(water_shared)
						{
							water_soundclass.create_fall_sound(fall_pos);
							water_soundclass.fall_files[fall_pos].setVolume((float)water_slider.getProgress()/100, (float)water_slider.getProgress()/100);
							water_soundclass.fall_files[fall_pos].setLooping(true);
							water_soundclass.fall_files[fall_pos].start();
						}
					}
				}
			});
	        thr.start();	
		}
		else
		{
			fall_pos = THEMECOUNT - 1;
			isSavedItemSelected = true;
//			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<------------------------- RESTORING ----------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>");
			play_fall_afterpause();
			play_animations();
			play_ambience_afterpauseORsave();			
			play_instrumental_afterpauseORsave();
		}			
		
		handle_fall = new Handler()
		{			
        	@Override
        	public void handleMessage(Message msg) 
        	{
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);	
        		try
	        	{
					if(isFall)
					{
						water_soundclass.create_fall_sound(fall_pos);
						if((water_soundclass.fall_files != null && water_slider != null) && water_shared)						
						{
							if(!water_soundclass.fall_files[fall_pos].isPlaying() && isMute)
							{								
								water_soundclass.fall_files[fall_pos].setVolume((float)water_slider.getProgress()/100, (float)water_slider.getProgress()/100);
								water_soundclass.fall_files[fall_pos].setLooping(true);	
								water_soundclass.fall_files[fall_pos].start();								
							}
						}
					}
					else
					{
						isFall_firstTime = true;																
					}
	        	}
				catch (NullPointerException e) {
					// TODO: handle exception
				}
        	}
        };
        
        handle_disappear = new Handler()
        {
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        		disAppear();
        		if(isSlideshowOn)
        			runAnimations();        		
        	}
        };
        
        disappear_handler = new Handler()
        {
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);        		
        		if(isShow)
        		{
        			disappear.startAnimation(fadeOut);
        			disappear.setVisibility(View.GONE); 
        			if(parent_pos != 0)
        			{
	        			clockTheme_array.get(grid_value).startAnimation(fadeOut);
						clockTheme_array.get(grid_value).setVisibility(View.INVISIBLE);
        			}        			
        		}
        		else
        		{
        			if(!isShow_controls_volume)
            		{
        				controls_volume.setVisibility(View.INVISIBLE);
            			isShow_controls_volume = true;
            		}
        			disappear.startAnimation(fadeIn);
        			disappear.setVisibility(View.VISIBLE);
        			if(parent_pos != 0)
        			{
	        			clockTheme_array.get(grid_value).startAnimation(fadeIn);
						clockTheme_array.get(grid_value).setVisibility(View.VISIBLE);
        			}
        		}
        		isShow = !isShow;
        	}
        };
        
        disappear_forever_handler = new Handler()
        {
			@Override
        	public void handleMessage(Message msg) 
        	{
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        		isShow = !isShow; 
        		isDisappearedForever = true;
        		disappear.startAnimation(fadeOut);
    			disappear.setVisibility(View.GONE);
    			if(parent_pos != 0)
    			{
	    			clockTheme_array.get(grid_value).startAnimation(fadeOut);
					clockTheme_array.get(grid_value).setVisibility(View.INVISIBLE);
    			}
        	}
        };
        
        handle_animations = new Handler()
        {
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        		if(THEMECOUNT < 4)        		
        			THEMECOUNT++;        		
        		else        		
        			THEMECOUNT = 1;       			
        		
        		themeUnSelection_slideshow();
        		themeSelection(THEMECOUNT - 1, null);
        		CCDirector.sharedDirector().replaceScene(GameScene.scene());
        	}			
        };        
		return 1;
	}

	private int themeUnSelection_slideshow() 
	{
		if(water_soundclass.fall_files != null)
		{			
			if(water_soundclass.fall_files[fall_pos] != null && water_soundclass.fall_files[fall_pos].isPlaying())
				water_soundclass.release_fall_sound(fall_pos);			
		}		
		return 1;
	}
	
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub		
		super.onPause();
		if(mGlSurfaceView != null)
		{
			mGlSurfaceView.onPause();
			CCDirector.sharedDirector().pause();
		}
		isAmbience = isInstrumental  = isFall = true;
//		
	}	
	
	/* <<<<<<<<<<<<<<<<<<<<<<<---------------------- To release all Mediaplayer resources before quiting Activity -------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>> */
	
	private void release_Sound() 
    {	
		if(isAmbience)
		{
			ambience_soundclass.release_Ambience_sound();
		}		
		if(isInstrumental)
		{
			for(int i=0; i<instrumental_soundclass.instrumental_files.length; i++)
	    	{
	    		if((instrumental_soundclass.instrumental_files != null && instrumental_soundclass.instrumental_files[i] != null) && instrumental_soundclass.instrumental_files[i].isPlaying())
	    			instrumental_soundclass.release_instrumental_sound(i);	    		
	    	}
		}
		if(isFall)
		{
			if((water_soundclass.fall_files != null && water_soundclass.fall_files[fall_pos] != null) && water_soundclass.fall_files[fall_pos].isPlaying())
				water_soundclass.pause_water(fall_pos);
			
			isFallPaused = true;
		}
		
		if(water_soundclass.fall_files[THEMECOUNT - 1] != null && water_soundclass.fall_files[THEMECOUNT - 1].isPlaying())
			water_soundclass.pause_water(THEMECOUNT - 1);
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	// TODO Auto-generated method stub		
    	if(event.getAction() == MotionEvent.ACTION_DOWN)
    	{
//    		System.out.println( "@@@@@@@@@@@@@@ onTouchEvent ACTION_DOWN @@@@@@@@@@@@@@@@@@" );
    		isTouchedDown = false;
    		dis_Appear();
    	}
    	if(event.getAction() == MotionEvent.ACTION_UP && !isTouchedDown)
    	{
//    		System.out.println( " @@@@@@@@@@@@@@ onTouchEvent ACTION_UP @@@@@@@@@@@@@@@@@@ " );
    		new Thread(new Runnable() {					
				@Override
				public void run() {
					// TODO Auto-generated method stub
					disAppear();
					}
			}).start();	    		
    	}
    	if(event.getAction() == MotionEvent.ACTION_MOVE) 
    	{
//    		System.out.println( "@@@@@@@@@@@@@@ onTouchEvent ACTION_MOVE @@@@@@@@@@@@@@@@@@" );
    		isTouchedDown = true;
    		dis_Appear();    		
    	}	    		    	
    	return true;
    }
   
    /* <<<<<<<<<<<<<<<<<<<----------------------- To make views present in current screen invisible ---------------------->>>>>>>>>>>>>>>>>>>>>>>>>> */

	private int disAppear() 
	{
		if(disappear_handler != null)
			disappear_handler.sendEmptyMessage(0);
//		else
//			System.out.println("disappear_handler is ---------->>>>>>>>>> "+disappear_handler);
		
		return 1;		
	} 

	@Override
	public void onClick(View v) 
	{		
		// TODO Auto-generated method stub
		isInOnClick = true;
		dis_Appear();	
		onClickORonTouch_selection(v);		
	}

	private int onClickORonTouch_selection(View v) 
	{		
		switch (v.getId()) 
		{			 
			case R.id.slideshow:
				 if(v.getId() == R.id.slideshow)
				 {
					 if(isSlideshow)
					 {
						 v.setBackgroundResource(R.drawable.slideshow_bt_hvr);				 	 
					 	 new Thread(new Runnable() {						
							@Override
							public void run() {
								// TODO Auto-generated method stub
								handle_disappear.sendEmptyMessage(1);
							}
						}).start();	
					 	isSlideshowOn = true;
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.slideshow_bt);
						 if(handle_disappear != null)
							 handle_disappear.removeMessages(1);
						 if(animationTimer != null)
						 {
							 animationTimer.cancel();
							 animationTimer = null;
						 }
//						 System.out.println(" Fall position is ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; "+fall_pos);
						 for(int i=0; i<theme_buttons.size(); i++)
						 {
							theme_buttons.get(i).setBackgroundResource(all_Images.themeThumbs[i]);
							theme_buttons.get(i).setClickable(true);
						 }
						 theme_buttons.get(fall_pos).setBackgroundResource(all_Images.themeThumbs_hvr[fall_pos]);
						 theme_buttons.get(fall_pos).setClickable(false);
						 isSlideshowOn = false;
					 }
					 isSlideshow = !isSlideshow;
				 }
				 break;
				 
			case R.id.reset:
				 reset_all();
				 break;
				 
			case R.id.open:
				 save_list.setAdapter(new SavedItemsAdapter(FeelIt.this, mCursor));
				 main_layout.setVisibility(View.GONE);
				 show_saved.setVisibility(View.VISIBLE);
				 clockTheme_array.get(grid_value).setVisibility(View.GONE);
				 break;
				 
			case R.id.save:			 
				 edit_text = new EditText(FeelIt.this);
				 edit_text.setHint("filename");
				 new AlertDialog.Builder(FeelIt.this).setTitle("Enter name of the file:").
				 setView(edit_text).
				 setPositiveButton("Save", new DialogInterface.OnClickListener() 
				 {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
						save_list.setAdapter(new SavedItemsAdapter(FeelIt.this, mCursor));
						String filename = edit_text.getText().toString();
//						System.out.println("Text is------------->>>>>>>>>>>>>" + filename);
						if(filename.length() > 0)
							save_playing(filename);
						else						
							Toast.makeText(FeelIt.this, "Enter filename!", Toast.LENGTH_SHORT).show();						
						
				 }								
				 }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				 {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				 }).create().show();
				 break;
				 
			case R.id.mute:
				 mute_unmute(v);			 
				 break;
				 
			case R.id.volume:
				 if(v.getId() == R.id.volume)
				 {
					 if(isShow_controls_volume)
					 {				 
						 controls_volume.startAnimation(fadeIn);
						 controls_volume.setVisibility(View.VISIBLE);					 				 
					 }
					 else
					 {				 
						 controls_volume.startAnimation(fadeOut);
						 controls_volume.setVisibility(View.INVISIBLE);					 
					 }
					 isShow_controls_volume = !isShow_controls_volume;
				 }
				 break;
				 
		/* ************************************** Themes section started ************************************ */		 
	
			case R.id.theme1:
				 if(disappear_forever_handler != null)
					 disappear_forever_handler.sendEmptyMessage(0);
				 disable_otherClicks();
				 if(isSlideshow)
				 {
					 themeUnSelection();
					 themeSelection(0, v);
					 THEMECOUNT = 1;
					 ThemeImageCount = 9;	
//					 CCDirector.sharedDirector().purgeCachedData();
//					 mGlSurfaceView.invalidate();
					 CCDirector.sharedDirector().pause();
					 new UploadTask().execute();
//					 CCDirector.sharedDirector().resume();
				 }
				 break;			
				 
			case R.id.theme2:
				 if(disappear_forever_handler != null)
					 disappear_forever_handler.sendEmptyMessage(0);
				 disable_otherClicks();
				 if(isSlideshow)
				 {
					 themeUnSelection();
				     themeSelection(1, v);			
					 THEMECOUNT = 2;
				     ThemeImageCount = 30;	
//				     CCDirector.sharedDirector().purgeCachedData();
//					 mGlSurfaceView.invalidate();
				     CCDirector.sharedDirector().pause();
					 new UploadTask().execute();
//					 CCDirector.sharedDirector().resume();
				 }
				 break;
				
			case R.id.theme3:
				//dis_Appear();
				 disappear_forever_handler.sendEmptyMessage(0);
				 disable_otherClicks();
				 if(isSlideshow)
				 {
					 themeUnSelection();
				     themeSelection(2, v);			   
					 THEMECOUNT = 3;
				     ThemeImageCount = 23;	
				     CCDirector.sharedDirector().pause();
					 new UploadTask().execute();
				 }
				 break;
				
			case R.id.theme4:
				//dis_Appear();
				 disappear_forever_handler.sendEmptyMessage(0);
				 disable_otherClicks();
				 if(isSlideshow)
				 {
					 themeUnSelection();
				     themeSelection(3, v);			
					 THEMECOUNT = 4;
				     ThemeImageCount = 29;	
				     CCDirector.sharedDirector().pause();
					 new UploadTask().execute();
				 }
				 break;
		 
		/* ************************************** ANIMATION section started ************************************ */
			
	       case R.id.bird:
	    	    animatin_layout.bringToFront();
	    	    int bird_id_animation = 0;
				if(v.getId() == R.id.bird)
				{
					 if(!animation_boolean[bird_id_animation] && layerBirdFly == null)
					 {
						 v.setBackgroundResource(R.drawable.bird_icon_hvr);
						 bird_ambience.setBackgroundResource(R.drawable.bird_icon_hvr);
						 all_Images.isAmbienceSelected.set(bird_id_animation, true);
						 layerBirdFly = new BirdFlyAnimClass();	
				    	 scene.addChild(layerBirdFly, 1);
				    	 // if bird sound is already started in Ambience then no need to start it again while starting bird Animation 
				    	 if(!ambience_boolean[1])
				    	 {
				    		 play_ambience(1);
				    		 ambience_boolean[1] = true;
				    	 }
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.bird_icon);
						 bird_ambience.setBackgroundResource(R.drawable.bird_icon);
						 all_Images.isAmbienceSelected.set(bird_id_animation, false);
						 if(layerBirdFly != null)
						 {
							 layerBirdFly.removeAllChildren(true);
							 scene.removeChild(layerBirdFly, true);
							 layerBirdFly = null;
						 }
						 if(ambience_boolean[1])
						 {
							 stop_ambience(1);
							 ambience_boolean[1] = false;
						 }
					 }
					 animation_boolean[bird_id_animation] = !animation_boolean[bird_id_animation];
				}			 
				break;
				 
	       case R.id.butterfly:
	    	    animatin_layout.bringToFront();
	    	    int butterfly_id = 1;
				if(v.getId() == R.id.butterfly)
				{
					 if(!animation_boolean[butterfly_id] && layerButterFly == null)
					 {
						 v.setBackgroundResource(R.drawable.butterfly_icon_hvr);
						 layerButterFly = new ButterFlyAnimClass();
				    	 scene.addChild(layerButterFly, 1);
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.butterfly_icon);
						 if(layerButterFly != null)
						 {
							 layerButterFly.removeAllChildren(true);
							 scene.removeChild(layerButterFly, true);
							 layerButterFly = null;
						 }
					 }
					 animation_boolean[butterfly_id] = !animation_boolean[butterfly_id];
				}			 
				break;
				 
	       case R.id.rain:
	    	    int rain_id_animation  = 2;
	    	    animatin_layout.bringToFront();
				if(v.getId() == R.id.rain)
				{
					 if(!animation_boolean[rain_id_animation])
					 {
						 if((!animation_boolean[3] && !animation_boolean[5]) && layerRain == null)
						 {
							 v.setBackgroundResource(R.drawable.rain_icon_hvr);
							 rain_ambience.setBackgroundResource(R.drawable.rain_icon_hvr);
					    	 isRainSelected = true;
					    	 all_Images.isAmbienceSelected.set(rain_id_animation, true);				    	 
							 layerRain = new RainAnimClass();
					    	 scene.addChild(layerRain,1);
					    	 if(!ambience_boolean[4])
					    	 {
					    		 play_ambience(4);
					    		 ambience_boolean[4] = true;
					    	 }
						 }
						 else
						 {
							 if(layerSnow != null && scene != null)
							 {
								 layerSnow.removeAllChildren(true);
								 scene.removeChild(layerSnow, true);
								 layerSnow = null;
								 animation_buttons.get(3).setBackgroundResource(all_Images.animations[3]);
								 animation_boolean[3] = false;
							 }
							 if(layerFlower != null && scene != null)
							 {
								 layerFlower.removeAllChildren(true);
								 scene.removeChild(layerFlower, true);
								 layerFlower = null;
								 animation_buttons.get(5).setBackgroundResource(all_Images.animations[5]);
								 animation_boolean[5] = false;
							 }	
							 v.setBackgroundResource(R.drawable.rain_icon_hvr);
							 rain_ambience.setBackgroundResource(R.drawable.rain_icon_hvr);
					    	 isRainSelected = true;
					    	 all_Images.isAmbienceSelected.set(rain_id_animation, true);
					    	 if(layerRain == null)
					    	 {
								 layerRain = new RainAnimClass();
						    	 scene.addChild(layerRain,1);
					    	 }							 
					    	 if(!ambience_boolean[4])
					    	 {
					    		 play_ambience(4);
					    		 ambience_boolean[4] = true;
					    	 }
						 }
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.rain_icon);
						 rain_ambience.setBackgroundResource(R.drawable.rain_icon);
				    	 isRainSelected = false;
				    	 all_Images.isAmbienceSelected.set(rain_id_animation, false);
				    	 if(layerRain != null)
				    	 {
					    	 layerRain.removeAllChildren(true);
					    	 scene.removeChild(layerRain, true);
					    	 layerRain = null;
				    	 }
						 if(ambience_boolean[4])
						 {
							 stop_ambience(4);
							 ambience_boolean[4] = false;
						 }
					 }
					 animation_boolean[rain_id_animation] = !animation_boolean[rain_id_animation];
				}		
				break;
				 
	       case R.id.snow:
	    	    int snow_id = 3;
	    	    animatin_layout.bringToFront();
				if(v.getId() == R.id.snow)
				{
					 if(!animation_boolean[snow_id])
					 {
						 if((!animation_boolean[5] && !animation_boolean[2]) && layerSnow == null)
						 {
							 v.setBackgroundResource(R.drawable.snow_icon_hvr);
							 all_Images.isAmbienceSelected.set(snow_id, true);
							 layerSnow = new Snow();
							 scene.addChild(layerSnow);
						 }
						 else
						 {
							 if(layerFlower != null && scene != null)
							 {
								 layerFlower.removeAllChildren(true);
								 scene.removeChild(layerFlower, true);
								 layerFlower = null;
								 animation_buttons.get(5).setBackgroundResource(all_Images.animations[5]);
								 animation_boolean[5] = false;
							 }
							 if(layerRain != null && scene != null)
							 {
								 layerRain.removeAllChildren(true);
								 scene.removeChild(layerRain, true);
								 layerRain = null;
								 animation_buttons.get(2).setBackgroundResource(all_Images.animations[2]);
								 animation_boolean[2] = false;
								 ambience_boolean[4] = false;
								 rain_ambience.setBackgroundResource(R.drawable.rain_icon);
						    	 all_Images.isAmbienceSelected.set(2, false);
								 stop_ambience(4);
							 }
							 v.setBackgroundResource(R.drawable.snow_icon_hvr);
							 all_Images.isAmbienceSelected.set(snow_id, true);
							 if(layerSnow == null)
							 {
								 layerSnow = new Snow();
								 scene.addChild(layerSnow);
							 }
						 }
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.snow_icon);
						 all_Images.isAmbienceSelected.set(snow_id, false);
//						 System.out.println("Removing addchild layerFlower");
						 if(layerSnow != null)
						 {
							 layerSnow.removeAllChildren(true);
							 scene.removeChild(layerSnow, true);
							 layerSnow = null;
						 }
					 }
					 animation_boolean[snow_id] = !animation_boolean[snow_id];
			    }			 
				break;
				 
	       case R.id.rainbow:
	    	    int rainbow_id = 4;
	    	    animatin_layout.bringToFront();
				if(v.getId() == R.id.rainbow)
				{
					 if(!animation_boolean[rainbow_id] && layerRainbow == null)
					 {
						 v.setBackgroundResource(R.drawable.rainbow_icon_hvr);
						 all_Images.isAmbienceSelected.set(rainbow_id, true);						 
						 layerRainbow = new Rainbow();
						 scene.addChild(layerRainbow, 1);											 
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.rainbow_icon);
						 all_Images.isAmbienceSelected.set(rainbow_id, false);
						 if(layerRainbow != null)
						 {
							 layerRainbow.removeAllChildren(true);
							 scene.removeChild(layerRainbow, true);
							 layerRainbow = null;
						 }
					 }
					 animation_boolean[rainbow_id] = !animation_boolean[rainbow_id];
				}			 
				break;
				 
	       case R.id.flower:
	    	    int flower = 5;
	    	    animatin_layout.bringToFront();
				if(v.getId() == R.id.flower)
				{
					 if(!animation_boolean[flower] && layerFlower == null)
					 {
						 if(!animation_boolean[3] && !animation_boolean[2])
						 {
							 v.setBackgroundResource(R.drawable.flower_icon_hvr);
							 all_Images.isAmbienceSelected.set(flower, true);
							 layerFlower = new FlowerSnow();
							 scene.addChild(layerFlower);
						 }
						 else
						 {
							 if(layerSnow != null && scene != null)
							 {
								 layerSnow.removeAllChildren(true);
								 scene.removeChild(layerSnow, true);
								 layerSnow = null;
								 animation_buttons.get(3).setBackgroundResource(all_Images.animations[3]);
								 animation_boolean[3] = false;
							 }
							 if(layerRain != null && scene != null)
							 {
								 layerRain.removeAllChildren(true);
								 scene.removeChild(layerRain, true);
								 layerRain = null;
								 animation_buttons.get(2).setBackgroundResource(all_Images.animations[2]);
								 animation_boolean[2] = false;
								 ambience_boolean[4] = false;
								 rain_ambience.setBackgroundResource(R.drawable.rain_icon);
						    	 all_Images.isAmbienceSelected.set(2, false);
								 stop_ambience(4);
							 }
							 v.setBackgroundResource(R.drawable.flower_icon_hvr);
							 all_Images.isAmbienceSelected.set(flower, true);
							 if(layerFlower == null)
							 {
								 layerFlower = new FlowerSnow();
								 scene.addChild(layerFlower);
							 }
						 }
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.flower_icon);
						 all_Images.isAmbienceSelected.set(flower, false);
						 if(layerFlower != null)
						 {
							 layerFlower.removeAllChildren(true);
							 scene.removeChild(layerFlower, true);
							 layerFlower = null;
						 }
					 }
					 animation_boolean[flower] = !animation_boolean[flower];
				}			 
				break;
				 
	       case R.id.thunder:
	    	    int thunder_id_animation = 6;
	    	    animatin_layout.bringToFront();
				if(v.getId() == R.id.thunder)
				{
					 if(!animation_boolean[thunder_id_animation] && layerLightning == null)
					 {
						 v.setBackgroundResource(R.drawable.thunder_icon_hvr);
						 thunder_ambience.setBackgroundResource(R.drawable.thunder_icon_hvr);
						 layerLightning = new LightiningAnimClass();
				    	 scene.addChild(layerLightning, 1);												 
				    	 all_Images.isAmbienceSelected.set(thunder_id_animation, true);
				    	 if(!ambience_boolean[8])
				    	 {
				    		 play_ambience(8);
				    		 ambience_boolean[8] = true;
				    	 }
					 }
					 else
					 {
						 v.setBackgroundResource(R.drawable.thunder_icon);
						 thunder_ambience.setBackgroundResource(R.drawable.thunder_icon);
						 all_Images.isAmbienceSelected.set(thunder_id_animation, false);
						 if(layerLightning != null)
						 {
							 layerLightning.removeAllChildren(true);
							 scene.removeChild(layerLightning, true);
							 layerLightning = null;
						 }
						 if(ambience_boolean[8])
						 {
							 stop_ambience(8);
							 ambience_boolean[8] = false;
						 }
					 }
					 animation_boolean[thunder_id_animation] = !animation_boolean[thunder_id_animation];
				}			 
				break;	 
				 
		/* ************************************** AMBIENCE section started ************************************ */
				 
	       case R.id.morning:
	    	    int morning_id = 0, morningref_id = R.id.morning;
	    	    ambience_layout.bringToFront();  
	    	    start_Ambience(v, morning_id, morningref_id);						 
				break;	
				 
	       case R.id.bird_ambience:
	    	    int bird_id_ambience = 1, birdref_id = R.id.bird_ambience;
	    	    start_Ambience(v, bird_id_ambience, birdref_id);
	    	    ambience_layout.bringToFront();						 
				break;
				 
	       case R.id.evening:
	    	    int evening_id_ambience = 2, eveningref_id = R.id.evening;
	    	    start_Ambience(v, evening_id_ambience, eveningref_id);
	    	    ambience_layout.bringToFront();							 
				break;
				 
	       case R.id.cricket:
	    	    int cricket_id = 3, cricketref_id = R.id.cricket;
	    	    start_Ambience(v, cricket_id, cricketref_id);
	    	    ambience_layout.bringToFront();							 
				break;	
				 
	       case R.id.rain_ambience:
	    	    int rain_id_ambience = 4, rainref_id = R.id.rain_ambience;
	    	    start_Ambience(v, rain_id_ambience, rainref_id);							 
				break;
				 
	       case R.id.frog:
	    	    int frog_id = 5, frogref_id = R.id.frog;
	    	    start_Ambience(v, frog_id, frogref_id);
	    	    ambience_layout.bringToFront();							 
				break;
				 
	       case R.id.wind:
	    	    int wind_id = 6, windref_id = R.id.wind;
	    	    start_Ambience(v, wind_id, windref_id);
	    	    ambience_layout.bringToFront();    	    
				break;	
				 
	       case R.id.river:
	    	    int river_id = 7, riverref_id = R.id.river;
	    	    start_Ambience(v, river_id, riverref_id);
	    	    ambience_layout.bringToFront();    	    
				break;
				 
	       case R.id.thunder_ambience:
	    	    int thunder_id_ambience = 8, thunderref_id = R.id.thunder_ambience;
	    	    start_Ambience(v, thunder_id_ambience, thunderref_id);
	    	    ambience_layout.bringToFront();    	    
				break;
				 
	       case R.id.cicidas:
	    	    int night_id_ambience = 9, cicidasref_id = R.id.cicidas;
	    	    start_Ambience(v, night_id_ambience, cicidasref_id);
	    	    ambience_layout.bringToFront();    	    
				break;		
				
				/* ************************************** INSTRUMENTAL section started ************************************ */
				 
	       case R.id.flute:    	    
	    	    int flute_id = 0, fluteref_id = R.id.flute;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = flute_id;
		        reset_instrumental();
		        start_Instrumental(v, flute_id, fluteref_id);
		        break;
				 
	       case R.id.xylophone:   	    
	    	    int xylophone_id = 1, xylophoneref_id = R.id.xylophone;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = xylophone_id;
		        reset_instrumental();
		        start_Instrumental(v, xylophone_id, xylophoneref_id);
		        break;	
				 
	       case R.id.saxophone:    	    
	    	    int saxophone_id = 2, saxophoneref_id = R.id.saxophone;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = saxophone_id;
		        reset_instrumental();
		        start_Instrumental(v, saxophone_id, saxophoneref_id);
		        break;	
				 
	       case R.id.sitar:    	    
	    	    int sitar_id = 3, sitarref_id = R.id.sitar;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = sitar_id;
	    	    reset_instrumental();
	    	    start_Instrumental(v, sitar_id, sitarref_id);
		        break;	 
				 
	       case R.id.violin:    	    
	    	    int violin_id = 4, violinref_id = R.id.violin;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = violin_id;
	   	    	reset_instrumental();
	   	    	start_Instrumental(v, violin_id, violinref_id);
		        break;
				
	       case R.id.piano:    	    
	   	    	int piano_id = 5, pianoref_id = R.id.piano;
	   	    	instrumental_layout.bringToFront();
	   	    	instrumental_position_save = instrumental_position = piano_id;
	  	    	reset_instrumental();
	  	    	start_Instrumental(v, piano_id, pianoref_id);
		        break;
				 
	       case R.id.guitar:    	    
	    	    int guitar_id = 6, guitarref_id = R.id.guitar;
	    	    instrumental_layout.bringToFront();
	    	    instrumental_position_save = instrumental_position = guitar_id;
	   	    	reset_instrumental();
	   	    	start_Instrumental(v, guitar_id, guitarref_id);
		        break;
				 
				 /* ************************************** MODES section started ************************************ */			 
	       
				 
	       case R.id.day_modes:	    	    
		        default_modes();
		        modes_boolean[1] = true;
		        if(layerNightMode != null)
		        {
		        	layerNightMode.removeAllChildren(true);
		        	scene.removeChild(layerNightMode, true);
		        	layerNightMode = null;
		        }
		        if(layerCollorNightMode != null)
		        {
		        	CCTouchDispatcher.sharedDispatcher().removeDelegate(layerCollorNightMode);
		        	layerCollorNightMode.removeAllChildren(true);
		        	scene.removeChild(layerCollorNightMode, true);
//		        	System.out.println(" Removing layerCollorNightMode -->>>> ");
		        	layerCollorNightMode = null;
		        }
		        if(layerEveningMode != null)
		        {
		        	layerEveningMode.removeAllChildren(true);
		        	scene.removeChild(layerEveningMode, true);
//		        	System.out.println(" Removing layerEveningMode -->>>> ");
		        	layerEveningMode = null;
		        }
		        v.setBackgroundResource(R.drawable.day_icon_hvr);		 
		        v.setClickable(false);
				break;	 
				 
	       case R.id.night_modes:	    	    
		        default_modes();
		        modes_boolean[2] = true;
		        if(layerNightMode == null)
		        {
		        	layerNightMode = new NightMode();
		            scene.addChild(layerNightMode, 8);
		        }
		        if(layerCollorNightMode != null)
		        {
		        	CCTouchDispatcher.sharedDispatcher().removeDelegate(layerCollorNightMode);
//		        	System.out.println(" Removing layerCollorNightMode -->>>> ");
		        	layerCollorNightMode.removeAllChildren(true);
		        	scene.removeChild(layerCollorNightMode, true);
		        	layerCollorNightMode = null;
		        }
		        if(layerEveningMode != null)
		        {
		        	layerEveningMode.removeAllChildren(true);
		        	scene.removeChild(layerEveningMode, true);
//		        	System.out.println(" Removing layerEveningMode -->>>> ");
		        	layerEveningMode = null;
		        }
		        v.setBackgroundResource(R.drawable.night_icon_hvr);	
		        v.setClickable(false);
				break;
				 
	       case R.id.night_modes_color:	    	    
		        default_modes();		
		        modes_boolean[3] = true;
//		        System.out.println(" layerCollorNightMode ---->>> "+layerCollorNightMode);
		        if(layerCollorNightMode == null)
		        {
//		        	System.out.println(" creating layerCollorNightMode --::: ");
		        	layerCollorNightMode = new CollorNightMode(FeelIt.this);
		        	scene.addChild(layerCollorNightMode,8);
		        }
		        if(layerNightMode != null)
		        {
//		        	System.out.println(" Removing v --::: ");
		        	layerNightMode.removeAllChildren(true);
		        	scene.removeChild(layerNightMode, true);
		        	layerNightMode = null;
		        }
		        if(layerEveningMode != null)
		        {
		        	layerEveningMode.removeAllChildren(true);
		        	scene.removeChild(layerEveningMode, true);
//		        	System.out.println(" Removing layerEveningMode -->>>> ");
		        	layerEveningMode = null;
		        }
		        v.setBackgroundResource(R.drawable.night_icon_with_color_hvr);	
		        v.setClickable(false);
				break;	 
				 
	       case R.id.done_save:
				show_saved.setVisibility(View.GONE);
				main_layout.setVisibility(View.VISIBLE);
				clockTheme_array.get(grid_value).setVisibility(View.VISIBLE);
				break;
				 
	       case R.id.edit_save:
				new AlertDialog.Builder(FeelIt.this).setMessage("Perform a long click on the item you want to delete from the list.").
				setPositiveButton("Got it...!", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
				break;		
	       				
		}
//		isInonTouch = false;
		return 1;
	}

	private void start_Ambience(View v, int id, int refId) 
	{
		if(v.getId() == refId)
		{
			 if(!ambience_boolean[id])
			 {
				 v.setBackgroundResource(all_Images.ambience_hvr[id]);
				 play_ambience(id);					 
			 }
			 else
			 {
				 v.setBackgroundResource(all_Images.ambience[id]);
				 stop_ambience(id);					 
			 }
			 ambience_boolean[id] = !ambience_boolean[id];
		}
	}	
	
	private void start_Instrumental(View v, int id, int refId) 
	{
		if(v.getId() == refId)
		{
			 if(instrumentalBoolean[id].equals("true"))
			 {
				 v.setBackgroundResource(all_Images.instrumental_music_hvr[id]);
				 play_instrumental(id, v);					
			 }
			 else
			 {
				 v.setBackgroundResource(all_Images.instrumental_music[id]);
				 stop_instrumentall(id);				 
			 }				 
		}
        editor.putInt("instrumental_id", id);
        editor.commit();
	}

	private void disable_otherClicks() 
	{
		for(int i=0; i<theme_buttons.size(); i++)
		{
			theme_buttons.get(i).setEnabled(false);
		}
	}
	
	/* *************************** To save currently playing sounds and animation ************************************** */

	private void save_playing(String filename2) 
	{
		Uri uri = Saved.addItem(getContentResolver(), filename2);
		String segment = uri.getPathSegments().get(1);
		int newId = Integer.parseInt(segment);
		Saved.setItem(FeelIt.this, newId, filename2, 
					  animation_boolean[0], animation_boolean[1], animation_boolean[2], animation_boolean[3], animation_boolean[4], animation_boolean[5], animation_boolean[6], 
				      ambience_boolean[0], ambience_boolean[1], ambience_boolean[2], ambience_boolean[3], ambience_boolean[4], ambience_boolean[5], ambience_boolean[6], ambience_boolean[7], ambience_boolean[8], ambience_boolean[9], 
				      instrumental_position_save);
	}
	
	/* <<<<<<<<<<<<<<<<<<<<<<<<----------------------- To stop currently playing sound and animations ------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>> */
	
	private void reset_all() 
	{
		isReset = true;
		mute.setBackgroundResource(R.drawable.mute_bt);
		isMute = true;
		pause_sound();
		reset_animation();
		reset_ambience();
		reset_instrumental();
		isReset = false;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To set all themes to reset state before selecting new theme ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */

	private int themeUnSelection() 
	{		
		if((water_soundclass.fall_files != null && water_soundclass.fall_files[fall_pos] != null) && water_soundclass.fall_files[fall_pos].isPlaying())
		{
			water_soundclass.release_fall_sound(fall_pos);
		}		
		for(int i=0; i<theme_buttons.size(); i++)
		{
			theme_buttons.get(i).setBackgroundResource(all_Images.themeThumbs[i]);
			theme_buttons.get(i).setEnabled(true);
		} 
		reset_animation();
		
		return 1;	
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To set all Animation to default state before selecting new Animation ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int reset_animation()
	{
		if(isSavedItemSelected)
		{
			for(int i=0;i <animation_boolean.length; i++)
			{
				animation_buttons.get(i).setBackgroundResource(all_Images.animations[i]);
				animation_boolean[i] = false;
			}
		}
//		System.out.println("<<<---@@@ inside reset_animation @@@--->>>");
		if(/*animation_boolean[0] == true &&*/ layerBirdFly != null)
		{			
			layerBirdFly.removeAllChildren(true);
			scene.removeChild(layerBirdFly, true);
			layerBirdFly = null;
			if(bSound != null)					
				bSound.stopSound();
			if(isReset)
				animation_buttons.get(0).setBackgroundResource(all_Images.animations[0]);
//			System.out.println("stopping bird animation @@@--->>>");
		}
		if(/*animation_boolean[1] == true && */layerButterFly != null)
		{
			layerButterFly.removeAllChildren(true);
			scene.removeChild(layerButterFly, true);
			layerButterFly = null;
			if(isReset)
				animation_buttons.get(1).setBackgroundResource(all_Images.animations[1]);
//			System.out.println("stopping butterfly animation @@@--->>>");
		}
		if(/*animation_boolean[2] == true && */layerRain != null)
		{
			layerRain.removeAllChildren(true);
			scene.removeChild(layerRain, true);
			layerRain = null;
			stop_ambience(4);
			if(isReset)
				animation_buttons.get(2).setBackgroundResource(all_Images.animations[2]);
//			System.out.println("stopping rain animation @@@--->>>");
		}
		if(/*animation_boolean[3] == true && */layerSnow != null)
		{
			layerSnow.removeAllChildren(true);
			scene.removeChild(layerSnow, true);
			layerSnow = null;
			if(isReset)
				animation_buttons.get(3).setBackgroundResource(all_Images.animations[3]);
//			System.out.println("stopping snow animation @@@--->>>");
		}
		if(/*animation_boolean[4] == true && */layerRainbow != null)
		{
			layerRainbow.removeAllChildren(true);
			scene.removeChild(layerRainbow, true);
			layerRainbow = null;
			if(isReset)
				animation_buttons.get(4).setBackgroundResource(all_Images.animations[4]);
//			System.out.println("stopping rainbow animation @@@--->>>");
		}
		if(/*animation_boolean[5] == true && */layerFlower != null)
		{
			layerFlower.removeAllChildren(true);
			scene.removeChild(layerFlower, true);
			layerFlower = null;
			if(isReset)
				animation_buttons.get(5).setBackgroundResource(all_Images.animations[5]);
//			System.out.println("stopping flower animation @@@--->>>");
		}
		if(/*animation_boolean[6] == true && */layerLightning != null)
		{
			layerLightning.removeAllChildren(true);
			scene.removeChild(layerLightning, true);
			layerLightning = null;
			stop_ambience(8);
			if(isReset)
				animation_buttons.get(6).setBackgroundResource(all_Images.animations[6]);
//			System.out.println("stopping thunder animation @@@--->>>");
		}
		if(/*modes_boolean[2] && */(layerNightMode != null && scene != null))
		{
			System.out.println("stopping nightmode animation in themeSelection --->>>");
			layerNightMode.removeAllChildren(true);
			scene.removeChild(layerNightMode, true);
			layerNightMode = null;
		}
		if(/*modes_boolean[2] && */(layerEveningMode != null && scene != null))
		{
//			System.out.println("stopping evening animation in themeSelection @@@--->>>");
			layerEveningMode.removeAllChildren(true);
			scene.removeChild(layerEveningMode, true);
			layerEveningMode = null;
		}
		isBird_ambience = true;
		if(isReset)
		{
			for(int i=0; i<animation_boolean.length; i++)
			{
				animation_boolean[i] = false;
			}
		}
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To set all Ambiance to default state before selecting new Ambience ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int reset_ambience()
	{		
		for(int i=0; i<ambience_buttons.size(); i++)
		{
			ambience_buttons.get(i).setBackgroundResource(all_Images.ambience[i]);
			ambience_boolean[i] = false;
			all_Images.isAmbienceSelected.set(i, false);
		}
		if(bSound != null)
		{					
			bSound.stopSound();			
		}
		ambience_soundclass.release_Ambience_sound();
		
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To set all Instrumental to default state before selecting new Instrumental ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int reset_instrumental() 
	{
		instr_id = sPreferences.getInt("instrumental_id", 0);
		if(instrumental_soundclass.instrumental_files[instr_id] != null && instrumental_soundclass.instrumental_files[instr_id].isPlaying())
		{
			instrumental_soundclass.release_instrumental_sound(instr_id);
			instrumental_buttons.get(instr_id).setBackgroundResource(all_Images.instrumental_music[instr_id]);
		}
		for(int i=0; i<instrumentalBoolean.length; i++)
		{			
			instrumental_buttons.get(i).setBackgroundResource(all_Images.instrumental_music[i]);
			if(isReset)
				instrumentalBoolean[i] = "true";
			if(i != instrumental_position)
				instrumentalBoolean[i] = "true";			
		}
		return 1;		
	}
	
	private int default_modes() 
	{
		day_modes.setBackgroundResource(R.drawable.day_icon);
		day_modes.setClickable(true);
		night_modes.setBackgroundResource(R.drawable.night_icon);
		night_modes.setClickable(true);
		night_mode_color.setBackgroundResource(R.drawable.night_icon_with_color);
		night_mode_color.setClickable(true);
		
		for(int i=0; i<modes_boolean.length; i++)
		{
			modes_boolean[i] = false;
		}
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To mute and un-mute the currently playing sounds ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */

	private void mute_unmute(View v) 
	{
		if(v.getId() == R.id.mute)
		{
			if(isMute)
			 {
				 v.setBackgroundResource(R.drawable.unmute_bt);	
				 if((water_soundclass.fall_files[0] != null && water_soundclass.fall_files[0].isPlaying()) && water_shared);
				 	water_soundclass.pause_water(0);					
				 pause_sound();				 
			 }
			 else
			 {
				 v.setBackgroundResource(R.drawable.mute_bt);	
				 new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(isAmbiencePaused)
							 play_ambience_afterpauseORsave();						 
						 if(isInstrumentalPaused)
							 play_instrumental_afterpauseORsave();
						 if(isFallPaused)
						 {
//							 System.out.println("in FeelIT mute_unmute if @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+isFallPaused);
							 water_soundclass.start_water(fall_pos);
						 }
					}
				}).start();
				 
			 }
			isMute = !isMute;
		}
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To pause currently playing sounds after mute has been ckicked ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int pause_sound()
	{		
		if(isAmbience)
		{
			if(isReset)
				for(int i=0; i<ambience_soundclass.ambience_files.length; i++)
				{
					if(ambience_soundclass.ambience_files[i] != null && ambience_soundclass.ambience_files[i].isPlaying())
						ambience_soundclass.stop_ambience(i);
				}				
			else
				ambience_soundclass.pause_ambience();			
			
			if(bSound != null)
			{				
				if(isReset)
				{
					bSound.stopSound();
					bSound.stopSound();
					bSound.stopAllBirdSound();
				}
				else
				{
					bSound.pauseSound();
					bSound.stopSound();
					bSound.stopAllBirdSound();
				}
			}
			isAmbiencePaused = true;
		}	
		
		if(isInstrumental)
		{
			for(int i=0; i<instrumental_soundclass.instrumental_files.length; i++)
	    	{
	    		if(instrumental_soundclass.instrumental_files[i] != null && instrumental_soundclass.instrumental_files[i].isPlaying())
	    			instrumental_soundclass.pause_instrumental(i);
	    	}
			isInstrumentalPaused = true;
		}	
		isFallPaused = true;
		
		/*if(isFall)
		{
			if((water_soundclass.fall_files != null && water_soundclass.fall_files[fall_pos] != null) && water_soundclass.fall_files[fall_pos].isPlaying())
				water_soundclass.pause_water(fall_pos);
			
			isFallPaused = true;
		}
		
		if(water_soundclass.fall_files[THEMECOUNT - 1] != null && water_soundclass.fall_files[THEMECOUNT - 1].isPlaying())
			water_soundclass.pause_water(THEMECOUNT - 1);*/
		
		return 1;		
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To play Ambiance music that was paused after un-mute button clicked ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */

	private int play_ambience_afterpauseORsave() 
	{
		for(int i=0; i<ambience_soundclass.ambience_files.length; i++)
		{
			if(all_Images.isAmbienceSelected.get(i).booleanValue())
			{
				if(isSavedItemSelected && ambience_soundclass.ambience_files[i] == null)				
					ambience_soundclass.create_ambience_sound(i);
				
				if((ambience_soundclass.ambience_files != null && ambience_soundclass.ambience_files[i] != null) && (ambience_slider != null && ambiance_shared))
				{
					if(!ambience_soundclass.ambience_files[i].isPlaying() && all_Images.isAmbienceSelected.get(i).booleanValue())
					{
						if(i == 1)
						{
							ambience_buttons.get(i).setBackgroundResource(all_Images.ambience_hvr[i]);
							playBirdSound();
						}
						else
						{
							ambience_soundclass.ambience_files[i].setVolume((float)ambience_slider.getProgress()/100, (float)ambience_slider.getProgress()/100);
							ambience_buttons.get(i).setBackgroundResource(all_Images.ambience_hvr[i]);							
							ambience_soundclass.start_ambience(i);
						}
					}			
				}
			}
		}
		isAmbiencePaused = false;
		isSavedItemSelected = false;
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To play Instrumental music that was paused after un-mute button clicked ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int play_instrumental_afterpauseORsave() 
	{
		if(instrumental_soundclass != null && instrumental_soundclass.instrumental_files[instrumental_position] != null)
			instrumental_soundclass.create_instrumental_sound(instrumental_position);
		if((instrumental_soundclass.instrumental_files != null && instrumental_soundclass.instrumental_files[instrumental_position] != null) && (instrumental_slider != null && instrumental_shared))
		{
			if(!instrumental_soundclass.instrumental_files[instrumental_position].isPlaying() && instrumentalBoolean[instrumental_position] == "false")
			{
				instrumental_buttons.get(instrumental_position).setBackgroundResource(all_Images.instrumental_music_hvr[instrumental_position]);
				instrumental_soundclass.instrumental_files[instrumental_position].setVolume((float)instrumental_slider.getProgress()/100, (float)instrumental_slider.getProgress()/100);				
				instrumental_soundclass.start_instrumental(instrumental_position);
			}			
		}		
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To PLAY Ambiance music when any of the Ambiance items selected ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */

	private int play_animations()
	{
		isAnimation = true;		
		for(int i=0; i<animation_buttons.size(); i++)
		{
			if(animation_boolean[i] == true)
				animation_buttons.get(i).setBackgroundResource(all_Images.animations_hvr[i]);
		}
		
		if(all_Images.isAnimationSelected.get(0).booleanValue() && all_Images.animationActual_boolean.get(0) == false)
		{
			if(layerBirdFly == null)
			{
				layerBirdFly = new BirdFlyAnimClass();
				scene.addChild(layerBirdFly, 1);
				animation_buttons.get(0).setBackgroundResource(all_Images.animations_hvr[0]);
			}
		}
		if(all_Images.isAnimationSelected.get(1).booleanValue() && all_Images.animationActual_boolean.get(1) == false)
		{
			if(layerButterFly == null)
			{
				layerButterFly = new ButterFlyAnimClass();
				scene.addChild(layerButterFly, 1);
				animation_buttons.get(1).setBackgroundResource(all_Images.animations_hvr[1]);
			}
		}
		if(all_Images.isAnimationSelected.get(2).booleanValue() && all_Images.animationActual_boolean.get(2) == false)
		{
			if(layerRain == null)
			{
				layerRain = new RainAnimClass();
				scene.addChild(layerRain, 1);
				animation_buttons.get(2).setBackgroundResource(all_Images.animations_hvr[2]);
			}
		}
		if(all_Images.isAnimationSelected.get(3).booleanValue() && all_Images.animationActual_boolean.get(3) == false)
		{
			if(layerSnow == null)
			{
				layerSnow = new Snow();
				scene.addChild(layerSnow, 1);
				animation_buttons.get(3).setBackgroundResource(all_Images.animations_hvr[3]);
				animation_boolean[3] = !animation_boolean[3];
			}
		}
		if(all_Images.isAnimationSelected.get(4).booleanValue() && all_Images.animationActual_boolean.get(4) == false)
		{
			if(layerRainbow == null)
			{
				layerRainbow = new Rainbow();
				scene.addChild(layerRainbow, 1);
				animation_buttons.get(4).setBackgroundResource(all_Images.animations_hvr[4]);
			}
		}
		if(all_Images.isAnimationSelected.get(5).booleanValue() && all_Images.animationActual_boolean.get(5) == false)
		{
			if(layerFlower == null)
			{
				layerFlower = new FlowerSnow();
				scene.addChild(layerFlower, 1);
				animation_buttons.get(5).setBackgroundResource(all_Images.animations_hvr[5]);
			}
		}
		if(all_Images.isAnimationSelected.get(6).booleanValue() && all_Images.animationActual_boolean.get(6) == false)
		{
			if(layerLightning == null)
			{
				layerLightning = new LightiningAnimClass();
				scene.addChild(layerLightning, 1);
				animation_buttons.get(6).setBackgroundResource(all_Images.animations_hvr[6]);
			}
		}
		isAnimationPaused = false;
		
		return 1;
	}
	
	private int play_ambience(final int id) 
	{
		if(ambience_soundclass != null && ambience_soundclass.ambience_files[id] == null)
			ambience_soundclass.create_ambience_sound(id);
		isAmbience = true;
		all_Images.isAmbienceSelected.set(id, true);
		if(bSound != null && id == 1)
		{
			if(ambiance_shared && isMute)
				playBirdSound();			
		}		
		else if((ambience_soundclass.ambience_files[id] != null && !ambience_soundclass.ambience_files[id].isPlaying()) && ambience_slider != null)
		{
			if(ambiance_shared && isMute)
			{
				Thread t = new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub						
						ambience_soundclass.ambience_files[id].setVolume((float)ambience_slider.getProgress()/100, (float)ambience_slider.getProgress()/100);						
						ambience_soundclass.start_ambience(id);
					}					
				});
				t.start();				
			}
		}
		return 1;    		
	}
	
	private void playBirdSound() 
	{		
		bSound.playSound((float)ambience_slider.getProgress()/100, (float)ambience_slider.getProgress()/100);
	}

	/* <<<<<<<<<<<<<<<<<<<------------------------ To STOP Ambiance music when any of the Ambiance item is un-selected ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private int stop_ambience(int id) 
	{		
		isAmbience = false;
		all_Images.isAmbienceSelected.set(id, false);
		if(bSound != null)	
		{
			bSound.stopSound();
			bSound.stopAllBirdSound();
		}
		
		if(ambience_soundclass.ambience_files[id] != null && ambience_soundclass.ambience_files[id].isPlaying())
			ambience_soundclass.stop_ambience(id);
		return 1;
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To PLAY Instrumental music when any of the Instrumental item is selected ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private void play_instrumental(int id, View v) 
	{		
		instrumental_buttons.get(id).setBackgroundResource(all_Images.instrumental_music_hvr[id]);
		isInstrumental = true;
		instrumentalBoolean[id] = "false";
		
		if(instrumental_soundclass != null && instrumental_soundclass.instrumental_files[id] == null)
			instrumental_soundclass.create_instrumental_sound(id);
		if((instrumental_soundclass.instrumental_files[id] != null && !instrumental_soundclass.instrumental_files[id].isPlaying()) && instrumental_slider != null)
		{			
			if(instrumental_shared && isMute)
			{
				instrumental_soundclass.instrumental_files[id].setVolume((float)instrumental_slider.getProgress()/100, (float)instrumental_slider.getProgress()/100);
				instrumental_soundclass.instrumental_files[id].setLooping(true);
				instrumental_soundclass.instrumental_files[id].start();				
			}			
		}	
	}
	
	/* <<<<<<<<<<<<<<<<<<<------------------------ To STOP Instrumental music when any of the Instrumental items un-selected ---------------------->>>>>>>>>>>>>>>>>>>>>>>> */
	
	private void stop_instrumentall(int id) 
	{
		instrumentalBoolean[id] = "true";
		instrumental_position_save = -1;
		if(instrumental_soundclass.instrumental_files[id] != null && instrumental_soundclass.instrumental_files[id].isPlaying())
			instrumental_soundclass.release_instrumental_sound(id);
			
		isInstrumental = false;
	}
	
	private int play_fall_afterpause()
	{
		if(water_soundclass != null && water_soundclass.fall_files[THEMECOUNT - 1] == null)
			water_soundclass.create_fall_sound(THEMECOUNT - 1);
		if((water_soundclass.fall_files != null && water_slider != null) && water_shared)						
		{
			if((water_soundclass.fall_files[THEMECOUNT - 1] != null && !water_soundclass.fall_files[THEMECOUNT - 1].isPlaying()) && isMute)
			{
				theme_buttons.get(THEMECOUNT - 1).setBackgroundResource(all_Images.themeThumbs_hvr[THEMECOUNT - 1]);
				water_soundclass.fall_files[THEMECOUNT - 1].setVolume((float)water_slider.getProgress()/100, (float)water_slider.getProgress()/100);
				water_soundclass.fall_files[THEMECOUNT - 1].setLooping(true);	
				water_soundclass.fall_files[THEMECOUNT - 1].start();
				theme_buttons.get(THEMECOUNT - 1).setEnabled(false);
//				System.out.println("setClickable is false $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
		}
		isFallPaused = false;		
		return 1;
	}	
	
	private void runAnimations() 
    {    	
    	Log.d(getClass().getName(), "runAnimations(): entering" );
	    fadeIn_fall = AnimationUtils.loadAnimation(FeelIt.this, R.anim.fadein_fall);
	    fadeIn_fall.setAnimationListener(myFadeInAnimationListener);
	    fadeOut_fall = AnimationUtils.loadAnimation(FeelIt.this, R.anim.fadeout_fall);
	    fadeOut_fall.setAnimationListener(myFadeOutAnimationListener);
	    if(isSlideshowOn == true)    
	    	launchInAnimation();
    }
	
	private void launchOutAnimation() 
    {
		if(animationTimer == null)
			animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handle_animations.sendEmptyMessage(0);
//				System.out.println("In launchOutAnimation method --->>>");
			}
		}, 10000, total_slideshow_time); 	  
    }    
    
    private void launchInAnimation() 
    {
    	if(animationTimer == null)
    		animationTimer = new Timer();
    	animationTimer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				handle_animations.sendEmptyMessage(1);
				handle_fall.sendEmptyMessage(0);
//				System.out.println("In launchInAnimation method --->>>");
			}
		}, 10000, total_slideshow_time);    		 	    
    } 
    
    private Runnable mLaunchFadeOutAnimation = new Runnable() 
    {
	    public void run() 
	    {
	    	try
	    	{
		    	if(THEMECOUNT < 4 && isSlideshowOn == true)
		    	{
		    		launchOutAnimation();		    		
		    		THEMECOUNT++;			    			       		
		    	}
		    	else
		    	{
		    		THEMECOUNT = 1; 
		    		launchOutAnimation();
		    	}
	    	}
	    	catch (OutOfMemoryError e) {				
			}	    	
	    }
    };    
    
    private Runnable mLaunchFadeInAnimation = new Runnable() 
    {
	    public void run() 
	    {	    	
	    	if(THEMECOUNT < 4 && isSlideshowOn == true)
	    	{
	    		launchInAnimation();	    		
    			THEMECOUNT++;
	    	}
	    	else
	    	{
	    		THEMECOUNT = 1; 
	    		launchInAnimation();	    		
	    	}	    	
	    }
    };   
    
    public class LocalFadeInAnimationListener implements AnimationListener 
    {    	
	    public void onAnimationEnd(Animation animation) 
	    {
	    	mLaunchFadeOutAnimation.run();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}		
	    
    };
    
    public class LocalFadeOutAnimationListener implements AnimationListener 
    {    	
	    public void onAnimationEnd(Animation animation) 
	    {
	    	mLaunchFadeInAnimation.run();
		}
		
	    public void onAnimationRepeat(Animation animation){
	    	
	    }
	
	    public void onAnimationStart(Animation animation){
	    	
	    }
    }
	
	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		/*CCDirector.sharedDirector().end();
		mGlSurfaceView.postInvalidate();*/
//		mGlSurfaceView = null;
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- Feelit STOPPED ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
//		wl.release();
		isAmbience = isInstrumental  = isFall = true;
		release_Sound();
				
		if(water_soundclass.fall_files[fall_pos] != null && water_soundclass.fall_files[fall_pos].isPlaying())
			water_soundclass.release_fall_sound(fall_pos);
		
		if(temp_gettimertime)
			countDown.cancel();
		
		if(!isSlideshow)
		{
			animationTimer.cancel();
			animationTimer = null;
		}
		bSound.stopSound();
		editor.putBoolean("isAppClosed", false);
		editor.commit();
		
		new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				//pause_sound();
				editor.putBoolean("set", false);				
				editor.putBoolean("isAppClosed", false);
				editor.putInt("gal_position", parent_pos);
				editor.commit();
				for(int i=0; i<animation_boolean.length; i++)
				{
					editor.putBoolean("anim_bool"+i, animation_boolean[i]);
					editor.commit();
				}		
				for(int i=0; i<ambience_boolean.length; i++)
				{
					editor.putBoolean("amb_bool"+i, ambience_boolean[i]);
					editor.commit();
				}	
				editor.putInt("instr_pos", instrumental_position);
				editor.commit();
				editor.putString("instr_string", instrumentalBoolean[instrumental_position]);
				editor.commit();
				reset_handler.sendEmptyMessage(0);
	//			reset_all();
			}
		}).start();
//		this.finish();
	}
	
	private int dis_Appear()
	{
//		System.out.println("in dis_Appear @@@@@@@@@@@@@@@@@@@@@@@@@@@@@&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		timer.cancel();
		timer = null;
		timer = new Timer();
		timer.schedule(new TimerTask() {							
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isShow)
				{
					if(disappear_forever_handler != null)
						disappear_forever_handler.sendEmptyMessage(0);
				}
			}
		}, 5000);
		return 1;
	}
	
	@Override
	protected void onRestart() 
    {
		// TODO Auto-generated method stub
		super.onRestart();
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- in Feelit onRestart ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
		new Thread(new Runnable() 
    	{			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				for(int i=0; i<animation_boolean.length; i++)
				{
					animation_boolean[i] = sPreferences.getBoolean("anim_bool"+i, false);
					all_Images.isAnimationSelected.set(i, animation_boolean[i]);
					all_Images.animationActual_boolean.set(i, !animation_boolean[i]);
//					System.out.println(" animation values after restoring are --->>> "+animation_boolean[i]);
				}		
				for(int i=0; i<ambience_boolean.length; i++)
				{
					ambience_boolean[i] = sPreferences.getBoolean("amb_bool"+i, false);
					all_Images.isAmbienceSelected.set(i, sPreferences.getBoolean("amb_bool"+i, false));					
//					System.out.println(" ambiance values after restoring are --->>> "+ambience_boolean[i]);
				}
			}
		}).start();
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- in Feelit onBackPressed ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");
//		startActivity(new Intent(FeelIt.this, MainPage.class));
//		Intent intent = new Intent();
//		setResult(RESULT_OK);
//		finish();
	}
	
	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
//		System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -------------------------------- Feelit DESTROYED ----------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>> ");		
		CCDirector.sharedDirector().end();
		wl.release();
		mGlSurfaceView.postInvalidate();
		mGlSurfaceView = null;
		if(bSound != null)
		{
			bSound.stopAllBirdSound();
			bSound.timer.cancel();
			bSound.timer = null;
		}
		
		if(mGlSurfaceView != null)
			mGlSurfaceView.destroyDrawingCache();
		if(layerBirdFly != null)
		{
			layerBirdFly.stopAllActions();			
			layerBirdFly = null;
		}
		/*if(layerButterFly != null)
		{
			layerButterFly.stopAllActions();
			layerButterFly.removeAllChildren(true);
			scene.removeChild(layerButterFly, true);
			layerButterFly = null;
		}*/
		if(globalThemePatch != null)
		{
			globalThemePatch.stopAllActions();
		}
		if(layerLightning != null)
		{
			layerLightning.stopAllActions();
			layerLightning = null;
		}
		/*if(layerRain != null)
		{
			layerRain.stopAllActions();
			layerRain = null;
		}*/
		if(layerSnow != null)
		{
			layerSnow.stopAllActions();
			layerSnow = null;
		}
		/*if(layerFlower != null)
		{
			layerFlower.stopAllActions();
			layerFlower = null;
		}*/
		
		new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				//pause_sound();
				editor.putBoolean("set", false);				
				editor.putBoolean("isAppClosed", false);
				editor.putInt("gal_position", parent_pos);
				editor.commit();
				for(int i=0; i<animation_boolean.length; i++)
				{
					editor.putBoolean("anim_bool"+i, animation_boolean[i]);
					editor.commit();
				}		
				for(int i=0; i<ambience_boolean.length; i++)
				{
					editor.putBoolean("amb_bool"+i, ambience_boolean[i]);
					editor.commit();
				}	
				editor.putInt("instr_pos", instrumental_position);
				editor.commit();
				editor.putString("instr_string", instrumentalBoolean[instrumental_position]);
				editor.commit();
				reset_handler.sendEmptyMessage(0);
	//			reset_all();
			}
		}).start();	
		
		System.gc();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
	{
		// TODO Auto-generated method stub
		dis_Appear();
		if(fromUser)
		{
			switch (seekBar.getId()) 
			{
				case R.id.ambience_slider_feelit:
					 for(int i=0; i<ambience_soundclass.ambience_files.length; i++)
					 {
						if((ambience_soundclass.ambience_files != null && ambience_soundclass.ambience_files[i] != null) && ambience_slider != null)
							ambience_soundclass.ambience_files[i].setVolume((float)progress/100, (float)progress/100);											
					 }					 
					 if(bSound != null)
					 {
						 bSound.setBirdVolume((float)progress/100, (float)progress/100);
					 }
					 editor.putInt("ambience_progress", progress);
					 editor.putInt("ambience_slider_settings", progress);
					 editor.commit();
					 break;				
					
				case R.id.instrumental_slider_feelit:
					 if((instrumental_soundclass.instrumental_files != null && instrumental_soundclass.instrumental_files[instrumental_position] != null) && instrumental_slider != null)
						 instrumental_soundclass.instrumental_files[instrumental_position].setVolume((float)progress/100, (float)progress/100);
					 
					 editor.putInt("instrumental_progress", progress);
					 editor.putInt("instrumental_slider_settings", progress);
					 editor.commit();
					 break;
					
				case R.id.water_slider_feelit:
					 if((water_soundclass.fall_files != null && water_soundclass.fall_files[fall_pos] != null) && water_slider != null)
						 water_soundclass.fall_files[fall_pos].setVolume((float)progress/100, (float)progress/100);
					 
					 editor.putInt("water_progress", progress);
					 editor.putInt("water_slider_settings", progress);
					 editor.commit();
					 break;	
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) 
	{
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) 
	{
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> adpt, final View v, final int position, final long id) 
	{
		// TODO Auto-generated method stub
		if(position != 0 && position != 4)
		{
			new AlertDialog.Builder(FeelIt.this).setMessage("Are you sure, you want to delete this element from the list...?").
			setPositiveButton("Yes", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub					
					final Cursor c = (Cursor)save_list.getAdapter().getItem(position);				
	                final Save save = new Save(c);
					Saved.deleteItem(FeelIt.this, save.id);
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			}).create().show();
			
			return true;
		}
		else		
			return false;
	}
	
	
	 static class GameScene extends CCLayer
	 {
			public static CCScene scene() 
			{
				scene = CCScene.node();
				layerBGWaterFall = new GameScene();
				scene.addChild(layerBGWaterFall);				
				return scene;
			}
			
	    	public GameScene()
	    	{
	    		sResponse1 = null;
	    		String themeNum = "" + THEMECOUNT;
				themeName = "theme" + themeNum + "_";
//	    		System.out.println(" asdasfasas -----------------------------" + themeName);	    		
	    		globalTheme = CCSprite.sprite(themeName+"bg.png");
	    		if(THEMECOUNT == 2 || THEMECOUNT == 3 || THEMECOUNT == 4)
	    			this.addChild(globalTheme, 1);
	    		else
	    			this.addChild(globalTheme);
	    		globalTheme.setPosition(CGPoint.make(0, 0f));
	    		globalTheme.setAnchorPoint(0.0f, 0.0f);
//	    		System.out.println("SCREEN_RATION_X-->>> "+ SCREEN_RATIO_X+"SCREEN_RATION_Y --->>> "+SCREEN_RATIO_Y);
	    		globalTheme.setScaleX(SCREEN_RATIO_X);
	    		globalTheme.setScaleY(SCREEN_RATIO_Y);
	    		
				waterAnimation(themeName);	
				
//				System.out.println("---- Creating an arrray list-------");	
				ArrayList<CCSpriteFrame> animFrames = new ArrayList<CCSpriteFrame>();
	    		for(int i = 0; i <= ThemeImageCount; i++) 
	    		{
	    			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().spriteFrameByName(String.format(themeName+"%d.png", i));
	    			animFrames.add(frame);
	    		}
//	    		System.out.println(">>>>---- adding frames to animFrames -----<<<<"+(((ThemeImageCount+1)/24f)/24f));
	    		CCAnimation animation = CCAnimation.animation("Water Fall", (((ThemeImageCount+1)/24f)/24f), animFrames);		    		
	    		globalThemePatch.runAction(CCRepeatForever.action(CCAnimate.action(animation, false)));
//	    		System.out.println(">>>>---- after runAction -----<<<<");
	    		if(!isFirstClearSprites)
	    		{
		    		for(int i=0; i <= previousThemeImageCount; i++)
		    			CCSpriteFrameCache.sharedSpriteFrameCache().removeSpriteFrame(String.format("theme"+PREVIOUSTHEME+"%d.png", i));
//		    		System.out.println("PREVIOUSTHEME is ------------------------------------->>>"+PREVIOUSTHEME);
//		    		System.out.println("previousThemeImageCount is --------------------------------------->>>"+previousThemeImageCount);
	    		}
	    		isFirstClearSprites = false;
        		PREVIOUSTHEME = THEMECOUNT;	
        		previousThemeImageCount = ThemeImageCount;
        		
	    		CleanMemory();
	    		
	    		if(isInOnClick)			// checks if any animation is started or not (if started then only add them to next theme)
	    		{
//	    			System.out.println(" <<<<<<<<<<<<<<<< isInOnClick --------------- isInOnClick is true, so adding animations ------------- isInOnClick >>>>>>>>>>>>>> "+isInOnClick);	    			
	    			add_animation();		// to add animation to next theme if they are started in previous theme
	    		}
				
				sResponse1 = "success";
//				System.out.println(" returning response --------------- >>>>>>>>>>>>>>> "+sResponse1);
				
	    	} 	
	    	

			@Override
			public void onEnter() 
			{
				// TODO Auto-generated method stub
				super.onEnter();
//				System.out.println(" in onEnter method 8888888888888888888888888888888888000000000000000000000000000000000000000000000 ");
			}
	    	
	    	public void waterAnimation(String themename)
	        {    		
//	    		System.out.println("Inside waterAnimation method--------------------->>>>>>>>>>>>>>>>>>>>------------------------");
	    		themeName = themename;
	    		if(isfirstinoncreate)
	    		{
	    			PREVIOUSTHEME = 0;
	    			isfirstinoncreate = false;
	    		}
	    		if(PREVIOUSTHEME != THEMECOUNT)
		    	{
		    		switch(THEMECOUNT)
			    	{
			    		case 1:
			    			CleanMemory();		    			
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_1.plist");
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_2.plist");
			        		ThemeImageCount = 9;
			        		pListCount = 2;
				    		globalThemePatch  = CCSprite.sprite("theme"+THEMECOUNT+"_0.png", true);
				    		this.addChild(globalThemePatch);
				    		globalThemePatch.setPosition(CGPoint.make(0,0f));
				    		globalThemePatch.setAnchorPoint(0.0f, 0.0f);
				    		globalThemePatch.setScaleX(THEME_SCALE_X);
				    		globalThemePatch.setScaleY(THEME_SCALE_Y);
			    			break;
			    			
			    		case 2:
			    			CleanMemory();
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_1.plist");
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_2.plist");
			        		ThemeImageCount = 30;
			        		pListCount = 2;
				    		globalThemePatch  = CCSprite.sprite("theme"+THEMECOUNT+"_0.png",true);
				    		this.addChild(globalThemePatch);
				    		globalThemePatch.setPosition(CGPoint.make(0,0f));
				    		globalThemePatch.setAnchorPoint(0.0f, 0.0f);
				    		{
					    		globalThemePatch.setScaleX(THEME_SCALE_X);
					    		globalThemePatch.setScaleY(THEME_SCALE_Y);
				    		}
			    			break;
			    			
			    		case 3:
			    			CleanMemory();
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_1.plist");
			        		ThemeImageCount = 23;	
				    		globalThemePatch  = CCSprite.sprite("theme"+THEMECOUNT+"_0.png", true);
				    		this.addChild(globalThemePatch);
				    		globalThemePatch.setPosition(CGPoint.make(0,0f));
				    		globalThemePatch.setAnchorPoint(0.0f, 0.0f);
				    		{
					    		globalThemePatch.setScaleX(THEME_SCALE_X);
					    		globalThemePatch.setScaleY(THEME_SCALE_Y);		
				    		}
			    			break;
			    			
			    		case 4:
			    			CleanMemory();	
			    			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("theme"+THEMECOUNT+"_1.plist");
			        		ThemeImageCount = 29;
				    		globalThemePatch  = CCSprite.sprite("theme"+THEMECOUNT+"_0.png", true);
				    		this.addChild(globalThemePatch);
				    		globalThemePatch.setPosition(CGPoint.make(0,0f));
				    		globalThemePatch.setAnchorPoint(0.0f, 0.0f);
				    		{
					    		globalThemePatch.setScaleX(THEME_SCALE_X);
					    		globalThemePatch.setScaleY(THEME_SCALE_Y);
				    		}
			    			break;   			
			    		
			    	}
		    	}
			}
	    	
	    	/* **************************** To restore all animations when user comes back to Activity without destroying ********************************* */
	    	
	    	private int add_animation() 
	    	{
	    		if(animation_boolean[0] == true && layerBirdFly == null)
	    		{
	    			layerBirdFly = new BirdFlyAnimClass();
	    			scene.addChild(layerBirdFly, 1);
	    		}
	    		if(animation_boolean[1] == true && layerButterFly == null)
	    		{
	    			layerButterFly = new ButterFlyAnimClass();
	    			scene.addChild(layerButterFly, 1);
	    		}
	    		if(animation_boolean[2] == true && layerRain == null)
	    		{
	    			layerRain = new RainAnimClass();
	    			scene.addChild(layerRain, 1);
	    		}
	    		if(animation_boolean[3] == true && layerSnow == null)
	    		{
	    			layerSnow = new Snow();
	    			scene.addChild(layerSnow, 1);
	    		}
	    		if(animation_boolean[4] == true && layerRainbow == null)
	    		{
	    			layerRainbow = new Rainbow();
	    			scene.addChild(layerRainbow, 1);
	    		}
	    		if(animation_boolean[5] == true && layerFlower == null)
	    		{
	    			layerFlower = new FlowerSnow();
	    			scene.addChild(layerFlower, 1);
	    		}
	    		if(animation_boolean[6] == true && layerLightning == null)
	    		{
	    			layerLightning = new LightiningAnimClass();
	    			scene.addChild(layerLightning, 1);
	    		}
	    		if(modes_boolean[2] && layerNightMode == null)
	    		{
	    			layerNightMode = new NightMode();
	    			scene.addChild(layerNightMode);
	    		}
	    		if(modes_boolean[0] && layerEveningMode == null)
	    		{
	    			layerEveningMode = new NightMode();
	    			scene.addChild(layerEveningMode);
	    		}
				return 1;
			}
	    	
			public int CleanMemory()
			{
	    		CCSpriteFrameCache.sharedSpriteFrameCache().removeAllSpriteFrames();
    			CCTextureCache.sharedTextureCache().removeUnusedTextures();
    			scene.removeSelf();
    			if(PREVIOUSTHEME != 0)
    			{				
    				for(int i = 1; i <= pListCount  ; i ++)
    				{
    					CCSpriteFrameCache.sharedSpriteFrameCache().removeSpriteFrame("theme"+PREVIOUSTHEME+"_"+pListCount+".plist");
    					CCSpriteFrameCache.sharedSpriteFrameCache();
    					CCSpriteFrameCache.purgeSharedSpriteFrameCache();
    				}
    			}   			
    			
    			if(!isFirstClearSprites)
    			{
		    		for(int i=0; i <= previousThemeImageCount; i++)
		    			CCSpriteFrameCache.sharedSpriteFrameCache().removeSpriteFrame(String.format("theme"+PREVIOUSTHEME+"%d.png", i));
//		    		System.out.println("PREVIOUSTHEME is ------------------------------------->>>"+PREVIOUSTHEME);
//		    		System.out.println("previousThemeImageCount is --------------------------------------->>>"+previousThemeImageCount);
	    		}
    			
	    		isFirstClearSprites = false;
        		PREVIOUSTHEME = THEMECOUNT;	
        		previousThemeImageCount = ThemeImageCount;
        		
    			System.gc();
				return 1;
	    	}	
			
			@Override
				public void onExit() {
				// TODO Auto-generated method stub
				super.onExit();
				cleanup();
			}
	    }

	 	@Override
		public boolean ccTouchesBegan(MotionEvent event) 
	 	{
	 		/*bucket_x = (int)event.getX();
			bucket_y = (int)event.getY();
			System.out.println(" ------- ccTouchesBEGAN ----------- "+"buttonTouchX -> "+bucket_x+" buttonTouchY -> "+bucket_y);*/
			isTouchedDown = false;
			
			return true;
		}
	 	
	 	@Override
		public boolean ccTouchesMoved(MotionEvent event) 
	 	{
	 		/*bucket_x = (int)event.getX(); 
	 		bucket_y = (int)event.getY();*/
			isTouchedDown = true;			
			dis_Appear();
			
			return false;
		}				
		
		@Override
		public boolean ccTouchesEnded(MotionEvent event) 
		{
			/*bucket_x = (int)event.getX();
			bucket_y = (int)event.getY();
			System.out.println(" ----------- ccTouchesENDED -------- "+"buttonTouchX -> "+bucket_x+" buttonTouchY -> "+bucket_y);*/
			if(event.getAction() == MotionEvent.ACTION_UP && !isTouchedDown)
			{
				new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//dis_Appear();
						System.out.println("Calling disAppear from ccTouchesEnded %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						disAppear();	
						dis_Appear();
						}
				}).start();
			}			
			return true;
		}
		
		@Override
		public boolean ccTouchesCancelled(MotionEvent event) 
		{
			/*bucket_x = (int)event.getX();
			bucket_y = (int)event.getY();
			System.out.println("------------- ccTouchesCANCELLED -----------"+"buttonTouchX "+bucket_x+" buttonTouchY "+bucket_y);*/
			return true;
		}	
		
	
	class CountDown extends CountDownTimer
	{		
		private boolean isImage = true;		
		CountDown(long startTime, long interval)
		{
			super(startTime, interval);
//			System.out.println("Came inside CountDown class ----------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
		@Override
		public void onFinish()
		{								
			if(!pad_int.equals("00"))
			{
//				System.out.println("Came inside onFinish method if ----------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				minute_int -= 1;
				pad_int = pad(minute_int);	
//				System.out.println("pad_int is -----------------------&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+pad_int);
				minute.setText(pad_int);					
				startTime = 60;
				countDown = new CountDown(startTime * 1000, interval);
				countDown.start();
			}
			else
			{					
				second.setText("00");					
				new AlertDialog.Builder(FeelIt.this).setMessage("Press Exit button to enter again...!")
				.setPositiveButton("Exit", new DialogInterface.OnClickListener() {						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						digital_clock.startAnimation(fadeOut);
						digital_clock.setVisibility(View.GONE);
						editor.putBoolean("set", false);
						editor.commit();
					}
				}).create().show();
			}
		}

		@Override
		public void onTick(long millisUntilFinished)
		{				
			String temp = "" + pad(millisUntilFinished / 1000);
//			System.out.println("Inside onTick method now---------------->>>>>>>>>>>>>>>>>>>>>>"+temp);
			second.setText(temp);		
			
			if(isImage )
				clock_image.setBackgroundResource(R.drawable.clock_icon_hvr);
			else
				clock_image.setBackgroundResource(R.drawable.clock_icon);
			isImage = !isImage;					
		}
	}
	
	private String pad(long c) 
	{		
		if(c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}	
	
	class UploadTask extends AsyncTask<Void, Void, String> 
	{
//		private ProgressDialog pDialog = null;
		
		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
//			pDialog = ProgressDialog.show(FeelIt.this, "Loading", "Please wait...", true);
		}		
		
		@Override
		protected String doInBackground(Void... unsued) 
		{
			//if(isInonTouch)
			{
				try 
				{
					/*new Thread(new Runnable() {					
						@Override
						public void run() {
							// TODO Auto-generated method stub
*/					
					/*if(CCDirector.sharedDirector().getRunningScene().isRunning())
						CCDirector.sharedDirector().replaceScene(GameScene.scene());
					else						
						CCDirector.sharedDirector().runWithScene(GameScene.scene());*/
					onLowMemory();						
						/*}
					}).start();*/				
				} 
				catch(Exception e) 
				{				
//					if(pDialog.isShowing())				
//						pDialog.dismiss();				
					return sResponse1;
				}
			}
			return sResponse1;			
		}		

		@Override
		protected void onPostExecute(String sResponse) 
		{
//			System.out.println(" responce is @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+sResponse);
			//if(isInonTouch)
			{
				try 
				{
					if(handle_fall != null)
						handle_fall.sendEmptyMessage(0);
					
					if(CCDirector.sharedDirector().getRunningScene().isRunning())
					{
//						System.out.println(" in IF CCDirector.sharedDirector().getRunningScene().isRunning() --->>> "+CCDirector.sharedDirector().getRunningScene().isRunning());
						CCDirector.sharedDirector().replaceScene(GameScene.scene());
					}
					else
					{
//						System.out.println(" in ELSE CCDirector.sharedDirector().getRunningScene().isRunning() --->>> "+CCDirector.sharedDirector().getRunningScene().isRunning());
						CCDirector.sharedDirector().runWithScene(GameScene.scene());
					}
					
					CCDirector.sharedDirector().resume();
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		// TODO Auto-generated method stub	
		boolean move = false;
		isInOnClick = true;
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			dis_Appear();
			isDown = false;
			if(v.getId() == R.id.analogclock_1 || v.getId() == R.id.analogclock_2 || v.getId() == R.id.analogclock_3 || v.getId() == R.id.analogclock_4 || v.getId() == R.id.analogclock_5 || v.getId() == R.id.analogclock_6)
			{
				move = startDrag(v);
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP && !isDown)
		{
			dis_Appear();
			isInonTouch = true;
			onClickORonTouch_selection(v);
			move = true;
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			dis_Appear();
			isDown = true;
		}	
		
		return move;
	}
	
	public boolean startDrag (View v)
	{
	    // Let the DragController initiate a drag-drop sequence.
	    // I use the dragInfo to pass along the object being dragged.
	    // I'm not sure how the Launcher designers do this.
	    Object dragInfo = v;
	    mDragController.startDrag (v, mDragLayer, dragInfo, DragController.DRAG_ACTION_MOVE);
	    return true;
	}
	
}