package com.icrg.waterfall.animations;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

import com.icrg.waterfall.FeelIt;

public class Rainbow extends CCLayer{
	CCSprite spriteRainbow = null;
	public Rainbow(){
		if(spriteRainbow == null){
			spriteRainbow = CCSprite.sprite("rainbow_2.png");
		}	
		if(spriteRainbow != null){
			addChild(spriteRainbow);	
			spriteRainbow.setAnchorPoint(0f, 0f);
			spriteRainbow.setScaleX(FeelIt.SCREEN_RATIO_X);
			spriteRainbow.setScaleY(FeelIt.SCREEN_RATIO_Y);	
			RainAnimFadeIn();
		}
	}
	public void RainAnimFadeIn(){
		spriteRainbow.setOpacity(0);
		CCFadeIn _RainAnimFadeIn =  CCFadeIn.action(20f);
		CCDelayTime _RainAnimDelayTime = CCDelayTime.action(3f);
		CCCallFunc _RainAnimCallFun = CCCallFunc.action(this, "RainAnimFadeIn");	
		CCFadeOut _RainAnimFadeOut =  CCFadeOut.action(20f);
		CCSequence _lightSequence = CCSequence.actions(_RainAnimFadeIn, _RainAnimFadeOut,_RainAnimDelayTime,_RainAnimCallFun);
		spriteRainbow.runAction(_lightSequence);
	}
}
