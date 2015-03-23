import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;
	private Socket socket;

	public ServerWorld() throws IOException {

		ServerSocket serverSocket = new ServerSocket(3769); // port num sent
		socket = serverSocket.accept();
		System.out.println("accepted");
		new ReadThread(socket, this).start();

		// mallet moves with mouse
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = getLocation();
				table.moveMallet(point);
				try {

					updateMallet2(e.getX(), e.getY());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});

		setVisible(true);
		new GameLoopThread(this).start();
	}

	public void updateMallet2(double x, double y) throws IOException {

		String text = x + " " + y;
		OutputStream out = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(out);
		writer.println(text);
		writer.flush();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
			/*
			 * LookAndFeel lat = UIManager.getLookAndFeel(); UIDefaults defaults
			 * = lat.getDefaults(); defaults.replace(key, value); for(Object
			 * key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key)); }
			 */
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		try {
			new ServerWorld();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
