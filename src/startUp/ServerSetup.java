package startUp;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import airHockey.ServerThread;

public class ServerSetup {
	private LoadingFrame frame;

	public ServerSetup() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		frame = new LoadingFrame();
		frame.setVisible(true);
		new ServerThread(frame).start();
	}
}
