package com.icrg.waterfall.animations;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4F;

import com.icrg.waterfall.FeelIt;


public class FlowerSnow extends CCLayer
{
	static CCParticleSystem	emitterflower = null;
	public FlowerSnow(){
		emitterflower = null;
		addFlowerSnow();
	}
	
	public void addFlowerSnow(){
		
		if( emitterflower == null )
			emitterflower = CCParticleSnow.node(100);
		
		if(emitterflower != null ){
			if(FeelIt.WIDTH >= 480 || FeelIt.HEIGHT >= 800){
				emitterflower.setLife(5);
				emitterflower.setLifeVar(1);
				// speed of particles
				emitterflower.setSpeed(100);
				emitterflower.setSpeedVar(30);
				emitterflower.setEmissionRate(emitterflower.getTotalParticles()/emitterflower.getLife());
			}
			else if(FeelIt.WIDTH >= 320 || FeelIt.HEIGHT >= 480){
				emitterflower.setLife(3.5f);
				emitterflower.setLifeVar(1);
				// speed of particles
				emitterflower.setSpeed(80);
				emitterflower.setSpeedVar(30);
				emitterflower.setEmissionRate(emitterflower.getTotalParticles()/emitterflower.getLife());
			}
			else if(FeelIt.WIDTH >= 240 || FeelIt.HEIGHT >= 320){
				emitterflower.setLife(3f);
				emitterflower.setLifeVar(1);
				// speed of particles
				emitterflower.setSpeed(70);
				emitterflower.setSpeedVar(30);
				emitterflower.setEmissionRate(emitterflower.getTotalParticles()/emitterflower.getLife());
			}
			ccColor4F startColor = emitterflower.getStartColor();
			startColor.r = 0.9f;
			startColor.g = 0.9f;
			startColor.b = 0.9f;
			emitterflower.setStartColor(startColor);
			
			// gravity
			emitterflower.setGravity(CGPoint.ccp(0,-10));
			
			ccColor4F startColorVar = emitterflower.getStartColorVar();
			startColorVar.b = 0.1f;
			emitterflower.setStartColorVar(startColorVar);
	
			emitterflower.setTexture(CCTextureCache.sharedTextureCache().addImage("flower_1.png"));
	
			emitterflower.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT);
		}
		addChild(emitterflower, 10);
	}
}