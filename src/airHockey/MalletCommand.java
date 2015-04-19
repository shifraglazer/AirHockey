package airHockey;

import java.awt.Point;

public class MalletCommand implements Command {
	private static final long serialVersionUID = 1L;
	private Double x;
	private Double y;

	public MalletCommand(double malletX, double malletY) {
		x = malletX;
		y = malletY;
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
		Point location = new Point(x.intValue(), y.intValue());
		table.moveMallet2(location);
	}

}
