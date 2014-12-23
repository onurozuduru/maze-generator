/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

/**
 * @author onur
 *
 */
public class Maze extends JFrame {
	protected final static int CELL_WIDTH = 10, CELL_HEIGHT = 10;
	protected static int WIDTH, HEIGHT;
	protected static int BOUNDS_X, BOUNDS_Y;
	
	private Stack<Cell> stack;
	private ArrayList<Cell> unvisitedCells;
	private Cell[][] cells;
	private Wall[][] walls;

	/**
	 * @throws HeadlessException
	 */
	public Maze() throws HeadlessException {
		this("MAZE");
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public Maze(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public void generate() {
		//TODO Dfs generator function.
	}
	public Wall theWallBetween(Cell c1, Cell c2) {
		int x = c1.getX() - c2.getX();
		int y = c1.getY() - c2.getY();
		if(x != 0 && y != 0)
			return null;
		return this.walls[c2.getX() + (x / 2)][c2.getY() + ( y / 2)];
	}
}
