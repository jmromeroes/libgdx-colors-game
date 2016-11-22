package com.mygdx.colors.elements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class Lock extends GameEntity{
	
	public static final String MAIN = "lock_";
	
	private final StaticState mainState;
	private final StaticState onState;
	private final PlayScreen playScreen;
	private final int colInMap;
	private final int rowInMap;
	private final Sound sound;

	public Lock(PlayScreen playScreen, Cell cell, int colInMap, int rowInMap){
		super(true);
        sound = Content.getInstance().getSound("station");
		
		this.playScreen = playScreen;
		this.colInMap = colInMap;
		this.rowInMap = rowInMap;
		
		setColor((String)cell.getTile().getProperties().get("color"));
		
		mainState = 
			new StaticState(
				MAIN, 
				this, 
				MAIN+getColor()
			);
		onState =
		    new StaticState(
		    	GeneralInformation.ON, 
		    	this, 
		    	MAIN+getColor()+"_"+GeneralInformation.ON
		    );

		try{
			addEntityState(mainState);
			addEntityState(onState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
	}
	
	@Override
	public void activate_action(GameEntity gameEntity) {
		if(gameEntity instanceof ColorMan){
			ColorMan colorMan = (ColorMan)gameEntity;
			if(getColor().equals(GeneralInformation.GREEN)){
				if(colorMan.getGreenKeys()>0){
					colorMan.removeKey(GeneralInformation.GREEN);
					activateLock();
					activateNextLocks();
				}
			}else if(getColor().equals(GeneralInformation.BLUE)){
				if(colorMan.getBlueKeys()>0){
					colorMan.removeKey(GeneralInformation.BLUE);
					activateLock();
					activateNextLocks();
				}
			}else if(getColor().equals(GeneralInformation.YELLOW)){
				if(colorMan.getYellowKeys()>0){
					colorMan.removeKey(GeneralInformation.YELLOW);
					activateNextLocks();
					activateLock();
				}
			}else if(getColor().equals(GeneralInformation.RED)){
				if(colorMan.getRedKeys()>0){
					colorMan.removeKey(GeneralInformation.RED);
					activateLock();
					activateNextLocks();
				}
			}
		}else if(gameEntity instanceof Lock){
			setHidden(true);
			activateLock();
			activateNextLocks();
		}
	}

	@Override
	public void update(float delta){
		if(getCurrentState().getName()==GeneralInformation.ON)
			setCurrentState(MAIN);
	}
	
	public void activateLock(){
		if(ColorsGame.soundEnabled)
			sound.play();
		
		setHidden(true);
		playScreen.addExplosion(getPosition().x+getWidth()/2, getPosition().y+getHeight()/2, 
								"tile_"+getColor()+"_particle", 10);
	}
	
	public void activateNextLocks(){
		if(colInMap+1<playScreen.getTiledMapWidth()){
			if(playScreen.getGameEntitiesArray()[rowInMap][colInMap+1] != null){
				if(playScreen.getGameEntitiesArray()[rowInMap][colInMap+1] instanceof Lock){
					if(!((Lock)playScreen.getGameEntitiesArray()[rowInMap][colInMap+1]).isHidden())
						((Lock)playScreen.getGameEntitiesArray()[rowInMap][colInMap+1]).activate_action(this);
				}
			}
		}
		
		if(colInMap-1>0){
			if(playScreen.getGameEntitiesArray()[rowInMap][colInMap-1] != null){
				if(playScreen.getGameEntitiesArray()[rowInMap][colInMap-1] instanceof Lock){
					if(!((Lock)playScreen.getGameEntitiesArray()[rowInMap][colInMap-1]).isHidden())
						((Lock)playScreen.getGameEntitiesArray()[rowInMap][colInMap-1]).activate_action(this);
				}
			}
		}
		
		if(rowInMap+1<playScreen.getTiledMapHeight()){
			if(playScreen.getGameEntitiesArray()[rowInMap+1][colInMap] != null){
				if(playScreen.getGameEntitiesArray()[rowInMap+1][colInMap] instanceof Lock){
					if(!((Lock)playScreen.getGameEntitiesArray()[rowInMap+1][colInMap]).isHidden())
						((Lock)playScreen.getGameEntitiesArray()[rowInMap+1][colInMap]).activate_action(this);
				}
			}
		}
		
		if(rowInMap-1>0){
			if(playScreen.getGameEntitiesArray()[rowInMap-1][colInMap] != null){
				if(playScreen.getGameEntitiesArray()[rowInMap-1][colInMap] instanceof Lock){
					if(!((Lock)playScreen.getGameEntitiesArray()[rowInMap-1][colInMap]).isHidden())
						((Lock)playScreen.getGameEntitiesArray()[rowInMap-1][colInMap]).activate_action(this);
				}
			}
		}
	}
}
