import java.io.IOException;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread(World world) throws IOException {

	this.world=world;

	}

	@Override
	public void run() {
		while (true) {
			int speed = world.getPuckSpeed();
			if (speed > 0) {
				world.movePuck();
				world.repaint();
				System.out.println("repainted");
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
