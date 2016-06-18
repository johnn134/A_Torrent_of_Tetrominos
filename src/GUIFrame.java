/*
 * GUIFrame.java
 * 
 * @author: John Nelson
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GUIFrame extends JFrame{
	
	GameManager gamemanager;
	
	private int screenWidth;
	private int screenHeight;
	
	GUIScene scene;
	
	public GUIFrame(int w, int h) {
		gamemanager = GameManager.getInstance();
		
		this.screenWidth = w;
		this.screenHeight = h;
		
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ATOT");
		getContentPane().setBackground(Color.BLACK);
		
		//Set Initial Window location
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = screenWidth;
		int h = screenHeight;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		
		this.setLocation(x, y);
		
		//Create Screens
		scene = new GUIScene(screenWidth, screenHeight);
		
		this.add(scene);
		
		this.pack();
	}
}


