package airHockey;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		while (true) {
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				world.movePuck();
			}
			try {
				sleep(100 - (speed * 2));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
