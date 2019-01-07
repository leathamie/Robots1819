package Game;

// x, y, Color ? 

/**
 * Cell class
 *
 */
public class Cell {
	private int x; 
	private int y;
	private Color c;
	
	/**
	 * Cell constructor
	 */
	public Cell() {
		this.x = 0;
		this.y = 0;
		this.c = new Color();
	}
	
	/**
	 * Cell constructor
	 * @param abs
	 * @param ord
	 * @param color
	 */
	public Cell(int abs, int ord, Color color) {
		this.x = abs;
		this.y = ord;
		this.c = color;
	}
	

	/**
	 * Function getX
	 * @return int
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Function setX
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Function getY
	 * @return int
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Function setY
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Function getColor
	 * @return Color
	 */
	public Color getColor() {
		return this.c;
	}
	
	/**
	 * Function setColor
	 * @param c
	 */
	public void setColor(Color c) {
		this.c = c;
	}
	
}
