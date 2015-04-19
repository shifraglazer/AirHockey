package airHockey;

import java.net.Socket;

import commands.Command;

public interface ReaderListener {

	void onObjectRead(Command command);

	void onCloseSocket(Socket socket);

}
