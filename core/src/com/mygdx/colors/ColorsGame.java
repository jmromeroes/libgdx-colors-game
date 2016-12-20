package com.mygdx.colors;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.LevelSelectionScreen;
import com.mygdx.colors.screens.MenuScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.screens.PreloadScreen;
import com.mygdx.colors.utils.BoundedCamera;

public class ColorsGame extends Game {
	
	private final HashMap <Integer, GameScreen>screensMap = new HashMap<Integer, GameScreen>();
	private SpriteBatch batch;
	private final BoundedCamera camera = new BoundedCamera();
	
	public static final String TITLE = "COLORS GAME";
	private float ASPECT_RATIO;
	private float V_WIDTH;
    private float V_HEIGHT;
	
	public static final int PRELOAD_SCREEN = 0;
	public static final int MENU_SCREEN = 1;
	public static final int GAME_SCREEN = 2;
	public static final int LEVEL_SELECTION_SCREEN = 3;
	
	private GameScreen playScreen; 
	private GameScreen menuScreen;
	private GameScreen preloadScreen;
	private GameScreen levelSelectionScreen;
	
	public static boolean soundEnabled;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		soundEnabled = true;
        		
		ASPECT_RATIO   = (float)Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		V_WIDTH        = 1350F;
		V_HEIGHT       = V_WIDTH * ASPECT_RATIO;
		
		//Screen creation
		preloadScreen        = new PreloadScreen(this);
		menuScreen           = new MenuScreen(this);
		playScreen           = new PlayScreen(this);
		levelSelectionScreen = new LevelSelectionScreen(this);

		//We add screens to screen map
		screensMap.put(PRELOAD_SCREEN, preloadScreen);
		screensMap.put(GAME_SCREEN, playScreen);
		screensMap.put(MENU_SCREEN, menuScreen);
		screensMap.put(LEVEL_SELECTION_SCREEN, levelSelectionScreen);
		
		setScreen(PRELOAD_SCREEN);
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(getCamera().combined);
		batch.begin();
		getScreen().render(Gdx.graphics.getDeltaTime());
		batch.end();
		
		camera.update();
		
	}
	
	@Override 
	public void resize(int width, int height){
		camera.viewportWidth  = V_WIDTH;
		camera.viewportHeight = V_HEIGHT;
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
	
	public BoundedCamera getCamera() {
		return camera;
	}

	public void setScreen(int key){
		if(getScreen() != null)
			getScreen().dispose();
		
		super.setScreen(screensMap.get(key));
		((GameScreen)getScreen()).createAndStartMusic();
		((GameScreen)getScreen()).activateSpecificActions();
		Gdx.input.setInputProcessor(screensMap.get(key).getInputHandler());	
	}
	
	public void setLevel(int level){
		if(getScreen() != null)
			getScreen().dispose();
		
		PlayScreen playScreen = (PlayScreen) screensMap.get(GAME_SCREEN);
		playScreen.createAndStartMusic();
		playScreen.setLevel(level);
		super.setScreen(playScreen);
		
		Gdx.input.setInputProcessor(playScreen.getInputHandler());
	}
	
	public float getVHeight() {
		return V_HEIGHT;
	}

	public float getVWidth() {
		return V_WIDTH;
	}

}
