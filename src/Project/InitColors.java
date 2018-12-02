package Project;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class InitColors implements Behavior{

	EV3ColorSensor cs;
	
	//constructeur
	public InitColors(EV3ColorSensor colorSensor) {
		this.cs = colorSensor;
	}



	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return Main.initColors;
	}

	@Override
	public void action() {
		
		LCD.clear();
		SampleProvider echantillon;
		
		for(int i = 0 ; i < Parameters.COLORS.length ; i++) {
			LCD.drawString("Montre moi du "+Parameters.COLORS[i], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			echantillon = this.cs.getRGBMode();//utilise le mode rgb ; on peut peut etre le mettre à l'exterieur de la classe
			float[] vals = new float[3]; // pour utiliser le rgb
			echantillon.fetchSample(vals, 0);
			
			LCD.clear();
			LCD.drawString(vals[0]+" ; " + vals[1]+" ; "+vals[2], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			
			LCD.clear();
			float v1 = vals[0]*1000;
			int v1i = (int)v1;
			LCD.drawString("avant" + v1 + "apres" + v1i , 0, 3 );
			LCD.drawString("apres" + v1i , 0, 4 );
			Button.ENTER.waitForPressAndRelease();
			Main.colors[i][0] = (int)vals[0]*1000;
			Main.colors[i][1] = (int)vals[1]*1000;
			Main.colors[i][2] = (int)vals[2]*1000;
			LCD.clear();
			LCD.drawString(Main.colors[i][0] + " ; " + Main.colors[i][1] + " ; " + Main.colors[i][2], 0, 3 );
			Button.ENTER.waitForPressAndRelease();
		}
		Main.initColors = false;
		
		
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
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


}
