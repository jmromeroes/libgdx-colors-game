package com.mygdx.colors.elements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class DirectionTile extends GameEntity{
	
	//States information
	private final StaticState onState;
	private final StaticState offState;
	
	//Direction information
	private final String direction;
	private final Sound sound;
	
	public DirectionTile(Cell cell){
		super(true);
        sound  = Content.getInstance().getSound("jump");
		
		setColor((String)cell.getTile().getProperties().get("color"));
		direction = (String)cell.getTile().getProperties().get("direction");
		
		onState = 
			new StaticState(
				GeneralInformation.ON, 
				this, 
				getDirection()+"_"+getColor()+"_"+GeneralInformation.ON
			);
		
		offState = 
			new StaticState(
				GeneralInformation.OFF, 
				this,
				getDirection()+"_"+getColor()
			);
		
		try{
			addEntityState(onState);
			addEntityState(offState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(GeneralInformation.OFF);
	}
		
	
	@Override
	public void activate_action(GameEntity gameEntity) {
		ColorMan colorMan = (ColorMan) gameEntity;
		
		if(ColorsGame.soundEnabled)
			sound.play();
		
		if(colorMan.isFlappy())
			colorMan.setFlappy(false);
		
		if(getDirection().equals(GeneralInformation.RIGHT_UP)){
			colorMan.impulse(10f, 10f);
		}else if(getDirection().equals(GeneralInformation.LEFT_UP)){
			colorMan.impulse(-10f, 10f);
		}else if(getDirection().equals(GeneralInformation.RIGHT_DOWN)){
			colorMan.impulse(10f, -10f);
		}else if(getDirection().equals(GeneralInformation.LEFT_DOWN)){
			colorMan.impulse(-10f, -10f);
		}else if(getDirection().equals(GeneralInformation.UP)){
			colorMan.impulse(0, 13);
		}else if(getDirection().equals(GeneralInformation.DOWN)){
			colorMan.impulse(0,-10f);
		}
	}

	@Override
	public void update(float delta){
		if(getCurrentState().getName()==GeneralInformation.ON)
			setCurrentState(GeneralInformation.OFF);
	}


	public String getDirection() {
		return direction;
	}

}
