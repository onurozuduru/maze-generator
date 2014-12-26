/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author onur
 *
 */
public class Maze {
	protected final static int CELL_WIDTH = 10, CELL_HEIGHT = 10;
	protected static int WIDTH, HEIGHT;
	protected static int BOUNDS_X, BOUNDS_Y;
	
	private Stack<Cell> stack;
	private ArrayList<Cell> unvisitedCells;
	private ArrayList<Cell> container;
	public ArrayList<Cell> path;

	/**
	 * @throws HeadlessException
	 */
	public Maze(int bX, int bY) throws HeadlessException {
		this("MAZE", bX, bY);
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public Maze(String title, int bX, int bY) throws HeadlessException {
		setBOUNDS_X(bX);
		setBOUNDS_Y(bY);
		WIDTH = BOUNDS_X * CELL_WIDTH;
		HEIGHT = BOUNDS_Y * CELL_HEIGHT;
		this.stack = new Stack<Cell>();
		this.container = new ArrayList<Cell>();
		this.unvisitedCells = new ArrayList<Cell>();
		this.path = new ArrayList<Cell>();
		this.setCells();
	}
	
	private void setCells() {
		int limit = Maze.BOUNDS_X * Maze.BOUNDS_Y;
		for(int i = 0; i <  limit; ++i) {
			int x = i % Maze.BOUNDS_X;
			int y = i / Maze.BOUNDS_X;
			int oddOrEven = ((i / Maze.BOUNDS_X) % 2 == 0) ? 0 : 1;
			if(y % 2 == 1)
				this.container.add(new Wall(x, y));
			else if(i % 2 == oddOrEven) {
				Cell c = new Cell(x, y, Status.UNVISITED);
				this.container.add(c);
				this.unvisitedCells.add(c);
			}
			else
				this.container.add(new Wall(x, y));
		}
	}
	private int getIndexOfCell(Cell c) {
		return (BOUNDS_X * c.get_Y()) + c.get_X();
	}
	private int getIndexOfCell(int x, int y) {
		return (BOUNDS_X * y) + x;
	}
	public static void setBOUNDS_X(int bOUNDS_X) {
		BOUNDS_X = bOUNDS_X;
	}

	public static void setBOUNDS_Y(int bOUNDS_Y) {
		BOUNDS_Y = bOUNDS_Y;
	}

	public void generate() {
		Cell current = unvisitedCells.remove(0);
		current.setStatus(Status.VISITED);
		Cell finish = null;
		Cell start = current;
		while(!this.unvisitedCells.isEmpty()) {
			if(unvisitedCells.size() == 1)
				finish = unvisitedCells.get(0);
			Cell unvisited = this.chooseRandomUnvisitedNeighbour(current);
			if(unvisited != null) {
				this.stack.push(current);
				this.theWallBetween(current, unvisited).removeMe();
				current = unvisited;
				current.setStatus(Status.VISITED);
				this.unvisitedCells.remove(current);
			}
			else if(!this.stack.isEmpty()) {
				current = this.stack.pop();
			}
			else {
				System.out.println("yes");
				current = this.unvisitedCells.remove((int) (System.currentTimeMillis() % this.unvisitedCells.size()));
				current.setStatus(Status.VISITED);
			}
		}
		this.drawPath(start, finish);
	}
	public void drawPath(Cell start, Cell finish) {
		Cell c2 = null;
		Cell c1 = stack.pop();
		c1.setBackground(Color.YELLOW);
		this.theWallBetween(c1, finish).setBackground(Color.YELLOW);
		while(!stack.isEmpty()) {
			if(c2 != null)
				this.theWallBetween(c1, c2).setBackground(Color.YELLOW);
			if(stack.isEmpty())
				break;
			c2 = stack.pop();
			c1.setBackground(Color.YELLOW);
			c2.setBackground(Color.YELLOW);
			this.theWallBetween(c1, c2).setBackground(Color.YELLOW);
			if(!stack.isEmpty()) c1 = stack.pop();
		}
		start.setBackground(Color.GREEN);
		finish.setBackground(Color.RED);
	}
	public Cell chooseRandomUnvisitedNeighbour(Cell c) {
		ArrayList<Cell> n = this.findUnvisitedNeighbours(c);
		if(n.isEmpty())
			return null;
		return n.get((int) (System.currentTimeMillis() % n.size()));
	}
	public ArrayList<Cell> findUnvisitedNeighbours(Cell c) {
		ArrayList<Cell> ret = new ArrayList<Cell>();
		ArrayList<Position> pos = c.getNeighboursPositions();
		for(Position p : pos) {
			Cell neighbour = this.container.get(this.getIndexOfCell(c.get_X() + p.x, c.get_Y() + p.y));
			if(!neighbour.isVisited())
				ret.add(neighbour);
		}
		return ret;
	}
	public Wall theWallBetween(Cell c1, Cell c2) {		
		int x = c1.get_X() - c2.get_X();
		int y = c1.get_Y() - c2.get_Y();
		if(x != 0 && y != 0)
			return null;
		x = c2.get_X() + (x / 2);
		y = c2.get_Y() + (y / 2);
		return (Wall) this.container.get(getIndexOfCell(x, y));
//		return this.walls[c2.get_X() + (x / 2)][c2.get_Y() + ( y / 2)];
	}
	public ArrayList<Cell> getContainer() {
		return container;
	}
}
