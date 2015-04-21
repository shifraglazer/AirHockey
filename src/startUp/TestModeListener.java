package startUp;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import airHockey.AirHockey;
import airHockey.ClientWorld;
import airHockey.ServerWorld;

public class TestModeListener extends SetUpListener {

	public TestModeListener(AirHockey hockey) {
		super(hockey);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread1 = new Thread() {
			public void run() {
				try {
					new ServerWorld(true);
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

		hockey.dispose();
	}

}
