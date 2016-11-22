package com.mygdx.colors.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class SadEnemy extends GameEntity{
	public static final String MAIN = "sad_madBlock";
	
	private final StaticState mainState;
	private final Sound collisioningSound;
	
	private boolean goingUp;
	private boolean goingRight;
	
	private float speed;
	
	private int Xindex, Yindex;
	
	private Vector2 initialPosition;
	
	private PlayScreen playScreen;
	
	public SadEnemy(PlayScreen playScreen){
		super(false);

		this.goingUp = false;
		this.goingRight = true;
		
		this.playScreen = playScreen;
		
		collisioningSound = Content.getInstance().getSound("collision");
		this.speed = 5f;
		
		mainState = new StaticState(MAIN, this, MAIN);
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		setWidth(200);
		setHeight(200);
	}

	@Override
	public void activate_action(GameEntity gameEntity){
	}

	@Override
	public void update(float delta){
		calculateGravity();
		setPosition(getPosition().x+getDx(), getPosition().y+getDy());
	}
	
	@Override
	public void collisioningUp(GameEntity gameEntity){
		if(!(gameEntity instanceof ColorMan)){
			setDy(0);
			setPosition(getPosition().x, gameEntity.getPosition().y-getHeight()-1);
		}
	}
	
	@Override
	public void collisioningDown(GameEntity gameEntity){
		if(!(gameEntity instanceof ColorMan)){
			setPosition(getPosition().x, gameEntity.getPosition().y+gameEntity.getHeight()+1);
			
			if(goingRight){
				impulse(-3,18);
			}else{
				impulse(3,18);
			}
			goingRight = !goingRight;
			playScreen.getColorMan().impulse(playScreen.getColorMan().getDx(), 
											 playScreen.getColorMan().getDy()==0?5f:playScreen.getColorMan().getDy());
			if(ColorsGame.soundEnabled){
				collisioningSound.play(0.3f);
				Gdx.input.vibrate(30);
			}
		}
	}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
		setPosition(gameEntity.getPosition().x-getWidth()-1, getPosition().y);
	}
	
	@Override
	public void collisioningLeft(GameEntity gameEntity){
		setPosition(gameEntity.getPosition().x+gameEntity.getWidth()+1, getPosition().y);
	}
	
	@Override
	public void setPosition(float x, float y){
		if(initialPosition == null){
			initialPosition = new Vector2(x,y);
		}
		super.setPosition(x, y);
	}
	
	public void setGoingUp(boolean goingUp){
		this.goingUp = goingUp;
	}
	
	public void calculateGravity(){
		if(!playScreen.isSubgravitational()){
			setDy(getDy()-0.55f);
		}else{
			setDy(getDy()+0.55f);
		}
	}

	public void restartValues(){
		setPosition(initialPosition);
		setDx(0);
		setDy(0);
		goingRight = true;
	}
}
