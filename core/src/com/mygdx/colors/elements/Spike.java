package com.mygdx.colors.elements;

import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.states.StaticState;

public class Spike extends GameEntity{
	
	public static final String MAIN = "spikes";
	
	private final StaticState mainState;
	
	public Spike(){
		super(false);
        mainState = new StaticState(MAIN, this, MAIN);
		
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
	}

	@Override
	public void activate_action(GameEntity gameEntity){
	}

	@Override
	public void update(float delta) {
	}
}
