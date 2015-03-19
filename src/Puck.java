import java.awt.Graphics;

public class Puck {
	protected int speed;
	private double deltaX;
	private double deltaY;

	// the (x,y) coordinates of the center of the puck
	private int puckX;
	private int puckY;
	private int radius;
	private int width;
	private int height;

	public Puck(int radius, int width, int height) {
		this.radius = radius;
		puckX = width / 2;
		puckY = height / 2;
		deltaX = -3;
		deltaY = 2;
		speed = 90;
		this.width=width;
		this.height=height;
	}

	public int getPuckX() {
		return puckX;
	}

	public void setPuckX(int puckX) {
		this.puckX = puckX;
	}

	public int getPuckY() {
		return puckY;
	}

	public void setPuckY(int puckY) {
		this.puckY = puckY;
	}

	public void setSlope(int malletX, int malletY) {
		deltaX = puckY - malletY;
		deltaY = puckX - malletX;
	}

	public void move() {
		//TODO factor in the goals 
		puckX += deltaX;
		puckY += deltaY;
		//if hit side wall
		if(puckX-radius<=0||puckX+radius>=width){
			deltaX=-deltaX;
		}
		//hit top/bottom walls
		else if(puckY-radius<=0||puckY+radius>=height){
			deltaY=-deltaY;
		}
	}

	public void hit() {
		// FIXME don't know what hit speed should be
		// speed is set to 50 each time the puck is hit
		// the speed slowly decrements as time passes since the puck was last
		// hit
		speed = 50;
	}

	public void drawPuck(Graphics g) {
		System.out.println(puckX + " " + puckY);
		g.fillOval(puckX - radius, puckY - radius, radius * 2, radius * 2);
	}
}
