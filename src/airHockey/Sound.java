package airHockey;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private Clip clip;

	public Sound(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		resume(filename);
	}

	public void changeTrack(String filename) throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		stop();
		resume(filename);
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
}
