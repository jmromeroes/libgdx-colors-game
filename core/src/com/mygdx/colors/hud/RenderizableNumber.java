package com.mygdx.colors.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class RenderizableNumber{
	
	private float posX, posY;
	private String numberToRender;
	private LevelPane levelPane;
	private float width, height;

	public RenderizableNumber(int numberToRender){
		this.numberToRender = Integer.toString(numberToRender);
		this.width = 50;
		this.height = 70;
	}
	
	public void render(SpriteBatch spriteBatch){
		for(int i = 0; i < numberToRender.length(); i++){
			TextureAtlas textureAtlas = Content.getInstance().getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS);
			Sprite sprite = textureAtlas.createSprite("hud"+numberToRender.charAt(i));
			sprite.setSize(width, height);
			sprite.setPosition(posX + sprite.getWidth()*i, posY);
			sprite.draw(spriteBatch);
		}
	}
	
	public void update(float delta){
		if(levelPane != null){
			setPosition(levelPane.getPosition().x+levelPane.getWidth()/2f, 
					levelPane.getPosition().y+levelPane.getHeight()/2f);
		}
	}
	
	public void setNumber(int numberToRender){
		this.numberToRender = Integer.toString(numberToRender);
	}
	
	public int getNumber(){
		return Integer.parseInt(numberToRender);
	}
	
	/*
	 * @params The position is the vector that refers to the center point of the number 
	 */
	public void setPosition(float posX, float posY){
		this.posX = posX-numberToRender.length()*23;
		this.posY = posY-40;
	}

	public LevelPane getLevelPane() {
		return levelPane;
	}

	public void setLevelPane(LevelPane levelPane) {
		this.levelPane = levelPane;
	}
}
