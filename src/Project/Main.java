package Project;

import lejos.hardware.Button;
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
	public static boolean initColors = true;
	public static int[][] colors = new int[Parameters.COLORS.length][3];
	
	
	
	public static void main(String[] args) {
		EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
		
		
		Button.waitForAnyPress();
		MoveForward moveForward = new MoveForward(); // Avancer
		SwitchOff swichtOff = new SwitchOff(); //Stop
		InitColors ci = new InitColors(captColor);
		Behavior[] bArray = {moveForward, ci, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
	}

}
