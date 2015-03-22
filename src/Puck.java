import java.awt.Color;
import java.awt.Graphics;

public class Puck {
	private int speed;
	private double deltaX;
	private double deltaY;
	private float colorNum;

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
		speed = 0;
		this.width = width;
		this.height = height;
		colorNum = 0;
	}

	public void move() {
		// double angle1=Math.atan(deltaX/deltaY);
		// double angle1 =(90*deltaX)/(deltaX+deltaY);
		// double angle2=90-angle1;
		// deltaY=(Math.sin(angle1)/MOVERATE)*speed;
		// deltaX=(Math.sin(angle2)/MOVERATE)*speed;
		puckX += deltaX;
		puckY += deltaY;
		// if hit side wall
		if (puckX - radius <= 0 || puckX + radius >= width) {
			colorNum += .02;
			System.out.println("flip");
			deltaX = -deltaX;
			decreaseSpeed();
		}

		// hit top/bottom walls
		else if (puckY - radius <= 0 || puckY + radius >= height) {
			// TODO factor in the goals
			/* if (puckX > 50 && puckX < width - 50) {
			 * goal = true;
			 * reset();
			 * }
			 * else { */
			colorNum += .02;
			deltaY = -deltaY;
			System.out.println("flip");
			decreaseSpeed();
		}
	}

	private void reset() {
		puckX = width / 2;
		puckY = height / 2;
		speed = 0;
	}

	public void drawPuck(Graphics g) {
		g.setColor(Color.getHSBColor(colorNum, 1, 1));
		System.out.println(deltaX + " " + deltaY);
		System.out.println(speed);

		/* if (goal || !executor.isShutdown()) {
		 * g.setFont(new Font("Arial", Font.BOLD, 50));
		 * g.setColor(Color.BLUE);
		 * g.drawString("GOAL!", 70, 260);
		 * System.out.println("goal!");
		 * // time = 2;
		 * // executor = Executors.newScheduledThreadPool(1);
		 * // executor.scheduleAtFixedRate(timer, 0, 3, TimeUnit.SECONDS);
		 * goal = false;
		 * } */

		g.fillOval((int) (puckX - radius), (int) (puckY - radius), radius * 2, radius * 2);
	}

	// FIXME dont need?
	public void setSlope(int malletX, int malletY) {
		deltaX = puckY - malletY;
		deltaY = puckX - malletX;
		// double hyp=Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2));

		// double angle1 =(90*deltaX)/(deltaX+deltaY);
		
		int xneg = 1;
		int yneg = 1;
		if (deltaX < 0) {
			xneg = -1;
		}
		if (deltaY < 0) {
			yneg = -1;
		}

		double angle1 = Math.atan(deltaX / deltaY);
		double angle2 = 90 - angle1;
		deltaY = (Math.sin(angle1) * speed) * yneg;
		deltaX = (Math.sin(angle2) * speed) * xneg;

	}

	public double getSlope() {
		if (deltaY != 0) {
			return deltaX / deltaY;
		}
		return 0;
	}

	public void decreaseSpeed() {
		speed -= .5;
		// speed *= DECREASE;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int i) {
		speed = i;
	}
}
