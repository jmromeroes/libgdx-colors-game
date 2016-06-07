package com.mygdx.colors.interfaces;

import com.mygdx.colors.entities.GameEntity;

public interface Collisionable {
	public void collisioningRight(GameEntity gameEntity);
	public void collisioningLeft(GameEntity gameEntity);
	public void collisioningUp(GameEntity gameEntity);
	public void collisioningDown(GameEntity gameEntity);
}
