package loaders;

import java.util.ArrayList;

import engine.Engine;
import objects.Hitbox;

public class LevelLoader {
	
	private String hitboxPath;
	private LoadFile levelFile;
	private ArrayList<String> fileText;
	
	public LevelLoader(String hitboxPath) {
		this.hitboxPath = hitboxPath;
		levelFile = new LoadFile(hitboxPath);
		fileText = levelFile.getFileText();
	}
	
	public void load() {
		for(String s: fileText) {
			if(s.contains("hitbox")) {
				loadHitbox(s.substring(6));
			}
		}
	}
	
	/**
	 * s contains "hitbox"
	 * s - format -> |x|y|w|h|
	 * adds hitbox to game
	 */
	public void loadHitbox(String s) {
		int[] d = getData(s);
		Engine.entities.add(new Hitbox(d[0], d[1], d[2], d[3]));
	}
	
	/**
	 * @param s -> |x|y|z|w|
	 * @return an array of the x, y, z, w
	 */
	public int[] getData(String s) {
		int x = Integer.valueOf(s.substring(1, s.indexOf("|", 1)));
		s = s.substring(s.indexOf("|", 1));
		int y = Integer.valueOf(s.substring(1, s.indexOf("|", 1)));
		s = s.substring(s.indexOf("|", 1));
		int z = Integer.valueOf(s.substring(1, s.indexOf("|", 1)));
		s = s.substring(s.indexOf("|", 1));
		int w = Integer.valueOf(s.substring(1, s.indexOf("|", 1)));
		s = s.substring(s.indexOf("|", 1));
		int[] d = {x, y, z, w};
		return d;
	}
	
}
