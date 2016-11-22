package com.mygdx.colors.utils;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.states.AnimatedState;

public class AnimatedStateFactory {
	public static AnimatedState getAnimatedState(GameEntity entity, String stateName, String spritesName, float frameDuration, boolean isLoop){
		AnimatedState animatedState = 
			new AnimatedState(
				stateName, 
				entity, 
				getAnimation(spritesName, frameDuration, isLoop), 
				isLoop
			);

		return animatedState;
	}
	
	public static AnimatedState getAnimatedState(GameEntity entity, TextureAtlas textureAtlas, String stateName, String spritesName, float frameDuration, boolean isLoop){
		AnimatedState animatedState = 
			new AnimatedState(
				stateName, 
				entity, 
				getAnimation(textureAtlas, spritesName, frameDuration, isLoop), 
				isLoop
			);
		
		return animatedState;
	}
	
	private static Animation getAnimation(String spritesName, float frameDuration, boolean isLoop){
		Animation animation = new Animation(spritesName, frameDuration);
		animation.setLoop(isLoop);
		return animation;
	}
	
	private static Animation getAnimation(TextureAtlas textureAtlas, String spriteNames, float frameDuration, boolean isLoop){
		Animation animation = new Animation(textureAtlas, spriteNames, frameDuration);
		animation.setLoop(isLoop);
		return animation;
	}
}
