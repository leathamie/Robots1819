package Project;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
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
		
		Button.waitForAnyPress();
		MoveForward moveForward = new MoveForward(); // Avancer
		SwitchOff swichtOff = new SwitchOff(); //Stop
		Behavior[] bArray = {moveForward, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
	}

}
