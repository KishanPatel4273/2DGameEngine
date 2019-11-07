package controller;

import java.awt.event.KeyEvent;

import engine.PhysicsEngine;
import objects.Player;
import tools.Ray;
import tools.Vector;

public class Controller {
	
	//Characteristics of player's movement and environment
	private Vector currentVelocityX, jumpVelocity, gravity;
	private float walkSpeed, runSpeed, g;
	
	private Vector velocity = new Vector();
	private float jumpDuration;
	private boolean jumped = false;
	//tick rate is the # of times the method is called per second
	public float tickrate = 1/20f;
	private long initialTick;
	private boolean applyGravity = false;
	
	//add controller to player
	public Controller() {
		//sets constants for the game
		currentVelocityX = new Vector(15, 0);
		walkSpeed = 10;
		runSpeed = 15;
		g = 20;
		gravity = new Vector(0, -g);
		jumpVelocity = new Vector(0, 25);
		jumpDuration = - (jumpVelocity.getY()  * tickrate) / (gravity.getY() * 1/2 * tickrate *tickrate);
	}
	
	public void update(Player player, boolean[] key, long currentTick) {
		Vector displacement = new Vector();
		if(key[KeyEvent.VK_D]) {
			displacement.addVector(currentVelocityX);
		}
		if(key[KeyEvent.VK_A]) {
			displacement.addVector(Vector.scale(-1, currentVelocityX));
		}
		if(key[KeyEvent.VK_S]) {
		}
		
		//distance form ground, distance is in pixel values kind of
		int distanceToGround = (int) Ray.rayCasting(Vector.add(player.getCenter(), new Vector(0, -player.getHeight()/2)), new Vector(0, -1));
		
		if(key[KeyEvent.VK_SPACE] && velocity.getMagnitude() == 0) {
			velocity = (Vector.scale(1f, jumpVelocity));
			initialTick = currentTick;
			jumped = true;
		}
		
		if(distanceToGround > 0) {
			displacement.addY(gravity.getY());
		}
		
		//player jumped
		if(jumped && jumpDuration >= (currentTick - initialTick)) { 
			int deltaTick = (int) (currentTick - initialTick);
			float deltaY = PhysicsEngine.projectileMotion(velocity.getY(), gravity.getY(), deltaTick, tickrate);
			//DELTAY DOENT WORK GIVES HIEIGHTS NO NEGATIVES
			System.out.println(deltaY);
			//displacement.addY(deltaY);
		}
		
		/**
		if(velocity.getMagnitude() != 0) {
			if(velocity.getY() < g) {
				velocity.setY(0);
			} else {
				velocity = Vector.add(velocity, gravity);
			}
		}
		**/
		
		player.addVector(displacement);
		
		
		Vector collisonFix = PhysicsEngine.collisionDetection(player.getCollider());
		
		
		
		player.addVector(collisonFix);
	}
	
}
