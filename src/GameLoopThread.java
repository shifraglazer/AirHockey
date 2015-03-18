
public class GameLoopThread extends Thread {
	private Table table;

	public GameLoopThread(Table table) {
		this.table = table;
	}

	public void run() {
		while (true) {	
			try {
				table.repaint();
				sleep(table.getSleep());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
