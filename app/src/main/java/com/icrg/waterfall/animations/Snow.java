package com.icrg.waterfall.animations;

import org.cocos2d.layers.CCLayer;

import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4F;

import com.icrg.waterfall.FeelIt;

public class Snow extends CCLayer
{
	static CCParticleSystem emitter = null;
	public Snow()
	{
		emitter = null;
		addSnow();
	}
	
	public void addSnow()
	{		
		if( emitter == null )
			emitter = CCParticleSnow.node(100);
		
		if(emitter != null )
		{
			if(FeelIt.WIDTH >= 480 || FeelIt.HEIGHT >= 800)
			{
				emitter.setLife(5);
				emitter.setLifeVar(1);
				// speed of particles
				emitter.setSpeed(100);
				emitter.setSpeedVar(30);
				emitter.setEmissionRate(emitter.getTotalParticles()/emitter.getLife());
			}
			else if(FeelIt.WIDTH >= 320 || FeelIt.HEIGHT >= 480)
			{
//				System.out.println(" in 320 X 480 screen resolution ");
				emitter.setLife(3.5f);
				emitter.setLifeVar(1);
				// speed of particles
				emitter.setSpeed(80);
				emitter.setSpeedVar(30);
				emitter.setEmissionRate(emitter.getTotalParticles()/emitter.getLife());
			}
			else if(FeelIt.WIDTH >= 240 || FeelIt.HEIGHT >= 320)
			{
				emitter.setLife(3f);
				emitter.setLifeVar(1);
				// speed of particles
				emitter.setSpeed(70);
				emitter.setSpeedVar(30);
				emitter.setEmissionRate(emitter.getTotalParticles()/emitter.getLife());
			}
			ccColor4F startColor = emitter.getStartColor();
			startColor.r = 0.9f;
			startColor.g = 0.9f;
			startColor.b = 0.9f;
			emitter.setStartColor(startColor);
			
			// gravity
			emitter.setGravity(CGPoint.ccp(0,-10));
			
			ccColor4F startColorVar = emitter.getStartColorVar();
			startColorVar.b = 0.1f;
			emitter.setStartColorVar(startColorVar);
	
			emitter.setTexture(CCTextureCache.sharedTextureCache().addImage("Snow.png"));
	
			emitter.setPosition(FeelIt.WIDTH/2, FeelIt.HEIGHT);
		}
		addChild(emitter, 10);
	}
}