package airHockey;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MusicMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private Font font;

	private Map<String, URL> musicSrc;
	private Music music;
	private URL lastClicked;
	private boolean musicOn;

	private Sound sound;
	private boolean soundOn;
	private Class<? extends MusicMenu> cclass;

	private JMenuItem oldItem;
	private final Color defaultForeG = Color.BLACK;
	private final Color defaultBackG = Color.decode("#FAFAFA");
	private final Color selectedForeG = Color.decode("#0B2161");
	private final Color selectedBackG = Color.decode("#E0ECF8");

	// Music starts at random track
	private Random random;
	private int randomStart;

	public MusicMenu(Font font) throws UnsupportedAudioFileException, IOException {
		this.font = font;
		setFont(font);
		setText("Music");
		musicSrc = new HashMap<String, URL>();

		JMenuItem item = new JMenuItem("TURN MUSIC OFF");
		item.addActionListener(mute);
		add(item);

		JMenuItem item2 = new JMenuItem("TURN SOUND OFF");
		item2.addActionListener(soundMute);
		add(item2);

		// choose a random track to start with
		// at this point there are 7 tracks, so will select 0-6s
		random = new Random();
		randomStart = random.nextInt(7);

		// add all the sound tracks to the menu and maps
		cclass = getClass();
		addChoice("Bounce", "sound/bounceMusic.wav");
		addChoice("Circus Comedy", "sound/comedy_circus_music.wav");
		addChoice("Sailor Piccolo", "sound/sailorPiccolo.wav");
		addChoice("Gameloop (Original)", "sound/gameloop.wav");
		addChoice("Signals (Classic Piano)", "sound/classicPiano_Signals.wav");
		addChoice("Classic Orchestra", "sound/classical_orchestral_music.wav");
		addChoice("Light Hearted Orchestra", "sound/light_hearted_orchestral_music.wav");
	}

	private void addChoice(String name, String src) throws UnsupportedAudioFileException, IOException {
		JMenuItem item = new JMenuItem(name);
		item.setFont(font);
		item.addActionListener(changeTrackListen);
		add(item);
		URL url = cclass.getResource(src);
		musicSrc.put(name, url);

		// if the track is the selected random starting track, select it
		if (musicSrc.size() == randomStart) {
			selectItem(item, url);
		}
		// otherwise set the colors to the default colors
		else {
			deselectItem(item);
		}
	}

	public void startMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		music = new Music(lastClicked);
		musicOn = true;
	}

	public void startSound(String noise) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		sound = new Sound(noise);
		soundOn = true;
	}

	public void changeSound(String filename) throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		if (soundOn) {
			sound.changeTrack(filename);
		}
	}

	public void deselectItem(JMenuItem item) {
		item.setForeground(defaultForeG);
		item.setBackground(defaultBackG);
	}

	public void selectItem(JMenuItem item, URL src) {
		// TODO remove println
		System.out.println("music choice: " + src);
		item.setForeground(selectedForeG);
		item.setBackground(selectedBackG);
		oldItem = item;
		lastClicked = src;
	}

	ActionListener mute = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (musicOn) {
				musicOn = false;
				music.stop();
				item.setText("TURN MUSIC ON");
			}
			else {
				try {
					musicOn = true;
					item.setText("TURN MUSIC OFF");
					music.resume(lastClicked);
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	ActionListener soundMute = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (soundOn) {
				soundOn = false;
				sound.stop();
				item.setText("TURN SOUND ON");
			}
			else {
				soundOn = true;
				item.setText("TURN SOUND OFF");
			}
		}
	};

	ActionListener changeTrackListen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();

			// change colors;
			deselectItem(oldItem);

			URL src = musicSrc.get(item.getText());
			selectItem(item, src);

			if (musicOn) {
				try {
					music.changeTrack(src);
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
}
