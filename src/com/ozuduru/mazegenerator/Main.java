package com.ozuduru.mazegenerator;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		int[] inputs = showInputDiolog();
		
		if(inputs[0] > 0 && inputs[1] > 0)
			new MazeFrame(new Maze(inputs[0], inputs[1]));
		else if(inputs[0] == Integer.MIN_VALUE && inputs[1] == Integer.MIN_VALUE)
			return;
		else
			JOptionPane.showMessageDialog(null, "Invalid Value!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public static int[] showInputDiolog() {
		String title = "Maze Dimension";
		JTextField w = new JTextField();
		JTextField h = new JTextField();
		JPanel mes = new JPanel(new GridLayout(1, 4));
		
		mes.add(new JLabel("Width:", JLabel.CENTER));
		mes.add(w);
		mes.add(new JLabel("Height:", JLabel.CENTER));
		mes.add(h);
		
		int result = JOptionPane.showConfirmDialog(null, mes, title, JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
			return new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE};
		
		try {
			return new int[] {Integer.parseInt(w.getText()), Integer.parseInt(h.getText())};
		} catch (Exception e) {
			return new int[] {0, 0};
		}
		
	}
}
