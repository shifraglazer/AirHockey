package airHockey;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mallet {
	protected static final int MALLETRADIUS = 20;
	private int malletX;
	private int malletY;
	private Image image;

	public Mallet(int sideCenter) throws IOException {
		malletX = World.GAMEWIDTH / 2;
		malletY = sideCenter;
		image = ImageIO.read(getClass().getResource("pics/mallet.jpg"));
	}

	public void drawMallet(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(image, malletX - MALLETRADIUS, malletY - MALLETRADIUS,
				MALLETRADIUS * 2, MALLETRADIUS * 2, null);
	}

	public void setMalletXY(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		Point point = MouseInfo.getPointerInfo().getLocation();
		if(point.getY()-locy -(MALLETRADIUS*2) >= World.GAMEHEIGHT/2){
			malletX = (int) (point.getX() - locx);

			// TODO remove println
			System.out.println("Mallet 1 Y: " + point.getY());

			malletY = (int) (point.getY() - locy - MALLETRADIUS * 2);
	}
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
