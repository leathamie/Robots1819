package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import AStar.AStar;
import AStar.Node;

/**
 * 
 * Robot class
 *
 */
public class Robot {
	
	//position & direction of the robot
	private int x;
	private int y;
	private int direction;
	//speed of the robot
	private int speed = 40;
	//the actuel rgb code of the cell the robot is on
	private float[] actualRGB;
	// is this robot the firstplayer
	private boolean firstPlayer;
	
	// set of variable used to pilot the robot
	private Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	private Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	private Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},2);
	private MovePilot pilot = new MovePilot(chassis);
	// variables used to control the gyro sensor
	private static EV3GyroSensor gyrosensor = new EV3GyroSensor(SensorPort.S1);
	final SampleProvider sp = gyrosensor.getAngleAndRateMode();
	
	// variables used to control the color sensor
	private EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
	private SampleProvider sample = captColor.getRGBMode();
	
	// Bluetooth
	private BTConnector bt;
	private BTConnection btc;
	private OutputStream os;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	
	/**
	 * Robot consructor
	 */
	public Robot() {
		this.x = 0; 
		this.y = 0; 
		this.direction = Parameters.UP;
		this.pilot.setLinearSpeed(speed);
		this.pilot.setAngularSpeed(speed);
		
		this.actualRGB = new float[3];
		this.actualRGB[0] = 0;
		this.actualRGB[1] = 0;
		this.actualRGB[2] = 0;
		this.firstPlayer = false;
	}
	
	/**
	 * Function setFirstLPlayer
	 * @param b
	 * allows to decide if the current robot is the firstplayer
	 */
	public void setFirstPlayer(boolean b) {
		this.firstPlayer = b;
		if (!this.firstPlayer) {
			this.x = Parameters.BOARD_WIDTH - 1;
			this.y = Parameters.BOARD_LENGTH - 1;
		}
	}
	
	/**
	 * Function isFirstPlayer
	 * @return boolean
	 * allows to verify if the current robot is the firstplayer
	 */
	public boolean isFirstPlayer() {
		return this.firstPlayer;
	}

	/**
	 * Function getActualRGB
	 * @return float[]
	 * returns an array of float to get the rgb code of the cell the robot is on
	 */
	public float[] getActualRGB(){
		return this.actualRGB;
	}
	
	/**
	 * Function getX
	 * @return int
	 * return the x position of the robot
	 */
	public int getX() {
		return x;
	}

	/**
	 * Function setX
	 * @param x
	 * modify the x position of the robot
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Function getY
	 * @return int
	 * return the y position of the robot
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Function setY
	 * @param y
	 * modify the y position of the robot
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Function getDirection
	 * @return int
	 * return the direction, as an int, the robot is facing 
	 */
	public int getDirection() {
		return direction;
	}

	
	
	// IL FAUDRAIT EXPLIQUER CE QU'IL SE PASSE DANS LES IF
	/**
	 * Function setDirection
	 * @param d
	 * modify the direction the robot is facing
	 */
	private void setDirection(int d) {
		// checks if the parameter is equal to one of the four possibilities stored in the interface Parameters
		if(d == Parameters.LEFT || d == Parameters.RIGHT || d == Parameters.UP || d == Parameters.DOWN) {
			if(this.direction == Parameters.UP) {
				this.direction = d;
			}
			else if((this.direction == Parameters.LEFT && d == Parameters.RIGHT)||(this.direction == Parameters.RIGHT && d == Parameters.LEFT)) {					
				this.direction = Parameters.UP;
			}
			else if((this.direction == Parameters.LEFT && d == Parameters.LEFT)||(this.direction == Parameters.RIGHT && d == Parameters.RIGHT)) {					
				this.direction = Parameters.DOWN;
			}
			else if((this.direction == Parameters.DOWN && d == Parameters.RIGHT)) {					
				this.direction = Parameters.LEFT;				
			}
			else if((this.direction == Parameters.DOWN && d == Parameters.LEFT)) {					
				this.direction = Parameters.RIGHT;
			}	
		}
	}
	
	
	/**
	 * Function moveForward
	 * launch the two motors for the robots wheels
	 */
	public void moveForward() {
		Motor.B.setSpeed(this.speed);
		Motor.C.setSpeed(this.speed);
		Motor.B.forward();
		Motor.C.forward();
	}
	
	/**
	 * Function captureRGB
	 * @return float[]
	 * return what the color sensor scan by using the variable sample
	 */
	public float[] captureRGB() {
		float[] vals = new float[3]; // pour utiliser le rgb
		sample.fetchSample(vals, 0);
		return vals;
	}
	
	/**
	 * Function colorChange
	 * @return boolean
	 * checks if the current rgb code is different from the scanned rgb code
	 */
	public boolean colorChange() {
		float[] vals = new float[3];
		sample.fetchSample(vals, 0);
		if (!this.quickEquals(this.actualRGB, vals)) {
			this.actualRGB = vals;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Function goTo
	 * @param direct
	 * use the direction in parameters, and then move to the appropriated direction
	 */
	public void goTo(int direct) {
		this.setDirection(direct);
		this.lineCrossed();
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
	
	private void moveUp() {
		pilot.travel(90);
	}
	
	private void moveDown() {
		pilot.travel(-90);
	}
	
	private void moveLeft() {
		pilot.rotate(-80);
        pilot.travel(-55);
	}
	
	private void moveRight() {
        Motor.A.setSpeed(speed);
        Motor.C.setSpeed(speed);
        int angle = 0; 
        pilot.travel(125);
        while(true){
            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            angle = (int)sample[0];
           
            
            //je dois utiliser backward et forward des moteurs parce que sinon le robot va faire un arc de cercle
            //et en plus si je veux utiliser pilot je dois donner un angle donc il va s'en foutre de si il dépasse les 90 degres
            Motor.A.backward();
            Motor.C.forward();
           
            if (angle >= 90){
                Motor.A.stop(true);
                Motor.C.stop(true);
                pilot.travel(55);
                break;
            }
        }
	}
	
	public void stop() {
		Motor.B.stop(true);
		Motor.C.stop(true);
	}
	
	
	/**
	 * Function closeSensors
	 * allows the program to close all the sensors
	 */
	public void closeSensors() {
		this.captColor.close();
	}
	
	//COMPREND MAIS SAIS PAS COMMENT EXPLIQUER
	private boolean quickEquals(float[] c1, float[] c2 ) {
		for (int i = 0 ; i<3 ; i++) {
			if ((c1[i] < c2[i]-(0.5*c2[i])) || (c1[i] > c2[i]+(0.5*c2[i]))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Function lineCrossed
	 * allows to update the position of the robot
	 */	
	public void lineCrossed() {
		//if the direction of 
		if (this.direction == Parameters.UP) {
			this.y = this.y + 1;
		}else if (direction == Parameters.DOWN) {
			this.y = this.y - 1;
		}else if (direction == Parameters.LEFT) {
			this.x = this.x - 1;
		}else {
			this.x = this.x + 1; 
		}
		/*
		switch (this.direction) {
		case Parameters.UP:
			this.y = this.y + 1;
		case Parameters.DOWN:
			this.y = this.y - 1;
		case Parameters.LEFT:
			this.x = this.x - 1;
		case Parameters.RIGHT:
			this.x = this.x + 1; 
		}
		*/
	}
	
	/**
	 * Function chooseColor
	 * @return int
	 * randomly choose an int that will be send to the other robot in an other function
	 */
	public int chooseColor() {
		Random rand = new Random();
		int value = rand.nextInt(Parameters.COLORS.length - 2);
		return value;
	}
	
	/**
	 * Function sendData
	 * @param data
	 * @return boolean
	 * allows a robot to sens an int to the other robot
	 */
	public boolean sendData(int data) {
		os = btc.openOutputStream();
		dos = new DataOutputStream(os);
		System.out.println("\n\nEnvoi");
		// we send the data
		try{
			dos.write(data);
			dos.flush();
			System.out.println("Envoyé\n");
			dos.close();
			btc.close();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Function sendObject
	 * @param o
	 * @return boolean
	 * allows a robot to send an object to the other robot
	 */
	public boolean sendObject(Object o) {
		try {
			// create the link of connectoin
			os = btc.openOutputStream();
			oos = new ObjectOutputStream(os);
			// store the object to send (like a git commit)
			oos.writeObject(o);
			// send the object (like a git push)
			oos.flush();
			System.out.println("Envoyé\n");
			oos.close();
			btc.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Function receiveDate
	 * @param waitingMs
	 * @return int
	 * allows the robot to receive an int that will represent a color
	 */
	public int receiveData(int waitingMs) {
		int value = 0;
		try{
			bt = new BTConnector();
			btc = bt.waitForConnection(waitingMs, NXTConnection.PACKET);
			
			// si la connexion a été mise en place
			if(btc != null){
				
				// objets qui permettront de récupérer les données envoyées
				InputStream is = btc.openInputStream();
				DataInputStream dis = new DataInputStream(is);
				
				//on attribue le message a la variable valeur 
				value = dis.read();
				
				dis.close();
				btc.close();
			} else {
				System.out.println("Pas de connexion");
			}		
		} catch(Exception e) { }
		return value;
	}
	
	/**
	 * Function receiveObject
	 * @param waitingMs
	 * @return Object
	 * allows the robot to receive an object
	 */
	public Object receiveObject(int waitingMs) {
		Object o = new Object();
		try{	
			// the robot waits for the other to connect
			bt = new BTConnector();
			btc = bt.waitForConnection(waitingMs, NXTConnection.PACKET);
			
			//if the connection is made
			if(btc != null){
				FilterInputStream dis = null;
				Object value = dis.read();
				if (value instanceof Object){
					o = value;
				}
				dis.close();
				btc.close();
				
				
			} else {
				System.out.println("Pas de connexion");
			}		
		} catch(Exception e) { }
		return o;
	}
	
	/**
	 * Function goTo
	 * @param c
	 * @param b
	 */
	public void goTo(Color c, Board b) {
		List<int[]> posOfC = (List<int[]>) b.getAllPosition(c);
		int [] goal = this.findClosest(posOfC);
		List<Node> cells = findPath(goal, b);
		this.folowPath(cells);
	}
	public void goToWithRobot2(Color c, Board b, ArrayList<Integer> RobotPosition ) {
		List<int[]> posOfC = (List<int[]>) b.getAllPosition(c);
		int [] goal = this.findClosest(posOfC);
		int [][]  blocks = findOccupedCells(RobotPosition.get(0), RobotPosition.get(1), RobotPosition.get(2));
		List<Node> cells = findPath(goal, b, blocks);
		this.folowPath(cells);
	}
	
	/**
	 * Function findClosest
	 * @param pos
	 * @return int[]
	 */
	private int[] findClosest(List<int[]> pos) {
		if (!pos.isEmpty()) {
			int[] closest = pos.get(0); 
			int distClosest = Math.abs(closest[0] - this.x) + Math.abs(closest[1] - this.y);
			for (int i = 1 ; i <pos.size(); i++) {
				int dist = Math.abs(pos.get(i)[0] - this.x) + Math.abs(pos.get(i)[1] - this.y);
				if (dist < distClosest) {
					distClosest = dist; 
					closest = pos.get(i);
				}
			}
			return closest;
		}
		return null;	
	}
	
	/**
	 * Function findPath
	 * @param finalPos
	 * @param b
	 * @return List<Node>
	 */
	private List<Node> findPath( int [] finalPos, Board b ) {
		// vérifier la taille des tableaux 
		// vérifier que c'est bien dans le tableau 
		
		Node initialNode = new Node(this.x, this.y);
        Node finalNode = new Node(finalPos[0], finalPos[1]);
        int rows = b.getLengthY();
        int cols = b.getLengthX();
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        //int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        //aStar.setBlocks(blocksArray);
        List<Node> path = aStar.findPath();
        for (Node node : path) {
            System.out.println(node);
        }
        return path;
	}
	
	/**
	 * Function findPath
	 * @param finalPos
	 * @param b
	 * @param blocksArray
	 * @return List<Node>
	 */
	public List<Node> findPath( int [] finalPos, Board b, int[][] blocksArray ) {
		// vérifier la taille des tableaux 
		// vérifier que c'est bien dans le tableau 
		
		Node initialNode = new Node(this.x, this.y);
        Node finalNode = new Node(finalPos[0], finalPos[1]);
        int rows = b.getLengthY();
        int cols = b.getLengthX();
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        aStar.setBlocks(blocksArray);
        List<Node> path = aStar.findPath();
        for (Node node : path) {
            System.out.println(node);
        }
        return path; 
	}
	
	/**
	 * Function folowPath
	 * @param path
	 */
	public void folowPath (List<Node> path) {
		for (Node node : path) {
            if ((this.x + 1 == node.getCol())  && this.y == node.getRow()) {
            	if (this.direction == Parameters.UP) {
            		this.goTo(Parameters.RIGHT);
            	}else if (this.direction == Parameters.DOWN) {
            		this.goTo(Parameters.LEFT);
            	}else if (this.direction == Parameters.LEFT) {
            		this.goTo(Parameters.DOWN);
            	}else {
            		this.goTo(Parameters.UP);
            	}
            }else if (this.x - 1 == node.getCol() && this.y == node.getRow()) {
            	if (this.direction == Parameters.UP) {
            		this.goTo(Parameters.LEFT);
            	}else if (this.direction == Parameters.DOWN) {
            		this.goTo(Parameters.RIGHT);
            	}else if (this.direction == Parameters.LEFT) {
            		this.goTo(Parameters.UP);
            	}else {
            		this.goTo(Parameters.DOWN);
            	}
            }else if (this.x == node.getCol() && this.y + 1 == node.getRow()) {
	        	if (this.direction == Parameters.UP) {
	        		this.goTo(Parameters.UP);
	        	}else if (this.direction == Parameters.DOWN) {
	        		this.goTo(Parameters.DOWN);
	        	}else if (this.direction == Parameters.LEFT) {
	        		this.goTo(Parameters.RIGHT);
	        	}else {
	        		this.goTo(Parameters.LEFT);
	        	}
	        }else if (this.x == node.getCol() && this.y - 1 == node.getRow()) {
	        	if (this.direction == Parameters.UP) {
	        		this.goTo(Parameters.DOWN);
	        	}else if (this.direction == Parameters.DOWN) {
	        		this.goTo(Parameters.UP);
	        	}else if (this.direction == Parameters.LEFT) {
	        		this.goTo(Parameters.LEFT);
	        	}else {
	        		this.goTo(Parameters.RIGHT);
	        	}
	        }
            
        }
	}
	
	private int [][] findOccupedCells(int robotX, int robotY, int orientation) {
		int[][] blocksArray = new int[6][2];
		int i = 0;
		if (orientation == Parameters.UP) {
			for (int x = robotX ; x > x-Parameters.ROBOTCELLWIDTH ; x--){
				if (x > 0 && x > Parameters.BOARD_WIDTH) {
					for (int y = robotY ; y > y-Parameters.ROBOTCELLLENGTH ; y-- ) {
						if (y > 0 && y > Parameters.BOARD_LENGTH) {
							int [] cell = {x,y};
							blocksArray[i] = cell;
							i ++;
						}
					}
				}
			}
		}else if (orientation == Parameters.DOWN) {
			for (int x = robotX ; x < x+Parameters.ROBOTCELLWIDTH ; x++){
				if (x > 0 && x > Parameters.BOARD_WIDTH) {
					for (int y = robotY ; y < y+Parameters.ROBOTCELLLENGTH ; y++ ) {
						if (y > 0 && y > Parameters.BOARD_LENGTH) {
							int [] cell = {x,y};
							blocksArray[i] = cell;
							i ++;
						}
					}
				}
			}
		}else if (orientation == Parameters.LEFT) {
			for (int x = robotX ; x < x+Parameters.ROBOTCELLLENGTH ; x++){
				if (x > 0 && x > Parameters.BOARD_WIDTH) {
					for (int y = robotY ; y < y-Parameters.ROBOTCELLWIDTH ; y-- ) {
						if (y > 0 && y > Parameters.BOARD_LENGTH) {
							int [] cell = {x,y};
							blocksArray[i] = cell;
							i ++;
						}
					}
				}
			}
		}else if (orientation == Parameters.RIGHT) {
			for (int x = robotX ; x < x-Parameters.ROBOTCELLLENGTH ; x--){
				if (x > 0 && x > Parameters.BOARD_WIDTH) {
					for (int y = robotY ; y < y+Parameters.ROBOTCELLWIDTH ; y++ ) {
						if (y > 0 && y > Parameters.BOARD_LENGTH) {
							int [] cell = {x,y};
							blocksArray[i] = cell;
							i ++;
						}
					}
				}
			}
		}
		return blocksArray;
	}
	

}
