import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Table extends JFrame {
	private static final long serialVersionUID = 1L;
	private int sleep;
	private ScheduledExecutorService executor;
	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;

	public Table() {
		int width = 300;
		int height = 500;
		setTitle("Air Hockey");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// FIXME not sure if want to create all these Objects here or in separate World class
		// so easier to repaint
		puck = new Puck();

		// TODO check if print circles in correct starting locations
		mallet1 = new Mallet(height / 4, width / 2);
		mallet2 = new Mallet((height / 4) * 3, width / 2);
		new GameLoopThread(this).start();

		setVisible(true);
	}

	public int getSleep() {
		return sleep;
	}

	ActionListener hit = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			// reset the pucks speed
			puck.hit();
			// restart executor
			// TODO set up that only shuts down if executor is not null
			executor.shutdown();
			executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(decreaseSpeed, 0, 1, TimeUnit.SECONDS);
		}
	};

	private Runnable decreaseSpeed = new Runnable() {
		public void run() {
			// FIXME don't kknow what default sleep should be or if this is how should calculate it
			// every second since the puck was hit, decrease the speed
			// really just increase the loop speeds
			if (puck.speed > 0) {
				puck.speed--;
				sleep = (200 - puck.speed);
			}
		}
	};
}
