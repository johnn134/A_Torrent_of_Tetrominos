import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/*
 * GUIScene.java
 * 
 * @author: John Nelson
 */

@SuppressWarnings("serial")
public class GUIScene extends JPanel {
	
	WorldManager worldmanager;
	GameManager gamemanager;
	
	private GameController gamecontroller;
	
	private int screenWidth;
	private int screenHeight;
	
	private int headerHeight = 30;
	
	private int roundSize = 6;
	
	public boolean showMenu;
	private boolean muted;
	private boolean bright;
	
	private Color cellBorderColor = Color.BLACK;
	
	/*
	 * Constructor
	 */
	public GUIScene(int w, int h) {
		this.screenWidth = w;
		this.screenHeight = h;
		
		this.showMenu = false;
		this.muted = false;
		this.bright = false;
		
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		
		worldmanager = WorldManager.getInstance();
		gamemanager = GameManager.getInstance();
		
		gamecontroller = new GameController();
		
		/*** Bindings ***/
		
		//WASD
		this.getInputMap().put(KeyStroke.getKeyStroke("W"), "up");
		this.getInputMap().put(KeyStroke.getKeyStroke("S"), "down");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"), "right");
		//Arrow Keys
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		//Escape
		this.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "quit");
		//Action Mappings
		this.getActionMap().put("up", new MoveAction("up"));
		this.getActionMap().put("down", new MoveAction("down"));
		this.getActionMap().put("left", new MoveAction("left"));
		this.getActionMap().put("right", new MoveAction("right"));
		this.getActionMap().put("quit", new QuitAction());
		
