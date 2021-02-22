import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;

/* Malcolm Shi
 * May 19, 2019
 * ICS3U1
 * MazeRace is an original game whereby the object of the game is to race your character around a maze attempting to 
 * collect gold coins.  The game begins by placing the character randomly in a maze with a number of randomly placed 
 * gold coins.  The player(s) uses the keyboard to navigate through the maze and reach the coins.  The game currently 
 * has one mode: a single player game - where the goal is to race against the clock to see how fast you can capture 
 * all the coins.
 * 
 * Extra features:
 * - Background music
 * - Splash screen: coloured background, Mario and Sonic GIFs, coin GIFs, additional fonts
 * - Mario and Sonic player options
 * - Red theme for Mario, blue theme for Sonic
 * - Time is displayed in tenths of a second
 * - Player faces the right direction
 * - Sound effect when player collects coin
 * - Portals: player teleports to the other portal when entered
 * - Pop-up when player wins the game
 * - Scoreboard panel - coloured background
 * 
 * Areas of concern:
 * - Portals may not function correctly - depends on layout
 * - Wanted to add gold rings for Sonic mode, but I was not able to scale image correctly
 * 		- Would have added sonic ring sound FX as well
 * 
 * If I had more time, I would have added more levels, more modes, and more objects like something to stop the time for
 * a few seconds.  I would have also added a leaderboards screen displaying the fastest times.
 */


// Runs the program
public class MazeRaceApplication {
	
	// Main method - opens the GUI
	public static void main(String[] args) throws Exception {

		// open splash screen
		new MazeRaceSplash();
		
		// play background music throughout whole program
		backgroundMusic();

	}
	
	// Reads, clips, and plays music file
	private static void backgroundMusic() throws Exception {
			
		File soundFile = new File("backgroundMusic.wav"); // import sound file
		
		Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class)); // clip audio
		
		// stream, open and start audio
		AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);	
		clip.open(ais);
		clip.start();
		
	}

}
