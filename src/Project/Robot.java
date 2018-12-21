package Project;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Robot {
	private int x; 
	private int y; 
	private int direction;
	private int speed = 40;
	
	private Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	private Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	private Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},2);
	private MovePilot pilot = new MovePilot(chassis);
	private EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
	private SampleProvider sample = captColor.getRGBMode();
	
	public Robot() {
		this.setX(0); 
		this.setY(0); 
		this.setDirection(Parameters.UP);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int d) {
		if(d == Parameters.LEFT || d == Parameters.RIGHT || d == Parameters.UP || d == Parameters.DOWN) {
			this.direction = d;
		}
		
	}
	public void moveForward() {
		Motor.B.setSpeed(40);
		Motor.C.setSpeed(40);
		Motor.B.forward();
		Motor.C.forward();
	}
	public float[] captureRGB() {
		float[] vals = new float[3]; // pour utiliser le rgb
		sample.fetchSample(vals, 0);
		return vals;
	}
	
	
	/*
	 * Prends en parametre une direction sous forme d'entier et se déplace dans la direction appropriée 
	 */
	public void goTo(int direct) {
		if (direct == Parameters.LEFT) {
			this.moveLeft();
		}else if(direct == Parameters.RIGHT) {
			this.moveRight();
		}else if (direct == Parameters.DOWN) {
			this.moveDown();
		}else {
			this.moveUp();
		}
	}
	
	public void moveUp() {
		Motor.B.setSpeed(this.speed);
		Motor.C.setSpeed(this.speed);
		Motor.B.forward();
		Motor.C.forward();
		
		
	}
	public void moveDown() {
		Motor.B.setSpeed(this.speed);
		Motor.C.setSpeed(this.speed);
		Motor.B.forward();
		Motor.C.forward();
	}
	public void moveLeft() {
		
	}
	
	public void moveRight() {
		
	}
	
	public void stop() {
		Motor.B.stop(true);
		Motor.C.stop(true);
	}
	
	public void closeSensors() {
		
	}
	

}
