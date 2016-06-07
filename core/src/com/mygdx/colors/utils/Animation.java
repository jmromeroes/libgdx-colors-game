package com.mygdx.colors.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class Animation {
	
	private Array<Sprite> skeleton;
	private float frameDuration;
	private int currentSprite;
	private boolean isLoop;
	private float timeElapsed;
	private boolean isFinished;
	
	public Animation(TextureAtlas textureAtlas, String spritesName, float frameDuration){
		this.skeleton = textureAtlas.createSprites(spritesName);
		this.frameDuration = frameDuration;
	}
	
	public Animation(String spritesName, float frameDuration){
		this.skeleton = Content.getInstance().getTextureAtlas("mainTextureAtlas")
											 .createSprites(spritesName);
		this.frameDuration = frameDuration;
	}
	
	public void update(float delta){
		timeElapsed += delta;
	}
	
	public Sprite getSprite(){
		if(timeElapsed > frameDuration){

			if(isLoop){
				currentSprite = (currentSprite == skeleton.size-1)?0:currentSprite+1;
			}else{
				currentSprite = (currentSprite == skeleton.size-1)?skeleton.size-1:currentSprite+1;
			}
			
			if(isFinished == false){
				isFinished = (currentSprite == skeleton.size-1);
			}
			
			timeElapsed = 0;
		}
		return skeleton.get(currentSprite);
	}
	
	public boolean isAnimationFinished(){
		return isFinished;
	}

	public Array<Sprite> getSkeleton() {
		return skeleton;
	}

	public void setSkeleton(Array<Sprite> skeleton) {
		this.skeleton = skeleton;
	}

	public float getFrameDuration() {
		return frameDuration;
	}

	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}

	public int getCurrentSprite() {
		return currentSprite;
	}

	public void setCurrentSprite(int currentSprite) {
		this.currentSprite = currentSprite;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public void restart(){
		setCurrentSprite(0);
	}
}
