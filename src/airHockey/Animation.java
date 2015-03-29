package airHockey;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Animation extends JFrame{

	private IceSkate ice;
	private JLabel label;
	public Animation() throws IOException{
		setSize(300,300);
		ice=new IceSkate();
		label=new JLabel(new ImageIcon(ice.getImage()));
		add(label);
		pack();
	}
	public static void main(String args[]){
		Animation animation;
		try {
			animation = new Animation();
			animation.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
