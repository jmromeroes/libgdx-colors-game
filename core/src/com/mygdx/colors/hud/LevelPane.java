package com.mygdx.colors.hud;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.particles.Explosion;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public class LevelPane extends GameEntity{
	public static final String MAIN = "levelPane";
	
	private int level;
	private StaticState paneState;
	private boolean outOfScreen;
	
	private Vector2 posIn;
	private Vector2 posOut;
	private Vector2 positionToGo;
	
	private ColorsGame game;
	
	//This booleans do not refer about movement but about level number
	private boolean goingUp;
	private boolean goingDown;
	
	private boolean withPointToGo;
	
	private float speed;

	private RenderizableNumber number;
	
	private Sound sound;
	
	public LevelPane(ColorsGame game, float posX, float posY){
		super(false);
		posIn = new Vector2(posX, posY);
		positionToGo = posIn;
		
		this.sound = Content.getInstance().getSound("beep");
		this.game  = game;
		this.speed = 40;
		
		Random random = new Random();
		
		switch((random.nextInt(4))){
		case 0:
			setPositionOut(-random.nextFloat()*50f,
								random.nextFloat()*(float)game.getVHeight());
			break;
		case 1:
			setPositionOut(random.nextFloat()*50f+(float)game.getVWidth(),
								random.nextFloat()*(float)game.getVHeight());
			break;
		case 2:
			setPositionOut( random.nextFloat()*(float)game.getVWidth(),
								-random.nextFloat()*50f);
			break;
		case 3:
			setPositionOut((float)game.getVWidth(),
								random.nextFloat()*50f+(float)game.getVHeight());
			break;
		}
		
		setPosition(posOut);
		goingUp = false;
		goingDown = false;
		withPointToGo = true;
		
		paneState = new StaticState(MAIN, this, MAIN);
		
		try{
			addEntityState(paneState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		getCurrentState().getSprite().setSize(150, 150);
	}
	
	@Override 
	public void render(SpriteBatch spriteBatch){
		super.render(spriteBatch);
		if(!isHidden())
			number.render(spriteBatch);
	}
	@Override
	public void update(float delta){
		if(!withPointToGo){
			if(!goingUp && !goingDown){				
				setPosition(posIn);
			}
		}else if(!goingUp && !goingDown){
			if(getPosition().x<positionToGo.x){
				setPosition(getPosition().x+speed, getPosition().y);
			}else{
				setPosition(getPosition().x-speed, getPosition().y);
			}
			
			if(getPosition().y<positionToGo.y){
				setPosition(getPosition().x, getPosition().y+speed);
			}else{
				setPosition(getPosition().x, getPosition().y-speed);
			}
			
			if(Math.abs(positionToGo.y-getPosition().y)<=50
			   && Math.abs(positionToGo.x-getPosition().x)<=50){
					withPointToGo=false;
			}
		}else if(goingUp){
			setPosition(getPosition().x, getPosition().y-speed);
			
			if(getPosition().y <= -getHeight()){
				getPosition().y = game.getVHeight();
				getPosition().x = getPosition().x;
				setLevel(level+16);
				goingUp = false;
				withPointToGo = true;
			}
		}else if(goingDown){
			setPosition(getPosition().x, getPosition().y+speed);
			
			if(getPosition().y >= game.getVHeight()){
				getPosition().y = -getHeight();
				getPosition().x = getPosition().x;
				setLevel(level-16);
				goingDown = false;
				withPointToGo = true;
			}
		}
		
		number.update(delta);
	}
	
	public void setLevel(int level){
		this.level = level;
		number = new RenderizableNumber(level);
		number.setLevelPane(this);
	}
	
	public void goUp(){
		goingUp = true;
		withPointToGo=true;
	}
	
	public void goDown(){
		goingDown = true;
		withPointToGo=true;
	}
	
	public void setPositionOut(float posOutX, float posOutY){
		posOut = new Vector2(posOutX, posOutY);
	}
	
	public Vector2 getPositionOut(){
		return posOut;
	}
	
	public void touchDown(int screenX, int screenY, int pointer, int button){
		screenY = Gdx.graphics.getHeight() - screenY;
		
		float vwidth = game.getVWidth();
		float vheight = game.getVHeight();
		float cameraX = game.getCamera().getPosition().x-vwidth/2;
		float cameraY = game.getCamera().getPosition().y-vheight/2;
		
		screenY =(int)(game.getVHeight()*screenY/Gdx.graphics.getHeight()+cameraY);
		screenX =(int)(game.getVWidth()*screenX/Gdx.graphics.getWidth()+cameraX);
		
		if(screenX>getPosition().x && screenX < getPosition().x+getWidth()){
			if(screenY>getPosition().y && screenY < getPosition().y+getHeight()){
				sound.play(0.7f);
				if(!isHidden())
					game.setLevel(getRenderizableNumber().getNumber());
			  }
			}	
	}
		
	@Override
	public void activate_action(GameEntity gameEntity){}
	
	public RenderizableNumber getRenderizableNumber(){
		return number;
	}
}
