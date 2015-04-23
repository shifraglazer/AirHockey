package airHockey;

import java.io.IOException;
import java.net.ServerSocket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;

	public ServerWorld(boolean test) throws IOException,
			LineUnavailableException, UnsupportedAudioFileException {
		if (!test) {
			setLocationRelativeTo(null);
		}

		serverSocket = new ServerSocket(3769); // port num sent
		socket = serverSocket.accept();

		// 3 = server first
		setUp(3);
	}

	@Override
	public void movePuck() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException, InterruptedException {
		super.movePuck();
	}

	@Override
	public void syncPuck() throws IOException, InterruptedException {
		sendCommand(table.getPuckCommand());
	};

}