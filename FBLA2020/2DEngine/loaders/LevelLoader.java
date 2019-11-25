package loaders;

import java.util.ArrayList;

import engine.Engine;
import objects.*;

public class LevelLoader {
	
	private String path;
	private LoadFile levelFile;
	private ArrayList<String> fileText;
	
	/**
	 * loads the assets given in to the engine
	 */
	public LevelLoader(String path) {
		this.path = path;
		levelFile = new LoadFile(path);
		fileText = levelFile.getFileText();
	}
	
	/**
	 * reads the .txt file and executes the creation
	 */
	public void load() {
		for(String s: fileText) {//runs through each line
			//applies specific loaders to each line
			//loader depends on the tag 
			//add all obkects before player else error (idk y)
			if(s.contains("Entity")) {
				loadEntity(s.substring(6));
			}
			if(s.contains("Player")) {
				loadPlayer(s.substring(6));
			}
			if(s.contains("Hitbox")) {
				loadHitbox(s.substring(6));
			}
		}
	}
	
	/**
	 * s contained "entity"
	 * s - format -> |x|y|w|h|id|
	 * adds entity to game
	 */
	public void loadEntity(String s) {
		int[] d = getData(s, 5);
		Engine.entities.add(0, new Entity(d[0], d[1], d[2], d[3], d[4]));
	}
	
	/**
	 * s contained "Player"
	 * s - format -> |x|y|w|h|p#|
	 * adds player to game
	 */
	public void loadPlayer(String s) {
		int[] d = getData(s,5);
		Engine.entities.add(new Player(d[0], d[1], d[2], d[3], d[4]));
	}
	
	/**
	 * s contained "Hitbox"
	 * s - format -> |x|y|w|h|
	 * adds hitbox to game
	 */
	public void loadHitbox(String s) {
		int[] d = getData(s,4);
		Engine.entities.add(0, new Hitbox(d[0], d[1], d[2], d[3]));
	}
	
	/**
	 * @param s -> |x|y|z|w|...
	 * @param n -> number of data points in s
	 * @return an array of the x, y, z, w ...
	 */
	public int[] getData(String s, int n) {
		int[] d = new int[n];//array to store n data
		for(int i = 0; i < n; i++) {
			//fetches the data and converts it to an integer
			int data = Integer.valueOf(s.substring(1, s.indexOf("|", 1)));
			//removes the data from the line
			s = s.substring(s.indexOf("|", 1));
			d[i] = data;
		}
		return d;
	}
	
}
