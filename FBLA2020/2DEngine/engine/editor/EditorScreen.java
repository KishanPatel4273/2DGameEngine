package engine.editor;

import java.awt.Color;

import button.*;
import controller.InputHandler;
import engine.Engine;
import engine.Render;
import loaders.LoadFile;
import objects.Entity;
import objects.Hitbox;
import tools.Vector;
import window.Display;

public class EditorScreen {

	public String path;
	public LoadFile levelFile;
	
	//hitbox editor variables
	public boolean hitboxEditor = false;
	public boolean hitboxLatch = true;
	public Vector initialClick;
	
	/**
	 * sets up screen to edit levels
	 * path -> level file
	 */
	public EditorScreen(String path) {
		this.path = path;
		levelFile = new LoadFile(path);
		loadButtons();
	}
	
	/**
	 * creates the buttons need for the editor
	 */
	public void loadButtons() {
		//dimensions are set proportional to extra room added
		int w = 200;
		int h = w / 4;
		int x = Display.WIDTH + Display.SCALE/2;
		ButtonHandler.buttonList.add(new Button(x,  50, w, h, "addHitBox", true, "Editor"));
		ButtonHandler.getButton("addHitBox", "Editor").getButton().setBackground(Color.RED);
		
		ButtonHandler.buttonList.add(new Button(x,  100 + h/2, w, h, "", true, "Editor"));
		ButtonHandler.buttonList.add(new Button(x,  150 + 2 * h/2, w, h, "", true, "Editor"));
	}
	
	/**
	 * @param path -> new level path
	 * updates the current level being edited
	 */
	public void changeLevel(String path) {	
		this.path = path;
		levelFile = new LoadFile(path);
	}

	public void tick(Render render) {
		buttonHandler();
		hitboxHandler(render);
	}
	
	/**
	 * handles the buttons on the editor screen
	 */
	private void buttonHandler() {
		//if addHitbox is clicked
		if(ButtonHandler.isActive("addHitBox", "Editor")) {
			hitboxEditor = !hitboxEditor;//flips state of hitbox editor
			hitboxLatch = true;//turns latch true to signify editing is ready
			//turns button blue if on and red if off
			if(hitboxEditor) {
				ButtonHandler.getButton("addHitBox", "Editor").getButton().setBackground(Color.BLUE);
			} else {
				ButtonHandler.getButton("addHitBox", "Editor").getButton().setBackground(Color.RED);
			}
		}
	}

	/**
	 * handles adding new hitboxes
	 */
	private void hitboxHandler(Render render) { 
		if(hitboxEditor) {//check if hitbox editing is on
			//if the latch is off that signifies that no initial position of hitbox is set
			if(hitboxLatch && InputHandler.mouseClicked) {//if latch if off and editor clicks sets initial position
				//sets initial position
				initialClick = new Vector(InputHandler.mouseX, Display.HEIGHT - InputHandler.mouseY);
				hitboxLatch = false;//turns latch off b/c initial position of hitbox is set
				InputHandler.mouseClicked = false;//resets the mouse click
			}
			//if initial position is set
			if(!hitboxLatch) {
				//get current position of mouse -> is going to be the second corner of rectangle(hitbox)
				Vector currentHitbox = new Vector(InputHandler.mouseX, Display.HEIGHT - InputHandler.mouseY);
				//draws current hitbox for visual
				render.drawRectangle(initialClick, currentHitbox, render.toRGBInt(255, 0, 0));	
				//if editor clicks agian it signifies to add the hitbox to current level
				if(InputHandler.mouseClicked) {
					//converts the two corners of hit box to a single position and width and height 
					int[] d = render.convertDimension(initialClick, currentHitbox);
					//translates the d[0-1] -> position to its position to game start position 
					int x = (int) (d[0] + Engine.deltaTranslate.getX());
					int y = (int) (d[1] + Engine.deltaTranslate.getY());
					//writes hitbox data to level file
					String hitboxData = "Hitbox|" + x + "|" + y + "|" + d[2] + "|" + d[3] + "|";
					levelFile.write(hitboxData);
					//adds hitbox to current entity list
					Engine.entities.add(1, new Hitbox(d[0], d[1], d[2], d[3]));// at 1 do its before player else error
					//turns latch off and resets the mouse click
					hitboxLatch = true;
					InputHandler.mouseClicked = false;
				}
			}
		}
	}
}
