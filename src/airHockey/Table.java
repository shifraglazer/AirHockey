package airHockey;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	private int width = World.GAMEWIDTH;
	private int height = World.GAMEHEIGHT;

	private final Puck PUCK = Puck.getInstace();
	private Mallet mallet1;
	private Mallet mallet2;
	private Image tableImg;

	private static final double HITDIS = Puck.PUCKRADIUS + Mallet.MALLETRADIUS;

	private Image animated;
	private final static int GOALPICSIZE = 56;
	private ScheduledExecutorService executor;
	private Sound SOUND = Sound.getInstance();

	public Table() throws IOException {
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		mallet1 = new Mallet(((height / 4) * 3) - 10);
		mallet2 = new Mallet(height / 4 + 10);
		System.out.println("mallet 1: " + mallet1.getMalletX() + ", " + mallet1.getMalletY());
		System.out.println("mallet 2: " + mallet2.getMalletX() + ", " + mallet2.getMalletY());
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
		tableImg = ImageIO.read(getClass().getResource("pics/table1.jpg"));
		animated = new ImageIcon(getClass().getResource("pics/puck.gif")).getImage();

		System.out.println("expected width: " + width + " actual: " + getWidth());
		System.out.println("expected height: " + height + " actual: " + getHeight());
	}

	public MalletCommand getMallet1Location() {
		return mallet1.getCommand();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tableImg, 0, 0, width, height, null);
		PUCK.drawPuck(g);
		mallet1.drawMallet(g);
		mallet2.drawMallet(g);
		Graphics2D g2 = (Graphics2D) g;
		if (PUCK.getGoal()) {
			g2.drawImage(animated, (width / 2) - GOALPICSIZE / 2, (height / 2) - GOALPICSIZE / 2, GOALPICSIZE + 1, GOALPICSIZE, this);
		}
	}

	public int movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		int point = PUCK.move();
		if (checkHit()) {
			// bump so decrease speed
			PUCK.decreaseSpeed();

		}
		return point;
	}

	public boolean checkHit() {
		return calcMallet(mallet1) || calcMallet(mallet2);
	}

	public boolean calcMallet(Mallet mallet) {
		int malletX = mallet.getMalletX();
		int malletY = mallet.getMalletY();
		double diff = Math.sqrt(Math.pow((malletX - PUCK.puckX), 2) + Math.pow(malletY - PUCK.puckY, 2));
		if (diff <= HITDIS) {
			PUCK.setSlope(malletX, malletY);
			return true;
		}
		return false;
	}

	public void moveMallet(Point location) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// if (location.getY() < MIDDLE) {

		mallet1.setMalletXY(location);
		// }
		if (checkHit()) {
			// FIXME don't know if should play sound here - not playing at right time anyway
			SOUND.changeTrack("sound/score.wav");
			PUCK.changeColor();
			PUCK.setSpeed(20);
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
			}
		}
		repaint();
	}

	public void setPuck(int number) {
		PUCK.setResetY(number);
	}

	public void moveMallet2(Point location) {
		// if (location.getY() < MIDDLE) {
		mallet2.updateMallet2(location);
		// }
		if (checkHit()) {
			PUCK.changeColor();
			PUCK.setSpeed(20);
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
			}
		}
		repaint();
	}

	public int getPuckSpeed() {
		return PUCK.getSpeed();
	}

	public Mallet getMallet1() {
		return mallet1;
	}

	// speed decreases as time elapses since was last hit by a mallets
	private Runnable decreaseSpeed = new Runnable() {
		public void run() {
			if (PUCK.getSpeed() > 0) {
				PUCK.decreaseSpeed();
			}
			else {
				executor.shutdown();
			}
		}
	};

	public void updatePuckCoordinates(double x, double y) {
		PUCK.updateCoordinates(x, y);
	}
}
