package Project;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import td3.Arreter;
import td3.Avancer;
import td3.Eviter;

public class Main {

	public static void main(String[] args) {
		
		EV3ColorSensor c = new EV3ColorSensor(SensorPort.S3);
		// TODO Auto-generated method stub
		Button.waitForAnyPress();
		

	}

}
