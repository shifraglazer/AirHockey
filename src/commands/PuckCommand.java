package commands;

import airHockey.Table;

public class PuckCommand extends PositionCommand {
	private static final long serialVersionUID = 1L;

	public PuckCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public void perform(Table table) {
		table.updatePuckCoordinates(x, y);
	}
}
