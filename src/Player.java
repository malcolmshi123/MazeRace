import javax.swing.ImageIcon;

// Player object
public class Player extends Cell {
	
	// sets player icon using its file name
	public Player(String fileName) {
		
		setIcon(new ImageIcon(fileName));
		
	}
	
	// moves player based on initial and change in position
	public void move(int dRow, int dCol) {
		
		setRow(getRow() + dRow);
		setCol(getCol() + dCol);
		
	}
	
	

}
