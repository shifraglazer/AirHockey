package airHockey;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread(World world) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		this.world = world;

		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResource("sound/bounceMusic.wav")));
		clip.start();
		clip.loop(1000000);
	}

	@Override
	public void run() {
		while (true) {
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				world.movePuck();
				world.repaint();
			}
			try {
				sleep(100 - (speed * 2));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
