package com.mygdx.colors.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class StaticState extends EntityState{
	
	private Sprite sprite;
	
	public StaticState(String name, GameEntity gameEntity){
		super(name, gameEntity);
	}

	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void setSprite(String spriteName, int index){
		this.sprite = Content.getInstance().getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS)
				                           .createSprite(spriteName, index);
	}
	
	public void setSprite(String spriteName){
		this.sprite = Content.getInstance().getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS)
				                           .createSprite(spriteName);
	}
	
	@Override
	public Sprite getSprite(){
		return sprite;
	}

	@Override
	public void update(float delta){
	}
	
	@Override
	public boolean isFinished(){
		return false;
	}
	
	@Override
	public void setFinished(boolean isFinished){}
}
