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

public class Station extends GameEntity{

	public static final String PLAY = "station_play_";
	public static final String STOP = "station_stop_";
	
	private final StaticState playState;
	private final StaticState playStateOn;
	
	private final StaticState stopState;
	private final StaticState stopStateOn;
	
	private final Sound sound;
	
	private float dx;
	
	public Station(Cell cell){
		super(true);
        sound = Content.getInstance().getSound("station");
		
		setColor((String)cell.getTile().getProperties().get("color"));
		
		playState   = new StaticState(PLAY, this, PLAY + getColor());
		playStateOn = 
			new StaticState(
				PLAY + GeneralInformation.ON, 
				this,
				PLAY + getColor() + "_" + GeneralInformation.ON
			);
		
		stopState = new StaticState(STOP, this, STOP + getColor());
		
		stopStateOn = 
			new StaticState(
				STOP + GeneralInformation.ON, 
				this, 
				STOP+getColor()+"_"+GeneralInformation.ON
			);

		try{
			addEntityState(playState);
			addEntityState(playStateOn);
			addEntityState(stopState);
			addEntityState(stopStateOn);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(playState);
	}
	
	@Override
	public void activate_action(GameEntity gameEntity) {
		if(ColorsGame.soundEnabled)
			sound.play();
		
		if(gameEntity instanceof ColorMan){
			ColorMan colorMan = (ColorMan) gameEntity;
			if(getCurrentState().getName().equals(PLAY+GeneralInformation.ON) || 
					getCurrentState().getName().equals(PLAY)){
				this.dx = colorMan.getDx();
				
				if(colorMan.getDx()>0){
					colorMan.setPosition(colorMan.getPosition().x-colorMan.getSpeed(),
							colorMan.getPosition().y);
				}else{
					colorMan.setPosition(colorMan.getPosition().x+colorMan.getSpeed(),
							colorMan.getPosition().y);
				}
				
				colorMan.setDx(0);
				setCurrentState(STOP+GeneralInformation.ON);
			}else if(getCurrentState().getName().equals(STOP+GeneralInformation.ON)|| 
					getCurrentState().getName().equals(STOP)){
				colorMan.setDx(dx);
				setCurrentState(PLAY+GeneralInformation.ON);
			}
		}
	}

	@Override
	public void update(float delta){
		if(getCurrentState().getName().equals(PLAY+GeneralInformation.ON)){
			setCurrentState(PLAY);
		}
		
		if(getCurrentState().getName().equals(STOP+GeneralInformation.ON)){
			setCurrentState(STOP);
		}
	}
	
	@Override
	public void setCurrentState(String key){
		if(key.equals(GeneralInformation.ON)){
			if(!getCurrentState().getName().equals(PLAY+GeneralInformation.ON))
				if(!getCurrentState().getName().equals(STOP+GeneralInformation.ON))
					super.setCurrentState(getCurrentState().getName()+GeneralInformation.ON);
		}else{
			super.setCurrentState(key);
		}
	}
}
