package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class ScoreBoard extends Board{

	private TiledButton nextLevelButton;
	private TiledButton backToLevelsButton;
	private TiledButton tryAgainButton;
	
	private float speed;
	
	private float finalX, finalY;
	
	private float cameraX;
	
	private int numberOfStars;
	
	private HUD_Star star;
	
	private BitmapFont font;
	
	private int level;
	
	public ScoreBoard(GameScreen gameScreen, int level, int numberOfStars) {
		super(gameScreen);
		
		this.star = new HUD_Star();
		this.level = level;
		this.numberOfStars = numberOfStars;
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
			nextLevelButton.update(delta);
			tryAgainButton.update(delta);
		}
	}
	
	@Override
	public void render(SpriteBatch spriteBatch){
		super.render(spriteBatch);
		if(backToLevelsButton != null){
			backToLevelsButton.render(spriteBatch);
			nextLevelButton.render(spriteBatch);
			tryAgainButton.render(spriteBatch);
			font.draw(spriteBatch, "   LEVEL  "+level + " \n COMPLETE!", getPosition().x+100, 
					getPosition().y+650);
			
			for(int i = 0; i<numberOfStars; i++){
				star.setPosition(50+(getPosition().x+180*i), getPosition().y+290);
				star.render(spriteBatch);
			}
		}
	}
	
	public void createButtons(){
		backToLevelsButton = new TiledButton(getGameScreen(), TiledButton.BACK,
											 getPosition().x+100, getPosition().y+90);
		
		tryAgainButton = new TiledButton(getGameScreen(), TiledButton.TRY_AGAIN,
							             backToLevelsButton.getPosition().x+backToLevelsButton.getWidth()+60,
							             backToLevelsButton.getPosition().y);
				
		nextLevelButton = new TiledButton(getGameScreen(), TiledButton.NEXT,
										  tryAgainButton.getPosition().x+tryAgainButton.getWidth()+60,
										  backToLevelsButton.getPosition().y);
		
	}
	
	public void touchDown(int screenX, int screenY, int pointer, int button){
		if(backToLevelsButton != null){
			backToLevelsButton.touchDown(screenX, screenY, pointer, button);
			tryAgainButton.touchDown(screenX, screenY, pointer, button);
			nextLevelButton.touchDown(screenX, screenY, pointer, button);
		}
	}
	
}
