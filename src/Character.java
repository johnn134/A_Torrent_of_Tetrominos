/*
 * Character.java
 * 
 * @author: John Nelson
 */

public class Character extends GameObject {
	
	//Variables
	public GameCell cell;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	/*
	 * Default Constructor
	 */
	public Character() {
		super();
	}
	
	/*
	 * Constructor base on given position
	 */
	public Character(Position pos) {
		super();
		this.position = pos;
		
		placeCharacter();
	}
	
	/*
	 * moveInDirection
	 * 
	 * moves the character to a new position depending on the given direction
	 * 
	 * @param dir - direction to move
	 */
	public boolean moveInDirection(Direction dir) {
		switch(dir) {
			case UP:
				if(worldmanager.inBounds(position.x, position.y + 1)) {
					this.move(new Position(position.x, position.y + 1));
					gamemanager.checkForCollisions();
				}
				else {	//The player has moved past the top of the screen and wrapped around
					this.move(new Position(position.x, 0));
					gamemanager.addWrapScore();
					return false;
				}
				break;
				
			case DOWN:
				if(worldmanager.inBounds(position.x, position.y - 1))
					this.move(new Position(position.x, position.y - 1));
				else
					return false;
				break;
				
			case LEFT:
				if(worldmanager.inBounds(position.x - 1, position.y))
					this.move(new Position(position.x - 1, position.y));
				else
					return false;
				break;
				
			case RIGHT:
				if(worldmanager.inBounds(position.x + 1, position.y))
					this.move(new Position(position.x + 1, position.y));
				else
					return false;
				break;
		}
		
		return true;
	}
	
	/*
	 * move
	 * 
	 * Move the character from its current position to the new position
	 */
	public void move(Position pos) {
		this.leaveCell();
		
		position = pos;
		
		placeCharacter();
	}
	
	/*
	 * placeCharacter
	 * 
	 * Wrapper for placing the tetromino into the game scene
	 */
	public void placeCharacter() {
		this.findCell();
		this.enterCell();
	}
	
	/*
	 * enterCell
	 * 
	 * Occupy the cells at the Character's current position
	 */
	private void enterCell() {
		for(int i = 0; i < 4; i++) {
			if(cell != null) {
				cell.occupied = true;
				cell.occupant = "character";
				cell.color = color;
			}
		}
	}
	
	/*
	 * findCell
	 * 
	 * Deoccupy the cells at the Character's current position
	 */
	private void leaveCell() {
		for(int i = 0; i < 4; i++) {
			if(cell != null) {
				cell.occupied = false;
				cell.occupant = "none";
				cell.color = cell.defaultColor;
			}
		}
	}

	/*
	 * findCell
	 * 
	 * Finds the GameCells in the scene at the character's positions
	 */
	private void findCell() {
		cell = worldmanager.getCell(position);
	}
}
