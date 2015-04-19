package startUp;

import java.awt.event.ActionListener;

import airHockey.AirHockey;

public abstract class SetUpListener implements ActionListener {
	protected AirHockey hockey;

	public SetUpListener(AirHockey hockey) {
		this.hockey = hockey;
	}
}
