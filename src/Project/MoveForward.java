package Project;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class MoveForward implements Behavior{
	float[] actualRGB;
	int crossedColors;
	EV3ColorSensor cs;
	Colors colors;
	SampleProvider sample;
	
	
	
	
	public MoveForward(EV3ColorSensor colorSensor, Colors c, SampleProvider s) {
		this.crossedColors = 0;
		this.cs = colorSensor;
		this.colors = c;
		this.sample = s;
		this.sample = this.cs.getRGBMode();
		this.actualRGB = new float[3];
		this.actualRGB[0] = 0;
		this.actualRGB[1] = 0;
		this.actualRGB[2] = 0;
	}
	
	
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		//LCD.clear();
		//System.out.println("en avant ! ");
		
		Motor.B.setSpeed(40);
		Motor.C.setSpeed(40);
		Motor.B.forward();
		Motor.C.forward();
		
		/*
		float[] vals = new float[3];
		this.sample.fetchSample(vals, 0);
		this.c.setRgb(vals);
		
		colors.setColorName(this.c);
		if (this.c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
			this.crossedColors += 1;
		}
		System.out.println(this.crossedColors +"New Color ! " + this.c.getName());
		*/
		
		
		float[] vals = new float[3]; // pour utiliser le rgb
		sample.fetchSample(vals, 0);

		
		Color c = new Color(vals);
		this.colors.setColorName(c);
		
		if (!this.quickEquals(this.actualRGB, vals)) {
			this.actualRGB = vals;
			if (c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
				this.crossedColors += 1;
				Delay.msDelay(1000);
			}
			
		}
		LCD.drawString(this.crossedColors + " detected as" + c.getName(), 0, 3 );
		
		
		
		//LCD.drawString("New Color ! " + this.actualColor.getName(), 0, 1 ); // va peut être poser problème 
		//LCD.drawString("Nb crossed co " + this.crossedColors  , 0, 2 );
		
	}

	@Override
	public void suppress() {
		Motor.B.stop(true);
		Motor.C.stop(true);
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
