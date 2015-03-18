public class Puck {
	protected int speed;
	private double slope;

	// the (x,y) coordinates of the center of the puck
	private int puckX;
	private int puckY;

	public Puck() {
		slope = 0;
		speed = 0;
	}

	public void setSlope(int malletX, int malletY) {
		slope = (malletY - puckY) / (malletX - puckX);
	}

	public void hit() {
		// FIXME don't know what hit speed should be
		// speed is set to 50 each time the puck is hit
		// the speed slowly decrements as time passes since the puck was last hit
		speed = 50;
	}
}
