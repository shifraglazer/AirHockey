package airHockey;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			/* LookAndFeel lat = UIManager.getLookAndFeel(); UIDefaults defaults
			 * = lat.getDefaults(); defaults.replace(key, value); for(Object
			 * key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key)); } */
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
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
					new ClientWorld();
				}
				catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		};
		thread1.start();
		thread2.start();
	}

}
