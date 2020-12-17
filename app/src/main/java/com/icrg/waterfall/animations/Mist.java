package com.icrg.waterfall.animations;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCRenderTexture;
import org.cocos2d.types.ccBlendFunc;

import com.icrg.waterfall.FeelIt;

public class Mist extends CCLayer{
	CCRenderTexture mistRender = null;
	CCSprite spriteMist = null;
	public Mist(){
		mistRender = CCRenderTexture.renderTexture((int)FeelIt.WIDTH, (int)FeelIt.HEIGHT);
		mistRender.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT/2);
		this.addChild(mistRender);
		
		spriteMist = CCSprite.sprite("spritemist.png");
		spriteMist.setScale(1.8f);
		
		mistRender.begin();
		spriteMist.setPosition(0, 0);
		spriteMist.visit(null);
		spriteMist = CCSprite.sprite("spritemist2.png");
		
		spriteMist.setBlendFunc(new ccBlendFunc(0,(1111)));
	}
	public void MistLayer(){
		
	}
}
