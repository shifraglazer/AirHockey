import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JFrame;

public class World extends JFrame {
	private static final long serialVersionUID = 1L;
	private Table table;

	public World() throws IOException {
		setTitle("Air Hockey");
		setSize(300, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setUndecorated(true);
		table = new Table();
		add(table);

		// mallet moves with mouse
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				table.moveMallet(getLocation());
				repaint();
			}
		});

		setVisible(true);
	}

	public void movePuck() {
		table.movePuck();
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}

	public double getSlope() {
		return table.getSlope();
	}

}
