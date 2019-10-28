package controller;

import java.awt.event.KeyEvent;

import engine.PhysicsEngine;
import objects.Player;
import tools.Vector;

public class Controller {
	
	//Characteristics of player's movement and environment
	private int currentSpeed, walkSpeed, runSpeed, jumpSpeed, gravity;

	
	//add controller to player
	public Controller() {
		//sets constants for the game
		currentSpeed = (int) 15;
		walkSpeed = 10;
		runSpeed = 15;
		jumpSpeed = (256 + 16)*2;
	}
	
	public void update(Player player, boolean[] key) {
		Vector force = new Vector();
		if(key[KeyEvent.VK_D]) {
			force.addX(currentSpeed);
		}
		if(key[KeyEvent.VK_A]) {
			force.addX(-currentSpeed);
		}
		if(key[KeyEvent.VK_S]) {
			force.addY(-currentSpeed);
		}
		if((key[KeyEvent.VK_W] || key[KeyEvent.VK_SPACE])) {
			force.addY(currentSpeed);
		}
		
		player.addVector(force);
		
		Vector collisonFix = PhysicsEngine.collisionDetection(player.getCollider());
		
		
		player.addVector(collisonFix);
		//System.out.println(player.getCenter().toString());
	}
	
}
