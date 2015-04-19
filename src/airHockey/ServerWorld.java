package airHockey;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;

	public ServerWorld() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		ServerSocket serverSocket = new ServerSocket(3769); // port num sent
		socket = serverSocket.accept();
		System.out.println("accepted");
		new ReaderThread(socket, this).start();
		output=socket.getOutputStream();
		objOut=new ObjectOutputStream(output);
		// mallet moves with mouse
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				try {
					// move your mallet to wherever the mouse pointer is located
					Point point = getLocation();
					moveMallet(point);

					// send location of your mallet to second players
					sendCommand(table.getMallet1Location());
				}
				catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});

		// serve first
		table.setPuck(3);
		setVisible(true);
		startNoise();
		new GameLoopThread(this).start();
	}
	@Override
	public void movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		sendCommand(table.getPuckCommand());
		super.movePuck();
		
	}

}