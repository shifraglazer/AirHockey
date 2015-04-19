package airHockey;

public class PuckCommand implements Command {
	private double x;
	private double y;
	
	@Override
	public void perform(Table table) {
		table.updatePuckCoordinates(x, y);
	}

}
