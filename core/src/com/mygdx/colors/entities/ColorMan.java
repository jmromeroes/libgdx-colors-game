package com.mygdx.colors.entities;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.elements.DirectionTile;
import com.mygdx.colors.elements.Lock;
import com.mygdx.colors.elements.RegularTile;
import com.mygdx.colors.elements.Spike;
import com.mygdx.colors.exceptions.ResourceRepeatedInMapException;
import com.mygdx.colors.hud.Heart;
import com.mygdx.colors.interfaces.Gravitational;
import com.mygdx.colors.items.Star;
import com.mygdx.colors.particles.Particle;
import com.mygdx.colors.screens.GameScreen;
import com.mygdx.colors.screens.PlayScreen;
import com.mygdx.colors.states.AnimatedState;
import com.mygdx.colors.states.StaticState;
import com.mygdx.colors.utils.AnimatedStateFactory;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class ColorMan extends GameEntity implements Gravitational{

	//GLOBAL STATES NAMES
	public static final String JUMP_RIGHT = "p1_jump_right";
	public static final String WALK_RIGHT = "p1_walkright";
	public static final String REST_RIGHT = "p1_rest_right";
	public static final String HURT_RIGHT = "p1_hurt_right";
	
	public static final String JUMP_LEFT = "p1_jump_left";
	public static final String WALK_LEFT = "p1_walkleft";
	public static final String REST_LEFT = "p1_rest_left";
	public static final String HURT_LEFT = "p1_hurt_left";
	
	public static final String FRONT = "p1_front";
	
	public static final String CELEBRATION = "p1_celebration";
	
	public static final String FLAPPY = "flappy";
	
	//Going right information
	private final StaticState jumpingRightState;
	private final AnimatedState walkingRightState;
	private final StaticState restingRightState;
	private final StaticState hurtRightState;
	
	//Going left information
	private final StaticState jumpingLeftState;
	private final AnimatedState walkingLeftState;
	private final StaticState restingLeftState;
	private final StaticState hurtLeftState;
	
	//Front information
	private final StaticState frontState;
	
	//Celebration information
	private final AnimatedState celebrationState;
	
	//Flappy information
	private final StaticState flappyState;
	
	//Movement information
	private float speed;
	
	//Initial Position 
	private Vector2 initialPosition;
	
	//PlayScreen
	private GameScreen gameScreen;
	
	//Boolean States
	private boolean dead;
	private boolean accelerating;
	private boolean flappy;
	
	//Collectibles
	private int stars;
	
	private int blueKeys;
	private int redKeys;
	private int greenKeys;
	private int yellowKeys;
	
	private Sound dieSound;
	private Sound flappySound;
	
	private int lifes;
	
	private ArrayList<Heart> hearts;
	
	public ColorMan(GameScreen gameScreen){
		super(false);

		dieSound = Content.getInstance().getSound("explosion");
		setFlappySound(Content.getInstance().getSound("flappy"));
		
		this.dead = false;
		this.stars = 0;
		this.gameScreen = gameScreen;
		this.accelerating = false;
		this.flappy = false;
		this.lifes = 3;
		
		hearts = new ArrayList<Heart>();
		
		for(int i = 0; i < lifes; i++){
			Heart heart = new Heart(gameScreen, i*70+200, gameScreen.getGame().getVHeight()-100);
			hearts.add(heart);
		}
		
		//Flappy state
		flappyState = new StaticState(FLAPPY, this, FLAPPY);
		
		//Going right states
		jumpingRightState = new StaticState(JUMP_RIGHT, this, JUMP_RIGHT);
		
		walkingRightState = AnimatedStateFactory.getAnimatedState(this, WALK_RIGHT, WALK_RIGHT, 0.075f, true);
		
		restingRightState = new StaticState(REST_RIGHT, this, REST_RIGHT, 1);
		hurtRightState    = new StaticState(HURT_RIGHT, this, HURT_RIGHT);
		frontState        = new StaticState(FRONT, this, FRONT);
		jumpingLeftState  = new StaticState(JUMP_LEFT, this, JUMP_LEFT);
		walkingLeftState  = AnimatedStateFactory.getAnimatedState(this, WALK_LEFT, WALK_LEFT, 0.075f, true);
		restingLeftState  = new StaticState(REST_LEFT, this, REST_LEFT, 1);
		hurtLeftState     = new StaticState(HURT_LEFT, this, HURT_LEFT);
		celebrationState  = AnimatedStateFactory.getAnimatedState(this, CELEBRATION, CELEBRATION, 0.15f, true);
		
		//Movement info
		speed = 9.4f;
		setDx(speed);
		setDy(0);
		
		//Adding states
		try{
			addEntityState(jumpingRightState);
			addEntityState(walkingRightState);
			addEntityState(restingRightState);
			addEntityState(hurtRightState);
			
			addEntityState(jumpingLeftState);
			addEntityState(walkingLeftState);
			addEntityState(restingLeftState);
			addEntityState(hurtLeftState);
			
			addEntityState(frontState);
			
			addEntityState(celebrationState);
			
			addEntityState(flappyState);
		}catch(ResourceRepeatedInMapException exception){
			exception.printStackTrace();
		}
		
		setCurrentState(walkingRightState);
	}

	@Override
	public void activate_action(GameEntity gameEntity){}

	@Override
	public void update(float delta){
		if(!dead){
			getCurrentState().update(delta);
			
			setPosition(getPosition().x+getDx(), getPosition().y+getDy());
		
			if(getDx()>0 && !isFlappy()){
				setCurrentState(WALK_RIGHT);
			}
		
			if(getDx()<0  && !isFlappy()){
				setCurrentState(WALK_LEFT);
			}
			
			if(gameScreen.isSubgravitational() && !getCurrentState().getSprite().isFlipY()){
				getCurrentState().getSprite().flip(false, true);
			}
			
			if(!gameScreen.isSubgravitational() && getCurrentState().getSprite().isFlipY()){
				getCurrentState().getSprite().flip(false, true);
			}
			
			for(int i = 0; i < hearts.size(); i++){
				hearts.get(i).update(delta);
			}
			
			if(lifes<=0 && !((PlayScreen)gameScreen).isLevelFinished()){
				setDead(true);
				((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
						  getPosition().y+getHeight()/2, 
					      Particle.COLORS_MAN_PARTICLE, 30);
			}
		}
	}
	
	@Override
	public void render(SpriteBatch spriteBatch){
		if(!dead){
			super.render(spriteBatch);
		}
		if(gameScreen instanceof PlayScreen){
			for(int i = 0; i<hearts.size(); i++){
				hearts.get(i).render(spriteBatch);
			}
		}
	}
	
	@Override
	public void calculateGravity(){
		if(!gameScreen.isSubgravitational()){
			setDy(getDy()-0.35f);
		}else{
			setDy(getDy()+0.35f);
		}
		
		if(getDx()>0 && getDx()>speed){
			setDx(getDx()-0.15f);
		}else if(getDx()>0 && getDx()<speed){
			if(accelerating){
			   accelerating = false; 
			}
			setDx(speed);
		}
		
		if(getDx()<0 && getDx()<-speed){
			setDx(getDx()+0.15f);
		}else if(getDx()<0 && getDx()>-speed){
			if(accelerating){
			}
			setDx(-speed);
		}
	}
	
	public StaticState getJumpingRightState() {
		return jumpingRightState;
	}

	public AnimatedState getWalkingRightState() {
		return walkingRightState;
	}

	public StaticState getRestingRightState() {
		return restingRightState;
	}

	public StaticState getHurtRightState() {
		return hurtRightState;
	}

	public StaticState getJumpingLeftState() {
		return jumpingLeftState;
	}

	public AnimatedState getWalkingLeftState() {
		return walkingLeftState;
	}

	public StaticState getRestingLeftState() {
		return restingLeftState;
	}

	public StaticState getHurtLeftState() {
		return hurtLeftState;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void collisioningRight(GameEntity gameEntity) {
		if(gameEntity instanceof DirectionTile || gameEntity instanceof Spike
				|| gameEntity instanceof CrazyEnemy || gameEntity instanceof HappyEnemy
				|| gameEntity instanceof SadEnemy || gameEntity instanceof AngryEnemy){
			setDx(0);
			setDy(0);
			dead = true;
			if(gameScreen instanceof PlayScreen)
				((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
													  getPosition().y+getHeight()/2, 
												      Particle.COLORS_MAN_PARTICLE, 30);
			if(ColorsGame.soundEnabled)
				dieSound.play();
		}else if(gameEntity instanceof RegularTile){
			if(accelerating){
				gameEntity.setHidden(true);
				((PlayScreen)gameScreen).addExplosion(gameEntity.getPosition().x+gameEntity.getWidth()/2,
						gameEntity.getPosition().y+gameEntity.getHeight()/2, 
						"tile_"+gameEntity.getColor()+"_particle", 
						10);
			}else{
				setDx(0);
				setDy(0);
				dead = true;
				if(gameScreen instanceof PlayScreen)
					((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
														  getPosition().y+getHeight()/2, 
													      Particle.COLORS_MAN_PARTICLE, 30);
			}
			if(ColorsGame.soundEnabled)
				dieSound.play();
		}else if((gameEntity instanceof Lock)){
			setDx(-speed);
			setPosition(gameEntity.getPosition().x-getWidth(), getPosition().y);
		}
		}

	@Override
	public void collisioningLeft(GameEntity gameEntity) {
		if(gameEntity instanceof DirectionTile
			|| gameEntity instanceof Spike || gameEntity instanceof CrazyEnemy || gameEntity instanceof HappyEnemy
			|| gameEntity instanceof SadEnemy || gameEntity instanceof AngryEnemy){
			setDx(0);
			setDy(0);
			dead = true;
			if(gameScreen instanceof PlayScreen)
				((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
													  getPosition().y+getHeight()/2, 
												      Particle.COLORS_MAN_PARTICLE, 30);
			if(ColorsGame.soundEnabled)
				dieSound.play();
	    }else if(gameEntity instanceof RegularTile){
			if(accelerating){
				gameEntity.setHidden(true);
				((PlayScreen)gameScreen).addExplosion(gameEntity.getPosition().x+gameEntity.getWidth()/2,
						gameEntity.getPosition().y+gameEntity.getHeight()/2, 
						"tile_"+gameEntity.getColor()+"_particle", 
						10);
			}else{
				setDx(0);
				setDy(0);
				dead = true;
				if(gameScreen instanceof PlayScreen)
					((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
														  getPosition().y+getHeight()/2, 
													      Particle.COLORS_MAN_PARTICLE, 30);
			}
			if(ColorsGame.soundEnabled)
				dieSound.play();
	    }else if((gameEntity instanceof Lock)){
			setDx(speed);
			setPosition(gameEntity.getPosition().x+gameEntity.getWidth(), getPosition().y+2);
		}
	}

	@Override
	public void collisioningUp(GameEntity gameEntity){
		if(gameEntity instanceof Spike || gameEntity instanceof CrazyEnemy || gameEntity instanceof HappyEnemy
				|| gameEntity instanceof SadEnemy || gameEntity instanceof AngryEnemy){
			setDx(0);
			setDy(0);
			dead = true;
			if(gameScreen instanceof PlayScreen)
				((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
													  getPosition().y+getHeight()/2, 
												      Particle.COLORS_MAN_PARTICLE, 30);
			if(ColorsGame.soundEnabled)
				dieSound.play();
		}else if(!(gameEntity instanceof Star)){
			setDy(0);
			setPosition(getPosition().x, gameEntity.getPosition().y-getHeight()-2);
			
			if(isFlappy()){
				setDx(0);
				setDy(0);
				dead = true;
				if(gameScreen instanceof PlayScreen)
					((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
														  getPosition().y+getHeight()/2, 
													      Particle.COLORS_MAN_PARTICLE, 30);
				if(ColorsGame.soundEnabled)
					dieSound.play();
			}
		}
	}

	@Override
	public void collisioningDown(GameEntity gameEntity) {
		if(gameEntity instanceof Spike || gameEntity instanceof CrazyEnemy || gameEntity instanceof HappyEnemy
					|| gameEntity instanceof SadEnemy || gameEntity instanceof AngryEnemy){
			setDx(0);
			setDy(0);
			dead = true;
			if(gameScreen instanceof PlayScreen)
				((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
													  getPosition().y+getHeight()/2, 
												      Particle.COLORS_MAN_PARTICLE, 30);
			if(ColorsGame.soundEnabled)
				dieSound.play();
		}else if(!(gameEntity instanceof Star)){
			setDy(0);
			setPosition(getPosition().x, gameEntity.getPosition().y+gameEntity.getHeight());
			
			if(isFlappy()){
				setDx(0);
				setDy(0);
				dead = true;
				if(gameScreen instanceof PlayScreen)
					((PlayScreen)gameScreen).addExplosion(getPosition().x+getWidth()/2, 
														  getPosition().y+getHeight()/2, 
													      Particle.COLORS_MAN_PARTICLE, 30);
				if(ColorsGame.soundEnabled)
					dieSound.play();
			}
		}
	}
	
	public void impulse(float fx, float fy){
		fx = (fx==0 && getDx()>0)?speed:fx;
		fx = (fx==0 && getDx()<0)?-speed:fx;
		
		setDy(fy);
		setDx(fx);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2 initialPosition) {
		this.initialPosition = initialPosition;
	}
	
	@Override
	public void setPosition(float posX, float posY){
		if(initialPosition == null){
			initialPosition = new Vector2(posX, posY);
		}
		super.setPosition(posX, posY);
	}
	
	public int getStars(){
		return this.stars;
	}
	
	public void addStar(){
		stars++;
	}
	
	public void setStars(int stars){
		this.stars = stars;
	}
	
	
	public void addKey(String color){
		if(color.equals(GeneralInformation.BLUE)){
			blueKeys++;
		}else if(color.equals(GeneralInformation.GREEN)){
			greenKeys++;
		}else if(color.equals(GeneralInformation.YELLOW)){
			yellowKeys++;
		}else if(color.equals(GeneralInformation.RED)){
			redKeys++;
		}
	}
	
	public void removeKey(String color){
		if(color.equals(GeneralInformation.BLUE)){
			blueKeys--;
		}else if(color.equals(GeneralInformation.GREEN)){
			greenKeys--;
		}else if(color.equals(GeneralInformation.YELLOW)){
			yellowKeys--;
		}else if(color.equals(GeneralInformation.RED)){
			redKeys--;
		}
	}

	public int getBlueKeys(){
		return blueKeys;
	}
	
	public int getGreenKeys(){
		return greenKeys;
	}
	
	public int getYellowKeys(){
		return yellowKeys;
	}
	
	public int getRedKeys(){
		return redKeys;
	}
	
	public void restartValues(){
		stars=0;
		blueKeys=0;
		greenKeys=0;
		yellowKeys=0;
		lifes = 3;
		setFlappy(false);
		
		for(int i= 0; i < hearts.size(); i++){
			hearts.get(i).setHidden(false);
		}
	}

	public boolean isAccelerating() {
		return accelerating;
	}

	public void setAccelerating(boolean accelerating) {
		this.accelerating = accelerating;
	}
	
	public Sound getDieSound(){
		return dieSound;
	}
	
	public void setFlappy(boolean flappy){
		this.flappy = flappy;
		if(flappy){
			setCurrentState(FLAPPY);
		}else{
			setCurrentState(getDx()>0?WALK_RIGHT:WALK_LEFT);
		}
	}
	
	public boolean isFlappy(){
		return flappy;
	}

	public Sound getFlappySound() {
		return flappySound;
	}

	public void setFlappySound(Sound flappySound) {
		this.flappySound = flappySound;
	}
	
	public void lifeDown(){
		if(lifes>0){
			Heart heart = hearts.get(lifes-1);
			heart.setHidden(true);
			heart.blowUp();
			if(ColorsGame.soundEnabled)
				dieSound.play();
			lifes--;
		}
	}
	
	public boolean isSubgravitational(){
		return gameScreen.isSubgravitational();
	}
	
	public void flipSubgravitation(){
		gameScreen.flipSubgravitation();
	}
}
