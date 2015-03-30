package airHockey;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MusicMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private Font font;

	private Map<String, String> musicSrc;
	private Music music;
	private String lastClicked;
	private boolean musicOn;

	private Sound sound;
	private boolean soundOn;

	public MusicMenu(Font font) {
		this.font = font;
		setFont(font);
		setText("Music");
		musicSrc = new HashMap<String, String>();

		JMenuItem item = new JMenuItem("TURN MUSIC OFF");
		item.addActionListener(mute);
		add(item);

		JMenuItem item2 = new JMenuItem("TURN SOUND OFF");
		item2.addActionListener(soundMute);
		add(item2);

		addChoice("Bounce", "sound/bounceMusic.wav");
		addChoice("Circus Comedy", "sound/comedy_circus_music.wav");
		addChoice("Sailor Piccolo", "sound/sailorPiccolo.wav");
		addChoice("Signals Classic Piano", "sound/classicPiano_Signals.wav");
		addChoice("Classic Orchestra", "sound/classical_orchestral_music.wav");
		addChoice("Light Hearted Orchestra", "sound/light_hearted_orchestral_music.wav");
		lastClicked = "sound/bounceMusic.wav";
	}

	private void addChoice(String name, String src) {
		JMenuItem item = new JMenuItem(name);
		item.setFont(font);
		item.addActionListener(changeTrackListen);
		add(item);
		musicSrc.put(name, src);
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
			String name = (String) item.getText();
			String src = musicSrc.get(name);
			lastClicked = src;
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
