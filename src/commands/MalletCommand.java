package commands;

import airHockey.World;

public class MalletCommand implements Command {
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;

	public MalletCommand(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void updateCommand(double x, double y) {
		double Whalf = World.GAMEWIDTH / 2;
		double Lhalf = World.FRAMEHEIGHT / 2;

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
	public void perform(World world) {
		world.updateMalletCoordinates(x, y);
	}
}
