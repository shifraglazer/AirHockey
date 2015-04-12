package airHockey;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;
	private Socket socket;

	public ClientWorld(String serverAddress) throws IOException, LineUnavailableException,
			UnsupportedAudioFileException {
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
					updateMallet2(table.getMallet1Location());
					System.out.println("2 : " + (MIDDLE - table.getMallet1().getMalletY()));
				}
				catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});
		//serve second
		table.setPuck(1);

		setVisible(true);
		startNoise();
		new GameLoopThread(this).start();
	}

	public void updateMallet2(String location) throws IOException {
		double x = table.getMallet1().getMalletX();
		double y = table.getMallet1().getMalletY();
		double Whalf = GAMEWIDTH / 2;
		double Lhalf = GAMEHEIGHT / 2;
		// reflection over x and y axis
		double diffx = Math.abs(Whalf - x);
		double diffy = Math.abs(Lhalf - y);

		if (x >= Whalf && y >= Lhalf) {
			x = Whalf - diffx;
			y = Lhalf - diffy;
		}
		else if (x <= Whalf && y <= Lhalf) {
			x = Whalf + diffx;
			y = Lhalf + diffy;
		}
		else if (y >= Lhalf && x <= Whalf) {
			x = Whalf + diffx;
			y = Lhalf - diffy;

		}
		else if (y <= Lhalf && x >= Whalf) {
			y = Lhalf + diffy;
			x = Whalf - diffx;
		}

		OutputStream out = socket.getOutputStream();

		PrintWriter writer = new PrintWriter(out);
		writer.println(location);
		writer.flush();
	}
}
