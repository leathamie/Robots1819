package Game;

import java.awt.List;
import java.util.ArrayList;

import AStar.Node;

// hashset de cases ?

/**
 * 
 * Board class
 *
 */
public class Board {
	private Color[][] board;
	private boolean initBoard;
	
	/**
	 * Board constructor
	 */
	public Board() {
		this.board = new Color[Parameters.BOARD_WIDTH][Parameters.BOARD_LENGTH];
		this.initBoard = false;
	}
	
	/**
	 * Function setCell
	 * allows to set a specific color to a specific position on the board
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setCell(int x, int y, Color c) {
		this.board[x][y] = c;
	}
	
	/**
	 * Function getCell
	 * allows to get which color is at a specific position on the board
	 * @param x
	 * @param y
	 * @return Color
	 */
	public Color getCell (int x, int y) {
		return this.board[x][y];
	}
	
	
	/**
	 * Function setInitBoard
	 * allows to set the boolean initBoard to true
	 * meaning that the board is set, all of its cells has a color
	 */
	public void setInitBoard() {
		this.initBoard = true;
	}

	/**
	 * Function isInitBoard
	 * allows to know if the initialisation of the board is over
	 * @return boolean
	 */
	public boolean isInitBoard() {
		return this.initBoard;
	}
	
	/**
	 * Function getLengthX 
	 * @return int
	 */
	public int getLengthX() {
		return this.board.length;
	}
	
	/**
	 * Function getLenghY
	 * @return int
	 */
	public int getLengthY() {
		return this.board[0].length;
	}
	
	

	public java.util.List<int[]> getAllPosition(Color c){
		java.util.List<int[]> positions = null;
		for (int x =0 ; x < this.board.length ; x ++ ) {
			for (int y = 0 ; y < this.board[0].length ; y++) {
				if (this.board[x][y].equals(c)) {
					int[]pos = {x,y};
					positions.add(pos);
				}
			}
		}
		return positions;
	}
	
	
}
