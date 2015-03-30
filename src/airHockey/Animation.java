package airHockey;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Animation extends JFrame {
	private static final long serialVersionUID = 1L;
	private IceSkate ice;
	private JLabel label;

	public Animation() throws IOException {
		setSize(300, 300);
		ice = new IceSkate();
		label = new JLabel();
		label.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("pics/Hockey_Boy.gif"))));
		add(label);
		
	}

	public static void main(String args[]) {
		Animation animation;
		try {
			animation = new Animation();
			animation.setVisible(true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
