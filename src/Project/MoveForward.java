package Project;

import lejos.hardware.motor.Motor;
import lejos.robotics.subsumption.Behavior;

public class MoveForward implements Behavior{
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		//LCD.clear();
		//System.out.println("en avant ! ");
		Motor.B.forward();
		Motor.C.forward();
	}

	@Override
	public void suppress() {
		Motor.B.stop(true);
		Motor.C.stop(true);
	}

	public void countCrossedColors() {
		
	}
}
