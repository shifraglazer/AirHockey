package airHockey;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mallet {
	private int malletX;
	private int malletY;
	private int radius;
	private Image image;

	public Mallet(int sideCenter, int boardCenter, int radius) throws IOException {
		this.radius = radius;
		malletX = sideCenter;
		malletY = boardCenter;
		image=ImageIO.read(getClass().getResource("pics/mallet.jpg"));
	}

	public void drawMallet(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(image,malletX - radius, malletY - radius, radius * 2, radius * 2,null);
	}

	public void setMalletXY(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		Point point = MouseInfo.getPointerInfo().getLocation();
		malletX = (int) (point.getX() - locx);

		// TODO remove println
		System.out.println("Mallet 1 Y: " + point.getY());

		malletY = (int) (point.getY() - locy - radius);
	}

	public void updateMallet2(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		malletX = (int) (locx);
		malletY = (int) (locy);
	}

	public int getMalletX() {
		return malletX;
	}

	public int getMalletY() {
		return malletY;
	}
}
