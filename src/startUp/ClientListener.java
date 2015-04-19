package startUp;

import java.awt.event.ActionEvent;

import airHockey.AirHockey;

public class ClientListener extends SetUpListener {

	public ClientListener(AirHockey hockey) {
		super(hockey);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO get ip address from user
		new ClientSetup();
		hockey.dispose();
	}

}
