package Behaviors;

import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * InitColors class
 * implements the Behavior interface
 */
public class InitColors implements Behavior{

	
	Robot robot;
	Colors colors;
	
	/**
	 * InitColors constructor
	 * @param r
	 * @param c
	 */
	public InitColors(Robot r, Colors c){
		this.robot = r;
		this.colors = c;
	}


	/**
	 * behavior function
	 * Function that dictate when the behavior is launched
	 */ 
	@Override
	public boolean takeControl() {
		// the behavior is launched when all colors are set AND when the current robot is the robot1
		return !this.colors.isInitColors() && this.robot.isFirstPlayer();
	}

	/**
	 * behavior function 
	 * Function that dictate what the program is supposed to do when the behavior is launched
	 */
	@Override
	public void action() {
		// we clear the screen of previous writing
		LCD.clear();
		
		// for each color in the constant COLORS
		for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
			
			// we ask the user to scan a specific color
			LCD.drawString("Montre moi du "+Parameters.COLORS[i], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			
			//Color c = new Color(vals, Parameters.COLORS[i]);
			
			// scan and give the rgb code to an new Color object
			Color c = new Color(robot.captureRGB());
			// give the color its name according to the rgb code
			c.setName(Parameters.COLORS[i]);
			this.colors.setColor(i,c);
			
			
			LCD.clear();
			LCD.drawString(c.toString(), 0, 3 );
			Button.ENTER.waitForPressAndRelease();
		}
		
		/*
		// permet de tester la reconnaissance des couleurs 
		for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
			LCD.drawString("Test du "+Parameters.COLORS[i], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			echantillon = this.cs.getRGBMode();//utilise le mode rgb ; on peut peut etre le mettre à l'exterieur de la classe
			float[] vals = new float[3]; // pour utiliser le rgb
			echantillon.fetchSample(vals, 0);

			
			Color c = new Color(vals);
			this.colors.setColorName(c);
			
			
			LCD.clear();
			LCD.refresh();
			LCD.drawString("detected as" + c.getName(), 0, 3 );
			Button.ENTER.waitForPressAndRelease();
		}
		*/
		
		// set the initColors variable to true
		// meaning all the colors are set and we can pass to the next stage of the program
		this.colors.setInitColors();
		
		
		
	}

	/**
	 * behavior function
	 * Function that dictate what to do when the behavior stops
	 */
	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		LCD.clear();
		LCD.refresh();
		
	}
	
	
	/*
	public float[] getColorRGB() {
		SampleProvider echantillon = this.cs.getRGBMode();//utilise le mode rgb
		float[] vals = new float[3]; // pour utiliser le rgb
		String couleurs[] = new String[] {"Rouge" ,"Vert","Bleu"};
		echantillon.fetchSample(vals, 0);
		for (int i = 0 ; i< couleurs.length; i++) {
			LCD.drawString(couleurs[i]+":"+vals[i]*1000, i, 0);
		}
		
		this.cs.close();
		return vals;
	}
	*/


}
