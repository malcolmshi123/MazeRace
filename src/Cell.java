import javax.swing.JLabel;

// Cell object
public class Cell extends JLabel {
	
	// fields
	private int row;
	private int col;
	
	// empty constructor
	public Cell() {}

	// constructor with arguments
	public Cell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	// getter and setter pairs
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	// toString method
	@Override
	public String toString() {
		return "Cell [row=" + row + ", col=" + col + "]";
	}

}
