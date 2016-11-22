package com.mygdx.colors.particles;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colors.entities.GameEntity;

public class Explosion extends GameEntity{
	
	private final ArrayList<Particle> particles;
	private final Random random;
	private boolean isExtinct;
	
	public Explosion(float posX, float posY, String particleTextureName, int numberOfParticles){
		super(false);
		particles = new ArrayList<Particle>();
		
		random = new Random();
		
		for(int i = 0; i < numberOfParticles; i++){
			Particle particle = new Particle(particleTextureName);
			particle.setPosition(posX, posY);
			particle.setAngle(random.nextDouble()*2*Math.PI);
			particle.setSize(random.nextFloat()*
							(particleTextureName.equals(Particle.COLORS_MAN_PARTICLE)?100:70));
			particle.setSpeed(random.nextFloat()*10);
			
			particles.add(particle);
		}
	}
	
	public void update(float delta){
		for(int i = 0; i<particles.size(); i++){
			particles.get(i).update(delta);
			if(particles.get(i).isExtinct()){
				particles.remove(i);
				i--;
			}
		}
		
		if(particles.size()==0)
			setExtinct(true);
		
	}
	
	public void render(SpriteBatch spriteBatch){
		for(Particle particle: particles)
			particle.render(spriteBatch);
	}

	@Override
	public void activate_action(GameEntity gameEntity){}

	public boolean isExtinct() {
		return isExtinct;
	}

	public void setExtinct(boolean isExtinct) {
		this.isExtinct = isExtinct;
	}
}
