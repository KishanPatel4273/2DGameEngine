package window;

import java.awt.event.KeyEvent;

import controller.InputHandler;
import engine.Engine;
import tools.*;

public class Screen {


	public static String[] screenTypeTag = {"Start" , "level Select", "Game"};
	public static boolean[] screenTypeState= {true, false, false};
	
	public Engine engine;

	public Screen(int width, int height) {
		engine = new Engine(width, height);
	}
	
	public void update() {
		engine.run();//runs engines loop
	}
	
	
	/**
	 * @param screen with tag tag is turn true while everything else is turn to false
	 */
	public static void turnOnScreen(String tag) {
		for(int i = 0; i < screenTypeTag.length; i++) {
			if(screenTypeTag[i].equals(tag)) {//correct screen tag is located and turned true
				screenTypeState[i] = true;
			} else {//all other screens are turns off
				screenTypeState[i] = false;
			}
		}
	}
}
