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

public class AngryEnemy extends GameEntity{
	public static final String MAIN = "angry_madBlock";
	
	private final StaticState mainState;
	private final Sound collisioningSound;
	
	private boolean goingUp;
	
	private int Xindex, Yindex;
	
	private Vector2 initialPosition;
	
	private final PlayScreen playScreen;
	
	public AngryEnemy(PlayScreen playScreen){
		super(false);
		this.goingUp = false;
		
		this.playScreen = playScreen;
		
		collisioningSound = Content.getInstance().getSound("collision");
		
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
			impulse(0,17);
			playScreen.getColorMan().impulse(playScreen.getColorMan().getDx(), 
											 playScreen.getColorMan().getDy()==0?5f:playScreen.getColorMan().getDy());
			if(ColorsGame.soundEnabled){
				Gdx.input.vibrate(30);
				collisioningSound.play(0.3f);
			}
		}
	}
	
	@Override
	public void collisioningRight(GameEntity gameEntity){
	}
	
	@Override
	public void collisioningLeft(GameEntity gameEntity){
	}
	
	@Override
	public void setPosition(float x, float y){
//		try{
//			int Xindex2 = (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize());
//			int Yindex2 = (int)((y+5)/playScreen.getTileSize());
//			
//			if(Xindex == 0 || Yindex == 0){
//				Xindex = (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize());
//				Yindex = (int)((y)/playScreen.getTileSize());
//			}
//			
//			if(Xindex != (int)((x-GeneralInformation.INITIAL_MAP_X)/playScreen.getTileSize())){
//				if(playScreen.getGameEntitiesArray()[Yindex][Xindex2]==null){
//					playScreen.getGameEntitiesArray()[Yindex][Xindex]=null;
//					Xindex = Xindex2;
//					playScreen.getGameEntitiesArray()[Yindex][Xindex]=this;
//				}
//			}
//				
//			if(Yindex != (int)((y+5)/playScreen.getTileSize())){
//				if(playScreen.getGameEntitiesArray()[Yindex2][Xindex]==null){
//					playScreen.getGameEntitiesArray()[Yindex][Xindex]=null;
//					Yindex = Yindex2;
//					playScreen.getGameEntitiesArray()[Yindex][Xindex]=this;
//				}
//			}
//		}catch(ArrayIndexOutOfBoundsException e){}
//		
//		
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
	}
}
