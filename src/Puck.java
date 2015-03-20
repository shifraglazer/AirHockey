import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

public class Puck {
	private int speed;
	private double deltaX;
	private double deltaY;
	private static final double DECREASE = .8;
	private static final int MOVERATE=2;

	// the (x,y) coordinates of the center of the puck
	protected double puckX;
	protected double puckY;
	private int radius;
	private int width;
	private int height;

	public Puck(int radius, int width, int height) {
		this.radius = radius;
		puckX = width / 2;
		puckY = height / 2;
		speed =0;
		this.width = width;
		this.height = height;
	}

	public void move() {
	
		double angle1 =(90*deltaX)/(deltaX+deltaY);
		double angle2=90-angle1;
		deltaY=(Math.sin(angle1)/MOVERATE)*speed;
		deltaX=(Math.sin(angle2)/MOVERATE)*speed;
		puckX += deltaX;
		puckY += deltaY;
		// if hit side wall
		if (puckX - radius <= 0 || puckX + radius >= width) {
			deltaX = -deltaX;
			decreaseSpeed();
		}

		// hit top/bottom walls
		else if (puckY - radius <= 0 || puckY + radius >= height) {
			// TODO factor in the goals
			deltaY = -deltaY;
			decreaseSpeed();
		}
	}

	public void drawPuck(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int)(puckX - radius), (int)(puckY - radius), radius * 2, radius * 2);
	}

	// FIXME dont need?
	public void setSlope(int malletX, int malletY) {
		deltaX = puckY - malletY;
		deltaY = puckX - malletX;
		double gcd = gcd(deltaX, deltaY);
		if (gcd > 1) {
			deltaX /= gcd;
			deltaY /= gcd;
		}
	}

	public double getSlope() {
		if (deltaY != 0) {
			return deltaX / deltaY;
		}
		return 0;
	}

	public double gcd(double a, double b) {
		if (a == 0 || b == 0)
			return a + b;
		return gcd(b, a % b);
	}

	public void decreaseSpeed() {
		speed--;
		// speed *= DECREASE;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int i) {
		speed=i;
	}
}
