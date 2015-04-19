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

public class World extends JFrame implements ReaderListener, Serializable {
	private static final long serialVersionUID = 1L;
	protected Socket socket;
	protected Table table;
	private JLabel points1;
	private JLabel points2;
	private int total1;
	private int total2;
	private Font font;
	private Font fontBold;
	protected OutputStream output;
	protected ObjectOutputStream objOut;
	// TODO private boolean winner;

	private MusicMenu musicMenu;
	private final Sound sound = Sound.getInstance();

	protected static final int GAMEWIDTH = 300;
	protected static final int GAMEHEIGHT = 500;
	protected static final int MIDDLE = GAMEHEIGHT / 2;

	public World() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		setTitle("Air Hockey");
		// setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		fontBold = font.deriveFont(Font.BOLD);
		setUpMenu();
		setSize(GAMEWIDTH, GAMEHEIGHT + musicMenu.getHeight());
		table = new Table();
		add(table);
		this.setIconImage(new ImageIcon(getClass().getResource("pics/icehockey.png")).getImage());
		pack();
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

	public void movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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
	public void movingPuck(){
		
	}

	public void moveMallet(Point location) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		table.moveMallet(location);
		// sound.changeTrack("sound/moveMallet.wav");
	}

	public void moveMallet2(Point location) {
		table.moveMallet2(location);
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}

	public void startNoise() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		musicMenu.startMusic();
		sound.turnOn();
		sound.resume("sound/cartoon_mouse_says_uh_oh.wav");
	}
	
	public void sendCommand(Command command) throws IOException {
		
		objOut.writeObject(command);
		//out.close();
		//objOut.close();
		objOut.flush();
		objOut.reset();

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
