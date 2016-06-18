import java.util.ArrayList;

/*
 * GameManager.java
 */

public class GameManager {

	private static GameManager instance = null;
	
	private static boolean started = false;
	
	private static int steps;
	
	//Game Variables
	
	private static GameController gamecontroller;
	
	private static ArrayList<GameObject> gameobjects;
	
	private static int numGameObjects;
	
	private static int score;
	
	private static int highscore;
	
	private static int movePoints = 10;
	
	private static int wrapPoints = 500;
	
	private static Character character;
	
	private GameManager(){}	//singleton constructor
	
	/*
	 * getInstance
	 * 
	 * returns the instance of this singleton
	 */
	public static GameManager getInstance() {
		if(instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	/*
	 * startUp
	 * 
	 * Initializes GameManager values and "turns it on"
	 */
	public boolean startUp() {
		gameobjects = new ArrayList<GameObject>();
		numGameObjects = 0;
		steps = 0;
		score = 0;
		
		highscore = 0;	//Change this to read from save file
		
		started = true;
		
		return true;
	}
	
	/*
	 * shutDown
	 * 
	 * turns off the GameManager
	 */
	public boolean shutDown() {
		started = false;
		
		return true;
	}

	/*
	 * isStarted
	 * 
	 * checks to see if the GameManager has been started
	 */
	public boolean isStarted() {
		return started;
	}
	
	/*
	 * passController
	 * 
	 * sets the gamecontroller value
	 */
	public void passController(GameController g) {
		gamecontroller = g;
	}
	
	/*
	 * getGameObjects
	 * 
	 * returns the list of gameobjects
	 * 
	 * @return ArrayList<GameObject>
	 */
	public ArrayList<GameObject> getGameObjects() {
		return gameobjects;
	}
	
	/*
	 * addObject
	 * 
	 * adds the given gameobject to the game's list of objects
	 */
	public void addObject(GameObject gameobject) {
		gameobjects.add(gameobject);
		gameobject.ID = numGameObjects;
		
		if(gameobject instanceof Character) {
			character = (Character)gameobject;
		}
		
		numGameObjects++;
	}
	
	/*
	 * removeObject
	 * 
	 * removes the given gameobject from the game's list of objects
	 */
	public void removeObject(GameObject gameobject) {
		gameobjects.remove(gameobject);
		
		numGameObjects--;
	}
	
	/*
	 * deleteTetrominos
	 * 
	 * deletes all tetrominos in the current game scene
	 */
	public void deleteTetrominos() {
		ArrayList<GameObject> delObjs = new ArrayList<GameObject>();
		for(GameObject g : gameobjects) {
			if(g instanceof Tetromino) {
				delObjs.add(g);
				numGameObjects--;
			}
		}
		gameobjects.removeAll(delObjs);
	}
	
	/*
	 * resetGame
	 * 
	 * Deletes all objects in the scene and replaces the character
	 */
	public void resetGame() {
		WorldManager worldmanager = WorldManager.getInstance();
		
		worldmanager.resetCells();
		
		gameobjects.clear();
		numGameObjects = 0;
		if(score > highscore)
			highscore = score;
		setScore(0);
		
		gamecontroller.placeCharacter();
	}
	
	/*
	 * checkForCollisions
	 * 
	 * checks if the character is intersecting any tetrominos
	 * 
	 * @return boolean - true if no collisions; false if collisions
	 */
	public boolean checkForCollisions() {
		for(GameObject g : gameobjects) {
			if(g instanceof Tetromino) {
				for(Position p : ((Tetromino) g).cellLocations) {
					if(p.equals(character.position)) {	//Objects are colliding
						resetGame();
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highscore;
	}
	
	public void setScore(int val) {
		score = val;
	}
	
	/*
	 * addWrapScore
	 * 
	 * adds the score for wrapping around the screen
	 */
	public void addWrapScore() {
		score += wrapPoints - movePoints;
	}
	
	/*
	 * step
	 * 
	 * called after every player input - game update/loop
	 */
	public void step() {
		steps++;
		
		//Spawn a Tetromino
		if(steps % 1 == 0)
			new Tetromino();
		
		//Move all tetrominos down
		for(GameObject g : gameobjects) {
			if(g instanceof Tetromino) {
				((Tetromino)g).moveDown();
			}
		}
		
		if(checkForCollisions()) {	//Check for collisions before checking for offscreen tetrominos
			//Delete all tetrominos that move past the screen
			ArrayList<GameObject> delObjs = new ArrayList<GameObject>();
			for(GameObject g: gameobjects) {
				if(g instanceof Tetromino) {
					if(g.position.y < -2) {
						System.out.println("Tetromino has moved offscreen; deleting.");
						delObjs.add(g);
					}
				}
			}
			gameobjects.removeAll(delObjs);
			
			//Add Game Points
			setScore(getScore() + movePoints);
		}
		
	}
}
