package airHockey;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class World extends JFrame {
	private static final long serialVersionUID = 1L;
	protected Table table;
	private JLabel points1;
	private JLabel points2;
	private int total1;
	private int total2;
	private Font font;
	private Font fontBold;
	private boolean winner;
	protected MusicMenu musicMenu;

	public World() throws IOException {
		setTitle("Air Hockey");
		setSize(300, 500);
		// setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		fontBold = font.deriveFont(Font.BOLD);
		setUpMenu();
		table = new Table();
		add(table);

		pack();

		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	private void setUpMenu() {
		JMenuBar menu = new JMenuBar();
		musicMenu = new MusicMenu(font);
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

	public void movePuck() {
		int point = table.movePuck();
		if (point == 1) {
			points1.setText(String.valueOf(++total1));
			if (total1 >= 10) {
				winner = true;
				// TODO can instead return winner so know if should stop gameloops
			}
		}
		else if (point == 2) {
			points2.setText(String.valueOf(++total2));
			if (total2 >= 10) {
				winner = true;
			}
		}
		repaint();
	}

	public void moveMallet(Point location) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		table.moveMallet(location);
		musicMenu.changeSound("sound/moveMallet.wav");
	}

	public void moveMallet2(Point location) {
		table.moveMallet2(location);
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}
	
	public void startNoise() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		musicMenu.startMusic();
		musicMenu.startSound("sound/cartoon_mouse_says_uh_oh.wav");
	}
}
