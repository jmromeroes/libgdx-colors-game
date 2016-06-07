package com.mygdx.colors.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.input.InputHandler;
import com.mygdx.colors.utils.BoundedCamera;

public abstract class GameScreen extends ScreenAdapter{
	private ColorsGame game;
	private SpriteBatch spriteBatch;
	private BoundedCamera camera;
	
	private boolean isShaking;
	private InputHandler inputHandler;
	
	private boolean isSubgravitational;
	
	public GameScreen(ColorsGame game){
		this.setGame(game);
		spriteBatch = game.getBatch();
		camera = game.getCamera();
		isShaking = false;
		isSubgravitational = false;
		setInputHandler(new InputHandler(this));
	}
	
	@Override
	public abstract void render(float delta);
	
	@Override
	public abstract void dispose();

	
	public ColorsGame getGame() {
		return game;
	}

	public void setGame(ColorsGame game) {
		this.game = game;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	public BoundedCamera getCamera() {
		return camera;
	}
	
	public abstract void update(float delta);
	
	public void setCamera(BoundedCamera camera){
		this.camera = camera;
	}	
	
	public void touchDown(int x, int y, int pointer, int button) {}
	public void touchUp(int x, int y, int pointer, int button) {}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}
	
	public abstract void createAndStartMusic();
	public abstract void activateSpecificActions();

	public boolean isShaking() {
		return isShaking;
	}

	public void setShaking(boolean isShaking) {
		this.isShaking = isShaking;
	}
	
	public boolean isSubgravitational() {
		return isSubgravitational;
	}

	public void flipSubgravitation() {
		this.isSubgravitational = !this.isSubgravitational;
	}
}
