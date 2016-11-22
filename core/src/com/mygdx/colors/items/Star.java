package com.mygdx.colors.items;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.particles.Particle;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public class Star extends GameEntity{
	public static final String MAIN_STATE = "star"; 
	
	private final StaticState starState;
	private final PlayScreen playScreen;
	private final Sound sound;
	
	public Star(PlayScreen playScreen){
		super(false);
		sound = Content.getInstance().getSound("stars");
		this.playScreen = playScreen;
		
		starState = new StaticState(MAIN_STATE, this, "star");

		try{
			addEntityState(starState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN_STATE);
		
		setWidth(60);
		setHeight(60);
	}

	@Override
	public void update(float delta){}

	@Override 
	public void activate_action(GameEntity gameEntity) {}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
		collitioning(gameEntity);
	}

	@Override
	public void collisioningLeft(GameEntity gameEntity){
		collitioning(gameEntity);
	}

	@Override
	public void collisioningUp(GameEntity gameEntity){
		collitioning(gameEntity);
	}

	@Override
	public void collisioningDown(GameEntity gameEntity){
		collitioning(gameEntity);
	}
	
	public void collitioning(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan && !isHidden()){
			ColorMan colorMan = (ColorMan) gameEntity;
			colorMan.addStar();
			setHidden(true);
			playScreen.addExplosion(getPosition().x+getWidth()/2, 
									getPosition().y+getHeight()/2, 
									Particle.STAR_PARTICLE, 10);
			if(ColorsGame.soundEnabled)
				sound.play(1f);
		}
	}
	
}
