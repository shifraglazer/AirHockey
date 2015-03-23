



import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;




public class ReadThread extends Thread{

	private Socket socket;
	private World world;
	private Scanner scanner;
	public ReadThread(Socket socket,World world){
		this.world=world;
		this.socket=socket;
	
	}
	public void run(){
	
		try {
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line;

			while ((line = reader.readLine()) != null) {
				scanner=new Scanner(line);
				Double x=Double.valueOf(scanner.next());
				Double y=Double.valueOf(scanner.next());
				x=Table.WIDTH-x;
				y=Table.HEIGHT-y;
				Point location=new Point(x.intValue(),y.intValue());
				world.moveMallet2(location);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

	}
}
