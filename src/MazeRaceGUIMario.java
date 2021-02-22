import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.*;

// opens Mario game frame and its components
public class MazeRaceGUIMario extends JFrame implements KeyListener, ActionListener {
	
	// create constants
	private final int CELL_SIZE = 25;
	private final int NUM_COINS = 10;
	private final int NUM_CELLS_WIDTH = 27;
	private final int NUM_CELLS_HEIGHT = 27;
	
	// load object images
	private final ImageIcon WALL = new ImageIcon("images/red square.png");
	private final ImageIcon OUT_OF_BOUNDS = new ImageIcon("images/black square.png");
	private final ImageIcon PATH = new ImageIcon("images/grey square.png");
	private final ImageIcon COIN = new ImageIcon("images/gold coin.gif");
	private final ImageIcon PORTAL = new ImageIcon("images/portal.jpg");
	private final ImageIcon PORTAL2 = new ImageIcon("images/portal.jpg");
	
	// player object
	Player player;
	
	// create scoreboard and panel
	private JPanel scoreboardPanel = new JPanel(null);
	private Timer gameTimer = new Timer(100, this); // tenths of a second
	private JLabel scoreLabel = new JLabel("0");
	private JLabel timerLabel = new JLabel("0");
	
	// create a maze panel and grid to position objects
	private JPanel mazePanel = new JPanel(new GridBagLayout());
	private GridBagConstraints constraints = new GridBagConstraints();
	private Cell[][] maze = new Cell[NUM_CELLS_WIDTH][NUM_CELLS_HEIGHT];
	
	// coin and time variables
	private int numCoins = 0;
	private double time = 0;
	
	// cell objects for portals
	Cell portal;
	Cell portal2;
	
	// constructor method
	public MazeRaceGUIMario(Player player) {
		
		// set player
		this.player = player;
		
		// setup all game components
		scoreboardPanelSetup();
		mazePanelSetup();
		frameSetup();
		
	}
	
	// set dimensions and create layout of scoreboard
	private void scoreboardPanelSetup() {

		// set dimensions of scoreboard panel
		scoreboardPanel.setLayout(null);
		scoreboardPanel.setBounds(0, 0, CELL_SIZE*NUM_CELLS_WIDTH, 50);
		scoreboardPanel.setBackground(Color.yellow);
		
		// set dimensions of score and timer labels
		scoreLabel.setBounds(scoreboardPanel.getWidth()/2, 0, 100, 25);
		timerLabel.setBounds(scoreboardPanel.getWidth()/2, scoreboardPanel.getHeight()/2, 100, 25);
		
		// sdd labels to panel
		scoreboardPanel.add(scoreLabel);
		scoreboardPanel.add(timerLabel);
		
	}
	
	// set dimensions and create layout of maze
	private void mazePanelSetup() {

		// set dimensions of maze
		mazePanel.setBounds(0, 50, CELL_SIZE*NUM_CELLS_WIDTH, CELL_SIZE*NUM_CELLS_HEIGHT);
		
		// add objects to maze (methods)
		loadMaze();
		placeCoins();
		placePortals();
		placePlayer();
		
	}

	// set dimensions and create layout of game frame
	private void frameSetup() {

		// set title and size of frame
		setTitle("Malcolm's Maze Race");
		setSize(mazePanel.getWidth(), scoreboardPanel.getHeight() + mazePanel.getHeight() + CELL_SIZE);
		setLayout(null);
		
		// add panels
		add(scoreboardPanel);
		add(mazePanel);
		
		// add keyListener to frame
		addKeyListener(this);
		
		// centre frame on screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		
		// keep game frame a certain size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		// start game timer
		gameTimer.start();
		
	}
	
	// reads text file
	private void loadMaze() {

		int row = 0; // row index
		char[] line; // char array for characters in text file
		
		// run with a free "trial"
		try {
			
			Scanner input = new Scanner(new File("maze.txt")); // input text file
			
			// scan each line
			while(input.hasNext()) {
				
				line = input.nextLine().toCharArray();
				
				// fill entire cell with images
				for(int col = 0; col < line.length; col++)
					fillCell(line[col], row, col);
				
				row++;
				
			}
			
			input.close();
			
		} catch (FileNotFoundException error) {
			
			System.out.println("File error: " + error);
			
		}
		
	}
	
	// replaces a character with an image
	private void fillCell(char shape, int row, int col) {

		// create new cell
		maze[row][col] = new Cell(row, col);
		
		// set a character to a particular image
		if(shape == 'W')
			maze[row][col].setIcon(WALL);
		else if(shape == 'X')
			maze[row][col].setIcon(OUT_OF_BOUNDS);
		else if(shape == '.')
			maze[row][col].setIcon(PATH);
		else if(shape == 'C')
			maze[row][col].setIcon(COIN);
		
		// grid bag constraints
		constraints.gridx = col;
		constraints.gridy = row;
		mazePanel.add(maze[row][col], constraints);
		
	}

