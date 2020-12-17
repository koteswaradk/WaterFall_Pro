package com.icrg.waterfall.animations;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;

import com.icrg.waterfall.FeelIt;

public class LightiningAnimClass extends CCLayer{
	 CCSprite lightningSprite = null;
	 CCAnimation animationLightning = null;
	 CCAnimate actionLightning = null;
	 int countLight = 1;
	 int randomNumber=0;
	public LightiningAnimClass() {
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("lightning.plist");
    	ligtningAnim();
	}
	public void ligtningAnim(){
		System.out.println(" ---------------------------------- ligtningAnim -------------------------------");
		lightningSprite = CCSprite.sprite("lightning_"+showRandomIntegerRainbow()+".png",true);
		this.addChild(lightningSprite,1);
		
		lightningSprite.setPosition(CGPoint.make(0,0f));
		lightningSprite.setAnchorPoint(0f, 0f);
		lightningSprite.setScaleX(FeelIt.SCREEN_RATIO_X);
		lightningSprite.setScaleY(FeelIt.SCREEN_RATIO_Y);
			
		CCFadeOut _LightningFadeOut =  CCFadeOut.action(1f);
		CCDelayTime _LightningDelayTime = CCDelayTime.action(8f);
		CCCallFunc _LightningCallFun = CCCallFunc.action(this, "ligtningAnim");	
		CCCallFunc _RemovingLightningCallFun = CCCallFunc.action(this, "removieligtningAnim");
		CCSequence _lightSequence = CCSequence.actions(_LightningFadeOut, _LightningDelayTime,_RemovingLightningCallFun,_LightningCallFun);
		lightningSprite.runAction(_lightSequence);
   	}
	public void removieligtningAnim(){
		this.removeAllChildren(true);
	}
	private int showRandomIntegerRainbow(){
		int randomInt = 1;
		
		//note a single Random object is reused here
	    Random randomGenerator = new Random();
	    randomInt = randomGenerator.nextInt(7);
	    System.out.println("Generated : " + randomInt);
	    if(randomInt == 0){
	    	randomInt = 4;
	    }
		return randomInt;
 	  }
}
