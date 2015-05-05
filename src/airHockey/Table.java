package airHockey;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import commands.Command;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	private int width = World.GAMEWIDTH;
	private int height = World.FRAMEHEIGHT;

	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;
	private Image tableImg;

	private static final double HITDIS = Puck.PUCKRADIUS + Mallet.MALLETRADIUS;

	private Image animated;
	private final static int GOALPICSIZE = 56;
	private ScheduledExecutorService executor;

	// private Sound SOUND = Sound.getInstance();

	public Table() throws IOException {
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		puck = new Puck();
		mallet1 = new Mallet(((height / 4) * 3) - 10);
		mallet2 = new Mallet(height / 4 + 10);
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
		tableImg = ImageIO.read(getClass().getResource("pics/table1.jpg"));
		animated = new ImageIcon(getClass().getResource("pics/puck.gif")).getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tableImg, 0, 0, width, height, null);
		puck.draw(g);
		mallet1.draw(g);
		mallet2.draw(g);
		Graphics2D g2 = (Graphics2D) g;
		if (puck.getGoal()) {
			g2.drawImage(animated, (width / 2) - GOALPICSIZE / 2, (height / 2) - GOALPICSIZE / 2, GOALPICSIZE + 1, GOALPICSIZE, this);
		}
	}

	protected int movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		int point = puck.move();
		if (checkHit()) {
			// bump so decrease speed
			puck.decreaseSpeed();
			puck.changeColor();
		}
		// return which player number deserves a point
		return point;
	}

	private boolean checkHit() {
		return calcMallet(mallet1) || calcMallet(mallet2);
	}

	private boolean calcMallet(Mallet mallet) {
		double malletX = mallet.posX;
		double malletY = mallet.posY;
		double diff = Math.sqrt(Math.pow((malletX - puck.posX), 2) + Math.pow(malletY - puck.posY, 2));
		if (diff <= HITDIS) {
			puck.setSlope(malletX, malletY);
			return true;
		}
		return false;
	}

	protected void updateMalletCoordinates(double x, double y, int malletNum) {
		if (malletNum == 1) {
			mallet1.setMalletXY(x, y);
		}
		if (checkHit()) {
			// FIXME don't know if should play sound here - not playing at right time anyway
			// SOUND.changeTrack("sound/score.wav");
			puck.hit();
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
			}
		}
		if (malletNum == 2) {
			mallet2.updateCoordinates(x, y);
		}
		repaint();
	}

	protected Mallet getMallet1() {
		return mallet1;
	}

	protected void updatePuck(double x, double y, int speed) {
		puck.update(x, y, speed);
		repaint();
	}

	protected int getPuckSpeed() {
		return puck.getSpeed();
	}

	protected void setPuck(int number) {
		puck.setResetY(number);
	}

	//FIXME getCommand without 'p' or 'm'
	protected Command getCommand(char positionable) {
		if (positionable == 'p') {
			return puck.getCommand();
		}
		return mallet1.getCommand();
	}

	// speed decreases as time elapses since was last hit by a mallets
	private Runnable decreaseSpeed = new Runnable() {
		public void run() {
			if (puck.isMoving()) {
				puck.decreaseSpeed();
			}
			else {
				executor.shutdown();
			}
		}
	};
}
