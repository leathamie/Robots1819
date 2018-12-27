package Behaviors;

import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
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
		robot.moveForward();
		
		if (robot.colorChange()) {
			Color c = new Color(robot.getActualRGB());
			this.colors.setColorName(c);
			if (c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
				this.crossedColors += 1;
				robot.lineCrossed();
				Delay.msDelay(1000);
			}
			LCD.drawString(this.crossedColors + " detected as" + c.getName(), 0, 3 );
			
		}
		
		if(this.crossedColors == Parameters.BOARD_LENGTH) {
			this.crossedColors = 0; 
			if(this.robot.getX()%2 == 0) {
				robot.goTo(Parameters.RIGHT);
				robot.goTo(Parameters.RIGHT);
			}else {
				robot.goTo(Parameters.LEFT);
				robot.goTo(Parameters.LEFT);
			}
		}
		LCD.drawString(this.robot.getDirection() + "pos" + this.robot.getX() + "," + this.robot.getY(), 0, 4 );
		
		
		
		
		
		//LCD.drawString("New Color ! " + this.actualColor.getName(), 0, 1 ); // va peut être poser problème 
		//LCD.drawString("Nb crossed co " + this.crossedColors  , 0, 2 );
		
	}

	@Override
	public void suppress() {
		robot.stop();
	}
	 

	

}
