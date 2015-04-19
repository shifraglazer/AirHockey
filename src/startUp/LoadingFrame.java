package startUp;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class LoadingFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JLabel text;
	private JLabel pic;

	public LoadingFrame() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setSize(550, 106);
		setResizable(false);

		setLocation(500, 100);
		label = new JLabel();
		label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
		pic = new JLabel();
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pic.setIcon(new ImageIcon(getClass().getResource("pics/network.gif")));
		add(label);
		label.add(pic);
		text = new JLabel();
		ImageIcon image = new ImageIcon(getClass().getResource("pics/loading (2).gif"));

		text.setIcon(image);
		// text.setFont(new Font("Arial",Font.BOLD,40));
		// text.setBackground(Color.WHITE);
		label.add(text);
		setVisible(true);
	}
}
