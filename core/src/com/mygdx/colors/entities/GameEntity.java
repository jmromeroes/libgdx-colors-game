package com.mygdx.colors.entities;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.interfaces.Actionable;
import com.mygdx.colors.interfaces.Collisionable;
import com.mygdx.colors.states.EntityState;

public abstract class GameEntity implements Actionable, Collisionable{
		
	private final HashMap<String, EntityState> statesMap;
	private EntityState currentState;
	private Vector2 position;
	private float alpha;
	private String color;
	private final boolean activableByColor;
	private boolean hidden;
	private float dx, dy;
	
	public GameEntity(boolean activableByColor){
		statesMap = new HashMap<String, EntityState>();
		this.activableByColor = activableByColor;
		this.color = color;
		this.alpha = 1;
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

	public abstract void update(float delta);
	
	public void render(SpriteBatch spriteBatch){
		if(!hidden){
			currentState.render(spriteBatch);
		}
	}
	
	public HashMap<String, EntityState> getStates() {
		return statesMap;
	}

	public EntityState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(EntityState currentState) {
		this.currentState = currentState;
	}
	
	public void setCurrentState(String stateKey){
		this.currentState = getStates().get(stateKey);
	}
	
	public void setPosition(float posX, float posY){
		position = new Vector2(posX, posY);
	}
	
	public void addEntityState(EntityState state) throws ResourceRepeatedInMapException{
		
		if(!statesMap.containsKey(state.getName())){
			statesMap.put(state.getName(), state);
		}else{
			throw new ResourceRepeatedInMapException("Entity State");
		}
		
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setWidth(int width){
		getCurrentState().getSprite().setSize(width, getCurrentState().getSprite().getHeight());
	}
	
	public float getWidth(){
		return getCurrentState().getSprite().getWidth();
	}
	
	public void setHeight(int height){
		getCurrentState().getSprite().setSize(getCurrentState().getSprite().getWidth(), height);
    }
	
	public float getHeight(){
		return getCurrentState().getSprite().getHeight();
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public String getColor(){
		return color;
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public boolean isActivableByColor() {
		return activableByColor;
	}

	@Override
	public void collisioningRight(GameEntity gameEntity){}

	@Override
	public void collisioningLeft(GameEntity gameEntity){}

	@Override
	public void collisioningUp(GameEntity gameEntity){}

	@Override
	public void collisioningDown(GameEntity gameEntity){}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public void impulse(float dx, float dy){
		setDx(dx);
		setDy(dy);
	}
	
	public void restartValues(){};
	
	public Rectangle getBoundingRectangle(){
		return new Rectangle(getPosition().x, getPosition().y, getWidth(), getHeight());
	}
}
