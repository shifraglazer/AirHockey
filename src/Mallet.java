import java.awt.Color;
import java.awt.Graphics;

public class Mallet {
	private int malletX;
	private int malletY;

	private int radius;

	public Mallet(int sideCenter, int boardCenter, int radius) {
		this.radius = radius;
		malletX = sideCenter;
		malletY = boardCenter;
	}

	public void drawMallet(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(malletX - radius, malletY - radius, radius * 2, radius * 2);
	}

	public int getMalletX() {
		return malletX;
	}

	public void setMalletX(int malletX) {
		this.malletX = malletX;
	}

	public int getMalletY() {
		return malletY;
	}

	public void setMalletY(int malletY) {
		this.malletY = malletY;
	}
}
