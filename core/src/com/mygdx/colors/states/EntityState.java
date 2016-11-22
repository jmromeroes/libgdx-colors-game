package com.mygdx.colors.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.entities.GameEntity;

public abstract class EntityState{
	
	private final GameEntity entity;
	private final String name;
	
	public EntityState(String name, GameEntity entity){
		this.entity = entity;
		this.name = name;
	}

	public GameEntity getEntity() {
		return entity;
	}

	public void render(SpriteBatch spriteBatch){
		Sprite currentSprite = getSprite();
		
		currentSprite.setPosition(getEntity().getPosition().x, getEntity().getPosition().y);
		currentSprite.draw(spriteBatch);
		currentSprite.setAlpha(getEntity().getAlpha());
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract void setFinished(boolean isFinished);
	public abstract void update(float delta);
	public abstract Sprite getSprite();
	
	public void restart(){};
}
