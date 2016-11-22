package com.mygdx.colors.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.utils.Animation;

public class AnimatedState extends EntityState{
		
	private final Animation stateAnimation;
	private final boolean isLoop;
	private boolean isFinished;
	private Sprite currentSprite;
	
	public AnimatedState(String name, GameEntity entity, Animation stateAnimation, boolean isLoop){
		super(name, entity);
		this.isFinished     = false;
		this.stateAnimation = stateAnimation;
		this.isLoop         = isLoop;
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

	public boolean isFinished(){
		return isFinished;
	}
	
	public void restart(){
		stateAnimation.restart();
	}

	@Override
	public void setFinished(boolean isFinished) {
	}
}
