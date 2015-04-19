package airHockey;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import commands.PositionCommand;

public class Mallet implements Positionable, Serializable {
	private static final long serialVersionUID = 1L;
	protected static final int MALLETRADIUS = 20;
	private int malletX;
	private int malletY;
	private Image image;
	private PositionCommand malletCommand;
	private Table table;

	public Mallet(int sideCenter, Table table) throws IOException {
		malletX = World.GAMEWIDTH / 2;
		malletY = sideCenter;
		image = ImageIO.read(getClass().getResource("pics/mallet.jpg"));
		malletCommand = new PositionCommand(malletX, malletY, this);
		this.table = table;
	}

	public void drawMallet(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(image, malletX - MALLETRADIUS, malletY - MALLETRADIUS, MALLETRADIUS * 2, MALLETRADIUS * 2, null);
	}

	public void setMalletXY(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		Point point = MouseInfo.getPointerInfo().getLocation();
		if (point.getY() - locy - (MALLETRADIUS * 2) >= World.GAMEHEIGHT / 2) {
			malletX = (int) (point.getX() - locx);
			malletY = (int) (point.getY() - locy - MALLETRADIUS * 2);
		}
	}

	@Override
	public void updateCoordinates(double x, double y) {
		Point location = new Point((int) x, (int) y);
		table.updateCheckHit();
		malletX = (int) location.getX();
		malletY = (int) location.getY();
	}

	public int getMalletX() {
		return malletX;
	}

	public int getMalletY() {
		return malletY;
	}

	public PositionCommand getCommand() {
		malletCommand.updateCommand(malletX, malletY);
		return malletCommand;
	}
}
