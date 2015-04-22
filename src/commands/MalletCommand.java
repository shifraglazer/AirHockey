package commands;

import airHockey.World;

public class MalletCommand extends PositionCommand {
	private static final long serialVersionUID = 1L;

	public MalletCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public void perform(World world) {
		world.updateMalletCoordinates(x, y);
	}
}
