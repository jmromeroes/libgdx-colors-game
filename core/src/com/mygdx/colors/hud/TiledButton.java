package com.mygdx.colors.hud;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.particles.Explosion;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.LevelSelectionScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class TiledButton{
	
	//Public final information
	public static final int[][] PLAY_TEXT = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,1,1,1,1,0,1,0,0,0,0,1,1,1,1,0,1,0,0,1,0,0,0,0,0,1},
			{1,0,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,1},
			{1,0,0,0,0,0,1,1,1,1,0,1,0,0,0,0,1,1,1,1,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,1,0,0,0,0,1,1,1,1,0,1,0,0,1,0,0,1,1,0,0,0,0,0,0,1},	
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};

	public static final int[][] NEXT_TEXT = {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,0,0,1,0,0,0,1},
			{1,0,1,1,0,0,1,1,0,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,0,0,1,1,0,0,1},
			{1,0,1,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
	};
	
	public static final int[][] BACK_TEXT = {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,0,1,0,1,0,1,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,0,1,0,1,0,1,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
	};
	
	public static final int[][] CREATED_BY_TEXT = {
			{1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,0,0,0,0,1,1,1,0,0,1,0,0,1,0,0},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,1,0,0,1},
			{1,0,0,0,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,0,1,1,0,0,1,1,1,1,0,1,0,0,1,0,0,0,1,1,1,0,0,0,1,1,0,0,0},
			{1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,1,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,1,0,0,0},
			{1,1,1,1,0,1,0,0,1,0,1,1,1,1,0,1,0,0,1,0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,0,0,0,1,1,1,0,0,0,1,1,0,0,1}			
	};
	
	public static final int[][] LUCID_DREAM_TEXT = {
			{1,0,0,0,0,1,0,0,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,0,0,0,1,1,1,0,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,1,0,1,0,0,1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,0,0,0,1},
			{1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,0,0,0,1,1,1,0,0,1,0,0,1,0,1,1,1,1,0,1,0,0,1,0,1,0,1,0,1,0,1,1,1,1}
	};
	
	public static final int[][] COLORS_TEXT = {
			{1,1,1,1,0,1,1,1,1,0,1,0,0,0,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,0,1,0,0},
			{1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,0,0,1,0,1,1,1,1,0,1}
	};
	
	public static final int[][] TRY_AGAIN_TEXT = {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,0,1},
			{1,0,1,0,0,0,0,1,0,0,1},
			{1,0,1,0,0,0,1,1,1,0,1},
			{1,0,1,0,0,0,0,1,0,0,1},
			{1,0,1,1,1,1,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
	};
	
	public static final int[][] NEXT_LEVEL_SET_TEXT = {
			{1,0,0,0},
			{1,1,0,0},
			{1,1,1,0},
			{1,1,1,1},
			{1,1,1,0},
			{1,1,0,0},
			{1,0,0,0},
	};
	
	public static final int[][] PREVIOUS_LEVEL_SET_TEXT = {
			{0,0,0,1},
			{0,0,1,1},
			{0,1,1,1},
			{1,1,1,1},
			{0,1,1,1},
			{0,0,1,1},
			{0,0,0,1},
	};
	
	public static final int[][] PAUSE_TEXT = {
			{1,1,1,1,1,1,1},
			{1,0,0,0,0,0,1},
			{1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1},
			{1,0,0,0,0,0,1},
			{1,1,1,1,1,1,1},
	};
	
	public static final int[][] CONTINUE_PLAYING_TEXT = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,0,0,0,0,0,1},
			{1,0,0,1,1,0,0,0,0,1},
			{1,0,0,1,1,1,0,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,0,0,0,1},
			{1,0,0,1,1,0,0,0,0,1},
			{1,0,0,1,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}
	};
	
	public static final int[][] SOUND_ENABLED_TEXT = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,1,1,1,0,1},
			{1,0,0,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,0,1,1,1,1,0,1},
			{1,0,0,0,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}
	};
	
	public static final int[][] SOUND_NOT_ENABLED_TEXT = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,1,0,0,0,0,0,0,1,1},
			{1,0,1,0,0,1,1,1,0,1},
			{1,0,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,1,1,1,1,1,0,1},
			{1,0,1,0,0,1,1,1,0,1},
			{1,1,0,0,0,0,0,0,1,1},
			{1,1,1,1,1,1,1,1,1,1}
	};
	
	public static final int BACK=1;
	public static final int NEXT=2;
	public static final int PLAY=3;
	public static final int CREATED_BY=4;
	public static final int LUCID_DREAM=5;
	public static final int COLORS=6;
	public static final int TRY_AGAIN=7;
	public static final int PREVIOUS_LEVEL_SET=8;
	public static final int NEXT_LEVEL_SET=9;
	public static final int CONTINUE_PLAYING=10;
	public static final int SOUND_ENABLED=11;
	public static final int SOUND_NOT_ENABLED=12;
	public static final int PAUSE=13;
	
	//General information 
	private int textIndex;
	
	private Vector2 position;
	
	private boolean activated;
	
	private int height;
	private int width;
	
	private int tileSize;
	
	//Main Arrays 
	private ArrayList<Explosion> explosions;
	private ArrayList<ButtonTile> buttonTiles;
	
	private GameScreen gameScreen;
	
	private Sound sound;
	
	/*
	 * @params textIndex MUST be one of the previous defined final Integers (BACK, NEXT, PLAY, etc)
	 */ 
	public TiledButton(GameScreen gameScreen, int textIndex, float posX, float posY){
		this.gameScreen = gameScreen;
		this.setPosition(posX, posY);
		this.textIndex = textIndex;
		this.sound = Content.getInstance().getSound("beep");
		this.setActivated(false);
		
		setExplosions(new ArrayList<Explosion>());
		buttonTiles = new ArrayList<ButtonTile>();
		
		switch(textIndex){
			case BACK:
					setArrayWidth(11);
					setArrayHeight(11);
					this.tileSize = 8;
					createButton(BACK_TEXT);
				break;
			case NEXT:
					setArrayWidth(11);
					setArrayHeight(9);
					this.tileSize = 9;
					createButton(NEXT_TEXT);
				break;
			case PLAY:
					setArrayWidth(31);
					setArrayHeight(9);
					this.tileSize = 28;
					createButton(PLAY_TEXT);
				break;
			case CREATED_BY:
					setArrayWidth(48);
					setArrayHeight(5);
					this.tileSize = (int)(Math.random()*2+6);
					createButton(CREATED_BY_TEXT);
				break;
			case LUCID_DREAM:
					setArrayWidth(56);
					setArrayHeight(5);
					this.tileSize = (int)(Math.random()*2+6);
					createButton(LUCID_DREAM_TEXT);
				break;
			case COLORS:
					setArrayWidth(31);
					setArrayHeight(5);
					this.tileSize = 14;
					createButton(COLORS_TEXT);
				break;
			case TRY_AGAIN:
					setArrayWidth(11);
					setArrayHeight(9);
					this.tileSize = 9;
					createButton(TRY_AGAIN_TEXT);
			    break;
			case PREVIOUS_LEVEL_SET:
					setArrayWidth(4);
					setArrayHeight(7);
					this.tileSize = 30;
					createButton(PREVIOUS_LEVEL_SET_TEXT);
				break;
			case NEXT_LEVEL_SET:
					setArrayWidth(4);
					setArrayHeight(7);
					this.tileSize = 30;
					createButton(NEXT_LEVEL_SET_TEXT);
				break;
			case CONTINUE_PLAYING:
				setArrayWidth(10);
				setArrayHeight(11);
				this.tileSize = 7;
				createButton(CONTINUE_PLAYING_TEXT);
			break;
			case SOUND_ENABLED:
				setArrayWidth(10);
				setArrayHeight(11);
				this.tileSize = 10;
				createButton(SOUND_ENABLED_TEXT);
			break;
			case SOUND_NOT_ENABLED:
				setArrayWidth(10);
				setArrayHeight(11);
				this.tileSize = 10;
				createButton(SOUND_NOT_ENABLED_TEXT);
			break;
			case PAUSE:
				setArrayWidth(7);
				setArrayHeight(7);
				this.tileSize = 10;
				createButton(PAUSE_TEXT);
			break;
		}
	}
	
	public void update(float delta){
		
		if(textIndex == PAUSE){
			setPosition(gameScreen.getCamera().getPosition().x,
					gameScreen.getCamera().getPosition().y+gameScreen.getGame().getVHeight()/2-100);
		}
		
		for(ButtonTile buttonTile: buttonTiles){
			buttonTile.update(delta);
		}
		
		for(int i = 0; i< explosions.size(); i++){
			explosions.get(i).update(delta);
			if(explosions.get(i).isExtinct()){
				explosions.remove(i);
				i--;
			}
		}
		
		if(activated && explosions.isEmpty()){
			if(textIndex == PLAY)
				gameScreen.getGame().setScreen(ColorsGame.LEVEL_SELECTION_SCREEN);
			else if(textIndex == TRY_AGAIN){
				PlayScreen playScreen = (PlayScreen) gameScreen;
				playScreen.setLevel(playScreen.getLevel());
			}else if(textIndex == NEXT){
				PlayScreen playScreen = (PlayScreen) gameScreen;
				playScreen.setLevel(playScreen.getLevel()+1);
			}else if(textIndex == PREVIOUS_LEVEL_SET){
				LevelSelectionScreen levelSelectionScreen = (LevelSelectionScreen) gameScreen;
				levelSelectionScreen.levelDown();
			}else if(textIndex == NEXT_LEVEL_SET){
				LevelSelectionScreen levelSelectionScreen = (LevelSelectionScreen) gameScreen;
				levelSelectionScreen.levelUp();
			}else if(textIndex == BACK){
				gameScreen.getGame().setScreen(ColorsGame.LEVEL_SELECTION_SCREEN);
			}else if(textIndex == CONTINUE_PLAYING){
				((PlayScreen)gameScreen).closePauseBoard();
			}
		}
	}
	
	public void render(SpriteBatch spriteBatch){
		for(ButtonTile buttonTile: buttonTiles){
			buttonTile.render(spriteBatch);
		}
		
		for(Explosion explosion: explosions){
			explosion.render(spriteBatch);
		}
	}

	public Vector2 getPosition(){
		return position;
	}

	public void setPosition(float posX, float posY) {
		this.position = new Vector2(posX, posY);
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public int getArrayHeight() {
		return height;
	}
	
	public float getHeight(){
		return height*tileSize;
	}
	
	public void setArrayHeight(int height) {
		this.height = height;
	}

	public int getArrayWidth() {
		return width;
	}
	
	public float getWidth(){
		return width*tileSize;
	}
	
	public void setArrayWidth(int width) {
		this.width = width;
	}

	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}

	public void setExplosions(ArrayList<Explosion> explosions) {
		this.explosions = explosions;
	}

	public ArrayList<ButtonTile> getButtonTiles() {
		return buttonTiles;
	}

	public void setButtonTiles(ArrayList<ButtonTile> buttonTiles) {
		this.buttonTiles = buttonTiles;
	}
	
	public void touchDown(int screenX, int screenY, int pointer, int button){
		screenY = Gdx.graphics.getHeight() - screenY;
		
		float vwidth = gameScreen.getGame().getVWidth();
		float vheight = gameScreen.getGame().getVHeight();
		float cameraX = gameScreen.getCamera().getPosition().x-vwidth/2;
		float cameraY = gameScreen.getCamera().getPosition().y-vheight/2;
		
		screenY =(int)(gameScreen.getGame().getVHeight()*screenY/Gdx.graphics.getHeight()+cameraY);
		screenX =(int)(gameScreen.getGame().getVWidth()*screenX/Gdx.graphics.getWidth()+cameraX);
		
		if(screenX>getPosition().x && screenX < getPosition().x+getWidth()){
			if(screenY>getPosition().y && screenY < getPosition().y+getHeight()){
				activated = true;
				if(ColorsGame.soundEnabled)
					sound.play(0.7f);
				for(int i = 0; i < buttonTiles.size(); i++){
					ButtonTile buttonTile = buttonTiles.get(i);
					explosions.add(new Explosion(buttonTile.getPosition().x+buttonTile.getWidth()/2,
												 	buttonTile.getPosition().y+buttonTile.getHeight()/2,
												 	"tile_"+buttonTile.getColor()+"_particle", 3));
					buttonTiles.remove(i);
					i--;
				}
			  }
			}	
		
			if(activated && textIndex == PAUSE){
				if(!((PlayScreen)gameScreen).getColorMan().isDead())
					((PlayScreen)gameScreen).createPauseBoard();
				else{
					createButton(PAUSE_TEXT);
				}
			}
			
			if(activated && textIndex == SOUND_ENABLED){
				ColorsGame.soundEnabled=false;
			}
			
			if(activated && textIndex == SOUND_NOT_ENABLED){
				ColorsGame.soundEnabled=true;
			}
		}
	
	public void createButton(int[][] buttonText){
		for(int y=0; y<getArrayHeight(); y++){
			for(int x=0; x<getArrayWidth();x++){
				if(buttonText[y][x]==1){
				   ButtonTile buttonTile = new ButtonTile(gameScreen, this, x, getArrayHeight()-y-1, true);
				   buttonTile.setSize(tileSize);
				   
				   if(textIndex == NEXT){
					   buttonTile.setColor(GeneralInformation.RED);
				   }else if(textIndex == BACK){
					   buttonTile.setColor(GeneralInformation.BLUE);
				   }else if(textIndex == TRY_AGAIN){
					   buttonTile.setColor(GeneralInformation.GREEN);
				   }else if(textIndex == PAUSE){
					   buttonTile.setColor(GeneralInformation.BLUE);
				   }else if(textIndex == CONTINUE_PLAYING){
					   buttonTile.setColor(GeneralInformation.RED);
				   }else if(textIndex == SOUND_ENABLED || textIndex == SOUND_NOT_ENABLED){
					   buttonTile.setColor(GeneralInformation.YELLOW);
				   }
				   buttonTiles.add(buttonTile);
				}
			}
		}
	}
	
	public Sound getSound(){
		return sound;
	}
	
	public int getIndex(){
		return textIndex;
	}
}
