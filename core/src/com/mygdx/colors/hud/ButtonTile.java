package com.mygdx.colors.hud;

import java.util.Random;

import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.states.StaticState;

public class ButtonTile extends GameEntity{
	private final String[] colorsArray = {"blue", "green", "red", "yellow"};
	public static final String MAIN = "main";
	
	//Size must be the same for every tile in button
	private float size;
	
	private float speed;
	
	private StaticState mainState;
	private boolean activated;
	
	private TiledButton tiledButton;
	
	private int posXInText, posYInText;
	
	
	//This determines whether the button will be moving from random point or not
	private boolean withPointToGo;
	
	public ButtonTile(GameScreen gameScreen, TiledButton tiledButton, 
			          int posXInText, int posYInText, boolean withPointToGo){
		super(false);
		this.posXInText = posXInText;
		this.posYInText = posYInText;
		
		this.tiledButton = tiledButton;
		this.speed = 20f;
		this.size = 20f;
		
		Random random = new Random();
		
		setColor(colorsArray[random.nextInt(4)]);
		mainState = new StaticState(MAIN, this, "tile_"+getColor()+"_on");
		
		float cameraX = gameScreen.getCamera().getPosition().x - gameScreen.getGame().getVWidth()/2;
		this.withPointToGo = withPointToGo;
		if(withPointToGo){
			switch((random.nextInt(4))){
				case 0:
					setPosition(-random.nextFloat()*50f+cameraX,
										random.nextFloat()*(float)gameScreen.getGame().getVHeight());
					break;
				case 1:
					setPosition(random.nextFloat()*50f+(float)gameScreen.getGame().getVWidth()+cameraX,
										random.nextFloat()*(float)gameScreen.getGame().getVHeight());
					break;
				case 2:
					setPosition( random.nextFloat()*(float)gameScreen.getGame().getVWidth()+cameraX,
										-random.nextFloat()*50f);
					break;
				case 3:
					setPosition((float)gameScreen.getGame().getVWidth()+cameraX,
										random.nextFloat()*50f+(float)gameScreen.getGame().getVHeight());
					break;
			}
		}
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
	}
	
	@Override
	public void update(float delta){
		setWidth((int)size);
		setHeight((int)size);
		
		if(!withPointToGo){
			setPosition(tiledButton.getPosition().x+size*posXInText, 
				    	tiledButton.getPosition().y+size*posYInText);
		}else{
			if(getPosition().x<tiledButton.getPosition().x+size*posXInText){
				setPosition(getPosition().x+speed, getPosition().y);
			}else{
				setPosition(getPosition().x-speed, getPosition().y);
			}
			
			if(getPosition().y<tiledButton.getPosition().y+size*posYInText){
				setPosition(getPosition().x, getPosition().y+speed);
			}else{
				setPosition(getPosition().x, getPosition().y-speed);
			}
			
			if(Math.abs((tiledButton.getPosition().y+size*posYInText)-getPosition().y)<=20
			   && Math.abs((tiledButton.getPosition().x+size*posXInText)-getPosition().x)<=20){
					withPointToGo=false;
			}
		}
	}
	
	@Override
	public void activate_action(GameEntity gameEntity){}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public int getPosXInText() {
		return posXInText;
	}

	public void setPosXInText(int posXInText) {
		this.posXInText = posXInText;
	}

	public int getPosYInText() {
		return posYInText;
	}

	public void setPosYInText(int posYInText) {
		this.posYInText = posYInText;
	}

	public boolean isWithPointToGo() {
		return withPointToGo;
	}

	public void setWithPointToGo(boolean withPointToGo) {
		this.withPointToGo = withPointToGo;
	}
	
}
