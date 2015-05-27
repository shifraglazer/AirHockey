package airHockey;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameLoopThread extends Thread {
	private World world;
	private long lastExecutedTime;

	public GameLoopThread(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		while (true) {
			long newTime = System.currentTimeMillis();
			double delta = (newTime - lastExecutedTime) / 1000;
			// int speed = (int) (world.getPuckSpeed() * delta);
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				try {
					world.movePuck();
					world.syncPuck();
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				// sleep(16);
				sleep(21 - speed);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastExecutedTime = newTime;
		}

	}
}
