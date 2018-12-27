package Behaviors;

import Game.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class SwitchOff implements Behavior{
	
	private Arbitrator arb;
	private Robot bot;


	public SwitchOff(Robot r){
		this.bot = r;
	}
	

	public void setArbitrator(Arbitrator a){
		this.arb = a;
	}


	public boolean takeControl(){
		return Button.RIGHT.isDown();
	}
	

	public void action(){
		LCD.clear();
		LCD.drawString("EndProcess", 1, 0);
		LCD.refresh();
		//le robot ne bouge plus
		bot.stop();
		//on eteint les capteurs
		bot.closeSensors();

		arb.stop();
		System.exit(0);
	}


	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}



}