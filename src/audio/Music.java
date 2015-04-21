package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music extends Noise {

	@Override
	public void resume(URL src) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super.resume(src);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}