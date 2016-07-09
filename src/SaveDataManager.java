import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * SaveDataManager.java
 * 
 * @author: John Nelson
 */

public class SaveDataManager {

	private static SaveDataManager instance = null;
	
	private static boolean started = false;
	
	private static String savefilename = System.getenv("APPDATA") + "\\atot\\save_data.txt";
	
	private SaveDataManager(){}	//singleton constructor
	
	/*
	 * getInstance
	 * 
	 * returns the instance of this singleton
	 */
	public static SaveDataManager getInstance() {
		if(instance == null) {
			instance = new SaveDataManager();
		}
		return instance;
	}
	
	/*
	 * startUp
	 * 
	 * Initializes SaveDataManager values and "turns it on"
	 */
	public boolean startUp() {
		GameManager gamemanager = GameManager.getInstance();
		
		File dir = new File(System.getenv("APPDATA") + "\\atot");
		if(!dir.exists())
			dir.mkdir();
		
		try {	//try reading the highscore from the save file
			Scanner in = new Scanner(new File(savefilename));
			gamemanager.setHighScore(in.nextInt());
			in.close();
		}
		catch(FileNotFoundException e) {
			try {	//try creating a new save data file with a 0 highscore if not found
				FileWriter out = new FileWriter(new File(savefilename));
				out.write("0");
				out.flush();
				out.close();
			}
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		started = true;
		
		return true;
	}
	
	/*
	 * shutDown
	 * 
	 * turns off the SaveDataManager
	 */
	public boolean shutDown() {
		started = false;
		
		return true;
	}

	/*
	 * isStarted
	 * 
	 * checks to see if the SaveDataManager has been started
	 */
	public boolean isStarted() {
		return started;
	}
	
	public void saveData() {
		GameManager gamemanager = GameManager.getInstance();
		
		try {
			FileWriter out = new FileWriter(new File(savefilename));
			out.write("" + gamemanager.getHighScore());
			out.flush();
			out.close();
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}


