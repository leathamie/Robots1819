package Behaviors;

import Game.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * SwitchOff class
 * implements the Behavior interface
 * This class contains all the action made when the game is over
 *
 */
public class SwitchOff implements Behavior{
	
	private Arbitrator arb;
	private Robot bot;

	/**
	 * SwitchOff constructor
	 * @param r
	 */
	public SwitchOff(Robot r){
		this.bot = r;
	}
	

	public void setArbitrator(Arbitrator a){
		this.arb = a;
	}


	/**
	 * behavior function
	 * Function that dictate when the behavior is launched
	 */
	public boolean takeControl(){
		return Button.RIGHT.isDown();
	}
	
	/**
	 * behavior function
	 * Function that dictate what the program is supposed to do when the behavior is launched
	 */
	public void action(){
		LCD.clear();
		LCD.drawString("EndProcess", 1, 0);
		LCD.refresh();
		// the robot stop itself
		bot.stop();
		// all sensor are shut down
		bot.closeSensors();

		arb.stop();
		System.exit(0);
	}

	/**
	 * behavior function
	 * Function that dictate what to do when the behavior stops
	 */
	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}



}