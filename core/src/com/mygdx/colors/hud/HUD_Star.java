package com.mygdx.colors.hud;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public class HUD_Star extends GameEntity{
	
public static final String MAIN = "hud_star"; 
	
	private StaticState starState;
	private Sound sound;
	
	public HUD_Star(){
		super(false);
		sound = Content.getInstance().getSound("stars");
		
		starState = new StaticState(MAIN,this,MAIN);

		try{
			addEntityState(starState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		
		setWidth(150);
		setHeight(150);
	}

	@Override
	public void update(float delta){}

	@Override 
	public void activate_action(GameEntity gameEntity) {}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
	}

	@Override
	public void collisioningLeft(GameEntity gameEntity){
	}

	@Override
	public void collisioningUp(GameEntity gameEntity){
	}

	@Override
	public void collisioningDown(GameEntity gameEntity){
	}
	
	public void collitioning(GameEntity gameEntity){
	}
	
}
