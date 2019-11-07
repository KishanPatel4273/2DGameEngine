package engine;

import java.util.ArrayList;

import objects.*;
import tools.*;

public class Engine extends Render{
	
	//array to hold all entity types
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Entity> entitiesInFrame = new ArrayList<Entity>();
	public static ArrayList<Collider> collider = new ArrayList<Collider>();
	public static ArrayList<Vector> vectors = new ArrayList<Vector>();
	
	public static Collider Frame;
	
	public Engine(int width, int height) {
		super(width, height);//create render inside engine class
		int w  = 100, h = 100;
		Frame = new Collider(0, height, width, height);
		
		entities.add(new Entity(250, 750, 40, 40, 1));

		entities.add(new Entity(750, 250, 100, 500, 1));

		
		entities.add(new Entity(0, 100, 500, 100, 1));

		entities.add(new Entity(0, 1000, 100, 1500, 1));
		entities.add(new Entity(0, 1000, 1500, 100, 1));

		entities.add(new Entity(500, 600, w, h, 1));
		entities.add(new Entity(500 + w, 600, w, h, 1));
		entities.add(new Entity(500 + 2*w, 600, w, h, 1));
		entities.add(new Entity(500 + 3*w, 600, w, h, 1));
		entities.add(new Entity(500 + 4*w, 600, w, h, 1));

		entities.add(new Entity(500 - w, 600- w + 10, w, h, 1));

		entities.add(new Entity(100 + w, 200, w, h, 1));
		entities.add(new Entity(100 + 2*w, 200, w, h, 1));
		entities.add(new Entity(100 + 3*w, 200, w, h, 1));
		entities.add(new Entity(100 + 4*w, 200, w, h, 1));
		entities.add(new Entity(100 + 5*w, 200, w, h, 1));


		entities.add(new Entity(100, 700, 200, 200, 1));

		
		//entities.add(new Entity(500, 600, 300, 300, 1));
	
		//entities.add(new Entity(300, 700, 500, 300, 1));
		
		
		//entities.add(new Entity(300, 800, 200, 600, 1));
		
		//	entities.add(new Entity(500, 600, w*3, h*3, 1));

		entities.add(new Player(400, 400, 100, 200));
		
		
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
		updateFrame();//updates frame to only render and tick entities in frame

		for(int i = 0; i < entitiesInFrame.size(); i++) {//runs through all entities
			update(entitiesInFrame.get(i));
			render(entitiesInFrame.get(i));						
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
	
	/**
	 * checks which entities are in frame and puts those entities into the inframe array
	 */
	public void updateFrame() {
		entitiesInFrame.clear();//removes all entities in frame
		//clearing the entities in array wont destroy its data from another array
		for(int i = 0; i < entities.size(); i++) {//runs through all entities
			//checks if entity is in frame or is a player
			if(Collider.intersect(entities.get(i).getCollider(), Frame) || entities.get(i).getTag().equals("Player")) {
				entitiesInFrame.add(entities.get(i));//adds entity to array
				//bc objects are statics the manipulation of the object in one entity will update it in the other array 
			}
		}
	}
}
