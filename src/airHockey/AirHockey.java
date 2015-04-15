package airHockey;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AirHockey extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton server;
	private JButton client;
	private JButton testMode;

	public AirHockey() {
		setSize(150, 125);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3, 1));

		server = new JButton("SERVER");
		server.addMouseListener(mouse);
		server.addActionListener(serverListen);
		add(server);

		client = new JButton("CLIENT");
		client.addMouseListener(mouse);
		client.addActionListener(clientListen);
		add(client);

		testMode = new JButton("TEST MODE");
		testMode.addMouseListener(mouse);
		testMode.addActionListener(testModeListen);
		add(testMode);

		setVisible(true);
	}

	MouseListener mouse = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO when make nicer graphics use the following code to highlight
			// whichever botton
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

	ActionListener serverListen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				dispose();
				new ServerSetup();
			}
			catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	ActionListener clientListen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO get ip address from user
			new ClientSetup();
			dispose();
		}
	};

	ActionListener testModeListen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			Thread thread1 = new Thread() {
				public void run() {
					try {
						new ServerWorld();
					}
					catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
			};
			Thread thread2 = new Thread() {
				public void run() {
					try {
						new ClientWorld("localhost");
					}
					catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
			};
			thread1.start();
			thread2.start();
			dispose();
		}
	};

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
