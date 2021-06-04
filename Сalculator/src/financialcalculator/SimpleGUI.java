package financialcalculator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleGUI  {
		
	private JTextField input = new JTextField("", 40);
	
	private List<JButton> buttonsList = new ArrayList<>();
	

	public SimpleGUI() {
		buttonsList.add(new JButton("1"));
		buttonsList.add(new JButton("2"));
		buttonsList.add(new JButton("3"));
		buttonsList.add(new JButton("+"));
		buttonsList.add(new JButton("-"));
		buttonsList.add(new JButton("4"));
		buttonsList.add(new JButton("5"));
		buttonsList.add(new JButton("6"));
		buttonsList.add(new JButton("*"));
		buttonsList.add(new JButton("/"));
		buttonsList.add(new JButton("7"));
		buttonsList.add(new JButton("8"));
		buttonsList.add(new JButton("9"));
		buttonsList.add(new JButton("("));
		buttonsList.add(new JButton(")"));
		buttonsList.add(new JButton("0"));
		buttonsList.add(new JButton("."));
		buttonsList.add(new JButton("="));
		buttonsList.add(new JButton("C"));
		buttonsList.add(new JButton("CE"));

		createWindow();
	}

	private void createWindow() {
		EventQueue.invokeLater(() -> 
		{
			JFrame frame = new JFrame("Calculator");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        JPanel panelButtons = new JPanel(new GridLayout(4,3));
	        for(JButton button: buttonsList) {
				button.addActionListener(new ButtonEventListener());
				panelButtons.add(button);
			}
			
	        frame.getContentPane().add(new JPanel().add(input), BorderLayout.PAGE_START);
	        frame.getContentPane().add(panelButtons, BorderLayout.CENTER);
	        frame.setBounds(100, 100, 400, 200);
	        frame.setResizable(true);
	        frame.setVisible(true);	
		});
	}
			
	class ButtonEventListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command == "C") {
				input.setText("");
			} 
			else if (command == "CE") {
				String string = input.getText();
				String newStr = string.substring(0,string.length() - 1);
				input.setText(newStr);
			} else if (command.charAt(0) == '=') {
				String str = input.getText();
				str = str.replaceAll("\\s", "");
				Expression exp = new Expression(str);
				if (exp.getMessage() == null) {
					String result = exp.getResult().toString();
					input.setText(result);	
				} else {
					JOptionPane.showMessageDialog(new JPanel(), exp.getMessage(),"Error", JOptionPane.YES_NO_OPTION);
				}
			} else {
				input.setText(input.getText() + command);
			}
		}
	}

}
