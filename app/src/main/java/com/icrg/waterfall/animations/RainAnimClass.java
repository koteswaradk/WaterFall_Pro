package com.icrg.waterfall.animations;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;

import com.icrg.waterfall.FeelIt;

public class RainAnimClass extends CCLayer{
	 CCSprite rainSprite = null;
	 CCAnimation animationRain = null;
	 CCAnimate actionRain = null;
	public RainAnimClass() {
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("rain_1.plist");
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("rain_2.plist");
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("rain_3.plist");
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("rain_4.plist");
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("rain_5.plist");
    		rainAnim();
	}
	public void rainAnim(){
		if(rainSprite == null){
			rainSprite = CCSprite.sprite("rain_0.png",true);
			this.addChild(rainSprite,1);
		}
		
		rainSprite.setPosition(CGPoint.make(0,0f));
		rainSprite.setAnchorPoint(0f, 0f);
		rainSprite.setScaleX(FeelIt.THEME_SCALE_X);
		rainSprite.setScaleY(FeelIt.THEME_SCALE_Y);

		ArrayList<CCSpriteFrame> animFrames = new ArrayList<CCSpriteFrame>();
		for(int i = 0; i <= 29; i++) {
			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().spriteFrameByName(String.format("rain_%d.png", i));
			animFrames.add(frame);
		}
		CCAnimation animation = CCAnimation.animation("Rain Fall", 0.06f, animFrames);
		
		if(rainSprite != null)
			rainSprite.runAction(CCRepeatForever.action(CCAnimate.action(animation, false)));
   	}
}
