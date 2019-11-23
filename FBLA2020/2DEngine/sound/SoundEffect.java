package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
	//sound effects have to be played locally 
	
	private Clip clip;

	/**
	 * takes .wav file as input sound
	 *  and loads the sound to memory
	 */
	public SoundEffect(String path) {
		File file = new File(path);
		AudioInputStream sound;
		try {
			//gets the audio
			sound = AudioSystem.getAudioInputStream(file);
			//loads sound file to clip
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * plays sound effect from the beginning
	 * doesn't effect thread
	 */
	public void play() {
		//rewinds the sound to the beginning
		clip.setFramePosition(0);
		clip.loop(0);
	}

	/**
	 * plays sound in loop
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * stops sound effect if in play
	 */
	public void stop() {
		clip.stop();
	}
}