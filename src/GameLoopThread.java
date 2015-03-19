public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread() {
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

				sleep(100 - speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
