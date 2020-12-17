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

public class BirdFlyAnimClass extends CCLayer{
	CCSprite spriteBirdFly1 = null;
	CCSprite spriteBirdFly2 = null;
	CCSprite spriteBirdFly3 = null;
	boolean isRight = true;
	ArrayList<CCSpriteFrame> animFrames = null;
	static int noOfBurds = 3;
	private Random randomParm;
  	Object sender = null;
	public BirdFlyAnimClass() {
		randomParm = new Random();
//		System.out.println("FeelIt.WIDTH-->> "+FeelIt.WIDTH + "FeelIt.HEIGHT--->> " +FeelIt.HEIGHT+ "SCREEN_RATIO_X::: "+FeelIt.SCREEN_RATIO_X);
		birdFlyAnimatinLoading1();
		birdFlyAnimatinLoading2();
		birdFlyAnimatinLoading3();
	}
	public void birdFlyAnimatinLoading1(){
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("BirdFly.plist");
 		
 		spriteBirdFly1 = CCSprite.sprite("bird_0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteBirdFly1.setPosition(FeelIt.WIDTH, 0-200); 
 		
 		spriteBirdFly1.setScaleX(0.5f);
 		spriteBirdFly1.setScaleY(0.5f);
 		spriteBirdFly1.setAnchorPoint(0.5f, 0.5f);
 		birtdFlyanim(spriteBirdFly1);
	}
	public void birdFlyAnimatinLoading2(){
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("BirdFly.plist");
 		spriteBirdFly1 = CCSprite.sprite("bird_0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteBirdFly1.setPosition(0+200, 0+200); 

 		spriteBirdFly1.setScaleX(0.6f);
 		spriteBirdFly1.setScaleY(0.6f);
 		spriteBirdFly1.setAnchorPoint(0.5f, 0.5f);
 		birtdFlyanim(spriteBirdFly1);
	}
	public void birdFlyAnimatinLoading3(){
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("BirdFly.plist");
 		
 		spriteBirdFly1 = CCSprite.sprite("bird_0.png", true);//"bird_0.png"  comes from your .plist file
 		spriteBirdFly1.setPosition(0-200, 0-200); 
 		spriteBirdFly1.setScaleX(0.4f);
 		spriteBirdFly1.setScaleY(0.4f);
 		spriteBirdFly1.setAnchorPoint(0.5f, 0.5f);
 		birtdFlyanim(spriteBirdFly1);
	}
	public void birtdFlyanim(CCSprite bird)
	{
		CCSpriteSheet spritesheet = CCSpriteSheet.spriteSheet("BirdFly.png", 15);//spriteSheetWithFile:@"animations/grossini.png"];
 		spritesheet.addChild(bird,2);
 		this.addChild(spritesheet,1);
             //The name of your .png and .plist must be the same name
 		if(animFrames ==null){
	 		animFrames = new ArrayList<CCSpriteFrame>();
	 		for(int i = 0; i < 15; i++) {
	 			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().spriteFrameByName(String.format("bird_%d.png", i));
	 			animFrames.add(frame);
	 		}
 		}
 		CCAnimation animation = CCAnimation.animation("ButterFly", 0.02f, animFrames);
 		bird.runAction(CCRepeatForever.action(CCAnimate.action(animation, true)));
 		BirdFlyMovement(bird);
	}
	 	
	private static int showRandomInteger1(int aStart, int aEnd, Random aRandom){
 	    //get the range, casting to long to avoid overflow problems
 	    long range = (long)aEnd - (long)aStart + 1;
 	    // compute a fraction of the range, 0 <= frac < range
 	    long fraction = (long)(range * aRandom.nextDouble());
 	    int randomNumber =  (int)(fraction + aStart);    
		return randomNumber;
 	  }
	private static int showRandomInteger2(int aStart, int aEnd, Random aRandom){
 	    //get the range, casting to long to avoid overflow problems
 	    long range = (long)aEnd - (long)aStart + 1;
 	    // compute a fraction of the range, 0 <= frac < range
 	    long fraction = (long)(range * aRandom.nextDouble());
 	    int randomNumber =  (int)(fraction + aStart);    
		return randomNumber;
 	  }
	public void BirdFlyMovement(Object sender)
    { 
		CCSprite birdAction = (CCSprite)sender;
 		CCBezierConfig birdmovepath1 = new CCBezierConfig();
 		float tempX = (float)(showRandomInteger1(0,(int)(FeelIt.WIDTH),randomParm));
 		float tempY = (float)(showRandomInteger1(0,(int)(FeelIt.HEIGHT),randomParm));
 		birdmovepath1.controlPoint_1 = CGPoint.make(tempX,tempY);
 		
 		tempX = (float)(showRandomInteger2(0,(int)(FeelIt.WIDTH),randomParm));
 		tempY = (float)(showRandomInteger2(0,(int)(FeelIt.HEIGHT),randomParm));
 		birdmovepath1.controlPoint_2 = CGPoint.make(tempX,tempY);
 		
 		if(isRight){
// 			System.out.println(" isRight ------------------------------------------------");
 			birdAction.flipX_ = false;
	 		tempX = (float)(showRandomInteger1((int)FeelIt.WIDTH,(int)(FeelIt.WIDTH+FeelIt.WIDTH),randomParm));
	 		tempY = (float)(showRandomInteger1((int)FeelIt.WIDTH,(int)(FeelIt.HEIGHT+FeelIt.HEIGHT),randomParm));
	 		isRight = false;
 		}
 		else
 		{
// 			System.out.println(" isleft ------------------------------------------------");
 			birdAction.flipX_ = true;
 			tempX = (float)(showRandomInteger1(0,(int)(FeelIt.WIDTH+FeelIt.WIDTH),randomParm));
 			tempX -=  FeelIt.WIDTH;
	 		tempY = (float)(showRandomInteger1(0,(int)(FeelIt.HEIGHT+FeelIt.HEIGHT),randomParm));
	 		tempY -= FeelIt.HEIGHT;
	 		isRight = true;
 		}
// 		System.out.println(" tempX --->> "+tempX +"temp --->>>" +tempY);
 		birdmovepath1.endPosition = CGPoint.make(tempX,tempY);
 		CCBezierTo action_bezier = CCBezierTo.action(6, birdmovepath1);
		CCSequence sequence_HarvestValueMove = CCSequence.actions(action_bezier, CCCallFuncN.action(this, "BirdFlyMovement"));
		birdAction.runAction(sequence_HarvestValueMove);
    }
}
