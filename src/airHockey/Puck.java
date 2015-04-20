package airHockey;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import commands.PositionCommand;

public class Puck extends Positionable implements Serializable {
	private static final long serialVersionUID = 1L;
	protected static final int PUCKRADIUS = 12;
	private int width = World.GAMEWIDTH;
	private int height = World.GAMEHEIGHT;
	private int speed;
	private float colorNum;
	// private Image image;
	private int resety;

	// the current slope the puck is moving in
	private double deltaX;
	private double deltaY;

	private boolean goal;
	private ScheduledExecutorService executor;
	private int time;

	public Puck() throws IOException {
		// image = ImageIO.read(getClass().getResource("pics/puck.jpg"));
		posX = width / 2;
		posY = height / 2;
		speed = 0;
		resety = height / 4;
		colorNum = 0;
		executor = Executors.newScheduledThreadPool(1);
		command = new PositionCommand(posX, posY, 'p');
	}

	private void reset() {
		posX = width / 2;
		if (resety == (int) height / 4) {
			resety *= 3;
		}
		else {
			resety = height / 4;
		}
		posY = resety;
		speed = 0;
	}

	public int move() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		posX += deltaX;
		posY += deltaY;

		// if hit side wall
		if (posX - PUCKRADIUS <= 4 || posX + PUCKRADIUS >= width - 4) {
			colorNum += .02;
			deltaX = -deltaX;
			decreaseSpeed();
			changeColor();
		}

		// hit top/bottom walls
		// if the puck hit a side within the goal range, returns the player who
		// scored a point
		// otherwise returns 0
		else {
			if (posY - PUCKRADIUS <= 4) {
				return checkGoal(1);
			}
			else if (posY + PUCKRADIUS >= height - 4) {
				return checkGoal(2);
			}
		}
		return 0;
	}

	private int checkGoal(int player) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// if puck within goal range, return player who scores
		if (posX > 70 && posX < width - 70) {
			time = 2;
			executor.scheduleAtFixedRate(timer, 0, 4, TimeUnit.SECONDS);
			reset();
			// TODO sound.changeTrack("sound/goal.wav");
			goal = true;
			return player;
		}

		// otherwise bounce off wall
		deltaY = -deltaY;
		decreaseSpeed();
		changeColor();
		return 0;
	}

	public void drawPuck(Graphics g) {
		g.setColor(Color.getHSBColor(colorNum, 1, 1));

		g.fillOval((int) (posX - PUCKRADIUS), (int) (posY - PUCKRADIUS), PUCKRADIUS * 2, PUCKRADIUS * 2);
		/* g.drawImage(image,(int) (posX - radius), (int) (posY - radius),
		 * radius * 2, radius * 2,null); */

		if (goal) {
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.setColor(Color.BLUE);
			// g.drawString("GOAL!", 70, 260);
		}

	}

	public Runnable timer = new Runnable() {
		public void run() {
			time--;
			if (time == 0) {
				goal = false;
			}
		}
	};

	public void setSlope(double malletX, double malletY) {
		deltaY = posY - malletY;
		deltaX = posX - malletX;

		// keep track if original x or y was negative so know which end
		// direction should be negative
		// otherwise 2 negatives will just cancel out or don't know if x or y
		// was negative
		int xneg = 1;
		int yneg = 1;
		/* if (deltaX > 0 && deltaY < 0) { yneg = -1; } else if (deltaX < 0 &&
		 * deltaY < 0) { xneg = -1; yneg = -1; } else if (deltaX < 0 && deltaY >
		 * 0) { xneg = -1; } else if (deltaX > 0 && deltaY > 0) {
		 * } */
		if (deltaX < 0) {
			xneg = -1;
		}

		if (deltaY < 0) {
			yneg = -1;
		}
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);
		if (deltaX != 0 && deltaY != 0) {
			// calculate the inverse tangent of the slope
			double angle1 = Math.atan(deltaX / deltaY);
			double angle2 = 90 - angle1;
			deltaY = Math.sin(angle2) * yneg;
			deltaX = Math.sin(angle1) * xneg;
		}
		else if (deltaX == 0) {
			deltaY = yneg;
		}
		else {
			deltaX = xneg;
		}
	}

	public void decreaseSpeed() {
		speed--;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int i) {
		speed = i;
	}

	public void changeColor() {
		colorNum += .02;
	}

	public boolean getGoal() {
		return goal;
	}

	public void setResetY(int num) {
		if (num == 1) {
			resety = height / 4;
		}
		else {
			resety = height / 4 * 3;
		}
	}

	@Override
	public void updateCoordinates(double x, double y, Table table) {
		// TODO remove println
		System.out.println("puck performing");
		posX = x;
		posY = y;
		table.repaint();
	}
}
