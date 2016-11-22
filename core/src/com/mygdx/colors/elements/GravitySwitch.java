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

public class GravitySwitch extends GameEntity{
	
	public static final String MAIN = "gravity_switch_";
	
	private final StaticState mainState;
	private final StaticState onState;
	
	private final Sound sound;
	
	private final String direction;
	
	public GravitySwitch(Cell cell){
		super(true);
        sound  = Content.getInstance().getSound("subgravity");
		
		setColor((String)cell.getTile().getProperties().get("color"));
		direction = (String)cell.getTile()
				                .getProperties()
				                .get("direction");
		
		mainState = 
			new StaticState(
				MAIN, 
				this, 
				getDirection()!=null?MAIN+getColor()+"_"+getDirection():MAIN+getColor()
			);
		
		onState = 
			new StaticState(
				GeneralInformation.ON, 
				this,
				getDirection()!= null? MAIN+getColor()+"_"+getDirection()+"_"+GeneralInformation.ON
				                      :MAIN+getColor()+"_"+GeneralInformation.ON
			);
		
		try{
			addEntityState(mainState);
			addEntityState(onState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(GeneralInformation.ON);
	}
	
	@Override
	public void activate_action(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			if(ColorsGame.soundEnabled)
				sound.play();
			
			((ColorMan)gameEntity).flipSubgravitation();
			
			if(direction != null){
				if(direction.equals(GeneralInformation.RIGHT)){
					((ColorMan)gameEntity).setDx(((ColorMan)gameEntity).getSpeed());
				}
				if(direction.equals(GeneralInformation.LEFT)){
					((ColorMan)gameEntity).setDx(-((ColorMan)gameEntity).getSpeed());
				}
			}
		}
	}

	@Override
	public void update(float delta){
		if(getCurrentState().getName()==GeneralInformation.ON){
			setCurrentState(MAIN);
		}
	}

	public String getDirection() {
		return direction;
	}

}
