package Project;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Twister {
	
	private Colors colors;
	private Robot r;
	
	public Twister() {
		this.colors = new Colors();
		this.r = new Robot();
	}
	
	public void play() {
		LCD.drawString("Click when you are ready", 0, 3 );
		Button.waitForAnyPress();
		LCD.clear();
		
		/*
		//Create Behaviors class
		MoveForward moveForward = new MoveForward(captColor, colors, sample); // Avancer
		//MoveForward moveForward = new MoveForward();
		SwitchOff swichtOff = new SwitchOff(this.captColor, this.pilot); //Stop
		InitColors ic = new InitColors(captColor, colors);
		//DetectColors dc = new DetectColors(captColor, colors, sample);
		
		//lauch
		//Behavior[] bArray = {moveForward, dc, ic, swichtOff}; // du moins prioritaire au plus prioritaire
		Behavior[] bArray = {moveForward, ic, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
		*/
		
	}
	
}