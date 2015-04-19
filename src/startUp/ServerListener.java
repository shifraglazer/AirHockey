package startUp;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import airHockey.AirHockey;

public class ServerListener extends SetUpListener {

	public ServerListener(AirHockey hockey) {
		super(hockey);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			hockey.dispose();
			new ServerSetup();
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
