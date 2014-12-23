/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * @author onur
 *
 */
public class Cell extends JPanel {
	protected int x, y;
	private Status status;
	private Position[] neighboursPositions;
	/**
	 * 
	 */
	public Cell(int x, int y, Status s) {
		this.setX(x);
		this.setY(y);
		this.setStatus(s);
		this.setNeighboursPositions();
		
		Dimension d = getPreferredSize();
		d.setSize(Maze.CELL_WIDTH, Maze.CELL_HEIGHT);
		this.setPreferredSize(d);
		this.setLayout(null);
		this.setBackground(status.getColor());
	}
	public Cell(int x, int y) {
		this.setX(x);
		this.setY(y);
		
		Dimension d = getPreferredSize();
		d.setSize(Maze.CELL_WIDTH, Maze.CELL_HEIGHT);
		this.setPreferredSize(d);
		this.setLayout(null);
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Position[] getNeighboursPositions() {
		return neighboursPositions;
	}
	private void setNeighboursPositions() {
		if(this.x == 0 || this.y == 0 || this.x == Maze.BOUNDS_X - 1 || this.y == Maze.BOUNDS_Y - 1) {
			if((this.x == 0 && this.y == 0) ||
					(this.x == Maze.BOUNDS_X - 1 && this.y == Maze.BOUNDS_Y - 1) ||
					(this.x == 0 && this.y == Maze.BOUNDS_Y - 1) ||
					(this.x == Maze.BOUNDS_X -1 && this.y == 0)
					)
				this.neighboursPositions = new Position[2];
			else
				this.neighboursPositions = new Position[3];
			
			if(this.x == 0)
				this.neighboursPositions[0] = Position.RIGHT;
			else
				this.neighboursPositions[0] = Position.LEFT;
			if(this.y == 0)
				this.neighboursPositions[1] = Position.DOWN;
			else
				this.neighboursPositions[1] = Position.UP;
			if(0 < this.x && this.x < Maze.BOUNDS_X - 1)
				this.neighboursPositions[2] = Position.RIGHT;
			if(0 < this.y && this.y < Maze.BOUNDS_Y - 1)
				this.neighboursPositions[2] = Position.DOWN;
		}
		else
			this.neighboursPositions = Position.values();
	}
	private void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}

	private void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}

}
