package Behaviors;

import com.jcraft.jsch.jce.Random;

import Game.Board;
import Game.Color;
import Game.Colors;
import Game.Parameters;
import Game.Robot;
import lejos.robotics.subsumption.Behavior;

public class Play implements Behavior{
	private Robot robot;
	private Colors gameColors;
	private Board twisterBoard;
	
	public Play(Robot r, Colors c, Board b){
		this.robot = r;
		this.gameColors = c; 
		this.twisterBoard = b;
	}
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (robot.isFirstPlayer()) {
			Color c = chooseRandomColor();
			robot.sendObject(c); 
			Color goalColor = (Color) robot.receiveObject(Parameters.WAITINGDURATION);
			this.robot.goTo(goalColor, twisterBoard);
		}else {
			Color goalColor = (Color) robot.receiveObject(Parameters.WAITINGDURATION);
			this.robot.goTo(goalColor, twisterBoard);
			Color c = chooseRandomColor();
			robot.sendObject(c); 
		}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	public Color chooseRandomColor(){
		Random rand = new Random();
		int value = rand.nextInt(this.gameColors.getColors().length - 1);
		Color couleur = this.gameColors.getColors()[value];
		return couleur;
	}
	

}
