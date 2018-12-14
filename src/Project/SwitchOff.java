package Project;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class SwitchOff implements Behavior{
	
	private EV3ColorSensor capteur;
	private Arbitrator arb;
	private MovePilot pilot;


	public SwitchOff(EV3ColorSensor cs, MovePilot p){
		this.capteur = cs;
		this.pilot = p;
	}
	public SwitchOff(MovePilot p){
		this.pilot = p;
	}

	public void setArbitrator(Arbitrator a){
		this.arb = a;
	}


	public boolean takeControl(){
		return Button.RIGHT.isDown();
	}
	

	public void action(){
		LCD.clear();
		LCD.drawString("EndProcess", 1, 0);
		LCD.refresh();
		//le robot ne bouge plus
		pilot.stop();
		//on eteint les capteurs
		capteur.close();

		arb.stop();
		System.exit(0);
	}


	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}



}