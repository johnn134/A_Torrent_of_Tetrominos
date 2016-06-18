/*
 * WorldManager.java
 * 
 * @author: John Nelson
 */

public class WorldManager {

	private static WorldManager instance = null;
	
	private static boolean started = false;
	
	private static int width = 15;
	private static int height = 30;
	
	//Game Variables
	private static GameCell[][] cells;
	
	private WorldManager(){}	//singleton constructor

	/*
	 * getInstance
	 * 
	 * returns the instance of this singleton
	 */
	public static WorldManager getInstance() {
		if(instance == null) {
			instance = new WorldManager();
		}
		return instance;
	}
	
	/*
	 * startUp
	 * 
	 * Initializes WorldManager values and "turns it on"
	 */
	public boolean startUp() {
		cells = new GameCell[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				cells[i][j] = new GameCell(i, j);
			}
		}
		
		started = true;
		
		return true;
	}
	
	/*
	 * shutDown
	 * 
	 * turns off the WorldManager
	 */
	public boolean shutDown() {
		started = false;
		
		return true;
	}
	
	/*
	 * isStarted
	 * 
	 * checks to see if the WorldManager has been started
	 */
	public boolean isStarted() {
		return started;
	}
	
	public void setWidth(int _width) {
		width = _width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int _height) {
		height = _height;
	}
	
	public int getHeight() {
		return height;
	}
	
	/*
	 * inBounds
	 * 
	 * checks to see if the given values are within the world bounds
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public boolean inBounds(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return false;
		return true;
	}
	
	/*
	 * inBounds
	 * 
	 * checks to see if the given Position is within the world bounds
	 * 
	 * @param pos - position to check
	 */
	public boolean inBounds(Position pos) {
		if(pos.x < 0 || pos.x >= width || pos.y < 0 || pos.y >= height)
			return false;
		return true;
	}
	
	public GameCell[][] getCells() {
		return cells;
	}
	
	/*
	 * getCell
	 * 
	 * returns the gamecell with the given coordinates
	 */
	public GameCell getCell(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return null;
		else
			return cells[x][y];
	}
	
	/*
	 * getCell
	 * 
	 * returns the gamecell at the given position
	 */
	public GameCell getCell(Position pos) {
		if(pos.x < 0 || pos.x >= width || pos.y < 0 || pos.y >= height)
			return null;
		else
			return cells[pos.x][pos.y];
	}
	
	/*
	 * setCell
	 * 
	 * sets the occupied value of the cell at the given coordinates
	 */
	public void setCell(int x, int y, boolean occupied) {
		cells[x][y].occupied = occupied;
	}
	
	/*
	 * resetCells
	 * 
	 * Reinitializes all gamecells
	 */
	public void resetCells() {
		for(GameCell[] g : cells) {
			for(GameCell c : g) {
				c.reset();
			}
		}
	}
}


