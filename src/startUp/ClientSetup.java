package startUp;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JTextField;

import airHockey.ClientWorld;

public class ClientSetup extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField ip;

	public ClientSetup() {
		setSize(200, 70);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ip = new JTextField("Please enter your IP address");
		add(ip);
		ip.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField f = (JTextField) e.getSource();
				f.selectAll();
			}
		});
		ip.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String ipaddress = ip.getText();
					try {
						new ClientWorld(ipaddress);
					}
					catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});
		setVisible(true);

	}
}
