package airHockey;

import java.io.IOException;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;

	public ClientWorld(String serverAddress) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		setLocationRelativeTo(null);
		socket = new Socket(serverAddress, 3769);

		// 1 = server second
		setUp(1);
	}

}