		//Add A Mouse Listener for our custom menu GUI
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				checkMouseClick(e.getX(), e.getY());
			}
		});
	}
	
	/*
	 * loadMenu
	 * 
	 * sets whether the menu is shown or not
	 * 
	 * @param load - new shown value
	 */
	public void loadMenu(boolean load) {
		showMenu = load;
		
		repaint(0, 0, screenWidth, screenHeight + headerHeight);
	}
	
	/*
	 * setMute
	 * 
	 * sets whether the game is muted or not
	 * 
	 * @param m - new mute value
	 */
	public void setMute(boolean m) {
		muted = m;

		repaint(0, 0, screenWidth, screenHeight + headerHeight);
	}
	
	/*
	 * setBrightness
	 * 
	 * sets the brightness of the game
	 * 
	 * @param b - is the game bright or dark
	 */
	public void setBrightness(boolean b) {
		bright = b;
		if(b) {
			this.setBackground(Color.WHITE);
			cellBorderColor = Color.WHITE;
			for(GameCell[] g : worldmanager.getCells()) {
				for(GameCell c : g) {
					c.defaultColor = new Color(223, 223, 223);
					c.color = c.defaultColor;
					gamecontroller.c.color = Color.BLACK;
				}
			}
		}
		else {
			this.setBackground(Color.BLACK);
			cellBorderColor = Color.BLACK;
			for(GameCell[] g : worldmanager.getCells()) {
				for(GameCell c : g) {
					c.defaultColor = Color.DARK_GRAY;
					c.color = c.defaultColor;
					gamecontroller.c.color = Color.WHITE;
				}
			}
		}
		
		repaint(0, 0, screenWidth, screenHeight + headerHeight);
	}
	
	/*
	 * checkMouseClick
	 * 
	 * performs actions depending on where in the gui the player clicks
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void checkMouseClick(int x, int y) {
		if(showMenu) {
			int menuX = screenWidth / 4;
			int menuY = (screenHeight - (screenWidth / 2)) / 2 - 16 + headerHeight;
			int buttonSize = 32;
			
			if(x > menuX + (screenWidth / 4) - buttonSize / 2 && x < menuX + (screenWidth / 4) - buttonSize / 2 + buttonSize &&
					y > menuY + (screenWidth / 4) && y < menuY + (screenWidth / 4) + buttonSize) {	//Resume pressed
				loadMenu(false);
			}
			else if(x > menuX + (screenWidth / 4) - (buttonSize * 7 / 4) && x < menuX + (screenWidth / 4) - (buttonSize * 7 / 4) + buttonSize &&
					y > menuY + (screenWidth / 4) && y < menuY + (screenWidth / 4) + buttonSize) {	//Brightness pressed
				setBrightness(!bright);
			}
			else if(x > menuX + (screenWidth / 4)  + buttonSize * 3 / 4 && x < menuX + (screenWidth / 4)  + buttonSize * 3 / 4 + buttonSize &&
					y > menuY + (screenWidth / 4) && y < menuY + (screenWidth / 4) + buttonSize) {	//Mute pressed
				setMute(!muted);
			}
		}
		else if(x >= 7 && x <= 23 &&
				y >= 7 && y <= 22) {
			loadMenu(true);
		}
	}
	
	/*
	 * getNumOffset
	 * 
	 * returns the offset for centering a number in the gui
	 * 
	 * @param num - text number
	 * @return int
	 */
	public int getNumOffset(int num) {
		if(num < 10) {
			return 5;
		}
		else if(num < 100) {
			return 10;
		}
		else if(num < 1000) {
			return 15;
		}
		else if(num < 10000) {
			return 20;
		}
		else if(num < 10000) {
			return 25;
		}
		else if(num < 100000) {
			return 30;
		}
		else if(num < 1000000) {
			return 35;
		}
		else {
			return 0;
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(screenWidth, screenHeight + 32);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Draw Game Cells
		int w = worldmanager.getWidth();
		int h = worldmanager.getHeight();
		
		//Draw Character and Tetrominos
		gamecontroller.c.placeCharacter();
		for(GameObject o : gamemanager.getGameObjects()) {
			if(o instanceof Tetromino) {
				((Tetromino) o).placeTetromino();
			}
		}
		
		//Draw Header
		if(showMenu) {
			if(bright) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, screenWidth, headerHeight);
				
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, screenWidth, headerHeight);
				
				g.setColor(Color.WHITE);
			}
	
			g.setFont(g.getFont().deriveFont(20.0f).deriveFont(Font.BOLD));
			g.drawString("" + gamemanager.getScore(), screenWidth / 2 - getNumOffset(gamemanager.getScore()), headerHeight - 8);
		}
		else {
			if(bright) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, screenWidth, headerHeight);
				
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, screenWidth, headerHeight);
				
				g.setColor(Color.WHITE);
			}
			g.fillRect(7, 7, 16, 3);
			g.fillRect(7, 13, 16, 3);
			g.fillRect(7, 19, 16, 3);
	
			g.setFont(g.getFont().deriveFont(20.0f).deriveFont(Font.BOLD));
			g.drawString("" + gamemanager.getScore(), screenWidth / 2 - getNumOffset(gamemanager.getScore()), headerHeight - 8);
		}
		
		//Draw Scene
		if(showMenu) {
			int menuX = screenWidth / 4;
			int menuY = (screenHeight - (screenWidth / 2)) / 2 - 16 + headerHeight;
			int buttonRound = 8;
			int buttonSize = 32;
			
			//Draw scene
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					g.setColor(worldmanager.getCell(i, j).color.darker());
					g.fillRoundRect(screenWidth / w * i, headerHeight + screenHeight / h * (h - j - 1), screenWidth / w, screenHeight / h, roundSize, roundSize);
					g.setColor(cellBorderColor);
					g.drawRoundRect(screenWidth / w * i, headerHeight + screenHeight / h * (h - j - 1), screenWidth / w, screenHeight / h, roundSize, roundSize);
				}
			}
			
			//Draw menu over scene
			if(bright)
				g.setColor(Color.WHITE);
			else
				g.setColor(Color.GRAY);
			g.fillRoundRect(menuX, menuY, screenWidth / 2, screenWidth / 2, 16, 16);
			
			//Draw Highscore
			if(bright)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(17.0f));
			g.drawString("HIGHSCORE", menuX + (screenWidth / 18), menuY + (screenWidth / 8));
			g.drawString("" + gamemanager.getHighScore(), menuX + (screenWidth / 4) - getNumOffset(gamemanager.getHighScore()), menuY + (screenWidth / 5));
			
			//Resume Button
			g.setColor(new Color(0, 223, 0));
			g.fillRoundRect(menuX + (screenWidth / 4) - buttonSize / 2, menuY + (screenWidth / 4), buttonSize, buttonSize, buttonRound, buttonRound);
			
			g.setColor(new Color(250, 255, 250));
			int[] xp = {menuX + (screenWidth / 4) - buttonSize / 2 + 6, 
						menuX + (screenWidth / 4) - buttonSize / 2 + 6, 
						menuX + (screenWidth / 4) + buttonSize / 2 - 6};
			int[] yp = {menuY + (screenWidth / 4) + 6, 
						menuY + (screenWidth / 4) + buttonSize - 6, 
						menuY + (screenWidth / 4) + buttonSize / 2};
			g.fillPolygon(xp, yp, 3);
			
			//Mute Button
			if(muted) {
				//Button Background
				if(bright)
					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(menuX + (screenWidth / 4)  + buttonSize * 3 / 4, menuY + (screenWidth / 4), buttonSize, buttonSize, buttonRound, buttonRound);
				
				//Mute Symbol
				g.setColor(Color.WHITE);
				int[] xp2 = {menuX + (screenWidth / 4) + buttonSize * 3 / 4 + buttonSize - 11, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + buttonSize - 11, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10};
				int[] yp2 = {menuY + (screenWidth / 4) + 8, 
							 menuY + (screenWidth / 4) + buttonSize - 8, 
							 menuY + (screenWidth / 4) + buttonSize / 2};
				g.fillPolygon(xp2, yp2, 3);
				g.fillRect(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10, menuY + (screenWidth / 4) + 11, 4, 10);
				if(bright)
					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.DARK_GRAY);
				g.drawRect(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10, menuY + (screenWidth / 4) + 11, 4, 10);
				
				//Cancel Symbol
				g.setColor(Color.RED);
				g.fillOval(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 16, menuY + (screenWidth / 4) + 16, 10, 10);
				if(bright)
					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.DARK_GRAY);
				g.fillOval(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 17, menuY + (screenWidth / 4) + 17, 8, 8);
				g.setColor(Color.RED);
				int[] xp3 = {menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 17, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 18, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 24, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 25};
				int[] yp3 = {menuY + (screenWidth / 4) + 18, 
							 menuY + (screenWidth / 4) + 17, 
							 menuY + (screenWidth / 4) + 25, 
							 menuY + (screenWidth / 4) + 24};
				g.fillPolygon(xp3, yp3, 4);
			}
			else {
				//Button Background
				if(bright)
					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(menuX + (screenWidth / 4)  + buttonSize * 3 / 4, menuY + (screenWidth / 4), buttonSize, buttonSize, buttonRound, buttonRound);
				
				//Mute Symbol
				g.setColor(Color.WHITE);
				int[] xp2 = {menuX + (screenWidth / 4) + buttonSize * 3 / 4 + buttonSize - 11, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + buttonSize - 11, 
							 menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10};
				int[] yp2 = {menuY + (screenWidth / 4) + 8, 
							 menuY + (screenWidth / 4) + buttonSize - 8, 
							 menuY + (screenWidth / 4) + buttonSize / 2};
				g.fillPolygon(xp2, yp2, 3);
				g.fillRect(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10, menuY + (screenWidth / 4) + 11, 4, 10);
				if(bright)
					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.DARK_GRAY);
				g.drawRect(menuX + (screenWidth / 4) + buttonSize * 3 / 4 + 10, menuY + (screenWidth / 4) + 11, 4, 10);
			}
			
			//Brightness Button
			if(bright) {
				//Button Background
				g.setColor(Color.YELLOW);
				g.fillRoundRect(menuX + (screenWidth / 4) - (buttonSize * 7 / 4), menuY + (screenWidth / 4), buttonSize, buttonSize, buttonRound, buttonRound);
				
				//Button Sun Symbol
				g.setColor(Color.WHITE);
				g.fillOval(menuX + (screenWidth / 4) - (buttonSize * 7 / 4) + 4, menuY + (screenWidth / 4) + 4, buttonSize - 9, buttonSize - 9);
			}
			else {
				//Button Background
				g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(menuX + (screenWidth / 4) - (buttonSize * 7 / 4), menuY + (screenWidth / 4), buttonSize, buttonSize, buttonRound, buttonRound);
				
				//Button Moon Symbol
				g.setColor(Color.WHITE);
				g.fillOval(menuX + (screenWidth / 4) - (buttonSize * 7 / 4) + 4, menuY + (screenWidth / 4) + 4, buttonSize - 9, buttonSize - 9);
				g.setColor(Color.DARK_GRAY);
				g.fillOval(menuX + (screenWidth / 4) - (buttonSize * 7 / 4) + 9, menuY + (screenWidth / 4), buttonSize - 9, buttonSize - 9);
			}
		}
		else {
			//Draw scene
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					g.setColor(worldmanager.getCell(i, j).color);
					g.fillRoundRect(screenWidth / w * i, headerHeight + screenHeight / h * (h - j - 1), screenWidth / w, screenHeight / h, roundSize, roundSize);
					g.setColor(cellBorderColor);
					g.drawRoundRect(screenWidth / w * i, headerHeight + screenHeight / h * (h - j - 1), screenWidth / w, screenHeight / h, roundSize, roundSize);
				}
			}
		}
	}
	
	private class MoveAction extends AbstractAction {
		
		String dir;
		
		MoveAction(String dir) {
			this.dir = dir;
		}
		
		public void actionPerformed(ActionEvent e) {
			if(!showMenu) {
				System.out.println(dir + " Pressed");
				
				switch(dir) {
					case "up":
						gamecontroller.c.moveInDirection(Character.Direction.UP);
						break;
						
					case "down":
						gamecontroller.c.moveInDirection(Character.Direction.DOWN);
						break;
						
					case "left":
						gamecontroller.c.moveInDirection(Character.Direction.LEFT);
						break;
						
					case "right":
						gamecontroller.c.moveInDirection(Character.Direction.RIGHT);
						break;
				}
				
				gamemanager.step();
				
				repaint(0, 0, screenWidth, screenHeight + headerHeight);
			}
		}
	}
	
	private class QuitAction extends AbstractAction {
		
		QuitAction() {}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("Esc Pressed");
			System.exit(0);
		}
	}
}



