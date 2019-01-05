package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
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


public class Robot {
	private int x;
	private int y;
	private int direction;
	private int speed = 40;
	float[] actualRGB;
	
	private Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	private Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	private Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},2);
	private MovePilot pilot = new MovePilot(chassis);
	
	
	private EV3ColorSensor captColor = new EV3ColorSensor(SensorPort.S3);
	private SampleProvider sample = captColor.getRGBMode();
	
	// Bluetooth
	private BTConnector bt;
	private BTConnection btc;
	private OutputStream os;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	
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
	}

	public float[] getActualRGB(){
		return this.actualRGB;
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

	private void setDirection(int d) {
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
	
	public void moveForward() {
		Motor.B.setSpeed(this.speed);
		Motor.C.setSpeed(this.speed);
		Motor.B.forward();
		Motor.C.forward();
	}
	
	public float[] captureRGB() {
		float[] vals = new float[3]; // pour utiliser le rgb
		sample.fetchSample(vals, 0);
		return vals;
	}
	
	public boolean colorChange() {
		float[] vals = new float[3];
		sample.fetchSample(vals, 0);
		if (!this.quickEquals(this.actualRGB, vals)) {
			this.actualRGB = vals;
			return true;
		}
		return false;
	}
	
	
	/*
	 * Prends en parametre une direction sous forme d'entier et se déplace dans la direction appropriée 
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
		pilot.travel(125);
        pilot.rotate(80);
        pilot.travel(55);
	}
	
	public void stop() {
		Motor.B.stop(true);
		Motor.C.stop(true);
	}
	
	public void closeSensors() {
		this.captColor.close();
	}
	
	private boolean quickEquals(float[] c1, float[] c2 ) {
		for (int i = 0 ; i<3 ; i++) {
			if ((c1[i] < c2[i]-(0.5*c2[i])) || (c1[i] > c2[i]+(0.5*c2[i]))) {
				return false;
			}
		}
		return true;
	}
	
	public void lineCrossed() {
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
	
	public int chooseColor() {
		Random rand = new Random();
		int value = rand.nextInt(Parameters.COLORS.length - 2);
		return value;
	}
	public boolean sendData(int data) {
		os = btc.openOutputStream();
		dos = new DataOutputStream(os);
		System.out.println("\n\nEnvoi");
		//ON ENVOIE LA VALEUR; dans ce cas là c'est le nombre random
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
	public boolean sendObject(Object o) {
		try {
			os = btc.openOutputStream();
			oos = new ObjectOutputStream(os);
			oos.writeObject(o);
			oos.flush();
			System.out.println("Envoyé\n");
			oos.close();
			btc.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
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
	
	
	public Object receiveObject(int waitingMs) {
		Object o = new Object();
		try{	
			bt = new BTConnector();
			btc = bt.waitForConnection(waitingMs, NXTConnection.PACKET);
			
			// si la connexion a été mise en place
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
	
	public 
	public List<Node> findPath( int [] finalPos, Board b ) {
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
	

}
