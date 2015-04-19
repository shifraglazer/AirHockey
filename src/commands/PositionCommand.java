package commands;

import airHockey.Positionable;
import airHockey.Table;
import airHockey.World;

public class PositionCommand implements Command {
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;
	private Positionable positionable;

	public PositionCommand(double x, double y, Positionable positionable) {
		this.x = x;
		this.y = y;
		this.positionable = positionable;
	}

	public void updateCommand(double x, double y) {
		double Whalf = World.GAMEWIDTH / 2;
		double Lhalf = World.GAMEHEIGHT / 2;

		// reflection over x and y axis
		double diffx = Math.abs(Whalf - x);
		double diffy = Math.abs(Lhalf - y);
		if (x >= Whalf && y >= Lhalf) {
			x = Whalf - diffx;
			y = Lhalf - diffy;
		}
		else if (x <= Whalf && y <= Lhalf) {
			x = Whalf + diffx;
			y = Lhalf + diffy;
		}
		else if (y >= Lhalf && x <= Whalf) {
			x = Whalf + diffx;
			y = Lhalf - diffy;

		}
		else if (y <= Lhalf && x >= Whalf) {
			x = Whalf - diffx;
			y = Lhalf + diffy;
		}
		this.x = x;
		this.y = y;
	}

	@Override
	public void perform(Table table) {
		table.updateCoordinates(x, y, positionable);
	}
}
