package com.mygdx.colors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.AnimatedState;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.AnimatedStateFactory;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class ColorButton extends GameEntity{

	private StaticState notPressedState;
	private AnimatedState pressedState;
	
	private HUD hud;
	
	//Initial screen-reference position
	private float initialX, initialY;
	
	public static final String PRESSED = "_button_pressed";
	public static final String NOT_PRESSED = "_button_not_pressed";
	
	public ColorButton(HUD hud, String color){
		super(false);
		this.hud = hud;
		
		setColor(color);
		
		TextureAtlas textureAtlas = Content.getInstance().getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS);
		
		float initialMapX = GeneralInformation.INITIAL_MAP_X;
		
		pressedState = AnimatedStateFactory.getAnimatedState(this, PRESSED, color+PRESSED, 0.05f, false);
		
		notPressedState = new StaticState(NOT_PRESSED, this, color+PRESSED, 2);
		
		try{
			addEntityState(pressedState);
			addEntityState(notPressedState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(NOT_PRESSED);
	}
	
	@Override
	public void update(float delta){
		setWidth((int)GeneralInformation.INITIAL_MAP_X);
		setHeight((int)hud.getPlayScreen().getGame().getVHeight()/2);
		
		getCurrentState().update(delta);
		
		float vwidth = hud.getPlayScreen().getGame().getVWidth();
		float vheight = hud.getPlayScreen().getGame().getVHeight();
		float cameraX = hud.getPlayScreen().getCamera().getPosition().x-vwidth/2;
		float cameraY = hud.getPlayScreen().getCamera().getPosition().y-vheight/2;
		
		float x = initialX+cameraX;
		float y = initialY+cameraY;
		
		super.setPosition(x, y);
	}
	
	public StaticState getNotPressedState() {
		return notPressedState;
	}

	public void setNotPressedState(StaticState notPressedState) {
		this.notPressedState = notPressedState;
	}

	public AnimatedState getPressedState() {
		return pressedState;
	}

	public void setPressedState(AnimatedState pressedState) {
		this.pressedState = pressedState;
	}
	
	public void touchDown(int screenX, int screenY, int pointer){
		screenY = Gdx.graphics.getHeight() - screenY;
		
		float vwidth = hud.getPlayScreen().getGame().getVWidth();
		float vheight = hud.getPlayScreen().getGame().getVHeight();
		float cameraX = hud.getPlayScreen().getCamera().getPosition().x-vwidth/2;
		float cameraY = hud.getPlayScreen().getCamera().getPosition().y-vheight/2;
		
		screenY =(int)(hud.getPlayScreen().getGame().getVHeight()*screenY/Gdx.graphics.getHeight()+cameraY);
		screenX =(int)(hud.getPlayScreen().getGame().getVWidth()*screenX/Gdx.graphics.getWidth()+cameraX);
		
		if(screenX>getPosition().x && screenX < getPosition().x+getWidth()){
			if(screenY>getPosition().y && screenY < getPosition().y+getHeight()){
				getPressedState().getStateAnimation().setCurrentSprite(0);
				setCurrentState(PRESSED);
				hud.getPlayScreen().activateTiles(getColor());
			}	
		}
	}
	
	@Override
	public void setPosition(float posX, float posY){
		super.setPosition(posX, posY);
		this.initialX = posX;
		this.initialY = posY;
	}
	
    public void touchUp(int screenX, int screenY, int pointer){}
	public void activate_action(GameEntity gameEntity){}
}