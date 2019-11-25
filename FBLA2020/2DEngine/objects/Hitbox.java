package objects;

import engine.Render;

public class Hitbox extends Entity{
	
	/**
	 * hitbox is an invisible entity
	 */
	public Hitbox(int x, int y, int width, int height) {
		super(x, y, width, height, 2);
		setTag("hitbox");
		//setTransparency(true);
	}

}
