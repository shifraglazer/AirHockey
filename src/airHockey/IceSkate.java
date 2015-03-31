package airHockey;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IceSkate {
	private int x;
	private int y;
	private final Image image;

	public IceSkate() throws IOException {
		image = ImageIO.read(getClass().getResource("pics/Hockey_Boy.gif"));
		x = 30;
		y = 40;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

}