package airHockey;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MalletMotionListener extends MouseMotionAdapter {
	private World world;

	public MalletMotionListener(World world) {
		this.world = world;
	}

	// mallet moves with mouse
	@Override
	public void mouseMoved(MouseEvent e) {
		// move your mallet to wherever the mouse pointer is located
		Point point = world.getLocation();
		try {
			world.moveMallet(point.getX(), point.getY());

			// send location of your mallet to second players
			world.sendCommand(world.table.getCommand('m'));
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e1) {
			e1.printStackTrace();
		}

		world.repaint();
	}
}
