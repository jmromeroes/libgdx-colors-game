package com.mygdx.colors.elements;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.AnimatedState;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.AnimatedStateFactory;
import com.mygdx.colors.utils.Content;

public class Torch extends GameEntity{
	public static final String LIT = "tochLit";
	public static final String OFF = "off";
	
	private final AnimatedState litState;
	private final StaticState offState;
	private final PlayScreen playScreen;
	private Sound sound;
	
	public Torch(PlayScreen playScreen){
		super(false);
		
        this.playScreen = playScreen;
		this.sound = Content.getInstance().getSound("levelCompleted");
		
		litState = AnimatedStateFactory.getAnimatedState(this, LIT, LIT, 0.1f, true);
		
		offState = new StaticState(OFF, this, LIT, 1);
		
		try{
			addEntityState(litState);
			addEntityState(offState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(OFF);
	}


	@Override
	public void update(float delta){
		getCurrentState().update(delta);
	}


	@Override
	public void activate_action(GameEntity gameEntity){
		if(gameEntity instanceof ColorMan){
			if(ColorsGame.soundEnabled)
				sound.play(0.4f);
			
			((ColorMan)gameEntity).setDx(0);
			((ColorMan)gameEntity).setCurrentState(ColorMan.CELEBRATION);
			((ColorMan)gameEntity).setFlappy(false);
			if(playScreen.isSubgravitational()){
				playScreen.flipSubgravitation();
			}
			setCurrentState(LIT);
			this.playScreen.setLevelFinished(true);
		}
	}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
		activate_action(gameEntity);
	}

	@Override
	public void collisioningLeft(GameEntity gameEntity){
		activate_action(gameEntity);
	}

	@Override
	public void collisioningUp(GameEntity gameEntity){
		activate_action(gameEntity);
	}

	@Override
	public void collisioningDown(GameEntity gameEntity){
		activate_action(gameEntity);
	}
	
}
