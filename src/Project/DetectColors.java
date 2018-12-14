package Project;

//import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectColors implements Behavior {
	EV3ColorSensor cs;
	float[] actualRGB;
	float[] foundRGB; 
	Colors colors;
	int crossedColors;
	SampleProvider sample;
	
	
	
	public DetectColors(EV3ColorSensor colorSensor, Colors c, SampleProvider s){
		this.actualRGB = new float[3];
		this.foundRGB = new float[3];
		this.crossedColors = -1;
		this.cs = colorSensor;
		this.colors = c;
		this.sample = s;
		this.sample = this.cs.getRGBMode();
	}
	
	@Override
	public boolean takeControl() {
		if(this.colors.isInitColors()) {
			this.sample.fetchSample(this.foundRGB, 0);
			if (this.crossedColors == -1) {
				this.actualRGB = this.foundRGB;
				this.crossedColors = 0;
				return false;
			}else {
				if (quickEquals(this.foundRGB,this.actualRGB)) {
					return false;
				}else {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void action() {
		//LCD.clear();
		//LCD.refresh();
		//sample.fetchSample(vals, 0);
		
		this.actualRGB = this.foundRGB;
		Color c = new Color(this.foundRGB);
		this.colors.setColorName(c);
		
		
		if (c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
			this.crossedColors += 1;
		}
		System.out.println("New Color ! " + c.getName()); // va peut être poser problème 
		//LCD.drawString("Nb crossed co " + this.crossedColors  , 0, 2 );
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	private boolean quickEquals(float[] c1, float[] c2 ) {
		for (int i = 0 ; i<3 ; i++) {
			if ((c1[i] < c2[i]-(0.5*c2[i])) || (c1[i] > c2[i]+(0.5*c2[i]))) {
				return false;
			}
		}
		return true;
	}

	
		

}
