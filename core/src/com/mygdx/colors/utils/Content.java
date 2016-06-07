package com.mygdx.colors.utils;

import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;

public class Content {
	private HashMap<String, Texture> texturesMap;
	private HashMap<String, TextureAtlas> textureAtlasMap; 
	private HashMap<String, Sound> soundsMap; 
	private HashMap<String, BitmapFont> bitmapFontsMap; 
	
	private static Content instance;
	
	private Content(){
		texturesMap = new HashMap<String, Texture>();
		textureAtlasMap = new HashMap<String, TextureAtlas>();
		soundsMap = new HashMap<String, Sound>();
		bitmapFontsMap = new HashMap<String, BitmapFont>();
	}
	

	public static Content getInstance(){
		if(instance == null){
			instance = new Content();
		}
		
		return instance;
	}
	
	public void addTexture(String imageName, Texture image) throws ResourceRepeatedInMapException{
		if(!texturesMap.containsKey(imageName)){
			texturesMap.put(imageName, image);
		}else{
			throw new ResourceRepeatedInMapException("Image");
		}
	}
	
	public void removeTexture(String imageName){
		if(texturesMap.containsKey(imageName)){
			texturesMap.remove(imageName);
		}
	}
	
	public Texture getTexture(String key){
			return texturesMap.get(key);				
	}
	
	public void addTextureAtlas(String atlasName, TextureAtlas textureAtlas) throws ResourceRepeatedInMapException{
		if(!textureAtlasMap.containsKey(atlasName)){
			textureAtlasMap.put(atlasName, textureAtlas);
		}else{
			throw new ResourceRepeatedInMapException("Atlas");
		}
	}
	
	public TextureAtlas getTextureAtlas(String key){
		return textureAtlasMap.get(key);
	}
	
	public void removeTextureAtlas(String key){
		textureAtlasMap.remove(key);
	}
	
	public void addSound(String soundName, Sound sound) throws ResourceRepeatedInMapException{
		if(!soundsMap.containsKey(soundName)){
			soundsMap.put(soundName, sound);
		}else{
			throw new ResourceRepeatedInMapException("Sound");
		}
	}
	
	public void removeSound(String soundName){
		if(soundsMap.containsKey(soundName)){
			soundsMap.remove(soundName);
		}
	}
	
	public Sound getSound(String key){
		return soundsMap.get(key);				
	}
	
	public void addFont(String fontName, BitmapFont bitmapFont) throws ResourceRepeatedInMapException{
		if(!bitmapFontsMap.containsKey(fontName)){
			bitmapFontsMap.put(fontName, bitmapFont);
		}else{
			throw new ResourceRepeatedInMapException("BitmapFont");
		}
	}
	
	public void removeFont(String key){
		bitmapFontsMap.remove(key);
	}
	
	public BitmapFont getFont(String key){
		return bitmapFontsMap.get(key);
	}
}