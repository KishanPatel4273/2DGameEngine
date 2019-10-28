package objects;

import controller.Controller;
import controller.InputHandler;

public class Player extends Entity{

	private Controller controller;
	
	//Entity is a subset of player
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height, 0);//Initiates the entity object player's id is 0
		super.setTag("Player");
		setColor(255);
		
		//add controller module to player
		controller = new Controller();
	}
	
	//update the player's characteristics
	//overrides the entity update method
	public void update() {
		//calls an update to controller to let user update player
		controller.update(this, InputHandler.key);
	}
}
