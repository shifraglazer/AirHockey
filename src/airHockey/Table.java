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

	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;
	private Image tableImg;

	private static final double HITDIS = Puck.PUCKRADIUS + Mallet.MALLETRADIUS;

	private Image animated;
	private final static int GOALPICSIZE = 56;
	private ScheduledExecutorService executor;

	public Table() throws IOException {
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		puck = new Puck();
		mallet1 = new Mallet((height / 4) * 3 - 10);
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

	public String getMallet1Location() {
		return mallet1.getMalletX() + " " + mallet1.getMalletY();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tableImg, 0, 0, width, height, null);
		puck.drawPuck(g);
		mallet1.drawMallet(g);
		mallet2.drawMallet(g);
		Graphics2D g2 = (Graphics2D) g;
		if (puck.getGoal()) {
			g2.drawImage(animated, (width / 2) - GOALPICSIZE / 2, (height / 2) - GOALPICSIZE / 2, GOALPICSIZE + 1,
					GOALPICSIZE, this);
		}
	}

	public int movePuck() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		int point = puck.move();
		if (checkHit()) {
			// bump so decrease speed
			// puck.decreaseSpeed();
			// TODO remove setseped and uncomment decrease
			puck.setSpeed(20);
		}
		return point;
	}

	public boolean checkHit() {
		return calcMallet(mallet1) || calcMallet(mallet2);
	}

	public boolean calcMallet(Mallet mallet) {
		int malletX = mallet.getMalletX();
		int malletY = mallet.getMalletY();
		double diff = Math.sqrt(Math.pow((malletX - puck.puckX), 2) + Math.pow(malletY - puck.puckY, 2));
		if (diff <= HITDIS) {
			puck.setSlope(malletX, malletY);
			return true;
		}
		return false;
	}

	public void moveMallet(Point location) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// if (location.getY() < MIDDLE) {

		mallet1.setMalletXY(location);
		// }
		if (checkHit()) {
			// TODO smusicMenu.changeSound("sound/jumping_teon.wav");
			puck.changeColor();
			puck.setSpeed(20);
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
			}
		}
		repaint();
	}

	public void moveMallet2(Point location) {
		// if (location.getY() < MIDDLE) {
		mallet2.updateMallet2(location);
		// }
		if (checkHit()) {
			puck.changeColor();
			puck.setSpeed(20);
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
		return puck.getSpeed();
	}

	public Mallet getMallet1() {
		return mallet1;
	}

	// speed decreases as time elapses since was last hit by a mallets
	private Runnable decreaseSpeed = new Runnable() {
		public void run() {
			if (puck.getSpeed() > 0) {
				puck.decreaseSpeed();
			}
			else {
				executor.shutdown();
			}
		}
	};
}
