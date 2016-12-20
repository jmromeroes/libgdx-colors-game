package com.mygdx.colors.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.utils.GeneralInformation;

public class InputHandler implements InputProcessor{
	
	private GameScreen gameScreen;
	
	public InputHandler(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}

	@Override
	public boolean keyDown(int keycode){
		
		if(gameScreen instanceof PlayScreen){
			PlayScreen playScreen = (PlayScreen)gameScreen;
			
			if(keycode == Input.Keys.O){
				playScreen.activateTiles(GeneralInformation.RED);
			}
			
			if(keycode == Input.Keys.K){
				playScreen.activateTiles(GeneralInformation.YELLOW);
			}
	
			if(keycode == Input.Keys.Q){
				playScreen.activateTiles(GeneralInformation.GREEN);
			}

			if(keycode == Input.Keys.A){
				playScreen.activateTiles(GeneralInformation.BLUE);
			}
			
			playScreen.keyPressed();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character){
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gameScreen.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		gameScreen.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}	
	
}
