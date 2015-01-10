/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

/**
 * @author onur
 *
 */
public class Cell extends JPanel {
	protected int x, y;
	private Status status;
	private ArrayList<Position> neighboursPositions;
	/**
	 * 
	 */
	public Cell(int x, int y, Status s) {
		this.setX(x);
		this.setY(y);
		this.setStatus(s);
		this.setNeighboursPositions();
		this.setPreferredSize(new Dimension(Maze.CELL_WIDTH, Maze.CELL_HEIGHT));		
	}
	
	public Cell(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setStatus(Status.UNKNOWN);
		this.setPreferredSize(new Dimension(Maze.CELL_WIDTH, Maze.CELL_HEIGHT));
	}
	
	private void setNeighboursPositions() {
		this.neighboursPositions = new ArrayList<Position>(Arrays.asList(Position.values()));
		ArrayList<Position> removeList = new ArrayList<Position>();
		for(Position p : neighboursPositions) {
			if((p == Position.LEFT) && (this.x == 0 || this.x == 1))
				removeList.add(p);
			else if((p == Position.DOWN) && (this.y == Maze.BOUNDS_Y - 1 || this.y == Maze.BOUNDS_Y - 2))
				removeList.add(p);
			else if((p == Position.RIGHT) && (this.x == Maze.BOUNDS_X - 1 || this.x == Maze.BOUNDS_X - 2))
				removeList.add(p);
			else if((p == Position.UP) && (this.y == 0 || this.y == 1))
				removeList.add(p);
		}
		this.neighboursPositions.removeAll(removeList);
	}

	private void setX(int x) {
		this.x = x;
	}

	private void setY(int y) {
		this.y = y;
	}

	public void setStatus(Status status) {
		this.status = status;
		this.setBackground(status.getColor());
	}

	public Status getStatus() {
		return status;
	}
	
	public  ArrayList<Position> getNeighboursPositions() {
		return neighboursPositions;
	}
	
	public int get_X() {
		return x;
	}

	public int get_Y() {
		return y;
	}
	
	public boolean isVisited() {
		return this.getStatus() == Status.VISITED;
	}
}
