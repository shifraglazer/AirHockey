package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** Singleton Class */

public class Music extends Noise {
	// know for sure will have a Music so can create it here
	private static final Music MUSIC = new Music();

	// private constructor so new Sound can never be constructed
	private Music() {

	}

	@Override
	public void resume(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super.resume(src);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	// allow other classes access to the singleton instance
	public static Music getInstance() {
		return MUSIC;
	}
}