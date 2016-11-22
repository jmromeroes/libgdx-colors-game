package com.mygdx.colors.elements;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.particles.Particle;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.GeneralInformation;

public class BombTile extends GameEntity{
	
	public static final String MAIN = "_bomb";
	private final PlayScreen playScreen;
	private final StaticState mainState;
	private final StaticState onState;
	
	public BombTile(PlayScreen playScreen, Cell cell){
		super(true);
        setColor((String)cell.getTile().getProperties().get("color"));
		this.playScreen = playScreen;
		
		mainState = 
			new StaticState(
				MAIN, 
				this, 
				"tile_"+getColor()+MAIN
			);
		onState = 
			new StaticState(
			    GeneralInformation.ON, 
			    this, 
			    "tile_"+getColor()+MAIN+"_"+GeneralInformation.ON
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
	public void activate_action(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			ColorMan cm = (ColorMan)gameEntity;
			playScreen.addExplosion(cm.getPosition().x+cm.getWidth()/2, 
					cm.getPosition().y+cm.getHeight()/2, 
				      Particle.COLORS_MAN_PARTICLE, 30);
			
			cm.setDead(true);
			if(ColorsGame.soundEnabled)
				cm.getDieSound().play();
		}
	}

	@Override
	public void update(float delta) {
		if(getCurrentState().getName().equals(GeneralInformation.ON)){
			setCurrentState(MAIN);
		}
	}
}