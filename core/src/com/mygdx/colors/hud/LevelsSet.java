package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.screens.GameScreen;

public class LevelsSet {
	
	private int matrixDimension;
	private boolean levelSet;
	private int level;
	
	private LevelPane[][] levelPaneMatrix;
	
	private int totalLevels;
	
	private GameScreen gameScreen;
	
	public LevelsSet(GameScreen gameScreen){
		this.matrixDimension = 4;
		this.totalLevels = 180;
		this.gameScreen = gameScreen;
	}
	
	public void levelUp(){
		if(levelPaneMatrix[0][0].getRenderizableNumber().getNumber()<totalLevels){
			for(int y = 0; y < matrixDimension; y++){
				for(int x = 0; x < matrixDimension; x++){
					levelPaneMatrix[y][x].goUp();
				}
			}
		}
	}
	
	public void levelDown(){
			if(levelPaneMatrix[0][0].getRenderizableNumber().getNumber()!=1){
				for(int y = 0; y < matrixDimension; y++){
					for(int x = 0; x < matrixDimension; x++){
						levelPaneMatrix[y][x].goDown();
					}
				}
			}
	}
			
	
	public void render(SpriteBatch spriteBatch){
		for(int y = 0; y < matrixDimension; y++){
			for(int x = 0; x < matrixDimension; x++){
				levelPaneMatrix[y][x].render(spriteBatch);
				levelPaneMatrix[y][x].setHidden(false);
				if(!levelPaneMatrix[y][x].isHidden() && 
					levelPaneMatrix[y][x].getRenderizableNumber().getNumber()> 
					totalLevels){
						levelPaneMatrix[y][x].setHidden(true);
				}
			}
		}
	}
	
	public void update(float delta){
		for(int y = 0; y < matrixDimension; y++){
			for(int x = 0; x < matrixDimension; x++){
				levelPaneMatrix[y][x].update(delta);
			}
		}
		gameScreen.getCamera().setPosition(levelPaneMatrix[0][0].getPosition().x,
				   levelPaneMatrix[0][0].getPosition().y);

	}
	
	public void dispose(){
		levelPaneMatrix = null;
	}
	
	public void touchDown(int posX, int posY, int pointer, int button) {
		for(int y = 0; y < matrixDimension; y++){
			for(int x = 0; x < matrixDimension; x++){
				if(levelPaneMatrix != null){
					levelPaneMatrix[y][x].touchDown(posX, posY, pointer, button);
				}else{
					break;
				}
			}
		}
	}
	
	public void createPanes(){
		this.levelPaneMatrix = new LevelPane[matrixDimension][matrixDimension];
		
		int i = 0;
		for(int y = 0; y < matrixDimension; y++){
			for(int x = 0; x < matrixDimension; x++){
				i++;
				levelPaneMatrix[y][x] = new LevelPane(gameScreen.getGame(), 310+x*200, 20+y*200);
				levelPaneMatrix[y][x].setLevel(i);
			}	
		}
	}
}
