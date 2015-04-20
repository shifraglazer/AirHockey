package airHockey;

import java.io.Serializable;

import commands.PositionCommand;

public abstract class Positionable implements Serializable {
	private static final long serialVersionUID = 1L;
	protected PositionCommand command;
	protected double posX;
	protected double posY;
	
	public abstract void updateCoordinates(double x, double y, Table table);

	public PositionCommand getCommand() {
		command.updateCommand(posX, posY);
		return command;
	}
}
