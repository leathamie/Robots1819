package Game;

/**
 * 
 * Parameters interface
 *
 */
public interface Parameters {
	
	// constant that indicate the color of the border
	static final String BORDERCOLOR = "black";
	
	// constant that contain all of the detectable color on the board
	static final String[] COLORS = {"red","green","blue","brown",BORDERCOLOR};
	
	// constant that indicate the width of the board
	static final int BOARD_WIDTH = 5;
	
	// constant that indicate the length of the board
	static final int BOARD_LENGTH = 7;
	
	// approximate value in cm
	static final int CELL_SIZE = 8; 
	
	static final int UP = 1;
	static final int DOWN = -1; 
	static final int RIGHT = 2;
	static final int LEFT =-2;

	static final int WAITINGDURATION = 300;
	
	static final int ROBOTCELLWIDTH = 2;
	static final int ROBOTCELLLENGTH = 3;
	

}
