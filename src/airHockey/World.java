package airHockey;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.io.IOUtils;

import audio.MusicMenu;
import audio.Sound;
import audio.SoundMute;

import commands.Command;
import commands.MessageCommand;

public class World extends JFrame implements ReaderListener {
	private static final long serialVersionUID = 1L;
	public static final int FRAMEWIDTH = 600;
	public static final int FRAMEHEIGHT = 500;
	public static final int GAMEWIDTH = FRAMEWIDTH / 2;
	protected static final int MIDDLE = FRAMEHEIGHT / 2;
	
	protected Table table;
	protected Socket socket;

	private JLabel points1;
	private JLabel points2;
	private int total1;
	private int total2;

	private Font font;
	private Font fontBold;

	// TODO private boolean winner;
	private Chat chat;
	private MusicMenu musicMenu;
	private final Sound sound = Sound.getInstance();
	private ObjectOutputStream objOut;

	public World() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		setTitle("Air Hockey");
		Container container = getContentPane();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		fontBold = font.deriveFont(Font.BOLD);
		chat = new Chat();
		setFocusable(true);
		setUpMenu();
		setSize(FRAMEWIDTH, FRAMEHEIGHT + musicMenu.getHeight());
		table = new Table();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		add(table);
		add(chat);
		InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = getRootPane().getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		actionMap.put("enter", new EnterAction());

		setIconImage(new ImageIcon(getClass().getResource("pics/icehockey.png")).getImage());
		pack();
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	protected void setUp(int number) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		new ReaderThread(socket, this).start();
		OutputStream out = socket.getOutputStream();
		objOut = new ObjectOutputStream(out);
		
		table.addMouseMotionListener(new MalletMotionListener(this));
		table.setPuck(number);

		setVisible(true);
		startNoise();
		new GameLoopThread(this).start();
	}

	private void setUpMenu() throws UnsupportedAudioFileException, IOException {
		JMenuBar menu = new JMenuBar();
		JMenuItem soundItem = new JMenuItem("TURN SOUND OFF");
		soundItem.addActionListener(new SoundMute());
		musicMenu = new MusicMenu(soundItem, font);
		menu.add(musicMenu);

		menu.add(Box.createHorizontalStrut(380));

		JLabel play1 = new JLabel("Me: ");
		play1.setFont(font);
		menu.add(play1);
		total1 = 0;
		points1 = new JLabel(String.valueOf(total1));
		points1.setFont(fontBold);
		menu.add(points1);

		menu.add(Box.createHorizontalStrut(35));

		JLabel play2 = new JLabel("Opponent: ");
		play2.setFont(font);
		menu.add(play2);
		total2 = 0;
		points2 = new JLabel(String.valueOf(total2));
		points2.setFont(fontBold);
		menu.add(points2);

		setJMenuBar(menu);
	}

	public void movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
		int point = table.movePuck();
		if (point == 1) {
			points1.setText(String.valueOf(++total1));
			//if (total1 >= 10) {
				// winner = true;
				// TODO can instead return winner so know if should stop gameloop
			//}
		}
		else if (point == 2) {
			points2.setText(String.valueOf(++total2));
			//if (total2 >= 10) {
				// TODO winner = true;
			//}
		}

		repaint();
	}

	public void moveMallet(double x, double y) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		table.updateMalletCoordinates(x, y, 1);
		// sound.changeTrack("sound/moveMallet.wav");
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}

	public void startNoise() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		musicMenu.startMusic();
		sound.turnOn();
		sound.resume("sound/cartoon_mouse_says_uh_oh.wav");
	}

	public synchronized void sendCommand(Command command) throws IOException, InterruptedException {
		objOut.writeObject(command);
		objOut.flush();
		// objOut.reset();
		// out.close();
		// objOut.close();
	}

	public void updateChat(String msg) {
		chat.updateChat(msg);
	}

	public void updateMalletCoordinates(double x, double y) {
		table.updateMalletCoordinates(x, y, 2);
	}

	public void updatePuckCoordinates(double x, double y, int speed) {
		table.updatePuck(x, y, speed);
	}

	public void syncPuck() throws IOException, InterruptedException {
	}

	@Override
	public void onObjectRead(Command command) {
		command.perform(this);
	}

	@Override
	public void onCloseSocket(Socket socket) {
		IOUtils.closeQuietly(socket);
	}

	private class EnterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String text = chat.readText();
				MessageCommand message = new MessageCommand(text);
				sendCommand(message);
			}
			catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}