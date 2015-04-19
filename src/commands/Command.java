package commands;

import java.io.Serializable;

import airHockey.Table;

public interface Command extends Serializable{

	public void perform(Table table);
	
	
}
