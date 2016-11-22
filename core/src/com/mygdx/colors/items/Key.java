package com.mygdx.colors.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public class Key extends GameEntity{
	
	public static final String MAIN = "hud_key_";
	
	private final StaticState mainState;
	private final PlayScreen playScreen;
	private final Sound sound;
	
	public Key(PlayScreen playScreen, Cell cell){
		super(false);
		sound = Content.getInstance().getSound("stars");
		this.playScreen = playScreen;
		
		setColor((String)cell.getTile().getProperties().get("color"));
		
		mainState = new StaticState(MAIN, this, MAIN+getColor());
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		
		setWidth(70);
		setHeight(50);
	}
	
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
		collisioning(gameEntity);
	}
	
	@Override
	public void collisioningLeft(GameEntity gameEntity){
		collisioning(gameEntity);
	}
	
	@Override
	public void collisioningUp(GameEntity gameEntity){
		collisioning(gameEntity);
	}
	
	@Override
	public void collisioningDown(GameEntity gameEntity){
		collisioning(gameEntity);
	}
	
	public void collisioning(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			ColorMan colorMan = (ColorMan) gameEntity;
			colorMan.addKey(getColor());
			setHidden(true);
			playScreen.addExplosion(getPosition().x+getWidth()/2, 
									getPosition().y+getHeight()/2, 
									"tile_"+getColor()+"_particle", 10);
			if(ColorsGame.soundEnabled)
				sound.play();
		}
	}

	@Override
	public void activate_action(GameEntity gameEntity) {}
}