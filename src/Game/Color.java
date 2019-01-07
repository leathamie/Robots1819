package Game;

/**
 * 
 * Color class
 *
 */
public class Color {
	private float[] rgb = new float[3];
	private String name = null;
	
	/**
	 * Color constructor
	 */
	public Color() {
		this.setRgb(new float[3]);
		this.name = "";
	}
	/*
	public Color(float r, float g, float b) {
		this.rgb[0] = r;
		this.rgb[1] = g;
		this.rgb[2] = b;
	}
	public Color(int[] c) {
		this.rgb[0] = (float)(c[0]);
		this.rgb[1] = (float)(c[1]);
		this.rgb[2] = (float)(c[2]);
	}
	*/
	
	/**
	 * Color constructor
	 * @param c
	 * Create a color using an rgb code contained in an array of float
	 */
	public Color(float[] c) {
		this.rgb[0] = c[0]*1000;
		this.rgb[1] = c[1]*1000;
		this.rgb[2] = c[2]*1000;
		
	}
	
	/**
	 * Color constructor
	 * @param c
	 * @param n
	 * Create a color using an rgb code contained in an array of float and a string used for the name
	 */
	public Color(float[] c, String n) {
		this.rgb[0] = c[0]*1000;
		this.rgb[1] = c[1]*1000;
		this.rgb[2] = c[2]*1000;
		this.name = n;
	}

	
	/**
	 * Function getRgb
	 * @return float[]
	 * allows to get the rgb code of a color
	 */
	public float[] getRgb() {
		return rgb;
	}

	/**
	 * Function setRgb
	 * @param rgb
	 * allows to modify the rgb code of a color
	 */
	public void setRgb(float[] rgb) {
		this.rgb = rgb;
	}
	
	/**
	 * Function getRgb
	 * @param i
	 * @return int
	 * returns nly one part of the rgb code of a color 
	 */
	public int getRgb(int i) {// i= 0 r ; i=1 g ; i=2 b 
		return (int)this.rgb[i];
	}
	
	/**
	 * Function equals
	 * @return boolean
	 * @param o
	 */
	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of Color or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Color)) { 
            return false; 
        } 
        // typecast o to Color so that we can compare data members  
        Color c = (Color) o; 
		for (int i = 0 ; i<3 ; i++) {
			if ((c.getRgb(i) < this.rgb[i]-(0.5*this.rgb[i])) || (c.getRgb(i) > this.rgb[i]+(0.5*this.rgb[i]))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Function toString
	 * @return String
	 */
	@Override
	public String toString() {
		return "R: "+ (int)this.rgb[0] + ";G: " + (int)this.rgb[1] + ";B: " + (int)this.rgb[2] + ";";	
	}
	
	
	/** J'AI COMPRIS EN GROS CE QUE CA FAIT MAIS JE SAIS PAS COMMENT L'EXPLIQUER 
	 * Function quickEquals
	 * @param c
	 * @return boolean
	 */
	public boolean quickEquals(float[] c) {
		for (int i = 0 ; i<3 ; i++) {
			if ((c[i] < this.rgb[i]-(0.5*this.rgb[i])) || (c[i] > this.rgb[i]+(0.5*this.rgb[i]))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Function getName
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Function setName
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Function isBorder
	 * @return boolean
	 * check if the scanned color is black, if yes it returns true, if not it returns false
	 */
	public boolean isBorder(){
		if(this.name != null) {
			return false;
		}if (this.name.equals(Parameters.BORDERCOLOR)){
			return true; 
		}return false;
	}
	

}
