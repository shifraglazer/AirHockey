package airHockey;

import commands.PositionCommand;

public abstract class Positionable {
	protected PositionCommand command;
	protected double posX;
	protected double posY;

	public abstract void updateCoordinates(double x, double y, Table table);

	public PositionCommand getCommand() {
		command.updateCommand(posX, posY);
		return command;
	}
}
