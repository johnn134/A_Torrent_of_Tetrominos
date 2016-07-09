/*
 * game.java
 * 
 * @author: John Nelson
 */

public class game {
	
	private static int screenWidth = 256;
	private static int screenHeight = 512;
	
	public static void main(String[] args) {
		
		WorldManager worldmanager = WorldManager.getInstance();
		worldmanager.startUp();
		
		GameManager gamemanager = GameManager.getInstance();
		gamemanager.startUp();
		
		SaveDataManager savemanager = SaveDataManager.getInstance();
		savemanager.startUp();
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUIFrame(screenWidth, screenHeight).setVisible(true);
			}
		});
		
		savemanager.shutDown();
		gamemanager.shutDown();
		worldmanager.shutDown();
	}

}
