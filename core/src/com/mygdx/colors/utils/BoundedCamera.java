package com.mygdx.colors.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.screens.GameScreen;

public class BoundedCamera extends OrthographicCamera{
		private float xmin, xmax, ymin, ymax;
		private ColorMan colorMan;
		private Vector3 initialPosition;
		private float speed;
		
		private float lastX, lastY;
		
		private float dx, dy;
		
		private GameScreen gameScreen;
		
		public void setPosition(float x, float y){
			setPosition(x,y,0);
		}
		
		public Vector2 getPosition(){
			return new Vector2(position.x, position.y);
		}
		
		public void setPosition(float x, float y, float z){
			if(initialPosition == null){
				initialPosition = new Vector3(x, y, z);
			}
			
			position.set(x, y, z);
		}
		
		public void fixBounds(){
			
			if(position.x + viewportWidth/2 > xmax) {
				position.x = xmax - viewportWidth/2;
			}
			
			if(position.x - viewportWidth/2< xmin) {
				position.x = xmin + viewportWidth/2;
			}
			
			if(position.y + viewportHeight/2 > ymax) {
				position.y = ymax - viewportHeight/2;
			}
			
			if(position.y - viewportHeight/2< ymin) {
				position.y = ymin + viewportHeight/2;
			}
			
		}
		
		public void setBounds(float xmin, float xmax, float ymin, float ymax){
				this.xmin = xmin;
				this.xmax = xmax;
				this.ymin = ymin;
				this.ymax = ymax;
		}
		
		public void update(){
			if(colorMan != null){
				if(!colorMan.isDead()){
					setPosition(colorMan.getPosition().x, colorMan.getPosition().y, 0);
				}
			}
			
			if(gameScreen == null || !gameScreen.isShaking()){
				fixBounds();
			}
			
			dx = getPosition().x - lastX;
			dy = getPosition().y - lastY;
			
			lastX = getPosition().x;
			lastY = getPosition().y;
			
			super.update();
		}
		
		public ColorMan getColorMan() {
			return colorMan;
		}

		public void setColorMan(ColorMan colorMan) {
			this.colorMan = colorMan;
		}

		public float getXmin() {
			return xmin;
		}

		public void setXmin(float xmin) {
			this.xmin = xmin;
		}

		public float getXmax() {
			return xmax;
		}

		public void setXmax(float xmax) {
			this.xmax = xmax;
		}

		public float getYmin() {
			return ymin;
		}

		public void setYmin(float ymin) {
			this.ymin = ymin;
		}

		public float getYmax() {
			return ymax;
		}

		public void setYmax(float ymax) {
			this.ymax = ymax;
		}

		public Vector3 getInitialPosition() {
			return initialPosition;
		}

		public void setInitialPosition(Vector3 initialPosition) {
			this.initialPosition = initialPosition;
		}

		public float getDy() {
			return dy;
		}

		public void setDy(float dy) {
			this.dy = dy;
		}

		public float getDx() {
			return dx;
		}

		public void setDx(float dx) {
			this.dx = dx;
		}
		
		public GameScreen getGameScreen(){
			return gameScreen;
		}
		
		public void setGameScreen(GameScreen gameScreen){
			this.gameScreen = gameScreen;
		}
}