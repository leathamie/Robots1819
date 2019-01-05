package Game;

import java.awt.List;
import java.util.ArrayList;

import AStar.Node;

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
	public int getLengthX() {
		return this.board.length;
	}
	public int getLengthY() {
		return this.board[0].length;
	}
	
	public ArrayList<Integer[]> getAllPosition(Color c){
		ArrayList<Integer[]> positions  = new ArrayList<Integer[]>();
		for (int x =0 ; x < this.board.length ; x ++ ) {
			for (int y = 0 ; y < this.board[0].length ; y++) {
				if (this.board[x][y].equals(c)) {
					Integer []pos = {x,y};
					positions.add(pos);
				}
			}
		}
		return positions;
	}
	
}
