/*
 * Position.java
 * 
 * @author: John Nelson
 */

public class Position {
	
	//Variables
	public int x;
	public int y;
	
	public Position() {
		x = 0;
		y = 0;
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean equals(Position p) {
		return p.x == this.x && p.y == this.y;
	}
}


