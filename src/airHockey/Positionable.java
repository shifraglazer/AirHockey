package airHockey;

import java.awt.Graphics;
import java.awt.Image;

import commands.Command;

// both Mallet and Puck extend Positionable
public abstract class Positionable {
	protected double posX;
	protected double posY;

	//FIXME had problems serializing since image is not serializable
	protected Image image;

	protected void updateCoordinates(double x, double y) {
		posX = x;
		posY = y;
	}

	protected abstract void draw(Graphics g);

	protected abstract Command getCommand();
}
