package airHockey;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		while (true) {
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				try {
					world.movePuck();
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
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
