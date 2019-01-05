package Game;

public interface Parameters {
	static final String BORDERCOLOR = "black";
	static final String[] COLORS = {"red","green","blue","brown",BORDERCOLOR};
	static final int BOARD_WIDTH = 5;
	static final int BOARD_LENGTH = 7;
	static final int CELL_SIZE = 8; // approximate value in cm
	
	static final int UP = 1;
	static final int DOWN = -1; 
	static final int RIGHT = 2;
	static final int LEFT =-2;

	

}