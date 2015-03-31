package airHockey;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	private Clip clip;

	public Music(URL lastClicked) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		resume(lastClicked);
	}

	public void changeTrack(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		stop();
		resume(src);
	}

	public void stop() {
		clip.stop();
	}

	public void resume(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(src));
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}