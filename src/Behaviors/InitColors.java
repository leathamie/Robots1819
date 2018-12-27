package Behaviors;

import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class InitColors implements Behavior{

	Robot robot;
	Colors colors;
	
	//Constructor
	public InitColors(Robot r, Colors c){
		this.robot = r;
		this.colors = c;
	}



	@Override
	public boolean takeControl() {
		return !this.colors.isInitColors();
	}

	@Override
	public void action() {
		LCD.clear();
		
		for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
			LCD.drawString("Montre moi du "+Parameters.COLORS[i], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			
			//Color c = new Color(vals, Parameters.COLORS[i]);
			Color c = new Color(robot.captureRGB());
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
		
		this.colors.setInitColors();
		
		
		
	}

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
