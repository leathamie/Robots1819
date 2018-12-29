package Game;

// hashset de cases ?


public class Board {
	private Color[][] board;
	private boolean initBoard;
	
	public Board() {
		this.board = new Color[Parameters.BOARD_WIDTH][Parameters.BOARD_LENGTH];
		this.initBoard = false;
	}
	
	public void setCell(int x, int y, Color c) {
		this.board[x][y] = c;
	}
	
	public Color getCell (int x, int y) {
		return this.board[x][y];
	}
	
	public void setInitBoard() {
		this.initBoard = true;
	}

	public boolean isInitBoard() {
		return this.initBoard;
	}
}
