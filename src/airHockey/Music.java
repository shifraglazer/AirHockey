package airHockey;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	private Clip clip;

	public Music(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResource(filename)));
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void changeTrack(String filename) throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		stop();
		resume(filename);
	}

	public void stop() {
		clip.stop();
	}

	public void resume(String filename) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResource(filename)));
		clip.start();
		clip.loop(1000000);
	}

}
