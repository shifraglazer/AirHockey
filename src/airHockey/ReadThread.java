package airHockey;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ReadThread extends Thread {
	private Socket socket;
	private World world;
	private Scanner scanner;

	public ReadThread(Socket socket, World world) {
		this.world = world;
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line;

			while ((line = reader.readLine()) != null) {

				scanner = new Scanner(line);
				Double x = Double.valueOf(scanner.next());
				Double y = Double.valueOf(scanner.nextLine());
				double Whalf = Table.WIDTH / 2;
				double Lhalf = Table.HEIGHT / 2;
				// System.out.println("x recieved: " + x + " y re: " + y);
				// reflection over x and y axis
				double diffx = Math.abs(Whalf - x);
				double diffy = Math.abs(Lhalf - y);
				if (x > Whalf && y > Lhalf) {
					x = Whalf - diffx;
					y = Lhalf - diffy;
				}
				else if (x < Whalf && y < Lhalf) {
					x = Whalf + diffx;
					y = Lhalf + diffy;
				}
				else if (y > Lhalf && x < Whalf) {
					x = Whalf + diffx;
					y = Lhalf - diffy;

				}
				else if (y < Lhalf && x > Whalf) {
					x = Whalf - diffx;
					//y = Lhalf + diffy;
				}
				// TODO remove println
				System.out.println("change to: x recieved: " + x + " y re: " + y);
				Point location = new Point(x.intValue(), y.intValue());
				world.moveMallet2(location);

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
