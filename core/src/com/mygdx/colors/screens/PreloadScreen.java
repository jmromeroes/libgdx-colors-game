package com.mygdx.colors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class PreloadScreen extends GameScreen{
	
	public PreloadScreen(ColorsGame game){
		super(game);
		
		try{
			Content.getInstance().addTextureAtlas(GeneralInformation.MAIN_TEXTURE_ATLAS, new TextureAtlas("assets/images/ColorsGame.pack"));
			
			Content.getInstance().addTexture("board", new Texture("assets/images/HUD/board.jpg"));
			Content.getInstance().addTexture("background", new Texture("assets/images/HUD/background.jpg"));
			Content.getInstance().addTexture("background2", new Texture("assets/images/HUD/background2.png"));
			
			
			Content.getInstance().addSound("acceleration", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/acceleration.wav")));
			Content.getInstance().addSound("collision", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/collision.wav")));
			Content.getInstance().addSound("jump", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/jump.wav")));
			Content.getInstance().addSound("explosion", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/explosion.wav")));
			Content.getInstance().addSound("beep", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/beep.wav")));
			Content.getInstance().addSound("stars", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/stars.ogg")));
			Content.getInstance().addSound("station", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/station.wav")));
			Content.getInstance().addSound("subgravity", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/subgravity.wav")));
			Content.getInstance().addSound("powerUp", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/powerUp.wav")));
			Content.getInstance().addSound("levelCompleted", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/levelCompleted.wav")));
			Content.getInstance().addSound("flappy", Gdx.audio.newSound(Gdx.files.internal("assets/audio/effects/flappy.wav")));
			
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Ken/kenpixel_blocks.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 60;
			Content.getInstance().addFont(GeneralInformation.MAIN_FONT, generator.generateFont(parameter));
			generator.dispose();
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		getGame().setScreen(ColorsGame.MENU_SCREEN);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void createAndStartMusic(){}

	@Override
	public void activateSpecificActions() {
		// TODO Auto-generated method stub
		
	}
	
}
