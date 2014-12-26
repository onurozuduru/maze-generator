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
	VISITED(Color.WHITE),
	UNVISITED(Color.BLACK),
	UNKNOWN(Color.BLACK);
	
	private Color color;
	
	/**
	 * @param color
	 */
	private Status(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

}