	// places the coins in frame
	private void placeCoins() {
		
		// place coins for how many coins there are
		for(int coin = 1; coin <= NUM_COINS; coin++) {
			
			Cell cell = findEmptyCell();
			
			maze[cell.getRow()][cell.getCol()].setIcon(COIN);
			
		}
		
	}
	
	// places the portals in frame
	private void placePortals() {
		
		// find an empty cell for two portals
		portal = findEmptyCell();
		portal2 = findEmptyCell();
		
		// set portal icons at their grid coordinates
		maze[portal.getRow()][portal.getCol()].setIcon(PORTAL);	
		maze[portal2.getRow()][portal2.getCol()].setIcon(PORTAL2);
	}
	
	// places the player in frame
	private void placePlayer() {

		Cell cell = findEmptyCell();
		
		// set grid coordinates of player
		player.setRow(cell.getRow());
		player.setCol(cell.getCol());
		
		// set icon of player
		maze[player.getRow()][player.getCol()].setIcon(player.getIcon());
		
	}
	
	// finds an empty cell to place objects
	private Cell findEmptyCell() {

		Cell cell = new Cell(0,0); // create a new cell
		
		// random number generator for grid coordinates
		do {
			
			cell.setRow((int)(Math.random()*24) + 2);
			cell.setCol((int)(Math.random()*24) + 2);
			
		} while(maze[cell.getRow()][cell.getCol()].getIcon() != PATH);
		
		return cell;
		
	}
	
	// key is generated as a unicode character
	@Override
	public void keyTyped(KeyEvent key) {}
	
	// key is pressed
	@Override
	public void keyPressed(KeyEvent key) {
		
		// move player up when "up" is pressed
		if(key.getKeyCode()==KeyEvent.VK_UP && maze[player.getRow()-1][player.getCol()+0].getIcon() != WALL) {
			player.setIcon(new ImageIcon("images/mario0.gif"));
			try {
				movePlayer(-1, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// move player right when "right" is pressed
		else if(key.getKeyCode()==KeyEvent.VK_RIGHT && maze[player.getRow()+0][player.getCol()+1].getIcon() != WALL) {
			player.setIcon(new ImageIcon("images/mario1.gif"));
			try {
				movePlayer(0, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// move player down when "down" is pressed
		else if(key.getKeyCode()==KeyEvent.VK_DOWN && maze[player.getRow()+1][player.getCol()+0].getIcon() != WALL) {
			player.setIcon(new ImageIcon("images/mario2.gif"));
			try {
				movePlayer(1, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// move player left when "left" is pressed
		else if(key.getKeyCode()==KeyEvent.VK_LEFT && maze[player.getRow()+0][player.getCol()-1].getIcon() != WALL) {
			player.setIcon(new ImageIcon("images/mario3.gif"));
			try {
				movePlayer(0, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	// key is released
	@Override
	public void keyReleased(KeyEvent key) {}
	
	// move player based on changes in x and y coordinates
	private void movePlayer(int dRow, int dCol) throws Exception {

		maze[player.getRow()][player.getCol()].setIcon(PATH);
		
		// if player gets a coin
		if(maze[player.getRow()+dRow][player.getCol()+dCol].getIcon() == COIN) {
			
			soundFX(); // coin sound effect
			numCoins++;
			scoreLabel.setText(Integer.toString(numCoins));  // change score label
			
			if(numCoins == NUM_COINS) { // if all coins are collected
				
				gameTimer.stop(); // stop game timer
				
				// display option dialog to ask to play again
				int choice = JOptionPane.showOptionDialog(null, "Time: " + time/10 + "s\nPlay again?", "WINNER!", 
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				
				// if yes, play again
				if(choice == JOptionPane.YES_OPTION) {
					setVisible(false);
					new MazeRaceGUIMario(player);
				}
				
				// if no, return to home screen
				else {
					setVisible(false);
					new MazeRaceSplash();
				}
			}
		}
		
		// if player enters a portal
		else if(maze[player.getRow()+dRow][player.getCol()+dCol].getIcon() == PORTAL) {
			
			// set player coordinates to the other portal
			player.setRow(portal2.getRow());
			player.setCol(portal2.getCol());
		}
		
		// if player enters the other portal
		else if(maze[player.getRow()+dRow][player.getCol()+dCol].getIcon() == PORTAL2) {
			
			// set player coordinates to the first portal
			player.setRow(portal.getRow());
			player.setCol(portal.getCol());
		}
		
		player.move(dRow, dCol);
		maze[player.getRow()][player.getCol()].setIcon(player.getIcon());
		
	}
	
	// plays coin sound FX
	private static void soundFX() throws Exception {
		
		File soundFile = new File("coin.wav"); // import sound file
		
		Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class)); // clip audio
		
		// stream, open and start audio
		AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);	
		clip.open(ais);
		clip.start();
		
	}

	// actionListener for timer
	@Override
	public void actionPerformed(ActionEvent event) {

		// increment game timer and print in seconds
		if(event.getSource() == gameTimer) {
			time++;
			timerLabel.setText(Double.toString(time/10));
		}
		
	}

}
