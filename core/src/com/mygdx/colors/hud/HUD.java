package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.utils.GeneralInformation;

public class HUD{
	
	private ColorButton blueButton, greenButton, redButton, yellowButton;
	private PlayScreen playScreen;
	
	public HUD(PlayScreen playScreen){
		this.setPlayScreen(playScreen);
		
		//Button definition
		blueButton = new ColorButton(this, GeneralInformation.BLUE);
		blueButton.setPosition(0, 0);
		
		greenButton = new ColorButton(this, GeneralInformation.GREEN);
		greenButton.setPosition(0, blueButton.getPosition().y +playScreen.getGame().getVHeight()/2);
		
		yellowButton = new ColorButton(this, GeneralInformation.YELLOW);
		yellowButton.setPosition(playScreen.getGame().getVWidth()-GeneralInformation.INITIAL_MAP_X, 0);
		
		redButton = new ColorButton(this, GeneralInformation.RED);
	    redButton.setPosition(yellowButton.getPosition().x, 
	    					  yellowButton.getPosition().y + playScreen.getGame().getVHeight()/2);
	}
	
	public void update(float delta){
		blueButton.update(delta);
		greenButton.update(delta);
		yellowButton.update(delta);
		redButton.update(delta);
	}
	
	public void render(SpriteBatch batch){
		blueButton.render(batch);
		greenButton.render(batch);
		yellowButton.render(batch);
		redButton.render(batch);
	}
	
	public void touchDown(int screenX, int screenY, int pointer){
		blueButton.touchDown(screenX, screenY, pointer);
		greenButton.touchDown(screenX, screenY, pointer);
		yellowButton.touchDown(screenX, screenY, pointer);
		redButton.touchDown(screenX, screenY, pointer);
	}

	public void touchUp(int screenX, int screenY, int pointer){
		blueButton.touchUp(screenX, screenY, pointer);
	}
	
	public PlayScreen getPlayScreen() {
		return playScreen;
	}

	public void setPlayScreen(PlayScreen playScreen) {
		this.playScreen = playScreen;
	}
}
