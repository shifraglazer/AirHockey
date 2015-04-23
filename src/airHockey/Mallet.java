package airHockey;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

import commands.MalletCommand;

public class Mallet extends Positionable {
	protected static final int MALLETRADIUS = 20;

	public Mallet(int sideCenter) throws IOException {
		posX = World.GAMEWIDTH / 2;
		posY = sideCenter;
		image = ImageIO.read(getClass().getResource("pics/mallet.jpg"));
	}

	public void setMalletXY(double x, double y) {
		Point point = MouseInfo.getPointerInfo().getLocation();
		if (point.getY() - y - (MALLETRADIUS * 2) >= World.FRAMEHEIGHT / 2) {
			posX = point.getX() - x;
			posY = point.getY() - y - MALLETRADIUS * 2;
		}
	}

	@Override
	protected void draw(Graphics g) {
		g.drawImage(image, (int) posX - MALLETRADIUS, (int) posY - MALLETRADIUS, MALLETRADIUS * 2, MALLETRADIUS * 2, null);
	}

	@Override
	public MalletCommand getCommand() {
		return new MalletCommand(posX, posY);
	}
}
