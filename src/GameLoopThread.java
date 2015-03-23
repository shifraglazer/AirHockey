
import java.io.IOException;

public class GameLoopThread extends Thread {
	private World world;

	public GameLoopThread(World world) throws IOException {
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
				System.out.println("game loop "+ speed);
				sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
