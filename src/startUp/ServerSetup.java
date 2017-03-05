package startUp;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ServerSetup {

	public ServerSetup() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		LoadingFrame frame = new LoadingFrame();
		frame.setVisible(true);
		new ServerThread(frame).start();
	}
}
