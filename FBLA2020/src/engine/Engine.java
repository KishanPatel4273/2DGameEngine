package engine;

import java.util.ArrayList;

import objects.*;
import tools.*;

public class Engine extends Render{
	
	//array to hold all entity types
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Collider> collider = new ArrayList<Collider>();
	public static ArrayList<Vector> vectors = new ArrayList<Vector>();

	
	
	public Engine(int width, int height) {
		super(width, height);//create render inside engine class
		int w  = 100, h = 100;

		entities.add(new Entity(250, 750, 40, 40, 1));

		entities.add(new Entity(0, 100, 500, 100, 1));

		entities.add(new Entity(0, 1000, 100, 1500, 1));
		entities.add(new Entity(0, 1000, 1500, 100, 1));
	
		entities.add(new Entity(500 - w, 600- w, w, h, 1));
		entities.add(new Entity(500, 600, w, h, 1));
		entities.add(new Entity(500 + w, 600, w, h, 1));
		entities.add(new Entity(500 + 2*w, 600, w, h, 1));
		entities.add(new Entity(500 + 3*w, 600, w, h, 1));
		entities.add(new Entity(500 + 4*w, 600, w, h, 1));

		//entities.add(new Entity(500, 600, 300, 300, 1));
	
		//entities.add(new Entity(300, 700, 500, 300, 1));
		
		
		//entities.add(new Entity(300, 800, 200, 600, 1));
		entities.add(new Player(400, 350, 100, 200));
		
		
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		collider.add(new Collider(0,0,0,0));
		
		
		vectors.add(new Vector(0,0));
		vectors.add(new Vector(0,0));

	}
	
	//second game loop for engine
	//updates all game functions such as render and tick  
	public void run() {
		clear();//clears the pixel array so old frame is gone
		for(int i = 0; i < entities.size(); i++) {//runs through all entities
			update(entities.get(i));
			render(entities.get(i));						
		}
		for(Collider c: collider) {
			//drawRectangle(c.getX(), c.getY(), c.getWidth(), c.getHeight(), 16711680);
		}
		for(int i = 0; i < vectors.size()-1; i++) {
			//drawLine(vectors.get(i).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i+1).getY(), 65280);
			//drawLine(vectors.get(i).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i).getY(), 65280);
			//drawLine(vectors.get(i+1).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i+1).getY(), 65280);

		}
	}
	
	/**
	 * calls on update method in entity e
	 */
	public void update(Entity e) {
		e.update();//update methods is called
	}
	
	/**
	 *renders the entity
	 */
	public void render(Entity e) {
		if(!e.isTransparency()) {//render entity if it's not transparent
			renderSolidEntity(e);
		}
	}
}
