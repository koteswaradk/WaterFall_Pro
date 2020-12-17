package com.icrg.waterfall.animations;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccBlendFunc;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import com.icrg.waterfall.FeelIt;
import com.icrg.waterfall.R;


public class CollorNightMode extends CCLayer implements CCTouchDelegateProtocol
{
	static CCSprite blendSprite = null;
	static CCSprite nightModeSprite = null;
	static CCParticleSystem emitter = null;
	static Context context = null;
	static CCSprite _sliderKnob = null;
    static CCSprite _collorBar = null;
    static CCSprite _sampleBG = null;
    static ccColor4B _collors = null;
    static CCColorLayer _colorLayer = null;
    
    private Bitmap bmp; 
    
	public CollorNightMode(Context _context){
		if(context == null)
			context = _context;
		CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 1, false);
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("NightMode.plist");
		if(FeelIt.Red == null)
			pixelRGB();
		addSnow();	
		
	}
	
	public void addSnow(){
		//theme_1_black
		String blackPatch = "theme_"+FeelIt.THEMECOUNT+"_black";
		blendSprite = CCSprite.sprite(blackPatch+".png",true);
		blendSprite.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/2);
		addChild(blendSprite);
		blendSprite.setScaleX(FeelIt.SCREEN_RATIO_X);
		blendSprite.setScaleY(FeelIt.SCREEN_RATIO_Y);
		
		//theme_1_blue
		String bluePatch = "theme_"+FeelIt.THEMECOUNT+"_blue";
		nightModeSprite = CCSprite.sprite(bluePatch+".png",true);
		nightModeSprite.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/2);
		addChild(nightModeSprite);
		nightModeSprite.setScaleX(FeelIt.SCREEN_RATIO_X);
		nightModeSprite.setScaleY(FeelIt.SCREEN_RATIO_Y);
		
		
		_collorBar = new CCSprite(FeelIt.resPath+"/colorsource.png");
		addChild(_collorBar, 2);
		_collorBar.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/3);
		
		_sliderKnob = new CCSprite("sliderknob.png");
		addChild(_sliderKnob, 3);
		_sliderKnob.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/3-_collorBar.getContentSize().height/2);
		_sliderKnob.setScaleX(FeelIt.SCREEN_RATIO_X);
		_sliderKnob.setScaleX(FeelIt.SCREEN_RATIO_Y);
		

		ccBlendFunc blendFunc_ = new ccBlendFunc(GL10.GL_ZERO, GL10.GL_SRC_COLOR);
		
		nightModeSprite.setBlendFunc(blendFunc_);
		nightModeSprite.setOpacityModifyRGB(false);
		nightModeSprite.setColor(new ccColor3B(FeelIt.Red.length/2,FeelIt.Green.length/2,FeelIt.Blue.length/2));
		
//		_collors = new ccColor4B(0, 0, 0, 150);
//		_colorLayer = CCColorLayer.node(_collors);// layerWithColor:ccc4(0, 0, 0, 128)]; [self addChild:colorLayer z:0];
//		addChild(_colorLayer,1);
		
	}
	 CGPoint previousLocation = CGPoint.zero();
		@Override
		public boolean ccTouchesBegan(MotionEvent event) {
			_collorBar.setVisible(true);
			_sliderKnob.setVisible(true);
//			System.out.println(" --->>>> ccTouchesBegan <<<<-------- ");
			return true;
		}
		@Override
		public boolean ccTouchesCancelled(MotionEvent event) {
//			System.out.println(" --->>>> ccTouchesCancelled <<<<-------- ");
			return false;
		}
		@Override
		public boolean ccTouchesEnded(MotionEvent event) {
//			System.out.println(" --->>>> ccTouchesEnded <<<<-------- ");
//			scene.removeChild(_colorLayer, true);
//			scene.addChild(_colorLayer,1);
			_collorBar.setVisible(false);
			_sliderKnob.setVisible(false);
			return true;
		}
		@Override
		public boolean ccTouchesMoved(MotionEvent event) {
			CGPoint convertedLocation = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
			CGPoint start = convertedLocation;
			CGPoint end = previousLocation;
			
			 
			 float distance = CGPoint.ccpDistance(start, end);
	         if (distance > 1 && ( event.getX() >  ((FeelIt.WIDTH -((_collorBar.getContentSize().width)))/2) 
	        		 			&& event.getX() <  FeelIt.WIDTH -((FeelIt.WIDTH -((_collorBar.getContentSize().width)))/2))) 
	         {
	        	 int arrayvalue = (int)((end.x*(FeelIt.Red.length/_collorBar.getContentSize().width))-((FeelIt.WIDTH-_collorBar.getContentSize().width)/2));
//	        	 System.out.println(" arrayvalue ---->>> "+ arrayvalue);
	             int d = (int) distance;
	             for (int i = 0; i < d; i++) {
	                 float difx = end.x - start.x;
	                 float dify = end.y - start.y;
	                 float delta = (float) i / distance;
	                 float possitionX = start.x + (difx * delta);
	                 float possitionY =  FeelIt.HEIGHT/3 - _collorBar.getContentSize().height/2;//start.y + (dify * delta);
//	                 System.out.println("arrayvalue--->> "+arrayvalue+" Red.length __>>> "+Red.length);
	                 if(arrayvalue >= (FeelIt.Red.length)){
	                	 arrayvalue = FeelIt.Red.length-5;
	                 }
	                 else if(arrayvalue <= 0)
	                	 arrayvalue = 1;
	                 nightModeSprite.setColor(new ccColor3B(FeelIt.Red[(arrayvalue)],FeelIt.Green[(arrayvalue)],FeelIt.Blue[(arrayvalue)]));
	         		 nightModeSprite.setOpacityModifyRGB(false);
	                 nightModeSprite.setOpacity(50);
	                 _sliderKnob.setPosition(possitionX,possitionY);
	             }
	             previousLocation = convertedLocation;
	         }
			return CCTouchDispatcher.kEventHandled;
		}
		
		public void pixelRGB(){
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.colorsource);
			int picw = bmp.getWidth();
			int pich = bmp.getHeight();
			FeelIt.Red =  new int[picw] ;
			FeelIt.Green = new int[picw];
			FeelIt.Blue = new int[picw];
			int[] pix = new int[picw * pich];
			bmp.getPixels(pix, 0, picw, 0, 0, picw, pich);

		    int R, G, B,Y;
		    for (int y = pich/2; y < pich; y++)
		   	{
			    for (int x = 0; x < picw; x++)
		        {
			        int index = y * picw + x;
			        FeelIt.Red[x] = ((pix[index] >> 16) & 0xff);     //bitwise shifting
			        FeelIt.Green[x] = ((pix[index] >> 8) & 0xff);
			        FeelIt.Blue[x] = (pix[index] & 0xff);
//			        System.out.println("index"+index+"Red"+Red[x]+"Green"+Green[x]+"Blue"+Blue[x]+"picw--->> "+picw);
		       	}
			    break;
		   	}
		}
		
		/*public void deactiveTouch()
		{
			CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
			CCTouchDispatcher.sharedDispatcher().removeDelegate(layerCollorNightMode);
		}*/
}