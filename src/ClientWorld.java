import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;
	private Socket socket;

	public ClientWorld() throws IOException {

		setLocationRelativeTo(null);
		// client = new Socket("192.168.1.6", 3762);
		socket = new Socket("localhost", 3769); // port num sent
		new ReadThread(socket, this).start();

		// mallet moves with mouse
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = getLocation();
				
				table.moveMallet(point);

				try {
				
					updateMallet2(point);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});

		setVisible(true);
		new GameLoopThread(this).start();
	}

	public void updateMallet2(Point location) throws IOException {


		String text = String.valueOf(location.getX()) + " " + String.valueOf(location.getY());
		OutputStream out = socket.getOutputStream();
		System.out.println("point recieved: "+ text);

		PrintWriter writer = new PrintWriter(out);
		writer.println(text);
		writer.flush();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			/* LookAndFeel lat = UIManager.getLookAndFeel();
			 * UIDefaults defaults = lat.getDefaults();
			 * defaults.replace(key, value);
			 * for(Object key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key));
			 * } */
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		try {

			new ClientWorld();
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}
}
