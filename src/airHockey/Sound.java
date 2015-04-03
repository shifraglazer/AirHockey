package airHockey;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** Singleton Class */

public class Sound {
	// since every airHockey game will for sure need a Sound, can initialize it right away
	private static final Sound SOUND = new Sound();
	private Clip clip;
	private boolean on;

	// private constructor so new Sound can never be constructed
	private Sound() {

	}

	public void changeTrack(String filename) throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		if (on) {
			stop();
			resume(filename);
		}
	}

	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}

	public void resume(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResource(filename)));
		clip.start();
	}

	public void turnOn() {
		on = true;
	}

	public void turnOff() {
		on = false;
	}

	public boolean isOn() {
		return on;
	}

	// allow other classes access to the singleton instance
	public static Sound getInstance() {
		return SOUND;
	}

}