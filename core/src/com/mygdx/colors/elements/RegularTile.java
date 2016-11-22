package com.mygdx.colors.elements;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.interfaces.Collisionable;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.AnimatedState;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class RegularTile extends GameEntity{
	
	private final StaticState offState;
	private final StaticState onState;
	
	public RegularTile(Cell cell){
		super(true);

		setColor((String)cell.getTile().getProperties().get("color"));

		offState = 
			new StaticState(
				GeneralInformation.OFF, 
				this, 
				"tile_"+getColor()
			);
		onState = 
			new StaticState(
				GeneralInformation.ON, 
				this, 
				"tile_"+getColor()+"_"+GeneralInformation.ON
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
	public void activate_action(GameEntity gameEntity){}

	@Override
	public void update(float delta){
		if(getCurrentState().getName()==GeneralInformation.ON)
			setCurrentState(GeneralInformation.OFF);
	}

	@Override
	public void collisioningRight(GameEntity gameEntity){
		
	}

	@Override
	public void collisioningLeft(GameEntity gameEntity){}

	@Override
	public void collisioningUp(GameEntity gameEntity){}

	@Override
	public void collisioningDown(GameEntity gameEntity){}
}