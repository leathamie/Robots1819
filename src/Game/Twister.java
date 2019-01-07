package Game;

import Behaviors.InitBoard;
import Behaviors.InitColors;
import Behaviors.Play;
import Behaviors.SwitchOff;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Twister class where are created then called the different behaviors used by the robot
 */
public class Twister {

	//variables 
	private Colors colors;
	private Robot bot;
	private Board twisterBoard;
	
	
	/**
	 * Twister constructor
	 * Twister object is composed of 3 objects, colors, bot and twisterBoard
	 */
	public Twister() {
		this.colors = new Colors();
		this.bot = new Robot();
		this.twisterBoard = new Board();
	}
	
	/**
	 * Method play, launch the program
	 * will be used in the main 
	 */
	public void play() {
		LCD.drawString("Click when you are ready", 0, 3 );
		Button.waitForAnyPress();
		LCD.clear();
		//robotChoice allows the user to choose if the current robots name is robot1 or robot2
		robotChoice();
		
		
		//Create Behaviors object
		InitBoard initBoard = new InitBoard(this.bot, this.colors, this.twisterBoard);
		Play play = new Play(this.bot, this.colors, this.twisterBoard);
		SwitchOff swichtOff = new SwitchOff(this.bot); //Stop
		InitColors ic = new InitColors(this.bot, colors);
		
		
		//launch
		Behavior[] bArray = {play, initBoard, ic, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
		
		
	}
	
	/**
	 * Function robotChoice
	 * allows the user to choose if the current robots name is robot1 or robot2
	 * will be useful to know which robot starts to "play"
	 */
	public void robotChoice(){
        LCD.drawString("Veuillez choisir le nom de ce Robot", 0, 0);
        LCD.drawString("Bouton Haut : Robot 1", 0,2);
        LCD.drawString("Bouton du Bas : Robot 2", 0,3);
       
        if(Button.UP.isDown()){
            this.bot.setFirstPlayer(true);
            LCD.clear();
           
        } else if(Button.DOWN.isDown()){           
        	this.bot.setFirstPlayer(false);
            LCD.clear();
        }
    }
	
}
