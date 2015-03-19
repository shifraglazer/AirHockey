import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

public class Puck {
	private AtomicInteger speed;
	private int deltaX;
	private int deltaY;
	private static final double DECREASE = .8;

	// the (x,y) coordinates of the center of the puck
	protected int puckX;
	protected int puckY;
	private int radius;
	private int width;
	private int height;

	public Puck(int radius, int width, int height) {
		this.radius = radius;
		puckX = width / 2;
		puckY = height / 2;
		speed = new AtomicInteger(0);
		this.width = width;
		this.height = height;
	}

	public void move() {
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
		g.fillOval(puckX - radius, puckY - radius, radius * 2, radius * 2);
	}

	// FIXME
	public void setSlope(int malletX, int malletY) {
		deltaX = puckY - malletY;
		deltaY = puckX - malletX;
		int gcd = gcd(deltaX, deltaY);
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

	public int gcd(int a, int b) {
		if (a == 0 || b == 0)
			return a + b;
		return gcd(b, a % b);
	}

	public void decreaseSpeed() {
		speed.decrementAndGet();
		// speed *= DECREASE;
	}

	public int getSpeed() {
		return speed.get();
	}

	public void setSpeed(int i) {
		speed.set(i);
	}
}
