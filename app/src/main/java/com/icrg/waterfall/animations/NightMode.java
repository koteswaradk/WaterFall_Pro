package com.icrg.waterfall.animations;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.types.ccBlendFunc;

import com.icrg.waterfall.FeelIt;


public class NightMode extends CCLayer
{
	static CCSprite blendSprite = null;
	static CCSprite nightModeSprite = null;
	static CCParticleSystem emitter = null;
	public NightMode()
	{
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("NightMode.plist");
		addSnow();
	}
	
	public void addSnow(){
		//theme_1_black
		String blackPatch = "theme_"+FeelIt.THEMECOUNT+"_black";
		blendSprite = CCSprite.sprite(blackPatch+".png",true);
		blendSprite.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/2);
		addChild(blendSprite, 0);
//		blendSprite.setOpacity(255);
		blendSprite.setScaleX(FeelIt.SCREEN_RATIO_X);
		blendSprite.setScaleY(FeelIt.SCREEN_RATIO_Y);
		
		//theme_1_blue
		String bluePatch = "theme_"+FeelIt.THEMECOUNT+"_blue";
		nightModeSprite = CCSprite.sprite(bluePatch+".png",true);
		nightModeSprite.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/2);
		addChild(nightModeSprite, 1);
		nightModeSprite.setScaleX(FeelIt.SCREEN_RATIO_X);
		nightModeSprite.setScaleY(FeelIt.SCREEN_RATIO_Y);
		ccBlendFunc blendFunc_ = new ccBlendFunc(GL10.GL_ZERO, GL10.GL_SRC_COLOR);
		nightModeSprite.setBlendFunc(blendFunc_);
//		nightModeSprite.setOpacity(255);
	}
}