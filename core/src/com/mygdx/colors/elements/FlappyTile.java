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

public class FlappyTile extends GameEntity{
	public static final String MAIN = "flappy_tile_";
	
	private final StaticState mainState;
	private final StaticState onState;
	
	public FlappyTile(Cell cell){
		super(true);
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
	public void activate_action(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			ColorMan cm = (ColorMan)gameEntity;
			if(!cm.isFlappy()){
				cm.setFlappy(true);
				
				if(ColorsGame.soundEnabled)
					cm.getFlappySound().play();
				
				cm.impulse(0, cm.isSubgravitational()?-10:10);
			}else{
				cm.setFlappy(false);
				
				if(ColorsGame.soundEnabled)
					cm.getFlappySound().play();
			}
		}
	}

	@Override
	public void update(float delta) {
		if(getCurrentState().getName().equals(GeneralInformation.ON)){
			setCurrentState(MAIN);
		}
	}
}
