import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Home screen for MazeRace
public class MazeRaceSplash extends JFrame implements ActionListener {
	
	// player object
	private Player player;
	
	// JFrame components
	JPanel splash = new JPanel();
	JPanel GIFPanel = new JPanel();
	JLabel gameLabel = new JLabel("MazeRace");
	JButton marioButton = new JButton("MARIO");
	JButton sonicButton = new JButton("SONIC");
	JButton exitButton = new JButton("Exit");
	
	// coin gifs
	JLabel coinGIF = new JLabel(new ImageIcon(new ImageIcon("images/gold coin.gif").getImage().getScaledInstance
			(225, 225, 0)));
	JLabel ringGIF = new JLabel(new ImageIcon(new ImageIcon("images/gold ring.gif").getImage().getScaledInstance
			(300, 225, 0)));
	
	// player gifs
	JLabel marioGIF = new JLabel(new ImageIcon("images/mario.GIF"));
	JLabel sonicGIF = new JLabel(new ImageIcon("images/sonic.GIF"));
	
	// fonts
	Font font = new Font("Impact", Font.BOLD, 36);
	Font font2 = new Font("Impact", Font.PLAIN, 14);
	
	// constructor
	public MazeRaceSplash() {
		frameSetup();
	}
	
	// setup frame components
	private void frameSetup() {
		
		// splash panel
		splash.setBounds(0, 0, 675, 750);
		splash.setBackground(Color.red);
		splash.setLayout(null);
		
		// gif panel
		GIFPanel.setBounds(125, 300, 425, 200);
		GIFPanel.setBackground(Color.white);
		GIFPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		
		// title
		gameLabel.setBounds(250, 100, 175, 50);
		gameLabel.setFont(font);
		splash.add(gameLabel);
		
		// Mario button
		marioButton.setBounds(100, 600, 75, 50);
		marioButton.setFont(font2);
		marioButton.addActionListener(this);
		splash.add(marioButton);
		
		// Sonic button
		sonicButton.setBounds(300, 600, 75, 50);
		sonicButton.setFont(font2);
		sonicButton.addActionListener(this);
		splash.add(sonicButton);
		
		// exit button
		exitButton.setBounds(500, 600, 75, 50);
		exitButton.setFont(font2);
		exitButton.addActionListener(this);
		splash.add(exitButton);
		
		// coin GIF
		coinGIF.setBounds(0, 25, 225, 225);
		splash.add(coinGIF);
		
		// ring GIF
		ringGIF.setBounds(400, 25, 300, 225);
		splash.add(ringGIF);
		
		// Mario GIF
		marioGIF.setBounds(125, 300, 200, 200);
		GIFPanel.add(marioGIF);
		
		// Sonic GIF
		sonicGIF.setBounds(350, 300, 200, 200);
		GIFPanel.add(sonicGIF);
		
		// add GIF panel to splash panel
		splash.add(GIFPanel);
		add(splash);
		
		// set title and size of frame
		setTitle("Malcolm's Maze Race");
		setSize(675, 750);
		setLayout(null);
		
		// centre frame on screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		
		// keep game frame a certain size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		// if Mario button is clicked
		if(event.getSource() == marioButton) {
			player = new Player("images/mario1.gif"); // player is Mario
			new MazeRaceGUIMario(player); // open Mario GUI
		}
		
		// if Sonic button is clicked
		else if(event.getSource() == sonicButton) {
			player = new Player("images/sonic1.gif"); // player is Sonic
			new MazeRaceGUISonic(player); // open Sonic GUI
		}
		
		// close screen
		setVisible(false);
		
		// quit program if exit button is clicked
		if(event.getSource() == exitButton) {
			System.exit(0);
		}
		
	}

}
