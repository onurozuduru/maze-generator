/*********************************************************************************
*File: Maze.java
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Maze extends JPanel {
	protected final static int CELL_WIDTH = 10, CELL_HEIGHT = 10;
	protected final static int REFRESH_TIME = 60;
	protected static int WIDTH, HEIGHT; // Initilized by constructer.
	protected static int BOUNDS_X, BOUNDS_Y; // Initilized by constructer.
	
	private Stack<Cell> stack;
	private ArrayList<Cell> unvisitedCells;
	private ArrayList<Cell> container;
	public ArrayList<Cell> path;
	private Cell start, finish, current;
	protected boolean isGenerated;
	private Timer simulationTimer;

	public Maze(int bX, int bY) throws HeadlessException {
		setBOUNDS_X(bX);
		setBOUNDS_Y(bY);
		
		WIDTH = BOUNDS_X * CELL_WIDTH;
		HEIGHT = BOUNDS_Y * CELL_HEIGHT;
		
		this.stack = new Stack<Cell>();
		this.container = new ArrayList<Cell>();
		this.unvisitedCells = new ArrayList<Cell>();
		this.path = new ArrayList<Cell>();
		
		this.setCells();
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setPreferredSize(new Dimension(Maze.WIDTH, Maze.HEIGHT));
		
		for(Cell c : this.container)
			this.add(c);
		this.isGenerated = false;
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
	
	public static void setBOUNDS_X(int bOUNDS_X) {
		BOUNDS_X = bOUNDS_X;
	}

	public static void setBOUNDS_Y(int bOUNDS_Y) {
		BOUNDS_Y = bOUNDS_Y;
	}

	public void generate() {
		current = unvisitedCells.remove(getRandomInt(unvisitedCells.size()));
		current.setStatus(Status.VISITED);
		finish = null;
		start = current;
		
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
				current = this.unvisitedCells.remove(getRandomInt(unvisitedCells.size()));
				current.setStatus(Status.VISITED);
			}
		}
		isGenerated = true;
		start.setBackground(Color.GREEN);
		finish.setBackground(Color.RED);
	}
	
	public Timer generateAndSimmulate() {
		current = unvisitedCells.remove(getRandomInt(unvisitedCells.size()));
		current.setStatus(Status.VISITED);
		finish = null;
		start = current;
		
		simulationTimer = new Timer(Maze.REFRESH_TIME, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if(!unvisitedCells.isEmpty()) {
					if(unvisitedCells.size() == 1)
						finish = unvisitedCells.get(0);
					Cell unvisited = chooseRandomUnvisitedNeighbour(current);
					
					if(unvisited != null) {
						stack.push(current);
						theWallBetween(current, unvisited).removeMe();
						current = unvisited;
						current.setStatus(Status.VISITED);
						unvisitedCells.remove(current);
					}
					else if(!stack.isEmpty()) {
						current = stack.pop();
					}
					else {
						current = unvisitedCells.remove(getRandomInt(unvisitedCells.size()));
						current.setStatus(Status.VISITED);
					}
				}
				else {
					isGenerated = true;
					simulationTimer.stop();
					start.setBackground(Color.GREEN);
					finish.setBackground(Color.RED);
				}
			}//END OF Method actionPerformed
		});
		return simulationTimer;
	}
	
	public void drawPath() {
		if(!isGenerated)
			return;
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
			
			if(!stack.isEmpty())
				c1 = stack.pop();
		}
		start.setBackground(Color.GREEN);
		finish.setBackground(Color.RED);
	}
	
	public Cell chooseRandomUnvisitedNeighbour(Cell c) {
		ArrayList<Cell> n = this.findUnvisitedNeighbours(c);
		
		if(n.isEmpty())
			return null;
		return n.get(getRandomInt(n.size()));
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
	
	public int getRandomInt(int limit) {
		return (int) (System.currentTimeMillis() % limit);
	}
	
	public Wall theWallBetween(Cell c1, Cell c2) {		
		int x = c1.get_X() - c2.get_X();
		int y = c1.get_Y() - c2.get_Y();
		
		if(x != 0 && y != 0)
			return null;
		x = c2.get_X() + (x / 2);
		y = c2.get_Y() + (y / 2);
		
		return (Wall) this.container.get(getIndexOfCell(x, y));
	}
	
	private int getIndexOfCell(int x, int y) {
		return (BOUNDS_X * y) + x;
	}

	public ArrayList<Cell> getContainer() {		
		return container;
	}
	
	public Timer getSimulationTimer() {
		return this.simulationTimer;
	}
}
