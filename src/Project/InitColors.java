package Project;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class InitColors implements Behavior{

	EV3ColorSensor cs;
	Colors colors;
	
	//Constructor
	public InitColors(EV3ColorSensor colorSensor, Colors c){
		this.cs = colorSensor;
		this.colors = c;
	}



	@Override
	public boolean takeControl() {
		return !this.colors.isInitColors();
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
			LCD.drawString("RGB found : " + vals[0] + " ; " + vals[1] + " ; " + vals[2], 0, 3);
			Button.ENTER.waitForPressAndRelease();
			LCD.clear();
			
			//Color c = new Color(vals, Parameters.COLORS[i]);
			Color c = new Color(vals);
			c.setName(Parameters.COLORS[i]);
			this.colors.setColor(i,c);
			
			
			LCD.clear();
			LCD.drawString(c.toString(), 0, 3 );
			Button.ENTER.waitForPressAndRelease();
		}
		this.colors.setInitColors();		
		
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		LCD.clear();
		LCD.drawString("Ready ? Press 'center'", 0, 0);
		
		
		
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
