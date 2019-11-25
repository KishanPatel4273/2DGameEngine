package controller;

import java.awt.event.KeyEvent;

import engine.Engine;
import engine.PhysicsEngine;
import objects.Player;
import sound.SoundEffect;
import tools.Ray;
import tools.Vector;

public class Controller {
	
	//Characteristics of player's movement and environment
	private Vector currentVelocityX, jumpVelocity, gravity;
	private float walkSpeed, runSpeed, g;
	
	private Vector velocity = new Vector();
	private Vector velocityDirection = new Vector();
	
	//tick rate is the # of times the method is called per second
	public float tickrate = 1/20f;
	private long initialTick;
	private boolean applyGravity = true;
	
	private SoundEffect sound = new SoundEffect("res/button-4.wav");
	private SoundEffect sound1 = new SoundEffect("res/button-4.wav");
	private SoundEffect sound2 = new SoundEffect("res/Woosh.wav");

	
	//add controller to player
	public Controller() {
		//sets constants for the game
		currentVelocityX = new Vector(walkSpeed*tickrate, 0);// p/s * 1/tick
		walkSpeed = 100;
		runSpeed = 200;
		g = 200 * 10;
		gravity = new Vector(0, -g);
		jumpVelocity = new Vector(0, 200*3);
	}
	
	public void update(Player player, boolean[] key, long currentTick, int playerNumber) {
		Vector displacement = new Vector();
		boolean[] keys = new boolean[5];
		if(key[KeyEvent.VK_R]) {
			sound.play();
		}
		if(key[KeyEvent.VK_T]) {
			sound1.loop();
		}
		if(key[KeyEvent.VK_Y]) {
			sound1.stop();
		}

		if(key[KeyEvent.VK_F]) {
			sound2.play();
		}
		
		//keybinds for each player
		if(playerNumber == 1) {
			keys[0] = key[KeyEvent.VK_SHIFT];
			keys[1] = key[KeyEvent.VK_D];
			keys[2] = key[KeyEvent.VK_A];		
			keys[3] = key[KeyEvent.VK_W];
			keys[4] = key[KeyEvent.VK_SPACE];
		} else if(playerNumber == 2) {
			keys[0] = key[KeyEvent.VK_M];
			keys[1] = key[KeyEvent.VK_RIGHT];
			keys[2] = key[KeyEvent.VK_LEFT];		
			keys[3] = key[KeyEvent.VK_SLASH];
			keys[4] = key[KeyEvent.VK_UP];
		}
		
		if(keys[0]) {
			currentVelocityX = new Vector(runSpeed*tickrate, 0);// p/s * 1/tick
		} else {
			currentVelocityX = new Vector(walkSpeed*tickrate, 0);// p/s * 1/tick
		}
		
		//horizontal velocity last for 1 tick
		if(keys[1]) {//plater moves to the right
			velocity.setX(currentVelocityX.getX());
		}
		if(keys[2]) {//player moves to the left
			velocity.setX(Vector.scale(-1, currentVelocityX).getX());
		}
		if(keys[3]) {//testing purposes
			displacement.addVector(Vector.scale(1, new Vector(0, 50)));
		}
		
		//distance form ground, distance is in pixel values kind of
		int distanceToGround = (int) Ray.rayCasting(Vector.add(player.getCenter(), new Vector(0, -player.getHeight()/2)), new Vector(0, -1));
		
		//checks if player jumps and can jump
		if(keys[4] && distanceToGround == 0) {//to jump must be on a surface
			applyGravity = true;//the player will be in the air so gravity is needed
			initialTick = currentTick;//indicates the tick when the player jumps
			velocity = (Vector.scale(1, jumpVelocity));//gives the player vertical velocity
			//initialVelocity = velocity;
		}
		//a ceiling is directly above the player and if the player is moving vertically
		if(velocity.getY() != 0 && (int) Ray.rayCasting(Vector.add(player.getCenter(), new Vector(0, player.getHeight()/2)), new Vector(0, 1)) == 0) {
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
			//
			velocityDirection.setY(velocity.getY() + gravity.getY() * deltaTick * tickrate);

			//get a deltaY to move player at given tick relative to last tick
			float deltaDisplacementY = PhysicsEngine.projectileMotion(velocity.getY(), gravity.getY(), deltaTick, tickrate) - PhysicsEngine.projectileMotion(velocity.getY(), gravity.getY(), deltaTick-1, tickrate);
			if(deltaDisplacementY*-1 > distanceToGround) {//if deltaY update causes collision
				deltaDisplacementY = -distanceToGround;//makes deltaY snap the player to the ground
				velocityDirection.setY(0);
			}
			displacement.addY(deltaDisplacementY);//updates the players position by deltaY
		}
		//because horizontal velocity last for 1 tick, can apply x = vt t-1tick so x = v
		velocityDirection.setX(velocity.getX());
		displacement.addX(velocity.getX());
		
		
		player.addY((int) displacement.getY());//updates players final movement in y axis
		Engine.translateFrame(new Vector(displacement.getX(), 0));//applies x displacement to frame
		
		
		Vector collisionFix = new Vector();
		if(displacement.getMagnitude() != 0) {//if player doen't move their is no need to check for collision
			//check if collision happens and fixes them too(apply fix vector)

			collisionFix = PhysicsEngine.collisionFix(player.getCollider());	
		}
		velocity.setX(0);//set to 0 after collision so let physics engine know current velocity
		
		player.addY((int) collisionFix.getY());//updates players final movement in y axis
		Engine.translateFrame(new Vector(collisionFix.getX(), 0));//applies x displacement to frame
		
		
		//player.addVector(collisionFix);//fixes players position to stop collision
	}
}
