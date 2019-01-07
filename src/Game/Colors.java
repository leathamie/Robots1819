package Game;


/**
 * 
 * Colors class
 *
 */
public class Colors {
	private boolean initColors;
	private Color[] colors;
	
	/**
	 * Colors constructor
	 */
	public Colors() {
		this.initColors = false;
		this.colors = new Color[Parameters.COLORS.length];
	}

	/**
	 * Function isInitColors
	 * allows to know if the initisalization of the colors is over
	 * @return boolen
	 */
	public boolean isInitColors() {
		return initColors;
	}

	/**
	 * Function setInitColors
	 * allows to set the boolean initColors to true if the initialization is over
	 */
	public void setInitColors() {
		this.initColors = true;
	}

	/**
	 * Function getColors
	 * @return Color[]
	 */
	public Color[] getColors() {
		return colors;
	}

	/**
	 * Function setColors
	 * @param colors
	 * Set the variable colors with an array of colors
	 */
	public void setColors(Color[] colors) {
		this.colors = colors;
	}
	
	/**
	 * Function setColors
	 * @param i
	 * @param c
	 * Set the variable colors with a position i and a color c
	 */
	public void setColor(int i, Color c) {
		this.colors[i] = c;
	}
	
	
	/**
	 * Function getColor
	 * @param color
	 * @return Color
	 */
	public Color getColor(String color){
		// if all colors are initialized in the array
		if (this.initColors) {
			// for each color in the array
			for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
				// if the color in the array is the same as the string parameter 
				if (color == Parameters.COLORS[i]) {
					// we return this color
					return this.colors[i];
				}
			}return null;
		}return null;
	}
	

	/** J'AI PAS COMPRIS CE QUE CA FAIT 
	 * Function setColorName
	 * @param c
	 * @return boolean
	 */
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
