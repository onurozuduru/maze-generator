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
	//private Position[] neighboursPositions;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
		this.setBackground(status.getColor());
	}
	public  ArrayList<Position> getNeighboursPositions() {
		return neighboursPositions;
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
//		if(this.x == 0 || this.y == 0 || this.x == Maze.BOUNDS_X - 1 || this.y == Maze.BOUNDS_Y - 1) {
//			if((this.x == 0 && this.y == 0) ||
//					(this.x == Maze.BOUNDS_X - 1 && this.y == Maze.BOUNDS_Y - 1) ||
//					(this.x == 0 && this.y == Maze.BOUNDS_Y - 1) ||
//					(this.x == Maze.BOUNDS_X -1 && this.y == 0)
//					)
//				this.neighboursPositions = new Position[2];
//			else
//				this.neighboursPositions = new Position[3];
//			
//			if(this.x == 0)
//				this.neighboursPositions[0] = Position.RIGHT;
//			else
//				this.neighboursPositions[0] = Position.LEFT;
//			if(this.y == 0)
//				this.neighboursPositions[1] = Position.DOWN;
//			else
//				this.neighboursPositions[1] = Position.UP;
//			if(0 < this.x && this.x < Maze.BOUNDS_X - 1)
//				this.neighboursPositions[2] = Position.RIGHT;
//			if(0 < this.y && this.y < Maze.BOUNDS_Y - 1)
//				this.neighboursPositions[2] = Position.DOWN;
//		}
//		else {
//			this.neighboursPositions = new Position[4];
//			this.neighboursPositions[0] = Position.DOWN;
//			this.neighboursPositions[1] = Position.UP;
//			this.neighboursPositions[2] = Position.LEFT;
//			this.neighboursPositions[3] = Position.RIGHT;
//		}
//		if(this.neighboursPositions == null)
//			System.out.println("NULL"+this.x+" "+this.y);
	}
	private void setX(int x) {
		this.x = x;
	}
	public int get_X() {
		return x;
	}

	private void setY(int y) {
		this.y = y;
	}
	public int get_Y() {
		return y;
	}
	public boolean isVisited() {
		return this.getStatus() == Status.VISITED;
	}
}
