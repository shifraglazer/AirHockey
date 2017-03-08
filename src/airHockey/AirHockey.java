package airHockey;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import startUp.ClientListener;
import startUp.ServerListener;
import startUp.TestModeListener;

public class AirHockey extends JDialog {
	private static final long serialVersionUID = 1L;

	private MouseListener mouse = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO when make nicer graphics use the following code to highlight
			// whichever button
			// the mouse is hovering over
			// JButton button = (JButton) e.getSource();
			// button.setBorder(border);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// JButton button = (JButton) e.getSource();
			// button.setBorder(null);
		}
	};
	
	public AirHockey() {
		setSize(150, 125);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3, 1));

		// create menu option to choose if server, client, or testing so want both
		JButton server = new JButton("SERVER");
		server.addMouseListener(mouse);
		server.addActionListener(new ServerListener(this));
		add(server);

		JButton client = new JButton("CLIENT");
		client.addMouseListener(mouse);
		client.addActionListener(new ClientListener(this));
		add(client);

		// creates both a server and client using localhost
		JButton testMode = new JButton("TEST MODE");
		testMode.addMouseListener(mouse);
		testMode.addActionListener(new TestModeListener(this));
		add(testMode);

		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			/* LookAndFeel lat = UIManager.getLookAndFeel();
			 * UIDefaults defaults = lat.getDefaults(); defaults.replace(key, value);
			 * for(Object key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key));
			 * } */
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new AirHockey();
	}
}
