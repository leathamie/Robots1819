package Game;

public class Color {
	private float[] rgb = new float[3];
	private String name = null;
	
	
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
	public Color(float[] c) {
		this.rgb[0] = c[0]*1000;
		this.rgb[1] = c[1]*1000;
		this.rgb[2] = c[2]*1000;
		
	}
	
	public Color(float[] c, String n) {
		this.rgb[0] = c[0]*1000;
		this.rgb[1] = c[1]*1000;
		this.rgb[2] = c[2]*1000;
		this.name = n;
	}

	public float[] getRgb() {
		return rgb;
	}

	public void setRgb(float[] rgb) {
		this.rgb = rgb;
	}
	
	public int getRgb(int i) {// i= 0 r ; i=1 g ; i=2 b 
		return (int)this.rgb[i];
	}
	
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
	
	@Override
	public String toString() {
		return "R: "+ (int)this.rgb[0] + ";G: " + (int)this.rgb[1] + ";B: " + (int)this.rgb[2] + ";";	
	}
	
	public boolean quickEquals(float[] c) {
		for (int i = 0 ; i<3 ; i++) {
			if ((c[i] < this.rgb[i]-(0.5*this.rgb[i])) || (c[i] > this.rgb[i]+(0.5*this.rgb[i]))) {
				return false;
			}
		}
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBorder(){
		if(this.name != null) {
			return false;
		}if (this.name.equals(Parameters.BORDERCOLOR)){
			return true; 
		}return false;
	}
	

}
