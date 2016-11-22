package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public abstract class Board extends GameEntity{
	
	public static final String MAIN = "board"; 
	
	private StaticState mainState;
	private GameScreen gameScreen;
	
	public Board(GameScreen gameScreen){
		super(false);
		this.setGameScreen(gameScreen);
		
		mainState = 
			new StaticState(
				MAIN, 
				this, 
				new Sprite(Content.getInstance().getTexture("board"))
			);
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
	}

	@Override
	public abstract void update(float delta);
	

	@Override
	public void activate_action(GameEntity gameEntity){}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
}
