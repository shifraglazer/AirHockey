
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

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

	public void setMalletXY(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		Point point = MouseInfo.getPointerInfo().getLocation();
		malletX = (int) (point.getX() - locx);
		malletY = (int) (point.getY() - locy - radius);
	}
	public void updateMallet2(Point location){
		double locx = location.getX();
		double locy = location.getY();
		malletX = (int) (locx);
		malletY = (int) (locy - radius);
	}

	public int getMalletX() {
		return malletX;
	}

	public int getMalletY() {
		return malletY;
	}
}
