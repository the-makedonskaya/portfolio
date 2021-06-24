package ru.makedonskaya.snakegame;

import java.awt.Rectangle;

import sprites.SpriteApple;

public class Apple {
	
	private SnakeGamePanel game;
	
	private int x; 
	
	private int y; 
	
	public SpriteApple spriteApple = new SpriteApple();
	
	public Apple(SnakeGamePanel game) {
		this.game = game;
	}
	
	public Apple() {
		getNewApple();
	}
	
	public Rectangle getAppleLocation() {
		return new Rectangle(x, y, spriteApple.getDiameter(), spriteApple.getDiameter());
	}
	
	public void getNewApple() {
		this.x = (int) Math.round(Math.random() * game.getWidth());
		this.y = (int) Math.round(Math.random() * game.getHeight());
		spriteApple.setX(x);
		spriteApple.setY(y);
	}
	
}
