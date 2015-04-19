package airHockey;

import java.io.Serializable;

public interface Command extends Serializable{

	public void perform(Table table);
	
}
