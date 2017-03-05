package airHockey;

import java.io.IOException;
import java.net.ServerSocket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;

	public ServerWorld(boolean test) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		if (!test) {
			setLocationRelativeTo(null);
		}
		ServerSocket serverSocket = new ServerSocket(3769); // port num sent
		socket = serverSocket.accept();
		setUp(3); // 3 = server first
	}

	@Override
	public void syncPuck() throws IOException, InterruptedException {
		sendCommand(table.getCommand('p'));
	};
}