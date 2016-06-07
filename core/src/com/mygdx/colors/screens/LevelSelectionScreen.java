package com.mygdx.colors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.hud.LevelsSet;
import com.mygdx.colors.hud.TiledButton;
import com.mygdx.colors.background.Background;

public class LevelSelectionScreen extends GameScreen{
	private int totalNumberOfLevels;

	private LevelsSet levelsSet;
	
	private TiledButton buttonNext;
	private TiledButton buttonPrevious;
	
	private Music music;
	
	//Background
	private Background background;
	private Background background2;
	
	public LevelSelectionScreen(ColorsGame game){
		super(game);
		
		totalNumberOfLevels = 300;
		levelsSet = new LevelsSet(this);
		
		buttonPrevious = new TiledButton(this, TiledButton.PREVIOUS_LEVEL_SET,
										20, 300);
		buttonNext = new TiledButton(this, TiledButton.NEXT_LEVEL_SET,
									game.getVWidth()-140, 300);
	
		background = new Background(this, 3, "background");
		background2 = new Background(this, 3, "background2");
	}

	@Override
	public void render(float delta){
		update(delta);
		
		background.render(getSpriteBatch());
		background2.render(getSpriteBatch());
			
		levelsSet.render(getGame().getBatch());
		buttonNext.render(getGame().getBatch());
		buttonPrevious.render(getGame().getBatch());
	}

	@Override
	public void dispose(){
		levelsSet.dispose();
		music.dispose();
	}

	@Override
	public void update(float delta) {
		background.update(-1.7f, 0, false);
		background2.update(-0.8f, 0, false);
		
		levelsSet.update(delta);
		buttonNext.update(delta);
		buttonPrevious.update(delta);
		
		if(buttonNext.isActivated() && buttonNext.getExplosions().isEmpty()){
			buttonNext=null;
			buttonNext = new TiledButton(this, TiledButton.NEXT_LEVEL_SET,
										 getGame().getVWidth()-140, 300);
			}
		
		if(buttonPrevious.isActivated() && buttonPrevious.getExplosions().isEmpty()){
			buttonPrevious=null;
			buttonPrevious = new TiledButton(this, TiledButton.PREVIOUS_LEVEL_SET,
											 20, 300);
			}
		getCamera().fixBounds();
	}	
	
	public void levelUp(){
		levelsSet.levelUp();
	}
	
	public void levelDown(){
		levelsSet.levelDown();
	}
	
	public void touchDown(int posX, int posY, int pointer, int button) {
		levelsSet.touchDown(posX, posY, pointer, button);
		buttonNext.touchDown(posX, posY, pointer, button);
		buttonPrevious.touchDown(posX, posY, pointer, button);
	}

	@Override
	public void createAndStartMusic() {
		//Music stuff, very similar to MenuScreen
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/mainMenu.mp3"));
		music.setVolume(0.5f);
		music.setLooping(true);
		if(ColorsGame.soundEnabled)
			music.play();
	}

	@Override
	public void activateSpecificActions() {
		// TODO Auto-generated method stub
		levelsSet.createPanes();
		getCamera().setBounds(0, getGame().getVWidth(), 0, getGame().getVHeight());
	}
}
