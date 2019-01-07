package Behaviors;


import java.util.ArrayList;

import Game.Board;
import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
/**
 * 	
 * InitBoard class
 * implements the Behavior interface
 */
public class InitBoard implements Behavior{

	private int crossedColors;
	private Colors colors;
	private Robot robot;
	private Board board;
			
	/**
	 * InitBoard constructor
	 * @param r 
	 * @param c
	 * @param b
	 */
	public InitBoard(Robot r, Colors c, Board b) {
		this.crossedColors = 0;
		this.colors = c;
		this.robot = r;
		this.board = b;
	}
	

	/**
	 * behavior function
	 * Function that dictate when the behavior is launched
	 */
	public boolean takeControl() {
		//behavior is launched if the board isn't set AND if the current robot is the robot1
		return !this.board.isInitBoard();
	}

	/**
	 * behavior function 
	 * Function that dictate what the program is supposed to do when the behavior is launched
	 */
	@Override
	public void action() {
		if (this.robot.isFirstPlayer()) {
			//the robot goes forward without stopping
			robot.moveForward();
			
			//if the color of the case the robot is on, is different from the color of the precedent case then
			if (robot.colorChange()) {
				Color c = new Color(robot.getActualRGB());
				//allows to set the name of the color based on its RGB code
				this.colors.setColorName(c);
				
				// if the scanned color is black
				if (c.equals(colors.getColor(Parameters.BORDERCOLOR))) {
					this.crossedColors += 1;
					// call the lineCrossed function that allows to update the robot position
					robot.lineCrossed();
					Delay.msDelay(1000);
				}else {
					//the current cell is marked with its color
					board.setCell(robot.getX(),robot.getY(), c);
				}
				LCD.drawString(this.crossedColors + " detected as" + c.getName(), 0, 3 );
				
			}
			
			//if the robot reached the edge of the board
			// we check 
			if(this.crossedColors == Parameters.BOARD_LENGTH) {
				this.crossedColors = 0;
				if(this.robot.getX() != Parameters.BOARD_WIDTH) {
					// test if the robot has to go to the left or the right to stay on the board
					if(this.robot.getX()%2 == 0) {
						robot.goTo(Parameters.RIGHT);
						robot.goTo(Parameters.RIGHT);
					}else {
						robot.goTo(Parameters.LEFT);
						robot.goTo(Parameters.LEFT);
					}
				}else {
					LCD.clear();
					LCD.drawString("Make sure bot2 is ready", 0, 2 );
					LCD.drawString("Press enter", 0, 3 );
					Button.ENTER.waitForPressAndRelease();
					LCD.drawString("sending board ...", 0, 4 );
					// we send the initialized board to the other robot
					this.robot.sendObject(this.board); 
					
					// we set the initBoard variable to true
					// it means the initialisation of all the color in the board is over
					this.board.setInitBoard();
				}
				
			}
			//print the position of the robot and the direction it faces 
			LCD.drawString(this.robot.getDirection() + "pos" + this.robot.getX() + "," + this.robot.getY(), 0, 4 );
		
		}else {
			// if this robot is not the robot 1, he will only receive the board already initialised
			this.board = (Board) robot.receiveObject(Parameters.WAITINGDURATION);
			this.board.setInitBoard();
			
		}
			
		
			
	}
	
	/**
	 * behavior function
	 * Function that dictate what to do when the behavior stops
	 */
	@Override
	public void suppress() {
		robot.stop();
		LCD.clear();
	}
	 
	
	

}
