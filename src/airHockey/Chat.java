package airHockey;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text;
	private JTextArea area;
	private JScrollPane pane;
	public static final int CHATWIDTH = 300;
	public static final int CHATHEIGHT = 500;

	public Chat() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		area = new JTextArea();
		area.setPreferredSize(new Dimension(CHATWIDTH, CHATHEIGHT - 25));
		add(area);
		text = new JTextField();
		pane = new JScrollPane();
		area.add(pane);
		area.setEditable(false);
		text.setPreferredSize(new Dimension(CHATWIDTH, 25));
	
		add(text);
	}

	public String readText() throws IOException {
		String line = text.getText();
		area.append("Me: "+line + "\n");

		text.setText("");
		return line;
	}

	public void updateChat(String msg) {
		area.append("Opponent: "+msg + "\n");
		System.out.println(msg);
	}

}
