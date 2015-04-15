package airHockey;

import java.net.Socket;

public interface ReaderListener {

	// void onObjectRead(Command command);
	void onLineRead(String line);

	void onCloseSocket(Socket socket);

}
