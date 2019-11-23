package window;

import java.awt.event.KeyEvent;

import controller.InputHandler;
import engine.Engine;
import tools.*;

public class Screen {

	public Engine engine;
	public PerlinNoise np;
	
	int oct = 1;
	public Screen(int width, int height) {
		engine = new Engine(width, height);
		//np = new PerlinNoise(750, 1/100f, .5f, 3f, oct, new Vector(0, 400), 3454367); 
	}
	
	public void update() {
		engine.run();//runs engines loop
		//engine.draw2DNoise(np.mappedNoise, new Vector(0, 250), 255);
		//if(InputHandler.key[KeyEvent.VK_W]) {
		///	oct++;
		//	np = new PerlinNoise(750, 1/100f, .5f, 2f, oct, new Vector(0, 400), 3454367); 

		//}
	}
}
