package airHockey;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.io.IOUtils;

import audio.MusicMenu;
import audio.Sound;
import audio.SoundMute;
import commands.Command;

public class World extends JFrame implements ReaderListener, Serializable {
	private static final long serialVersionUID = 1L;
	protected Table table;
	protected Socket socket;

	private JLabel points1;
	private JLabel points2;
	private int total1;
	private int total2;

	private Font font;
	private Font fontBold;

	// TODO private boolean winner;

	private MusicMenu musicMenu;
	private final Sound sound = Sound.getInstance();
	private OutputStream out;

	public static final int GAMEWIDTH = 300;
	public static final int GAMEHEIGHT = 500;
	protected static final int MIDDLE = GAMEHEIGHT / 2;

	public World() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		setTitle("Air Hockey");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		fontBold = font.deriveFont(Font.BOLD);
		setUpMenu();
		setSize(GAMEWIDTH, GAMEHEIGHT + musicMenu.getHeight());
		table = new Table();
		add(table);
		setIconImage(new ImageIcon(getClass().getResource("pics/icehockey.png")).getImage());
		pack();
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void setUp(int number) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		new ReaderThread(socket, this).start();
		out = socket.getOutputStream();
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

		menu.add(Box.createHorizontalStrut(100));

		JLabel play1 = new JLabel("You: ");
		play1.setFont(font);
		menu.add(play1);
		total1 = 0;
		points1 = new JLabel(String.valueOf(total1));
		points1.setFont(fontBold);
		menu.add(points1);

		menu.add(Box.createHorizontalStrut(32));

		JLabel play2 = new JLabel("Not You: ");
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
			if (total1 >= 10) {
				// winner = true;
				// TODO can instead return winner so know if should stop gameloops
			}
		}
		else if (point == 2) {
			points2.setText(String.valueOf(++total2));
			if (total2 >= 10) {
				// TODO winner = true;
			}
		}

		repaint();
	}

	public void moveMallet(Point location) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		table.moveMallet(location);
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

	public void sendCommand(Command command) throws IOException, InterruptedException {
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(command);
		objOut.flush();
		// out.close();
		// objOut.close();
	}

	@Override
	public void onObjectRead(Command command) {
		command.perform(table);
	}

	@Override
	public void onCloseSocket(Socket socket) {
		IOUtils.closeQuietly(socket);
	}
}
