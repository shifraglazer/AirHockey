package commands;

import airHockey.Table;

public class MessageCommand implements Command {
	private static final long serialVersionUID = 1L;
	private String msg;

	public MessageCommand(String msg) {
		this.msg = msg;
	}

	@Override
	public void perform(Table table) {
		// TODO message goes to chat area
		System.out.println(msg);
	}
}