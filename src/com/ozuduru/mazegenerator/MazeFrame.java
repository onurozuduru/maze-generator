/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author onur
 *
 */
public class MazeFrame extends JFrame {
	private Maze maze;
	private JPanel mazePanel;

	/**
	 * @throws HeadlessException
	 */
	public MazeFrame(Maze maze) throws HeadlessException {
		this("MAZE", maze);
	}
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public MazeFrame(String title, Maze maze) throws HeadlessException {
		super(title);
		this.maze = maze;
		this.maze.generate();
		this.setMazePanel();
		this.getContentPane().add(this.mazePanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();
	}

	private void setMazePanel() {
		this.mazePanel = new JPanel(new GridLayout(Maze.BOUNDS_Y, Maze.BOUNDS_X));
		ArrayList<Cell> list = this.maze.getContainer();
		for(Cell c : list)
			this.mazePanel.add(c);
	}

}
