package Project;

public class Colors {
	private boolean initColors;
	private Color[] colors;
	
	public Colors() {
		this.initColors = false;
		this.colors = new Color[Parameters.COLORS.length];
	}

	public boolean isInitColors() {
		return initColors;
	}

	public void setInitColors() {
		this.initColors = true;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}
	
	public void setColor(int i, Color c) {
		this.colors[i] = c;
	}
	
	public Color getColor(String color){
		if (this.initColors) {
			for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
				if (color == Parameters.COLORS[i]) {
					return this.colors[i];
				}
			}return null;
		}return null;
	}
	

	public boolean setColorName(Color c) {
		boolean set = false;
		c.setName("White");
		for (int i = 0 ; i<this.colors.length ; i++) {
			if (colors[i].equals(c)) {
				c.setName(colors[i].getName());
				set = true;
			}
		}
		return set;
	}
}
