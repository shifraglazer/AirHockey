package airHockey;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import startUp.LoadingFrame;

public class ServerThread extends Thread {
	private LoadingFrame frame;

	public ServerThread(LoadingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		try {
			new ServerWorld(false);
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		frame.dispose();
	}
}
