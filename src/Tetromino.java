import java.awt.Color;

/*
 * Tetromino.java
 * 
 * @author: John Nelson
 */

public class Tetromino extends GameObject {

	//Variables
	public Shape shape;
	
	public int rotation;
	
	public GameCell[] cells;
	
	public Position[] cellLocations;
	
	public enum Shape {
		SQUARE, IBLOCK, TBLOCK, LBLOCK, BACKWARDSLBLOCK, SBLOCK, ZBLOCK
	}
	
	/*
	 * Default Constructor
	 */
	public Tetromino() {
		super();

		this.rotation = 0;
		this.cells = new GameCell[4];
		this.cellLocations = new Position[4];
		
		//Generates the tetromino at a valid location with a random shape
		this.generateShape();
		this.generateRotation();
		this.findEmptyPosition();
		this.setColor();
	}
	
	/*
	 * Tetromino
	 * 
	 * Custom constructor for a given shape and position
	 */
	public Tetromino(Shape shape, Position position) {
		super();
		
		this.position = position;
		this.shape = shape;
		this.setColor();
		this.rotation = 0;
		this.cells = new GameCell[4];
		this.cellLocations = new Position[4];
		
		placeTetromino();
	}
	
	/*
	 * setColor
	 * 
	 * Instantiates the color variable based on the shape
	 */
	private void setColor() {
		switch(shape) {
			case SQUARE:
				color = Color.BLUE;
				break;
				
			case IBLOCK:
				color = Color.CYAN;
				break;
				
			case TBLOCK:
				color = Color.MAGENTA;
				break;
				
			case LBLOCK:
				color = Color.GREEN;
				break;
				
			case BACKWARDSLBLOCK:
				color = Color.ORANGE;
				break;
				
			case SBLOCK:
				color = Color.RED;
				break;
				
			case ZBLOCK:
				color = Color.YELLOW;
		}
	}
	
