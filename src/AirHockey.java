import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AirHockey {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		new GameLoopThread().start();
	}
}