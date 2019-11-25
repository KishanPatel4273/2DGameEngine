package engine;

import java.util.ArrayList;

import button.ButtonHandler;
import engine.editor.EditorScreen;
import loaders.LevelLoader;
import objects.*;
import sound.SoundEffect;
import tools.*;
import window.Screen;

public class Engine extends Render{
	
	//array to hold all entity types
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Entity> entitiesInFrame = new ArrayList<Entity>();
	
	public static ArrayList<Collider> collider = new ArrayList<Collider>();
	public static ArrayList<Vector> vectors = new ArrayList<Vector>();

	
	public static Collider Frame;
	public static Vector deltaTranslate = new Vector();
	
	public ButtonHandler buttonHandler;
	public EditorScreen editorScreen;
	public LevelLoader test;
	
	public Engine(int width, int height) {
		super(width, height);//create render inside engine class
		Frame = new Collider(0, height, width, height);
		
		buttonHandler = new ButtonHandler();
		editorScreen = new EditorScreen("res/Level1.txt");
		buttonHandler.addListener();

		loadLevel("res/Level1.txt");
		
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
		
		buttonHandler();
		
		clear();//clears the pixel array so old frame is gone
		screenHandler();
		
		
		editorScreenButtonHandler();
	}
	
	/**
	 * handles the current screen on
	 */
	public void screenHandler() {
		if(Screen.screenTypeState[2]) {//game
			updateFrame();//updates frame to only render and tick entities in frame
			render();//renders level
		} else if(Screen.screenTypeState[0]) {//start
			fillRectangle(0, height, width, height, toRGBInt(255, 155, 155));
		}
	}
	
	public void render() {
		//rendering 
		for(int i = 0; i < entitiesInFrame.size(); i++) {//runs through all entities
			update(entitiesInFrame.get(i));
			render(entitiesInFrame.get(i));						
		}
		for(Collider c: collider) {
			//drawRectangle(c.getX(), c.getY(), c.getWidth(), c.getHeight(), 16711680);
		}
		for(int i = 0; i < vectors.size()-1; i++) {
			drawLine(vectors.get(i).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i+1).getY(), 65280);
			//drawLine(vectors.get(i).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i).getY(), 65280);
			//drawLine(vectors.get(i+1).getX(), vectors.get(i).getY(), vectors.get(i+1).getX(), vectors.get(i+1).getY(), 65280);
		}		
	}
	
	/**
	 * handles the games buttons
	 */
	public void buttonHandler() {
		buttonHandler.tick();//ticks button handler to update it
	}
	
	/**
	 * handles the editor screen buttons and rendering of it
	 */
	public void editorScreenButtonHandler() {
		editorScreen.tick(this);
		//IMPORTANT
		ButtonHandler.clear();//clears all button states
	}
	
	/**
	 * @param path -> level file
	 * updates the current screen and editor to new level
	 */
	public void loadLevel(String path) {
		entities.clear();
		//loads new level
		new LevelLoader("res/Level1.txt").load();
		editorScreen.changeLevel(path);
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
	
	/**
	 * translates the frame by moving all entities by -translate
	 * method doen't effect the player
	 */
	public static void translateFrame(Vector translate) {
		deltaTranslate.addVector(translate);//updates deltaTranslate
		for(Entity e: entities) {//runs through all entities
			if(e.getId() != 0) {//not player entity
				//applies -translate to all entities this keeps contents(distance) same
				e.addVector(Vector.scale(-1, translate));
			}
		}
	}
}