	/*
	 * findEmptyPosition
	 */
	public void findEmptyPosition() {
		boolean keepLooking = true;
		this.position = new Position((int)(Math.random() * worldmanager.getWidth()), worldmanager.getHeight());
		this.findPositions();
		for(GameObject g : gamemanager.getGameObjects()) {
			if(g instanceof Tetromino && keepLooking && g != this) {
				for(Position p : ((Tetromino)g).cellLocations) {
					for(Position _p : this.cellLocations) {
						if(keepLooking) {
							if(p.equals(_p)) {
								keepLooking = false;
								this.findEmptyPosition();
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * generateShape
	 * 
	 * randomly generates a shape for the new tetromino
	 */
	public void generateShape() {
		int r = (int)(Math.random() * 7);
		switch(r) {
			case 0:
				this.shape = Shape.BACKWARDSLBLOCK;
				break;
				
			case 1:
				this.shape = Shape.IBLOCK;
				break;
				
			case 2:
				this.shape = Shape.LBLOCK;
				break;

			case 3:
				this.shape = Shape.SBLOCK;
				break;

			case 4:
				this.shape = Shape.SQUARE;
				break;

			case 5:
				this.shape = Shape.TBLOCK;
				break;

			case 6:
				this.shape = Shape.ZBLOCK;
				break;
		}
	}
	
	public void generateRotation() {
		int r = (int)(Math.random() * 3.9);
		this.rotation = r * 90;
	}
	
	/*
	 * moveDown
	 * 
	 * Move the Tetromino down one position
	 */
	public void moveDown() {
		this.leaveCells();
		this.position.y--;
		this.placeTetromino();
	}
	
	/*
	 * placeTetromino
	 * 
	 * Wrapper for placing the tetromino into the game scene
	 */
	public void placeTetromino() {
		this.findPositions();
		this.findCells();
		this.enterCells();
	}
	
	/*
	 * enterCells
	 * 
	 * Occupy the cells at the Tetromino's current position
	 */
	private void enterCells() {
		for(int i = 0; i < 4; i++) {
			if(cells[i] != null) {
				cells[i].occupied = true;
				cells[i].occupant = "tetromino";
				cells[i].color = color;
			}
		}
	}
	
	/*
	 * findCells
	 * 
	 * Deoccupy the cells at the Tetromino's current position
	 */
	private void leaveCells() {
		for(int i = 0; i < 4; i++) {
			if(cells[i] != null) {
				cells[i].occupied = false;
				cells[i].occupant = "none";
				cells[i].color = cells[i].defaultColor;
			}
		}
	}

	/*
	 * findCells
	 * 
	 * Finds the GameCells in the scene at the tetromino's positions
	 */
	private void findCells() {
		for(int i = 0; i < 4; i++) {
			cells[i] = worldmanager.getCell(cellLocations[i]);
		}
	}
	
	/*
	 * findPositions
	 * 
	 * Finds all of the positions this tetromino would 
	 * occupy in its current position and rotation
	 */
	private void findPositions() {
		switch(shape) {
			case SQUARE:
				cellLocations[0] = new Position(position.x, position.y);
				cellLocations[1] = new Position(position.x, position.y + 1);
				cellLocations[2] = new Position(position.x + 1, position.y);
				cellLocations[3] = new Position(position.x + 1, position.y + 1);
				break;
				
			case IBLOCK:
				if(rotation == 0 || rotation == 180) {	//----
					cellLocations[0] = new Position(position.x, position.y);
					cellLocations[1] = new Position(position.x + 1, position.y);
					cellLocations[2] = new Position(position.x + 2, position.y);
					cellLocations[3] = new Position(position.x + 3, position.y);
				}
				else {	//|
					cellLocations[0] = new Position(position.x, position.y);
					cellLocations[1] = new Position(position.x, position.y + 1);
					cellLocations[2] = new Position(position.x, position.y + 2);
					cellLocations[3] = new Position(position.x, position.y + 3);
				}
				break;
				
			case TBLOCK:
				switch(rotation) {
					case 0:		//_|_
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x + 1, position.y);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 2, position.y);
						break;
						
					case 90:	//-|
						cellLocations[0] = new Position(position.x, position.y + 1);
						cellLocations[1] = new Position(position.x + 1, position.y);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 1, position.y + 2);
						break;
						
					case 180:	//T
						cellLocations[0] = new Position(position.x + 1, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 2, position.y + 1);
						break;
						
					case 270:	//|-
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x, position.y + 2);
						break;
				}
				break;
				
			case LBLOCK:
				switch(rotation) {
					case 0:		//L
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x, position.y + 2);
						cellLocations[3] = new Position(position.x + 1, position.y);
						break;
						
					case 90:	//__|
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x + 1, position.y);
						cellLocations[2] = new Position(position.x + 2, position.y);
						cellLocations[3] = new Position(position.x + 2, position.y + 1);
						break;
						
					case 180:	//7
						cellLocations[0] = new Position(position.x, position.y + 2);
						cellLocations[1] = new Position(position.x + 1, position.y);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 1, position.y + 2);
						break;
						
					case 270:	//|---
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 2, position.y + 1);
						break;
				}
				break;
				
			case BACKWARDSLBLOCK:
				switch(rotation) {
					case 0:		//_|
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x + 1, position.y);
						cellLocations[2] = new Position(position.x + 1, position.y + 1);
						cellLocations[3] = new Position(position.x + 1, position.y + 2);
						break;
						
					case 90:	//---|
						cellLocations[0] = new Position(position.x, position.y + 1);
						cellLocations[1] = new Position(position.x + 1, position.y + 1);
						cellLocations[2] = new Position(position.x + 2, position.y);
						cellLocations[3] = new Position(position.x + 2, position.y + 1);
						break;
						
					case 180:	//|`
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x, position.y + 2);
						cellLocations[3] = new Position(position.x + 1, position.y + 2);
						break;
						
					case 270:	//|___
						cellLocations[0] = new Position(position.x, position.y);
						cellLocations[1] = new Position(position.x, position.y + 1);
						cellLocations[2] = new Position(position.x + 1, position.y);
						cellLocations[3] = new Position(position.x + 2, position.y);
						break;
				}
				break;
				
			case SBLOCK:
				if(rotation == 0 || rotation == 180) {	//S
					cellLocations[0] = new Position(position.x, position.y);
					cellLocations[1] = new Position(position.x + 1, position.y);
					cellLocations[2] = new Position(position.x + 1, position.y + 1);
					cellLocations[3] = new Position(position.x + 2, position.y + 1);
				}
				else {	//',
					cellLocations[0] = new Position(position.x, position.y + 1);
					cellLocations[1] = new Position(position.x, position.y + 2);
					cellLocations[2] = new Position(position.x + 1, position.y);
					cellLocations[3] = new Position(position.x + 1, position.y + 1);
				}
				break;
				
			case ZBLOCK:
				if(rotation == 0 || rotation == 180) {	//Z
					cellLocations[0] = new Position(position.x, position.y + 1);
					cellLocations[1] = new Position(position.x + 1, position.y);
					cellLocations[2] = new Position(position.x + 1, position.y + 1);
					cellLocations[3] = new Position(position.x + 2, position.y);
				}
				else {	//,'
					cellLocations[0] = new Position(position.x, position.y);
					cellLocations[1] = new Position(position.x, position.y + 1);
					cellLocations[2] = new Position(position.x + 1, position.y + 1);
					cellLocations[3] = new Position(position.x + 1, position.y + 2);
				}
				break;
		}
	}
}


