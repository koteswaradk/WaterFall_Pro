package com.icrg.waterfall.animations;

import java.util.ArrayList;
import java.util.Random;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCBezierTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.CGPoint;

import com.icrg.waterfall.FeelIt;

public class ButterFlyAnimClass extends CCLayer{
	CCSprite spriteButterFly1 = null,spriteButterFly2 = null,spriteButterFly3 = null;
	ArrayList<CCSpriteFrame> animButterFlyFrames = null;
	private Random randomParm;
  	Object sender = null;
  	boolean isRight = true;
	public ButterFlyAnimClass() {
		randomParm = new Random();
		butterFlyAnimatinLoading2();
		butterFlyAnimatinLoading3();
		butterFlyAnimatinLoading1();
	}
	
	public void butterFlyAnimatinLoading1(){
 		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("ButterFlyTexture.plist");
 		spriteButterFly1 = CCSprite.sprite("butterfly0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteButterFly1.setPosition(FeelIt.WIDTH, 0-200);
 		spriteButterFly1.setScaleX(1f);
 		spriteButterFly1.setScaleY(1f);
 		spriteButterFly1.setAnchorPoint(0.5f, 0.5f);
 		butterFlyRunAnim(spriteButterFly1);
 	}
	public void butterFlyAnimatinLoading2(){
 		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("ButterFlyTexture.plist");
 		spriteButterFly2 = CCSprite.sprite("butterfly0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteButterFly2.setPosition(0+290, 0+200); 
 		spriteButterFly2.setScaleX(0.9f);
 		spriteButterFly2.setScaleY(0.9f);
 		spriteButterFly2.setAnchorPoint(0.5f, 0.5f);
 		butterFlyRunAnim(spriteButterFly2);
 	}

	public void butterFlyAnimatinLoading3(){
 		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("ButterFlyTexture.plist");
 		spriteButterFly3 = CCSprite.sprite("butterfly0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteButterFly3.setPosition(0-200, 0-200); 
 		spriteButterFly3.setScaleX(1.1f);
 		spriteButterFly3.setScaleY(1.1f);
 		spriteButterFly3.setAnchorPoint(0.5f, 0.5f);
 		butterFlyRunAnim(spriteButterFly3);
 	}
 	public void butterFlyRunAnim(CCSprite butterfly){
 		
 		CCSpriteSheet spritesheet = CCSpriteSheet.spriteSheet("ButterFlyTexture.png", 10);//spriteSheetWithFile:@"animations/grossini.png"];
 		spritesheet.addChild(butterfly,3);
 		this.addChild(spritesheet,2);
 		animButterFlyFrames = new ArrayList<CCSpriteFrame>();
 		for(int i = 0; i <= 10; i++) {
 			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().spriteFrameByName(String.format("butterfly%d.png", i));
 			animButterFlyFrames.add(frame);
 		}
 		CCAnimation animation = CCAnimation.animation("ButterFly", 0.02f, animButterFlyFrames);
 		butterfly.runAction(CCRepeatForever.action(CCAnimate.action(animation, false)));
 		ButterFlyMovement(butterfly);
 	}
	private static int showRandomInteger1(int aStart, int aEnd, Random aRandom){
 	    long range = (long)aEnd - (long)aStart + 1;
 	    long fraction = (long)(range * aRandom.nextDouble());
 	    int randomNumber =  (int)(fraction + aStart);    
		return randomNumber;
 	  }
	private static int showRandomInteger2(int aStart, int aEnd, Random aRandom){
 	    long range = (long)aEnd - (long)aStart + 1;
 	    long fraction = (long)(range * aRandom.nextDouble());
 	    int randomNumber =  (int)(fraction + aStart);    
		return randomNumber;
 	  }
	public void ButterFlyMovement(Object sender)
    { 
		CCSprite ButterFlyAction = (CCSprite)sender;
 		CCBezierConfig birdmovepath1 = new CCBezierConfig();
 		float tempX = (float)(showRandomInteger1(0,(int)(FeelIt.WIDTH),randomParm));
 		float tempY = (float)(showRandomInteger1(0,(int)(FeelIt.HEIGHT),randomParm));
 		birdmovepath1.controlPoint_1 = CGPoint.make(tempX,tempY);
 		
 		tempX = (float)(showRandomInteger2(0,(int)(FeelIt.WIDTH),randomParm));
 		tempY = (float)(showRandomInteger2(0,(int)(FeelIt.HEIGHT),randomParm));
 		birdmovepath1.controlPoint_2 = CGPoint.make(tempX,tempY);
 		
 		if(isRight){
 			ButterFlyAction.flipX_ = true;
	 		tempX = (float)(showRandomInteger1((int)FeelIt.WIDTH,(int)(FeelIt.WIDTH+FeelIt.WIDTH),randomParm));
	 		tempY = (float)(showRandomInteger1((int)FeelIt.WIDTH,(int)(FeelIt.HEIGHT+FeelIt.HEIGHT),randomParm));
	 		isRight = false;
 		}
 		else
 		{
 			ButterFlyAction.flipX_ = false;
 			tempX = (float)(showRandomInteger1(0,(int)(FeelIt.WIDTH+FeelIt.WIDTH),randomParm));
 			tempX -=  FeelIt.WIDTH;
	 		tempY = (float)(showRandomInteger1(0,(int)(FeelIt.HEIGHT+FeelIt.HEIGHT),randomParm));
	 		tempY -= FeelIt.HEIGHT;
	 		isRight = true;
 		}
// 		System.out.println(" tempX --->> "+tempX +"temp --->>>" +tempY);
 		birdmovepath1.endPosition = CGPoint.make(tempX,tempY);
 		CCBezierTo action_bezier = CCBezierTo.action(6, birdmovepath1);
		CCSequence sequence_HarvestValueMove = CCSequence.actions(action_bezier, CCCallFuncN.action(this, "ButterFlyMovement"));
		ButterFlyAction.runAction(sequence_HarvestValueMove);
    }
}
