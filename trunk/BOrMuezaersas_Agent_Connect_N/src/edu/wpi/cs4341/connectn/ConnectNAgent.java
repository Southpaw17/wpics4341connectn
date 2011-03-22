package edu.wpi.cs4341.connectn;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class which does intial game setup
 * 
 * @author Ryan O'Meara
 */
public class ConnectNAgent {
	public static final String AGENT_NAME = "BOrMuezazrease Agent";		//Name of this AI agent

	/**
	 * Main function to start and setup program
	 * @param args The arguments to the program, should be none
	 */
	public static void main(String[] args) {
		int width, height, numToWin, playerNumber, timeLimit;

		// use BufferedReader for easy reading
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// send player name
	    System.out.println(AGENT_NAME);
	    System.out.flush();

	    // read game config
	    try{
	    	//Parse the initial parameters sent by the referee
	    	String [] gameConfig = input.readLine().split(" ");
	    	height = Integer.parseInt(gameConfig[0]);
	    	width = Integer.parseInt(gameConfig[1]);
	    	numToWin = Integer.parseInt(gameConfig[2]);
	    	playerNumber = Integer.parseInt(gameConfig[3]);
	    	timeLimit = Integer.parseInt(gameConfig[4]);
		        
	    	//Set up referee interface
	    	RefInterface agent = new RefInterface(width, height, numToWin, playerNumber, timeLimit);
		     
	    	//Run agent
	    	agent.runGame();
	    }catch(Exception e){
	    	System.err.println(AGENT_NAME + " crashed due to exception parsing initial input.  Stack track");
	    	e.printStackTrace(System.err);
	    }     
	}

}
