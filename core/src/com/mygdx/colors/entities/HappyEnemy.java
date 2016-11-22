package com.mygdx.colors.entities;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.Content;

public class HappyEnemy extends GameEntity{
	public static final String MAIN = "happy_madBlock";
	
	private StaticState mainState;
	
	private boolean goingTransparent;
	
	private float alpha;
	
	private int Xindex, Yindex;
	
	private Vector2 initialPosition;
	
	private PlayScreen playScreen;
	
	private float timeHidden;
	
	public HappyEnemy(PlayScreen playScreen){
		super(false);
		this.goingTransparent = false;
		
		this.playScreen = playScreen;
		
		mainState = new StaticState(MAIN, this, MAIN);
		
		timeHidden = 0.0f;
		try{
			addEntityState(mainState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(MAIN);
		setWidth(100);
		setHeight(100);
	}

	@Override
	public void activate_action(GameEntity gameEntity){
	}

	@Override
	public void update(float delta){
		if(goingTransparent){
			if(!isHidden()){
				alpha-=0.01;
				if(alpha<=0.1){
					setHidden(true);
				}
			}else{
				timeHidden += delta;
				if(timeHidden > 1){
					setHidden(false);
					timeHidden = 0;
					goingTransparent = false;
				}
			}
		}else{
			
			if(alpha>=0.9){
				goingTransparent=true;
			}else if(!isHidden()){
				alpha+=0.01;
			}
		}

		getCurrentState().getSprite().setAlpha(alpha);
	}
	
	@Override
	public void collisioningUp(GameEntity gameEntity){
	}
	
	@Override
	public void collisioningDown(GameEntity gameEntity){
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
		if(initialPosition == null){
			initialPosition = new Vector2(x,y);
		}
	
		super.setPosition(x, y);
	}
	
	public void restartValues(){
		setPosition(initialPosition);
		setDx(0);
		setDy(0);
	}
}
