/*********************************************************************************
*File: Cell.java
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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

//class Cell is our main component on that project.
//Every Cell is a JPanel and they keep
//their x and y values for placing them to right position on the maze,
//their status to figure out they are visited or not,
//and they keep their neighbours positions (up, down, right, left) so we can easily
//	find how many neighbours they have and positions of neighbours.
public class Cell extends JPanel {
	protected int x, y;
	private Status status;
	private ArrayList<Position> neighboursPositions;
	
	//There are 2 constructors;
	//
	//the first one only takes x and y integers as arguments
	//	to set position values of the Cell.
	//It set the status of Cell as UNKNOWN implicitly.
	//We need the constructor without setting neighbours because class Wall is extends class Cell
	//	and we do not need to know walls' neighbours.
	public Cell(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setStatus(Status.UNKNOWN);
		this.setPreferredSize(new Dimension(Maze.CELL_WIDTH, Maze.CELL_HEIGHT));
	}
	
	//The second one takes Status argument in addition to first one,
	//however it is not the only difference, that constructor first calls
	//the first constructor then it sets status and it sets neighbours of the Cell.
	public Cell(int x, int y, Status s) {
		this(x, y);
		this.setStatus(s);
		this.setNeighboursPositions();	
	}
	
	//This function finds that how many neighbours are around this Cell and what are thier
	//	positions according to this Cell.
	//It is a void function but it sets neighboursPositions field inside.
	private void setNeighboursPositions() {
		//The logic is simple first we initialize a new list with all 4 positions (up, down, left, right) inside,
		//then we remove unnecessary ones for example cell at the left-up corner does not have up and left neighbour.
		this.neighboursPositions = new ArrayList<Position>(Arrays.asList(Position.values()));
		ArrayList<Position> removeList = new ArrayList<Position>();
		
		for(Position p : neighboursPositions) {
			//in that if else block left side of AND is positions as you can see,
			//	the right side is to check this cell's position, is it on corners or borders.
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
		if(x < 0 || x > Maze.BOUNDS_X)
			throw new IndexOutOfBoundsException();
		this.x = x;
	}

	private void setY(int y) {
		if(y < 0 || y > Maze.BOUNDS_Y)
			throw new IndexOutOfBoundsException();
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
	
	//Getters for x and y are named with underscore since there are
	//	already getX and getY functions on JPanel class.
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
