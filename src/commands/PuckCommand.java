package commands;

import airHockey.World;

public class PuckCommand extends PositionCommand {
	private static final long serialVersionUID = 1L;
	private int speed;

	public PuckCommand(double x, double y, int speed) {
		super(x, y);
		this.speed = speed;
	}

	@Override
	public void perform(World world) {
		world.updatePuckCoordinates(x, y, speed);
	}
}
