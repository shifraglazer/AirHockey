import java.io.IOException;

import javax.swing.LayoutStyle;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AirHockey {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			/*LookAndFeel lat = UIManager.getLookAndFeel();
			UIDefaults defaults = lat.getDefaults();
			defaults.replace(key, value);
			for(Object key : UIManager.getLookAndFeel().getDefaults().keySet()) {
			    System.out.println(key + " = " + UIManager.get(key));
			}*/
			
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		try {
			new GameLoopThread().start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}