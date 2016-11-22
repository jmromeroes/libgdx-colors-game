package com.mygdx.colors.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class StaticState extends EntityState{
	
	private final Sprite sprite;
	
	public StaticState(String name, GameEntity gameEntity, Sprite sprite){
		super(name, gameEntity);
		
		this.sprite = sprite;
	}

	public StaticState(String name, GameEntity gameEntity, String spriteName, int index){
		super(name, gameEntity);
		
		this.sprite = Content.getInstance().getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS)
                .createSprite(spriteName, index);
	}

	public StaticState(String name, GameEntity gameEntity, String spriteName){
		super(name, gameEntity);
		
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
	public void setFinished(boolean isFinished){}
}
