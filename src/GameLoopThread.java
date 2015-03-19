import java.io.IOException;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread() throws IOException {
		this.world = new World();
	}

	@Override
	public void run() {
		while (true) {
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				world.movePuck();
				world.repaint();
			}
			try {
				// FIXME need to factor slope into sleep
				// made up random formula
				double slope = world.getSlope();
				int sleep = (int) ((100 - speed) + slope);
				if (sleep < 1) {
					sleep = 1;
				}
				// TODO remove println
				System.out.println(sleep);
				sleep(sleep);
				// FIXME when sleep hit 100, the puck disapears instead of just stopping. Don't
				// thing executor restarts.
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
