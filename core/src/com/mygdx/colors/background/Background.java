package com.mygdx.colors.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.utils.Content;

public class Background {
	
	private final Texture backgroundImage;
	
	private float posX, posY;
	private float dx, dy;
	
	private final GameScreen gameScreen;
	
	private final float width, height;
	
	private int referenceHeight, referenceWidth;
	
	private final float movementScale;
	
	private boolean goingRight;
	
	public Background(GameScreen gameScreen, float movementScale, String textureName){
		this.gameScreen = gameScreen;
		this.backgroundImage = Content.getInstance().getTexture(textureName);
		this.width = gameScreen.getGame().getVWidth()+100;
		this.height = gameScreen.getGame().getVHeight()+100;
		this.movementScale = movementScale;
		this.goingRight = false;
	}
	
	public void update(float dx, float dy, boolean parallelToCamera){
		float cameraPositionX = gameScreen.getCamera().getPosition().x
								-gameScreen.getGame().getVWidth()/2;
		float cameraPositionY = gameScreen.getCamera().getPosition().y
								-gameScreen.getGame().getVHeight()/2;
		
		posX += dx/movementScale;
		
		if(parallelToCamera){
			if(dx > 0){
				if(cameraPositionX-posX>=getWidth()){
					posX = posX+getWidth();
				}else if(cameraPositionX-posX<=1){
					posX = posX-getWidth();
				}
			}else if(dx < 0){
				if(cameraPositionX-posX<=1){
					posX = posX-getWidth();
				}
			}
		}else{
			if(dx > 0){
				if(cameraPositionX-posX<=1){
					posX = posX-getWidth();
				}
			}else if(dx < 0){
				if(cameraPositionX-posX>=getWidth()){
					posX = posX+getWidth();
				}
			}
		}
		
		posY += dy/movementScale;
		
		if(parallelToCamera){
			if(dy > 0){
				if(cameraPositionY-posY>=getHeight()){
					posY = cameraPositionY;
				}
			}else if(dy < 0){
				if(cameraPositionY-posY<=-1){
					posY = cameraPositionY-getHeight();
				}
			}
		}
	}
	
	public void render(SpriteBatch spriteBatch){
		spriteBatch.draw(backgroundImage, posX, posY, width, height);
        spriteBatch.draw(backgroundImage, posX+getWidth(), getHeight()+posY, width, height);
		spriteBatch.draw(backgroundImage, posX+getWidth(), posY, width, height);
		spriteBatch.draw(backgroundImage, posX, posY+getHeight(), width, height);
	}
	
	public void dispose(){
		backgroundImage.dispose();
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getX(){
		return posX;
	}
	
	public void setX(float posX){
		this.posX = posX;
	}
	
	public float getY(){
		return posY;
	}
	
	public void setY(float posY){
		this.posY = posY;
	}

}
