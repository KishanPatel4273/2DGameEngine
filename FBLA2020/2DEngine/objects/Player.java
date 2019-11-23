package objects;

import controller.Controller;
import controller.InputHandler;
import window.Display;

public class Player extends Entity{

	private Controller controller;
	private int playerNumber;
	
	//Entity is a subset of player
	public Player(int x, int y, int width, int height, int playerNumber) {
		super(x, y, width, height, 0);//Initiates the entity object player's id is 0
		super.setTag("Player");
		setColor(255);
		this.playerNumber = playerNumber;
		//add controller module to player
		controller = new Controller();
	}
	
	//update the player's characteristics
	//overrides the entity update method
	public void update() {
		//calls an update to controller to let user update player
		controller.update(this, InputHandler.key, Display.numberOfTicks, playerNumber);
	}
}
