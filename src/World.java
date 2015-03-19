import javax.swing.JFrame;

public class World extends JFrame {
	private static final long serialVersionUID = 1L;
	private Table table;

	public World() {
		setTitle("Air Hockey");
		setSize(300, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		table = new Table();
		add(table);
		setVisible(true);
	}

	public void updateTable(int x, int y) {
		table.updateMallet(x, y);
	}

	public void movePuck() {
		table.movePuck();
	}

	public int getPuckSpeed() {
		return table.getPuckSpeed();
	}
}
