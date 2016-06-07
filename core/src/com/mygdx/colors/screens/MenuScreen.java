package com.mygdx.colors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.background.Background;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.hud.ButtonTile;
import com.mygdx.colors.hud.TiledButton;
import com.mygdx.colors.utils.ContactListener;

public class MenuScreen extends GameScreen{
	
	private TiledButton playButton;
	
	private TiledButton createdByButton;
	private TiledButton lucidDreamButton;
	private TiledButton colorsButton;
	
	private ColorMan colorMan;
	
	private boolean playerJumped;
	
	//Music
	private Music music;
	
	//Background
	private Background background;
	private Background background2;
	
	public MenuScreen(ColorsGame game){
		super(game);
		
		background = new Background(this, 3, "background");
		background2 = new Background(this, 3, "background2");
		
		this.playerJumped = true;
		
		playButton = new TiledButton(this, TiledButton.PLAY, 240, 240);
		createdByButton = new TiledButton(this, TiledButton.CREATED_BY, 20, 20);
		lucidDreamButton = new TiledButton(this, TiledButton.LUCID_DREAM, 
								createdByButton.getPosition().x+createdByButton.getWidth()+30, 20);
		colorsButton = new TiledButton(this, TiledButton.COLORS, 450,
				                        playButton.getPosition().y+playButton.getHeight()
				                        +130);
		
		this.colorMan = new ColorMan(this);
		this.colorMan.setPosition(playButton.getPosition().x+200, game.getVHeight()+colorMan.getHeight()*2);
    }
	
	@Override
	public void render(float delta) {
		update(delta);
		
		background.render(getSpriteBatch());
		background2.render(getSpriteBatch());
		
		colorsButton.render(getSpriteBatch());
		
		playButton.render(getSpriteBatch());
		colorMan.render(getSpriteBatch());
		createdByButton.render(getSpriteBatch());
		lucidDreamButton.render(getSpriteBatch());
	}

	@Override
	public void update(float delta){
		
		background.update(1.7f, 0, false);
		background2.update(0.8f, 0, false);
		
		colorsButton.update(delta);

		playButton.update(delta);
		createdByButton.update(delta);
		lucidDreamButton.update(delta);
		
		for(ButtonTile buttonTile: playButton.getButtonTiles()){
			ContactListener.checkCollisionBetweenEntities(buttonTile, colorMan);
		}
		
		float maxColorManPosition = playButton.getWidth()+playButton.getPosition().x-colorMan.getWidth();
		float minColorManPosition = playButton.getPosition().x;
		float jumpPosition = playButton.getPosition().x+playButton.getWidth()/2;
		
		if((colorMan.getDx()>0 && colorMan.getPosition().x>jumpPosition-colorMan.getWidth()) ||
				(colorMan.getDx() < 0 && colorMan.getPosition().x < jumpPosition)){
					if(!playerJumped){
						colorMan.impulse(colorMan.getDx(),10f);
						playerJumped = true;
					}
		}
		
		if(colorMan.getPosition().x > maxColorManPosition||colorMan.getPosition().x < minColorManPosition){
				colorMan.setDx(-colorMan.getDx());
				playerJumped = false;
		}
		
		colorMan.calculateGravity();
		colorMan.update(delta);
	}

	@Override 
	public void touchDown(int screenX, int screenY, int pointer, int button){
		playButton.touchDown(screenX, screenY, pointer, button);
		colorsButton.touchDown(screenX, screenY, pointer, button);

		lucidDreamButton.touchDown(screenX, screenY, pointer, button);
		createdByButton.touchDown(screenX, screenY, pointer, button);
	}
	
	@Override
	public void dispose(){
		music.dispose();
	}
	
	//Must be called
	public void createAndStartMusic(){
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/mainMenu.mp3"));
		music.setVolume(0.5f);
		music.setLooping(true);
		music.play();
	}

	@Override
	public void activateSpecificActions(){}
}