package snakegame;
import javax.swing.JFrame;

public class Main{

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Snake");
		SnakeGamePanel game = new SnakeGamePanel();
		frame.add(game);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.start();
		
		while (true) {
			game.move();
			game.repaint();
			Thread.sleep(10);
		}

	}

}
