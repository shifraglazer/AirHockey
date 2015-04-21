package commands;

import airHockey.World;

public class PuckCommand implements Command {
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;
	private int speed;

	public PuckCommand(double x, double y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void updateCommand(double x, double y, int speed) {
		this.speed = speed;
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
		world.updatePuckCoordinates(x, y, speed);
	}
}
