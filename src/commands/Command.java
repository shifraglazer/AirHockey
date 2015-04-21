package commands;

import java.io.Serializable;

import airHockey.World;

public interface Command extends Serializable {

	public void perform(World world);

}
