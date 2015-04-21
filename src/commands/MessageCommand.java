package commands;


import airHockey.World;

public class MessageCommand implements Command {
	private static final long serialVersionUID = 1L;
	private String msg;

	public MessageCommand(String msg) {
		this.msg = msg;
	}

	@Override
	public void perform(World world) {
		world.updateChat(msg);
		
	}
}