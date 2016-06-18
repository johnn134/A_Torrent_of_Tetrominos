/*
 * ResourceManager.java
 * 
 * @author: John Nelson
 */

public class ResourceManager {

	private static ResourceManager instance = null;
	
	private static boolean started = false;
	
	private ResourceManager() {}
	
	public static ResourceManager getInstance() {
		if(instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	/*
	 * startUp
	 * 
	 * Initializes GameManager values and "turns it on"
	 */
	public boolean startUp() {
		
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
}
