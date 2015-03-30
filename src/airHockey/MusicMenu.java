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
	private Map<String, String> musicSrc;
	private Music music;
	private String lastClicked;
	private boolean on;
	private Font font;

	public MusicMenu(Font font) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		this.font = font;
		setFont(font);
		setText("Music");
		musicSrc = new HashMap<String, String>();
		JMenuItem item = new JMenuItem("TURN OFF");
		item.addActionListener(mute);
		add(item);
		addChoice("Bounce", "sound/bounceMusicTrimmed.wav");
		addChoice("Circus", "sound/comedy_circus_music.wav");
		addChoice("Esther My Child", "sound/EstherMyChild.wav");
		addChoice("Signals Classic Piano", "sound/classicPiano_Signals.wav");
		addChoice("Classic Orchestra", "sound/classical_orchestral_music.wav");
		addChoice("Light Hearted Orchestra", "sound/light_hearted_orchestral_music.wav");
		lastClicked = "sound/bounceMusicTrimmed.wav";
		music = new Music(lastClicked);
		on = true;
	}

	private void addChoice(String name, String src) {
		JMenuItem item = new JMenuItem(name);
		item.setFont(font);
		item.addActionListener(changeTrackListen);
		add(item);
		musicSrc.put(name, src);
	}

	ActionListener mute = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (on) {
				on = false;
				music.stop();
				item.setText("TURN ON");
			}
			else {
				try {
					on = true;
					item.setText("TURN OFF");
					music.resume(lastClicked);
				}
				catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
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
			if (on) {
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
