package Project;

import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
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
	
	private EV3 ev3 = null;
	private EV3GyroSensor gyro = null;
   	private SampleProvider gyroSamples = null;
   	private UnregulatedMotor leftMotor = null;
   	private UnregulatedMotor rightMotor = null;
   	private Audio audio = null;
   	float[] angle = { 0.0f };
	float gyroTacho = 0;

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
	public void goTo(int direct) {
		if 
	}
	*/
	public void moveUp() {
		Motor.B.setSpeed(40);
		Motor.C.setSpeed(40);
		Motor.B.forward();
		Motor.C.forward();
		
		
	}
	public void moveDown() {
		Motor.B.setSpeed(40);
		Motor.C.setSpeed(40);
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
	
   public void resetGyro() {
	      if (gyro != null) {
	         Delay.msDelay(1000); //wait until the hands are off the robot
	         
	         gyro.reset();
	         gyroSamples = gyro.getAngleMode();
	         gyroTacho = 0;
	         System.out.println("Gyro is reset");
	      }
	   }
	   
	   public void resetGyroTacho() {
	      gyroTacho += getGyroAngle();
	   }
	   
	   public void run() {
		      Delay.msDelay(500);      
		      LCD.clear();
		      
		      while (true) {
		      
		         LCD.drawString("Rotate?", 0, 0);
		         Button.ENTER.waitForPress();
		         LCD.clearDisplay();
		         
		         leftMotor.setPower(100);
		         rightMotor.setPower(100);
		         //start turning right
		         leftMotor.forward();
		         rightMotor.backward();
		         while (getGyroAngle() < 60) {
		            Thread.yield();
		         }
		         leftMotor.stop();
		         rightMotor.stop();

		         //wait until the robot is really stopped
		         Delay.msDelay(100);
		         
		         if (getGyroAngle() > 90f) {
		            System.out.println("Angle is " + angle[0]);
		            leftMotor.setPower(30);
		            rightMotor.setPower(30);
		            //turn left slowly to correct the rotation angle
		            rightMotor.forward();
		            leftMotor.backward();
		            while (getGyroAngle() > 93) {
		               Thread.yield();
		            }
		            leftMotor.stop();
		            rightMotor.stop();
		         }
		         

		         resetGyroTacho();
		      }
		   }

	   public void init() {
	      ev3 = LocalEV3.get();
	      gyro = new EV3GyroSensor(SensorPort.S4);
	      System.out.println("Gyro init");
	      leftMotor = new UnregulatedMotor(MotorPort.B);
	      System.out.println("Left motor init");
	      rightMotor = new UnregulatedMotor(MotorPort.C);
	      System.out.println("Right motor init");
	      gyroSamples = gyro.getAngleMode();
	   }
	   
	   public float getGyroAngleRaw() {
	      gyroSamples.fetchSample(angle, 0);
	      return angle[0];
	   }

	   public float getGyroAngle() {
	      float rawAngle = getGyroAngleRaw();
	      return rawAngle - gyroTacho;
	   }

}
