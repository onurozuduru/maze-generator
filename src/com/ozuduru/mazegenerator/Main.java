/*********************************************************************************
*File: Main.java
*Author: Onur Ozuduru
*   e-mail: onur.ozuduru { at } gmail.com
*   github: github.com/onurozuduru
*   twitter: twitter.com/OnurOzuduru
*
*License: The MIT License (MIT)
*
*   Copyright (c) 2016 Onur Ozuduru
*   Permission is hereby granted, free of charge, to any person obtaining a copy
*   of this software and associated documentation files (the "Software"), to deal
*   in the Software without restriction, including without limitation the rights
*   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*   copies of the Software, and to permit persons to whom the Software is
*   furnished to do so, subject to the following conditions:
*  
*   The above copyright notice and this permission notice shall be included in all
*   copies or substantial portions of the Software.
*  
*   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
*   SOFTWARE.
*********************************************************************************/

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
			new MazeFrame("MAZE GENERATOR", new Maze(inputs[0], inputs[1]));
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
