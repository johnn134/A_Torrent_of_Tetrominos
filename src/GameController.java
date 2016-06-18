/*
 * GameController.java
 * 
 * @author: John Nelson
 */

public class GameController {
	
	WorldManager worldmanager;
	GameManager gamemanager;
	
	public Character c;
	
	public GameController() {
		worldmanager = WorldManager.getInstance();
		gamemanager = GameManager.getInstance();
		
		gamemanager.passController(this);
		
		placeCharacter();
	}
	
	public void placeCharacter() {
		c = new Character(new Position(worldmanager.getWidth() / 2, worldmanager.getHeight() / 2));
	}
}
