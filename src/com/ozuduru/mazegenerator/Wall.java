/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.Color;

/**
 * @author onur
 *
 */
public class Wall extends Cell {

	/**
	 * @param x
	 * @param y
	 */
	public Wall(int x, int y) {
		super(x, y);
		this.setBackground(Color.BLACK);
	}
	
	public void removeMe() {
		if(this.isRemoved() == true)
			return;
		this.setStatus(Status.VISITED);
		this.setBackground(this.getStatus().getColor());
	}
	
	public boolean isRemoved() {
		return (this.getStatus() == Status.VISITED) ? true : false;
	}
}
