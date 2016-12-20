package com.mygdx.colors.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class HillEnemy extends GameEntity{
	
	public static final String MAIN = "hillEnemy";
	
	private final StaticState mainState;
	
	private final Sound collisioningSound;
	
	private boolean goingUp;
	private boolean goingDown;
	private boolean goingRight;
	private boolean goingLeft;
	
	private float speed;
	private float dx, dy;
	private int Xindex, Yindex;
	private PlayScreen playScreen;
	
	public HillEnemy(PlayScreen playScreen, Cell cell){
		super(false);

		this.playScreen = playScreen;
		
		collisioningSound = Content.getInstance().getSound("collision");
		this.speed = 5f;
		
		mainState = new StaticState(MAIN, this, MAIN);
		
		goingDown = false;
		goingUp = false;
		setGoingRight(true);
		setGoingLeft(false);
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
	}

	@Override
	public void activate_action(GameEntity gameEntity){
	}

	@Override
	public void update(float delta){
		if(goingLeft){
			dx = -speed;
		}
		
		if(goingRight){
			dx = speed;
		}
		
		setPosition(getPosition().x+dx, getPosition().y+dy);
	}
	
	@Override
	public void collisioningUp(GameEntity gameEntity){
	}
	
	@Override
	public void collisioningDown(GameEntity gameEntity){
	}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
		if(!(gameEntity instanceof ColorMan)){
			setGoingRight(false);
			setGoingLeft(true);
		}
	}
	
	@Override
	public void collisioningLeft(GameEntity gameEntity){
		if(!(gameEntity instanceof ColorMan)){
			setGoingLeft(false);
			setGoingRight(true);
		}
	}
	
	@Override
	public void setPosition(float x, float y){
//		if(Xindex == 0 || Yindex == 0){
//			Xindex = (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize());
//			Yindex = (int)((y)/playScreen.getTileSize());
//		}
//		
//		if(Xindex != (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize())){
//			playScreen.getGameEntitiesArray()[Yindex][Xindex]=null;
//			Xindex = (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize());
//
//			if(playScreen.getGameEntitiesArray()[Yindex][Xindex]==null)
//				playScreen.getGameEntitiesArray()[Yindex][Xindex]=this;
//		}
//			
//		if(Yindex != (int)((y+5)/playScreen.getTileSize())){
//			playScreen.getGameEntitiesArray()[Yindex][Xindex]=null;
//			Yindex = (int)((y+5)/playScreen.getTileSize());
//			
//			if(playScreen.getGameEntitiesArray()[Yindex][Xindex]==null)
//				playScreen.getGameEntitiesArray()[Yindex][Xindex]=this;
//		}
		
		super.setPosition(x, y);
	}
	
	public void setGoingUp(boolean goingUp){
		this.goingUp = goingUp;
	}
	
	public void setGoingDown(boolean goingDown){
		this.goingDown = goingDown;
	}

	public boolean isGoingRight() {
		return goingRight;
	}

	public void setGoingRight(boolean goingRight) {
		this.goingRight = goingRight;
	}

	public boolean isGoingLeft() {
		return goingLeft;
	}

	public void setGoingLeft(boolean goingLeft) {
		this.goingLeft = goingLeft;
	}

}
