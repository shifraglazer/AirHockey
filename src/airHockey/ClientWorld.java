package airHockey;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;

	public ClientWorld(String serverAddress) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		setLocationRelativeTo(null);
		socket = new Socket(serverAddress, 3769);
		new ReaderThread(socket, this).start();

		// mallet moves with mouse
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = getLocation();

				try {
					moveMallet(point);
					sendCommand(table.getMallet1Location());
					System.out.println("2 : " + (MIDDLE - table.getMallet1().getMalletY()));
				}
				catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});
		// serve second
		table.setPuck(1);

		setVisible(true);
		startNoise();
		new GameLoopThread(this).start();
	}
}
