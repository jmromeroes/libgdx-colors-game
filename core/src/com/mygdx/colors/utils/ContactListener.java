package com.mygdx.colors.utils;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.colors.entities.GameEntity;

public class ContactListener{

	public static void checkCollisionBetweenEntities(GameEntity entity, GameEntity entity2){
		if(!entity.equals(entity2)){
			if(!entity.isHidden() && !entity2.isHidden()){
				Rectangle entity2Rectangle = entity2.getBoundingRectangle();
				Rectangle entityRectangle = entity.getBoundingRectangle();
				
				entity2Rectangle.y += entity2.getDy();
				entityRectangle.y += entity.getDy();
				
				if(entity2Rectangle.overlaps(entityRectangle)){
					float colorManX = entity2.getPosition().x;
					float colorManY = entity2.getPosition().y+entity2.getDy();
					float colorManWidth = entity2.getWidth();
					float colorManHeight = entity2.getHeight();
					
					float entityX = entity.getPosition().x==colorManX?entity.getPosition().x+1f:entity.getPosition().x;
					float entityY = entity.getPosition().y;
					float entityWidth = entity.getWidth();
					float entityHeight = entity.getHeight();
					
					if(colorManX>=entityX && colorManX <= entityX + entityWidth){
						if(colorManY>entityY && colorManY < entityY + entityHeight){
							if(entityY+entityHeight-colorManY<30){
								entity2.collisioningDown(entity);
								entity.collisioningUp(entity2);
							}
						}
					}
					
					if(colorManX+colorManWidth>=entityX && colorManX+colorManWidth <= entityX + entityWidth){
						if(colorManY>entityY && colorManY < entityY + entityHeight){
							entity2.collisioningDown(entity);
							entity.collisioningUp(entity2);
						}
					}
					
					if(colorManX>=entityX && colorManX <= entityX + entityWidth){
						if(colorManY+colorManHeight>entityY && colorManY+colorManHeight <= entityY + entityHeight){
							if(Math.abs(entityY-(colorManY+colorManHeight))<30){
								entity2.collisioningUp(entity);
								entity.collisioningDown(entity2);
							}
						}
					}
					
					if(colorManX+colorManWidth>=entityX && colorManX+colorManWidth <= entityX + entityWidth){
						if(colorManY+colorManHeight>entityY && colorManY+colorManHeight <= entityY + entityHeight){
							if(Math.abs(entityY-(colorManY+colorManHeight))<30){
								entity2.collisioningUp(entity);
				 				entity.collisioningDown(entity2);
							}
						}
					}
				}
				
				entity2Rectangle.x += entity2.getDx();
				entityRectangle.x += entity.getDx();
				
				if(entity2Rectangle.overlaps(entityRectangle)){
					float colorManX = entity2.getPosition().x+entity2.getDx();
					float colorManY = entity2.getPosition().y;
					float colorManWidth = entity2.getWidth();
					float colorManHeight = entity2.getHeight();
					
					float entityX = entity.getPosition().x;
					float entityY = entity.getPosition().y;
					float entityWidth = entity.getWidth();
					float entityHeight = entity.getHeight();
					
					if(colorManX>entityX && colorManX < entityX + entityWidth){
						if(colorManY>entityY && colorManY < entityY + entityHeight){
							entity2.collisioningLeft(entity);
								entity.collisioningRight(entity2);
						}
					}
					
					if(colorManX>entityX && colorManX < entityX + entityWidth){
						if(colorManY+colorManHeight/2>entityY && colorManY+colorManHeight/2< entityY + entityHeight){
								entity2.collisioningLeft(entity);
								entity.collisioningRight(entity2);
						}
					}
					
					if(colorManX+colorManWidth>entityX && colorManX+colorManWidth < entityX + entityWidth){
						if(colorManY>entityY && colorManY < entityY + entityHeight){
								entity2.collisioningRight(entity);
								entity.collisioningLeft(entity2);
						}
					}
					
					if(colorManX>entityX && colorManX < entityX + entityWidth){
						if(colorManY+colorManHeight>entityY && colorManY+colorManHeight<entityY + entityHeight){
								entity2.collisioningLeft(entity);
								entity.collisioningRight(entity2);
						}
					}
					
					if(colorManX+colorManWidth>entityX && colorManX+colorManWidth < entityX + entityWidth){
						if(colorManY+colorManHeight>entityY && colorManY+colorManHeight < entityY + entityHeight){
								entity2.collisioningRight(entity);
								entity.collisioningLeft(entity2);
						}
					}
					
					if(colorManX+colorManWidth>entityX && colorManX+colorManWidth < entityX + entityWidth){
						if(colorManY+colorManHeight/2>entityY && colorManY+colorManHeight/2 < entityY + entityHeight){
								entity2.collisioningRight(entity);
								entity.collisioningLeft(entity2);
						}
					}
				}
			}
		
		}
	}
}
