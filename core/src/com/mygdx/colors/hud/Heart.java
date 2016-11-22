package com.mygdx.colors.hud;

import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;

public class Heart extends GameEntity{
	public static final String MAIN = "hud_heartFull";
	
	private GameScreen gameScreen;
	
	private StaticState mainState;
	
	private float posX, posY;
	
	public Heart(GameScreen gameScreen, float posX, float posY){
		super(false);
		this.gameScreen = gameScreen;
		mainState = new StaticState(MAIN, this, MAIN);
	
		this.posX = posX;
		this.posY = posY;
		
		setPosition(posX, posY);
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		
		getCurrentState().getSprite().setSize(70, 60);
	}
	
	public void blowUp(){
		if(gameScreen instanceof PlayScreen){
			((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, getPosition().y+getHeight()/2, 
								"tile_red_particle", 16);
		}
	}
	
	@Override
	public void update(float delta){
		if(gameScreen instanceof PlayScreen){
			setPosition(posX+gameScreen.getCamera().getPosition().x-gameScreen.getGame().getVWidth()/2, 
					posY+gameScreen.getCamera().getPosition().y-gameScreen.getGame().getVHeight()/2);
		}
	}
	
	@Override
	public void activate_action(GameEntity gameEntity) {}
}
