package Game;

// x, y, Color ? 
public class Cell {
	private int x; 
	private int y;
	private Color c;
	
	public Cell() {
		this.x = 0;
		this.y = 0;
		this.c = new Color();
	}
	public Cell(int abs, int ord, Color color) {
		this.x = abs;
		this.y = ord;
		this.c = color;
	}
	

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return this.c;
	}
	public void setColor(Color c) {
		this.c = c;
	}
	
}
