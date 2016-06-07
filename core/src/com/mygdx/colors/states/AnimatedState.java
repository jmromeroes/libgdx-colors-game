package com.mygdx.colors.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.utils.Animation;

public class AnimatedState extends EntityState{
		
	private Animation stateAnimation;
	private boolean isLoop;
	private boolean isFinished;
	private Sprite currentSprite;
	
	public AnimatedState(String name,GameEntity entity){
		super(name, entity);
		this.isFinished = false;
	}	

	public void setStateAnimation(Animation stateAnimation){
		this.stateAnimation = stateAnimation;
	}
	
	public Animation getStateAnimation(){
		return stateAnimation;
	}
	
	public void update(float delta){
		stateAnimation.update(delta);
		
		if(stateAnimation.isAnimationFinished()){
			isFinished=true;
		}
	}
	
	public Sprite getSprite(){
		return stateAnimation.getSprite();
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public boolean isFinished(){
		return isFinished;
	}
	
	public void setFinished(boolean isFinished){
		this.isFinished = isFinished;
	}
	
	public void restart(){
		stateAnimation.restart();
	}
}
