import java.awt.Color;

/*
 * GameCell.java
 * 
 * @author: John Nelson
 */

public class GameCell {

	//Variables
	public Position position;
	
	public boolean occupied;
	
	public String occupant;
	
	public Color color;
	
	public Color defaultColor = Color.DARK_GRAY;
	
	/*
	 * Default Constructor
	 */
	public GameCell() {
		initializeGameCell();
		this.position = new Position();
	}
	
	public GameCell(Position position) {
		initializeGameCell();
		this.position = position;
	}
	
	public GameCell(int x, int y) {
		initializeGameCell();
		this.position = new Position(x, y);
	}
	
	private void initializeGameCell() {
		occupied = false;
		occupant = "none";
		color = defaultColor;
	}
	
	public void reset() {
		occupied = false;
		occupant = "none";
		color = defaultColor;
	}
}
