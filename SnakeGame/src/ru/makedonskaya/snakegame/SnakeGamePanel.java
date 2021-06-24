package ru.makedonskaya.snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sprites.SpriteSnake;

public class SnakeGamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public int score = 0;

	private Snake snake = new Snake(this);
	
	private Apple apple = new Apple(this);
	
	public SnakeGamePanel() {
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				snake.changeOfDirection(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		setFocusable(true);
		
	}
	
	public void start() {
		snake.start((int) Math.round(Math.random() * getWidth()), (int) Math.round(Math.random() * getHeight()));
		apple.getNewApple();
	}
	
	private boolean isSnakeToAppleCollision() {
		return apple.getAppleLocation().intersects(snake.getSnakeLocation());
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "your score is: " + score,
				"Game Over", JOptionPane.YES_NO_OPTION);
	}
	
	public void move() {
		if (isSnakeToAppleCollision()){
			score++;
			for (int i = 0; i < 20; i++) {
				snake.spriteSnake.body.add(new SpriteSnake(snake.getX(),snake.getY()));
			}
			apple.getNewApple();
		}
		snake.move();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		snake.spriteSnake.paint(g2d);
		apple.spriteApple.paint(g2d);
		
		g2d.setColor(Color.BLUE);
		g2d.setFont(new Font("Verdana", Font.BOLD, 30));
		g2d.drawString(String.valueOf(score), 350, 30);
	}
	
}
