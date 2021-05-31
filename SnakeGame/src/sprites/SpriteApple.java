package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpriteApple {
	
	private static final int DIAMETER = 20;
	
	private int x;
	
	private int y;
	
	public void paint(Graphics2D apple) {
		apple.setColor(Color.RED);
		apple.fillOval(this.x, this.y, DIAMETER, DIAMETER);
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

	public static int getDiameter() {
		return DIAMETER;
	}
	
	
}
