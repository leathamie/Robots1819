package Behaviors;

import com.jcraft.jsch.jce.Random;

import Game.Board;
import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * Play class
 * implements the Behavior interface
 */
public class Play implements Behavior{
	private Robot robot;
	private Colors gameColors;
	private Board twisterBoard;
	
	/**
	 * Play constructor
	 * @param r
	 * @param c
	 * @param b
	 */
	public Play(Robot r, Colors c, Board b){
		this.robot = r;
		this.gameColors = c; 
		this.twisterBoard = b;
	}
	
	/**
	 * behavior function
	 * Function that dictate when the behavior is launched
	 */ 
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	
	/**
	 * behavior function 
	 * Function that dictate what the program is supposed to do when the behavior is launched
	 */
	@Override
	public void action() {
		// if the robot is the first player
		if (robot.isFirstPlayer()) {
			// it will randomly choose a color to send to the second player
			Color c = chooseRandomColor();
			// it will send it via bluetooth
			robot.sendObject(c); 
			// it is then its turn to wait to receive data
			Color goalColor = (Color) robot.receiveObject(Parameters.WAITINGDURATION);
			// once it receive the data, it just uses it to go where it is supposed to
			this.robot.goTo(goalColor, twisterBoard);
			
		//if the robot isn't the first player	
		}else {
			// it just wait to receive some data
			Color goalColor = (Color) robot.receiveObject(Parameters.WAITINGDURATION);
			// one it receive it, it just goes where it is supposed to
			this.robot.goTo(goalColor, twisterBoard);
			// then it chooses a random color to send to the first player
			Color c = chooseRandomColor();
			// it simply send it using bluetooth
			robot.sendObject(c); 
		}
		
	}

	/**
	 * behavior function
	 * Function that dictate what to do when the behavior stops
	 */
	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method chooseRandomColor
	 * @return Color, the color where the other robot will have to go
	 */
	public Color chooseRandomColor(){
		Random rand = new Random();
		int value = rand.nextInt(this.gameColors.getColors().length - 1);
		Color couleur = this.gameColors.getColors()[value];
		return couleur;
	}
	

}
