package airHockey;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class Chat extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea area;
	private JTextField text;

	public Chat() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(World.GAMEWIDTH, World.FRAMEHEIGHT));
		
		area = new JTextArea("Welcome to chat!\n");
		area.setEditable(false);
		area.setLineWrap(true);
		JScrollPane pane = new JScrollPane(area);
		add(pane, BorderLayout.CENTER);

		text = new JTextField();
		text.setBorder(new EtchedBorder());
		add(text, BorderLayout.SOUTH);
	}

	public String readText() throws IOException {
		String line = text.getText();
		area.append("Me: " + line + "\n");

		text.setText("");
		return line;
	}

	public void updateChat(String msg) {
		area.append("Opponent: " + msg + "\n");
	}

}
