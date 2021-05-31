package snakegame;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import sprites.SpriteSnake;


public class Snake {
	
	public SpriteSnake spriteSnake = new SpriteSnake();

	private int x;
	
	private int y;
		
	private int speed = 1;
	
	private int direction = 0;
	
	private SnakeGamePanel game;
	
	public Snake(SnakeGamePanel game) {
		this.game = game;
	}
		
	public void start(int x, int y) {
		spriteSnake.setX(x);
		this.x = spriteSnake.getX();
		spriteSnake.setY(y);
		this.y = spriteSnake.getY();
		
		spriteSnake.body.add(new SpriteSnake(this.x, this.y));
	}
	
	public Rectangle getSnakeLocation() {
		return new Rectangle(this.x, this.y, spriteSnake.getSizeSnake(), spriteSnake.getHEIGHTSNAKE());
	}
	
	public void move() {
			if (x + speed > game.getWidth()) {
				x = 0;
				spriteSnake.body.get(0).setX(x);
				changeSnakeBodyDirection();
			}
			if (x + speed == 0) {
				x = game.getWidth();
				spriteSnake.body.get(0).setX(x);
				changeSnakeBodyDirection();
			}
			if (direction == 1) {
				x = x + speed;
				spriteSnake.body.get(0).setX(x);
				changeSnakeBodyDirection();
			}
			if (direction == -1) {
				x = x - speed;
				spriteSnake.body.get(0).setX(x);
				changeSnakeBodyDirection();	
			}

			if (y + speed > game.getHeight()) {
				y = 0;
				spriteSnake.body.get(0).setY(y);
				changeSnakeBodyDirection();	
			}
			if (y + speed == 0) {
				y = game.getHeight();
				spriteSnake.body.get(0).setY(y);
				changeSnakeBodyDirection();	
			}
			if (direction == 2) {
				y = y + speed;
				spriteSnake.body.get(0).setY(y);
				changeSnakeBodyDirection();		
			}
			if (direction == -2) {
				y = y - speed;
				spriteSnake.body.get(0).setY(y);
				changeSnakeBodyDirection();		
			}	
	}
	
	public boolean isHeadToBodyCollision() {
		SpriteSnake head = spriteSnake.body.get(0);
		int x = head.getX();
		int y = head.getY();
		int len = spriteSnake.body.size();
		for (int i = 1; i < len; i++) {
			if (x == spriteSnake.body.get(i).getX() && y == spriteSnake.body.get(i).getY()) {
				return true;
			}
		}
		return false;
	}
	
	public void changeSnakeBodyDirection() {
		if (isHeadToBodyCollision()) {
			game.gameOver();
			game.score = 0;
			SpriteSnake head = spriteSnake.body.get(0);
			spriteSnake.body.clear();
			spriteSnake.body.add(head);
	
		} else {
			for (int i = spriteSnake.body.size() - 1; i > 0; i--) {
				spriteSnake.body.get(i).setX(spriteSnake.body.get(i - 1).getX());
				spriteSnake.body.get(i).setY(spriteSnake.body.get(i - 1).getY());
			}
		}	
	}
	
	public void changeOfDirection(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if	(direction != 1) {
				direction = -1;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (direction != -1) {
				direction = 1;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (direction != 2) {
				direction = -2;
			}
		}
			
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (direction != -2) {
				direction = 2;
			}
		}
	}
	
	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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

	public int getDirection() {
		return direction;
	}
	
}
