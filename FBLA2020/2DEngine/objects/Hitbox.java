package objects;

import engine.Render;

public class Hitbox extends Entity{
	
	/**
	 * hitbox is an invisible entity
	 */
	public Hitbox(int x, int y, int width, int height) {
		super(x, y, width, height, 42);
		setTag("hitbox");
		setTransparency(true);
		//setColor(Render.toRGBInt(255, 0, 0));
	}

}
