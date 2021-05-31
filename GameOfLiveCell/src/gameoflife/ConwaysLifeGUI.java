package gameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class ConwaysLifeGUI {

	private LifeGameService service;

	private static final int DEFAULT_WIDTH = 600;

	private static final int DEFAULT_HEIGHT = 600;
	
	private volatile boolean goNextGeneration = false;
	
	public ConwaysLifeGUI() {
		this.service = new LifeGameService();
	}

	public void go() {
		EventQueue.invokeLater(() -> 
		{
			LifeFrame frame = new LifeFrame();
			frame.setTitle("моя игра");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			frame.setResizable(false);
			
			LifeComponent component = new LifeComponent();
			frame.add(component);
			
			JPanel buttonPanel = new JPanel();
			
			JButton buttonPlay = new JButton("Play");
			buttonPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					goNextGeneration = !goNextGeneration;
					buttonPlay.setText(goNextGeneration ? "Pause" : "Play");
				}
			});
			
			JButton buttonNextStep = new JButton("Next Step");
			buttonNextStep.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					component.repaint();
				}
			});
			
			JButton buttonNewGame = new JButton("New Game");
			buttonNewGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					service.clearField();
					component.repaint();
				}
			});
			
			frame.add(buttonPanel,BorderLayout.SOUTH);
			buttonPanel.add(buttonPlay,BorderLayout.CENTER);
			buttonPanel.add(buttonNextStep,BorderLayout.CENTER);
			buttonPanel.add(buttonNewGame,BorderLayout.CENTER);
			
			frame.setVisible(true);			
		});
	}
	
	class LifeFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		public LifeFrame() {
			new Timer(150, new ActionListener() {
				@Override
		        public void actionPerformed(ActionEvent e) {
					if (goNextGeneration) {
						repaint();
					}
		        }
			}).start(); 
			pack();
		}
	}

	class LifeComponent extends JPanel {
		
		public LifeComponent() {
		    setBackground(Color.BLACK);   
		}
		
		private static final long serialVersionUID = 1L;
	
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.green);

			List<List<Integer>> cells = service.getCells();

			for (int i = 0; i < cells.size(); i++) {
				for (int j = 0; j < cells.get(i).size(); j++) {
					if (cells.get(i).get(j) == 7) {
						g2.fillOval(i * 10, j * 10, 10, 10);
					}
				}
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		}

	}

}
