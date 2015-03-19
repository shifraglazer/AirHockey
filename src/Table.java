import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	private int sleep;
	private ScheduledExecutorService executor;

	private final static int WIDTH = 300;
	private final static int HEIGHT = 500;
	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;
	private final static int PUCKRADIUS = 5;
	private final static int MALLETRADIUS = 8;
	private final static double HITDIS = PUCKRADIUS + MALLETRADIUS;

	public Table() {
		setSize(new Dimension(WIDTH, HEIGHT));

		sleep = 0;
		// FIXME not sure if want to create all these Objects here or in
		// separate World class
		// so easier to repaint
		puck = new Puck(PUCKRADIUS, WIDTH, HEIGHT);

		// TODO check if print circles in correct starting locations
		mallet1 = new Mallet(WIDTH / 2, HEIGHT / 4, MALLETRADIUS);
		mallet2 = new Mallet(WIDTH / 2, (HEIGHT / 4) * 3, MALLETRADIUS);
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000,
				TimeUnit.MILLISECONDS);

		setVisible(true);

	}

	public int getSleep() {
		return sleep;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		puck.drawPuck(g);
		mallet1.drawMallet(g);
		mallet2.drawMallet(g);
	}

	public boolean checkHit() {
		int puckX = puck.getPuckX();
		int puckY = puck.getPuckY();
		int mallet1X = mallet1.getMalletX();
		int mallet1Y = mallet1.getMalletY();
		int mallet2X = mallet2.getMalletX();
		int mallet2Y = mallet2.getMalletY();
		double diffM1 = Math.sqrt(Math.pow((mallet1X - puckX), 2)
				+ Math.pow(mallet1Y - puckY, 2));
		double diffM2 = Math.sqrt(Math.pow((mallet2X - puckX), 2)
				+ Math.pow(mallet2Y - puckY, 2));
		if (diffM1 <= HITDIS) {
			puck.setSlope(mallet1X, mallet1Y);
			return true;
		} else if (diffM2 <= HITDIS) {
			puck.setSlope(mallet2X, mallet2Y);
			return true;
		}
		return false;
	}

	public void movePuck() {
		puck.move();
		if (checkHit()) {
			//bump so decrease speed
			puck.decreaseSpeed();
		}
	}

	public int getPuckSpeed() {
		return puck.speed;
	}

	public void updateMallet(int x, int y) {
		mallet2.setMalletX(x);
		mallet2.setMalletY(y);
		if (checkHit()) {
			System.out.println(puck.speed);
			puck.hit();
			System.out.println("hit");
			System.out.println(puck.speed);
			// restart executor
			// TODO set up that only shuts down if executor is not null
			if (executor.isShutdown()) {
				executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(decreaseSpeed, 0, 1000,
						TimeUnit.MILLISECONDS);
			}
		}

		repaint();
	}

	private Runnable decreaseSpeed = new Runnable() {
		public void run() {
			// FIXME don't kknow what default sleep should be or if this is how
			// should calculate it
			// every second since the puck was hit, decrease the speed
			// really just increase the loop speeds
			if (puck.speed > 0) {
				puck.speed--;
				sleep = (200 - puck.speed);
			} else {
				executor.shutdown();
			}
		}
	};
}
