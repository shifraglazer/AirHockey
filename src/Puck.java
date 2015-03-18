import java.awt.Graphics;

public class Puck {
	protected int speed;
	private double slope;

	// the (x,y) coordinates of the center of the puck
	private int puckX;
	private int puckY;

	public Puck() {
		puckX=150;
		puckY=250;
		slope = 0;
		speed = 0;
	}

	public void setSlope(int malletX, int malletY) {
		slope = (malletY - puckY) / (malletX - puckX);
	}

	public void hit() {
		// FIXME don't know what hit speed should be
		// speed is set to 50 each time the puck is hit
		// the speed slowly decrements as time passes since the puck was last hit
		speed = 50;
	}
	public void drawPuck(Graphics g){
		System.out.println(puckX+" "+ puckY);
		g.drawOval(puckX,puckY, 10, 10);
	}
}
