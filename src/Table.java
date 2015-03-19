import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	private ScheduledExecutorService executor;

	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;
	private final static int PUCKRADIUS = 12;
	private final static int MALLETRADIUS = 20;
	private final static double HITDIS = PUCKRADIUS + MALLETRADIUS;

	private final static int WIDTH = 300;
	private final static int HEIGHT = 500;
	
	public Table() {
		setSize(new Dimension(WIDTH, HEIGHT));
		puck = new Puck(PUCKRADIUS, WIDTH, HEIGHT);
		mallet1 = new Mallet(WIDTH / 2, HEIGHT / 4, MALLETRADIUS);
		mallet2 = new Mallet(WIDTH / 2, (HEIGHT / 4) * 3, MALLETRADIUS);
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		puck.drawPuck(g);
		mallet1.drawMallet(g);
		mallet2.drawMallet(g);
	}

	public void movePuck() {
		puck.move();
		if (checkHit()) {
			// bump so decrease speed
			puck.decreaseSpeed();
		}
	}

	public void updateMallet(int x, int y) {
		mallet2.setMalletX(x);
		mallet2.setMalletY(y);
		if (checkHit()) {
			puck.setSpeed(90);
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000, TimeUnit.MILLISECONDS);
			}
		}

		repaint();
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

	public int getPuckSpeed() {
		return puck.getSpeed();
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
