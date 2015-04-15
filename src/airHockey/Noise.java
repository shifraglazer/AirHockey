package airHockey;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Noise {
	protected Clip clip;
	protected boolean on;

	public void resume(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(src));
		clip.start();
	}

	public void resume(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		resume(getClass().getResource(filename));
	}

	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}

	public void changeTrack(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		stop();
		resume(src);
	}

	public void changeTrack(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		changeTrack(getClass().getResource(filename));
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
}
