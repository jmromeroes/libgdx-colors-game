package com.mygdx.colors.elements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class AccelerationPit extends GameEntity{
	
	private final StaticState offState;
	private final StaticState onState;
	
	private final String direction;
	
	private Sound sound;
	public AccelerationPit(Cell cell){
		super(true);
		sound  = Content.getInstance().getSound("acceleration");
		
		direction = (String)cell.getTile().getProperties().get("direction");
		setColor((String)cell.getTile().getProperties().get("color"));
		
		offState = 
		    new StaticState(
				GeneralInformation.OFF, 
				this, 
				getDirection()+"_"+getColor()
			);
		onState = 
			new StaticState(
				GeneralInformation.ON, 
				this, 
				getDirection()+"_"+getColor()+"_"+GeneralInformation.ON
			);
		
		try{
			addEntityState(offState);
			addEntityState(onState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(GeneralInformation.OFF);
	}

	@Override
	public void activate_action(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			if(ColorsGame.soundEnabled)
				sound.play(0.5f);
			
			ColorMan colorMan = (ColorMan)gameEntity;
			colorMan.setAccelerating(true);
			colorMan.setDx(getDirection().equals(GeneralInformation.RIGHT)?17:-17);
		}
	}

	@Override
	public void update(float delta){
		if(getCurrentState().getName().equals(GeneralInformation.ON)){
			setCurrentState(GeneralInformation.OFF);
		}
	}

	public String getDirection() {
		return direction;
	}

}
