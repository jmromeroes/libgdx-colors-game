package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class PauseBoard extends Board{
	
	private TiledButton continuePlayingButton;
	private TiledButton backToLevelsButton;
	private TiledButton tryAgainButton;
	private TiledButton soundButton;
	
	private float speed;
	private float finalX, finalY;
	private float cameraX;
	private BitmapFont font;
	private int level;

	public PauseBoard(GameScreen gameScreen, int level) {
		super(gameScreen);
		
		this.level = level;
		this.speed = 80;
		this.finalX = 370;
		this.finalY = 20;
		this.cameraX = getGameScreen().getCamera().getPosition().x-getGameScreen().getGame().getVWidth()/2;
		this.font = Content.getInstance().getFont(GeneralInformation.MAIN_FONT);
		
		setPosition(gameScreen.getGame().getVWidth()+20+cameraX,
			    	finalY+gameScreen.getCamera().getPosition().y-gameScreen.getGame().getVHeight()/2);
		setWidth((int)getGameScreen().getGame().getVWidth()-(int)finalX*2);
		setHeight((int)getGameScreen().getGame().getVHeight()-(int)finalY*2);
	}
	
	@Override
	public void update(float delta){
		if(getPosition().x != finalX+cameraX){
			if(getPosition().x< finalX+cameraX){
				getPosition().x = finalX+cameraX;
			}else{
				getPosition().x -= speed;
			}
		}
		
		if(getPosition().x == finalX + cameraX && backToLevelsButton == null){
			createButtons();
		}
		
		if(backToLevelsButton != null){
			backToLevelsButton.update(delta);
			tryAgainButton.update(delta);
			continuePlayingButton.update(delta);
			soundButton.update(delta);
		}
		
		if(soundButton != null && soundButton.isActivated()){
			if(soundButton.getIndex() == TiledButton.SOUND_ENABLED){
				soundButton = null;
				soundButton = new TiledButton(getGameScreen(), TiledButton.SOUND_NOT_ENABLED,
						  tryAgainButton.getPosition().x,
						  tryAgainButton.getPosition().y+tryAgainButton.getHeight()+40);

			}else{
				soundButton = null;
				soundButton = new TiledButton(getGameScreen(), TiledButton.SOUND_ENABLED,
						  tryAgainButton.getPosition().x,
						  tryAgainButton.getPosition().y+tryAgainButton.getHeight()+40);
			}
		}
	}
	
	@Override
	public void render(SpriteBatch spriteBatch){
		super.render(spriteBatch);
		if(backToLevelsButton != null){
			backToLevelsButton.render(spriteBatch);
			tryAgainButton.render(spriteBatch);
			soundButton.render(spriteBatch);
			continuePlayingButton.render(spriteBatch);
			font.draw(spriteBatch, "    PAUSED \n\n   LEVEL "+level + "!", getPosition().x+100, 
					getPosition().y+630);
		}
	}
	
	public void createButtons(){
		backToLevelsButton = new TiledButton(getGameScreen(), TiledButton.BACK,
											 getPosition().x+100, getPosition().y+90);
		
		tryAgainButton = new TiledButton(getGameScreen(), TiledButton.TRY_AGAIN,
							             backToLevelsButton.getPosition().x+backToLevelsButton.getWidth()+60,
							             backToLevelsButton.getPosition().y);
				
		continuePlayingButton = new TiledButton(getGameScreen(), TiledButton.CONTINUE_PLAYING,
										  tryAgainButton.getPosition().x+tryAgainButton.getWidth()+60,
										  backToLevelsButton.getPosition().y);
		
		
		soundButton = new TiledButton(getGameScreen(), 
				  ColorsGame.soundEnabled?TiledButton.SOUND_ENABLED:TiledButton.SOUND_NOT_ENABLED,
				  tryAgainButton.getPosition().x,
				  tryAgainButton.getPosition().y+tryAgainButton.getHeight()+40);

	}
	
	public void touchDown(int screenX, int screenY, int pointer, int button){
		if(backToLevelsButton != null){
			backToLevelsButton.touchDown(screenX, screenY, pointer, button);
			tryAgainButton.touchDown(screenX, screenY, pointer, button);
			continuePlayingButton.touchDown(screenX, screenY, pointer, button);
			soundButton.touchDown(screenX, screenY, pointer, button);
		}
	}
}
