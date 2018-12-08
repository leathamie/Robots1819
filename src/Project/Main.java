package Project;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {

	public static Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	public static Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	public static Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},2);
	public static MovePilot pilot = new MovePilot(chassis);
	
	
	
	
	public static void main(String[] args) {
		//create variables
		EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
		Colors colors = new Colors(); 
		
		
		LCD.drawString("Click when you are ready", 0, 3 );
		Button.waitForAnyPress();
		LCD.clear();
		
		//Create Behaviors class
		MoveForward moveForward = new MoveForward(); // Avancer
		SwitchOff swichtOff = new SwitchOff(); //Stop
		InitColors ci = new InitColors(captColor, colors);
		
		//lauch
		Behavior[] bArray = {moveForward, ci, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
	}

}
