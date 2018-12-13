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
	private Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	private Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	private Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},2);
	private MovePilot pilot = new MovePilot(chassis);
	
	private EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
	private Colors colors = new Colors();
	private SampleProvider sample = captColor.getRGBMode();
	
	public Twister() {
		
	}
	
	public void play() {
		LCD.drawString("Click when you are ready", 0, 3 );
		Button.waitForAnyPress();
		LCD.clear();
		
		//Create Behaviors class
		MoveForward moveForward = new MoveForward(); // Avancer
		SwitchOff swichtOff = new SwitchOff(this.captColor, this.pilot); //Stop
		InitColors ci = new InitColors(captColor, colors);
		DetectColors dc = new DetectColors(captColor, colors, sample);
		
		//lauch
		Behavior[] bArray = {moveForward, dc, ci, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
	}
	
}
