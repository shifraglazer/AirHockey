package airHockey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class SoundMute implements ActionListener {
	private Sound sound = Sound.getInstance();

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();
		if (sound.isOn()) {
			sound.turnOff();
			sound.stop();
			item.setText("TURN SOUND ON");
		}
		else {
			sound.turnOn();
			item.setText("TURN SOUND OFF");
		}
	}
}