/**
 * 
 */
package com.ozuduru.mazegenerator;

/**
 * @author onur
 *
 */
public enum Position {
	UP(0, -2),
	DOWN(0, 2),
	LEFT(-2, 0),
	RIGHT(2, 0);
	
	public int x, y;

	/**
	 * @param x
	 * @param y
	 */
	private Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
