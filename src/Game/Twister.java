package Game;

import Behaviors.InitBoard;
import Behaviors.InitColors;
import Behaviors.SwitchOff;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Twister {
	
	private Colors colors;
	private Robot bot;
	private Board twisterBoard;
	
	
	public Twister() {
		this.colors = new Colors();
		this.bot = new Robot();
		this.twisterBoard = new Board();
	}
	
	public void play() {
		LCD.drawString("Click when you are ready", 0, 3 );
		Button.waitForAnyPress();
		LCD.clear();
		robotChoice();
		
		
		//Create Behaviors class
		
		InitBoard initBoard = new InitBoard(this.bot, this.colors, this.twisterBoard);
		
		SwitchOff swichtOff = new SwitchOff(this.bot); //Stop
		InitColors ic = new InitColors(this.bot, colors);
		
		
		//lauch
		//Behavior[] bArray = {moveForward, dc, ic, swichtOff}; // du moins prioritaire au plus prioritaire
		Behavior[] bArray = {initBoard, ic, swichtOff}; // du moins prioritaire au plus prioritaire
		Arbitrator arby = new Arbitrator(bArray);
		swichtOff.setArbitrator(arby);
		arby.go();
		
		
	}
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
