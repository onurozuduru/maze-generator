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
		else
			JOptionPane.showMessageDialog(null, "Invalid Value!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	public static int[] showInputDiolog() {
		JTextField w = new JTextField();
		JTextField h = new JTextField();
		JPanel mes = new JPanel(new GridLayout(1, 3));
		mes.add(w);
		mes.add(new JLabel("X", JLabel.CENTER));
		mes.add(h);
		if(JOptionPane.showConfirmDialog(null, mes, "WIDTH HEIGHT", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION)
			return new int[] {0, 0};
		try {
			return new int[] {Integer.parseInt(w.getText()), Integer.parseInt(h.getText())};
		} catch (Exception e) {
			return new int[] {0, 0};
		}
		
	}
}
