package airHockey;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




public class ServerSetup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoadingFrame frame;

	public ServerSetup() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException{
		frame=new LoadingFrame();
		frame.setVisible(true);
		new ServerThread(frame).start();
	}
}
