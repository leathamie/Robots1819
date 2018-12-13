package Project;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectColors implements Behavior {
	EV3ColorSensor cs;
	Color actualColor;
	Colors colors;
	int crossedColors;
	SampleProvider sample;
	float[] foundColors = new float[3]; 
	
	
	public DetectColors(EV3ColorSensor colorSensor, Colors c, SampleProvider s){
		this.actualColor = null;
		this.crossedColors = 0;
		this.cs = colorSensor;
		this.colors = c;
		this.sample = s;
		this.sample = this.cs.getRGBMode();
	}
	
	@Override
	public boolean takeControl() {
		if(this.colors.isInitColors()) {
			this.sample.fetchSample(this.foundColors, 0);
			if (this.actualColor == null) {
				this.actualColor = new Color(this.foundColors); 
				return false;
			}else {
				if (this.actualColor.quickEquals(this.foundColors)) {
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
		LCD.clear();
		LCD.drawString("I'm in!" + this.actualColor.getName(), 0, 1 );
		this.actualColor.setRgb(this.foundColors);
		if (this.actualColor.equals(colors.getColor(Parameters.BORDERCOLOR))) {
			this.crossedColors += 1;
		}
		LCD.drawString("New Color !" + this.actualColor.getName(), 0, 1 ); // va peut être poser problème 
		LCD.drawString("Nb crossed co " + this.crossedColors  , 0, 2 );
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
	
		

}
