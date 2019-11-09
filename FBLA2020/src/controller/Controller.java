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

	//tick rate is the # of times the method is called per second
	public float tickrate = 1/20f;
	private long initialTick;
	private boolean applyGravity = true;
	
	//add controller to player
	public Controller() {
		//sets constants for the game
		currentVelocityX = new Vector(walkSpeed*tickrate, 0);// p/s * 1/tick
		walkSpeed = 200;
		runSpeed = 350;
		g = 200 * 10;
		gravity = new Vector(0, -g);
		jumpVelocity = new Vector(0, 1);
	}
	
	public void update(Player player, boolean[] key, long currentTick) {
		Vector displacement = new Vector();
		
		if(key[KeyEvent.VK_SHIFT]) {
			currentVelocityX = new Vector(runSpeed*tickrate, 0);// p/s * 1/tick
		} else {
			currentVelocityX = new Vector(walkSpeed*tickrate, 0);// p/s * 1/tick
		}
		
		//horizontal velocity last for 1 tick
		if(key[KeyEvent.VK_D]) {//plater moves to the right
			velocity.setX(currentVelocityX.getX());
		}
		if(key[KeyEvent.VK_A]) {//plater moves to the left
			velocity.setX(Vector.scale(-1, currentVelocityX).getX());
		}
		if(key[KeyEvent.VK_W]) {//testing purposes
			displacement.addVector(Vector.scale(1, new Vector(0, 50)));
		}
		
		//distance form ground, distance is in pixel values kind of
		int distanceToGround = (int) Ray.rayCasting(Vector.add(player.getCenter(), new Vector(0, -player.getHeight()/2)), new Vector(0, -1));
		
		//checks if player jumps and can jump
		if(key[KeyEvent.VK_SPACE] && distanceToGround == 0) {//to jump must be on a surface
			applyGravity = true;//the player will be in the air so gravity is needed
			initialTick = currentTick;//indicates the tick when the player jumps
			velocity = (Vector.scale(200*3, jumpVelocity));//gives the player vertical velocity				
		}
		//a ceiling is directly above the player
		if((int) Ray.rayCasting(Vector.add(player.getCenter(), new Vector(0, player.getHeight()/2)), new Vector(0, 1)) == 0) {
			//ray is casted up from the top face of the player if is 0 a ceiling is above it with no pixels in-between
			velocity.setY(0);//resets the vertical velocity
		}
		//if player is in the air and gravity isn't on
		if(distanceToGround != 0  && !applyGravity) {
			applyGravity = true;//the player will be in the air so gravity is needed
			initialTick = currentTick;//indicates the tick when the player jumps
		}
		if(distanceToGround == 0 && initialTick != currentTick) {
			applyGravity = false;//stops gravity b/c plater is on ground 
			velocity.setY(0);//resets the vertical velocity
		}
		if(applyGravity) {//updates player with kinematics 
			float deltaTick = currentTick - initialTick;//time in air
			//get a deltaY to move player at given tick relative to last tick
			float deltaDisplacementY = PhysicsEngine.projectileMotion(velocity.getY(), gravity.getY(), deltaTick, tickrate) - PhysicsEngine.projectileMotion(velocity.getY(), gravity.getY(), deltaTick-1, tickrate);
			if(deltaDisplacementY*-1 > distanceToGround) {//if deltaY update causes collision
				deltaDisplacementY = -distanceToGround;//makes deltaY snap the player to the ground
			}
			displacement.addY(deltaDisplacementY);//updates the players position by deltaY
		}
		//because horizontal velocity last for 1 tick, can apply x = vt t-1tick so x = v
		displacement.addX(velocity.getX());
		
		player.addVector(displacement);//updates players final movement
		//check if collision happens and fixes them too(apply fix vector)
		Vector collisonFix = PhysicsEngine.collisionDetection(player.getCollider());	
		velocity.setX(0);//set to 0 after collision so let physics engine know current velocity
		player.addVector(collisonFix);//fixes players position to stop collision
	}
}
