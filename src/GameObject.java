import java.awt.Color;

/*
 * GameObject.java
 * 
 * @author: John Nelson
 */

public class GameObject {
	
	//Variables
	WorldManager worldmanager;
	GameManager gamemanager;
	
	public Position position;
	
	public int size;
	public int ID;
	
	public Color color;
	
	public Orientation alignment;
	
	public enum Orientation {
		BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT, 
		CENTERLEFT, CENTER, CENTERRIGHT, 
		TOPLEFT, TOPCENTER, TOPRIGHT
	}
	
	/*
	 * Default Constructor
	 */
	public GameObject() {
		initializeGameObject();
	}
	
	public GameObject(Position position) {
		initializeGameObject();
		this.position = position;
	}
	
	public GameObject(Position position, int size) {
		initializeGameObject();
		this.position = position;
		this.size = size;
	}
	
	/*
	 * initializeGameObject
	 * 
	 * Initializes the values of the gameobject to their default state
	 */
	public void initializeGameObject() {
		worldmanager = WorldManager.getInstance();
		gamemanager = GameManager.getInstance();
		ID = -1;
		position = new Position();
		alignment = Orientation.BOTTOMLEFT;
		size = 0;
		color = Color.WHITE;
		
		gamemanager.addObject(this);
		
		if(ID == -1) {
			System.out.println("ERROR: Failed to obtain unique ID from WorldManager");
		}
	}
	
	/*** Getters/Setters ***/
}


