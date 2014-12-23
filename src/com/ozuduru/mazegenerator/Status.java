/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.Color;

/**
 * @author onur
 *
 */
public enum Status {
	VISITED(true, Color.WHITE),
	UNVISITED(false, Color.BLACK);
	
	private boolean isVisited;
	private Color color;
	
	/**
	 * @param isVisited
	 * @param color
	 */
	private Status(boolean isVisited, Color color) {
		this.isVisited = isVisited;
		this.color = color;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isVisited() {
		return this.isVisited;
	}

}
