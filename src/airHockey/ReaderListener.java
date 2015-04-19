package airHockey;

import java.net.Socket;

public interface ReaderListener {

	void onObjectRead(Command command);

	void onCloseSocket(Socket socket);

}
