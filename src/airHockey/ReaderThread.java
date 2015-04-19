package airHockey;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReaderThread extends Thread {
	private Socket socket;
	private ReaderListener listener;

	public ReaderThread(Socket socket, ReaderListener listener) {
		this.socket = socket;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
		
			while (true) {
				Command command=(Command) objIn.readObject();
				listener.onObjectRead(command);
			}
			//in.close();
			//onjIn.close();
		}
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		//listener.onCloseSocket(socket);
	}
}
