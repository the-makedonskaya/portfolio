package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ru.makedonskaya.snakegame.Snake;

public class SpriteSnake {
	
	private static int HEIGHTSNAKE = 20;
	
	private int sizeSnake = 20;
	
	private int x;
	
	private int y;
	
	public List<SpriteSnake> body = new ArrayList<SpriteSnake>();
	
	Snake snake;
	
	public SpriteSnake() {	
	}
	
	public SpriteSnake(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void paint(Graphics2D g) {
		g.setColor(Color.green);
		for (SpriteSnake tail : body) {
			g.fillRect(tail.getX(), tail.getY(), sizeSnake, HEIGHTSNAKE);	
		}	
	}
	
	public int getSizeSnake() {
		return sizeSnake;
	}

	public void setSizeSnake(int sizeSnake) {
		this.sizeSnake = sizeSnake;
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

	
	public static int getHEIGHTSNAKE() {
		return HEIGHTSNAKE;
	}


	public static void setHEIGHTSNAKE(int hEIGHTSNAKE) {
		HEIGHTSNAKE = hEIGHTSNAKE;
	}
	
}
