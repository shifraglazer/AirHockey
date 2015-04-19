package commands;

import airHockey.Table;

public class MalletCommand extends PositionCommand {
	private static final long serialVersionUID = 1L;

	public MalletCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public void perform(Table table) {
		table.moveMallet2(x, y);
	}
}