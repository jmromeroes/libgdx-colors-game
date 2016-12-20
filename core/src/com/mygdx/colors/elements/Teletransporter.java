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

public class Teletransporter extends GameEntity{
	public static final String MAIN = "transporter_";
	private final PlayScreen playScreen;
	private final StaticState mainState;
	private final StaticState onState;
	
	private final String direction;
	
	private final int coordX, coordY;
	
	public Teletransporter(PlayScreen playScreen, Cell cell, int coordX, int coordY){
		super(true);
        
		setColor((String)cell.getTile().getProperties().get("color"));
		direction = (String)cell.getTile().getProperties().get("direction");
		
		this.coordX     = coordX;
		this.coordY     = coordY;
		this.playScreen = playScreen;
		
		mainState = 
			new StaticState(
				MAIN, 
				this, 
				MAIN+getDirection()+"_"+getColor()
			);
		onState = 
			new StaticState(
				GeneralInformation.ON, 
				this, 
				MAIN+getDirection()+"_"+getColor()+"_"+GeneralInformation.ON
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
			try{
				if(getDirection().equals(GeneralInformation.UP)){
					for(int y = coordY; y <= coordY+3; y++){
						if(playScreen.getTile(coordX,y)==null){
							playScreen.addExplosion(cm.getPosition().x, cm.getPosition().y,
									Particle.COLORS_MAN_PARTICLE, 10);
							cm.setPosition(getPosition().x, 
									playScreen.getTile(coordX, y-1).getPosition().y+playScreen.getTileSize());
							playScreen.addExplosion(cm.getPosition().x, cm.getPosition().y,
									Particle.COLORS_MAN_PARTICLE, 10);
							
							if(ColorsGame.soundEnabled)
								cm.getDieSound().play();
							
							cm.flipSubgravitation();
							
							break;
						}
					}
				}else{ 
					for(int y = coordY; y >= coordY-3; y--){
						if(playScreen.getTile(coordX,y)==null){
							playScreen.addExplosion(cm.getPosition().x, cm.getPosition().y,
									Particle.COLORS_MAN_PARTICLE, 10);
							cm.setPosition(getPosition().x, 
									playScreen.getTile(coordX, y+1).getPosition().y-cm.getHeight());
							playScreen.addExplosion(cm.getPosition().x, cm.getPosition().y,
									Particle.COLORS_MAN_PARTICLE, 10);
							
							cm.flipSubgravitation();
							
							if(ColorsGame.soundEnabled)
								cm.getDieSound().play();
							break;
						}
					}
				}
			}catch(ArrayIndexOutOfBoundsException exception){}
		}
	}

	@Override
	public void update(float delta) {
		if(getCurrentState().getName().equals(GeneralInformation.ON)){
			setCurrentState(MAIN);
		}
	}

	public String getDirection() {
		return direction;
	}

}
