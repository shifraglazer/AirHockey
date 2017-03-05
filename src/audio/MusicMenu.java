package audio;

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
	private Music music = new Music();
	private URL lastClicked;
	private Class<? extends MusicMenu> cclass;

	private JMenuItem oldItem;
	private final Color defaultForeG = Color.BLACK;
	private final Color defaultBackG = Color.decode("#FAFAFA");
	private final Color selectedForeG = Color.decode("#0B2161");
	private final Color selectedBackG = Color.decode("#E0ECF8");

	// music starts at random track
	private int randomStart;

	public MusicMenu(JMenuItem soundItem, Font font) throws UnsupportedAudioFileException, IOException {
		this.font = font;
		setFont(font);
		setText("Music");
		musicSrc = new HashMap<String, URL>();

		JMenuItem item = new JMenuItem("TURN MUSIC OFF");
		item.addActionListener(mute);
		add(item);

		add(soundItem);

		// choose a random track to start with
		// at this point there are 8 tracks
		// so chooses 0-7 and then add 1 so get right choice on list
		Random random = new Random();
		randomStart = random.nextInt(8) + 1;

		// add all the sound tracks to the menu and maps
		cclass = getClass();
		addChoice("Bounce", "sound/bounceMusic.wav");
		addChoice("Circus Comedy", "sound/comedy_circus_music.wav");
		addChoice("Sailor Piccolo", "sound/sailorPiccolo.wav");
		addChoice("Gameloop (Original)", "sound/gameloop.wav");
		addChoice("Gameloop2 (Original)", "sound/gameloop2.wav");
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

	//FIXME is there an easier way to toggle colors??
	public void deselectItem(JMenuItem item) {
		item.setForeground(defaultForeG);
		item.setBackground(defaultBackG);
	}

	public void selectItem(JMenuItem item, URL src) {
		item.setForeground(selectedForeG);
		item.setBackground(selectedBackG);
		oldItem = item;
		lastClicked = src;
	}

	public void startMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		music.turnOn();
		music.resume(lastClicked);
	}

	private ActionListener mute = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (music.isOn()) {
				music.turnOff();
				music.stop();
				item.setText("TURN MUSIC ON");
			}
			else {
				try {
					music.turnOn();
					item.setText("TURN MUSIC OFF");
					music.resume(lastClicked);
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	private ActionListener changeTrackListen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();

			// change colors;
			deselectItem(oldItem);

			URL src = musicSrc.get(item.getText());
			selectItem(item, src);
			if (music.isOn()) {
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
