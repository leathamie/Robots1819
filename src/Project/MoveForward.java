package Project;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class MoveForward implements Behavior{
	int crossedColors;
	Colors colors;
	Robot robot;
	
	
	
	
	public MoveForward(Robot r, Colors c) {
		this.crossedColors = 0;
		this.colors = c;
		this.robot = r;
	}
	
	
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		//LCD.clear();
		//System.out.println("en avant ! ");
		robot.moveUp();
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
		
		
		if (robot.colorChange()) {
			Color c = new Color(robot.actualRGB);
			this.colors.setColorName(c);
			if (c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
				this.crossedColors += 1;
				robot.lineCrossed();
				Delay.msDelay(1000);
			}
			LCD.drawString(this.crossedColors + " detected as" + c.getName(), 0, 3 );
			
		}
		
		
		
		
		//LCD.drawString("New Color ! " + this.actualColor.getName(), 0, 1 ); // va peut être poser problème 
		//LCD.drawString("Nb crossed co " + this.crossedColors  , 0, 2 );
		
	}

	@Override
	public void suppress() {
		robot.stop();
	}

	

}
