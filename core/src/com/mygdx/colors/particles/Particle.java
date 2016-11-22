package com.mygdx.colors.particles;

import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class Particle extends GameEntity{
	
	//Public information
	public static final String COLORS_MAN_PARTICLE = "colorsMan_particle";
	public static final String STAR_PARTICLE = "star_particle";
	
	//General Information
	private double angle;
	private float size;
	private float speed;
	private float dx, dy;
	
	private boolean isExtinct;
	
	//States information
	private StaticState mainState;

	public static final String MAIN_STATE = "main";
	
	public Particle(String textureName){
		super(false);
		this.setExtinct(false);
		
		mainState = 
			new StaticState(
				MAIN_STATE, 
				this,
				Content.getInstance()
				       .getTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS)
				       .createSprite(textureName)
			);
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
		    exception.printStackTrace();
		}
		
		setCurrentState(MAIN_STATE);
	}
	
	@Override
	public void update(float delta){
		size-=3;
		
		if(size<0)
			setExtinct(true);

		dx = (float)Math.cos(angle)*speed;
		dy = (float)Math.sin(angle)*speed;
		
		getCurrentState().getSprite().setSize(size, size);
		setPosition(getPosition().x+dx, getPosition().y+dy);
	}
	
	@Override
	public void activate_action(GameEntity gameEntity) {}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public boolean isExtinct() {
		return isExtinct;
	}

	public void setExtinct(boolean isExtinct) {
		this.isExtinct = isExtinct;
	}
}
