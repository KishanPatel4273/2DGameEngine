package loaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class LoadFile {

	private ArrayList<String> fileText;
	private File file;
	private BufferedReader br;
	private String pathDir = System.getProperty("user.dir");

	/**
	 * @param path for .txt file loads file and stores information of file in fileText
	 */
	public LoadFile(String path) {
		//System.out.println(pathDir);
		fileText = new ArrayList<String>();// initialize fileTest
		file = new File(path);// store file in file
		// br and line are used to load file line by line
		br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				while ((line = br.readLine()) != null) {// reads a line at a time
					fileText.add(line);// stores info
				}
				// Possible error are caught
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return file's text
	 */
	public ArrayList<String> getFileText() {
		return fileText;//text is stored line by line
	}
	
	/**
	 * @param line - is appended to file
	 */
	public void write(String line){
		Writer write;
		try {
			//loads file and opens it
			write = new BufferedWriter(new FileWriter(file, true));
			// + "\n" to goto next line
			write.append(line + "\n");//add line to file
			write.close();//closes file
			fileText.add(line);//updates current info in game
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}