
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class World extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Table table;

	public World() throws IOException {

		setTitle("Air Hockey");
		setSize(300, 500);
		// setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar menu = new JMenuBar();
		JButton exit = new JButton("X");
		exit.setBackground(Color.RED);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exit);
		setJMenuBar(menu);
		setUndecorated(true);
		table = new Table();
		add(table);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void movePuck() {
		table.movePuck();
	}

	public void moveMallet(Point location) {
		table.moveMallet(location);
	}

	public void moveMallet2(Point location) {
		table.moveMallet2(location);
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}

	public double getSlope() {
		return table.getSlope();
	}
}
