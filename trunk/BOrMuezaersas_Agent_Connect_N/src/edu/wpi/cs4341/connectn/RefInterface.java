package edu.wpi.cs4341.connectn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class which interfaces with the referee to send and receive moves and trigger the generation of moves
 * 
 * @author Ryan O'Meara
 */
public class RefInterface{
	public static final int PLAYER = 1;
	public static final int OPPONENT = -1*PLAYER;
	public static final int WRAPUP_TIME = 500;	//Milliseconds to assume necessary to wrap up move
	
	private int width;
	private int height;
	private int n;
	private int currentPlayer;			//the current player.  1
	private int timeLimit;				//Time limit in milliseconds
	private BufferedReader input;
	private Timer moveExpiration;
	private CNThread currentCalc;
	private MoveCalculator calc;
	
	/**
	 * TimerTask which makes the agent send moves on time
	 * @author Ryan O'Meara
	 */
	class TimeExpiration extends TimerTask{
		@Override
		public void run() {
			//stop thread
	        if(currentCalc != null){currentCalc.CNstop();}
	        
	        // send move
			int moveValue = 0;
	        System.out.println(String.valueOf(moveValue));
	        System.out.flush();
		}
	}
	
	/**
	 * Creates a new referee interface
	 * @param inWidth The width of the board to play on
	 * @param inHeight The height of the board to play on
	 * @param inNumtoWin The number of pieces which must be in a row to win
	 * @param inPlayerNumber The input play number.  0 = first, 1 = second
	 * @param inTimeLimit The time limit for a move in seconds
	 */
	public RefInterface(int inWidth, int inHeight, int inNumtoWin, int inPlayerNumber, int inTimeLimit){
		width = inWidth;
		height = inHeight;
		n = inNumtoWin;
		if(inPlayerNumber == 0){
			currentPlayer = PLAYER;
		}else{
			currentPlayer = OPPONENT;
		}
		timeLimit = 1000*inTimeLimit - WRAPUP_TIME;
		
		input = new BufferedReader(
	            new InputStreamReader(System.in));
		
		moveExpiration = new Timer();
		currentCalc = null;
		calc = new MoveCalculator(height, width);
	}
	
	/**
	 * Runs the game agent, retrieving opponent moves and generating the agent's moves
	 */
	public void runGame(){
		while (true) {
            if (currentPlayer == PLAYER) {
                //Setup TimerTask to expire and send
                try{
                	moveExpiration.schedule(new TimeExpiration(), timeLimit);
                }catch(Exception e){
                	System.err.println(ConnectNAgent.AGENT_NAME + " errored scheduling timeout.  Stack trace:");
                	e.printStackTrace(System.err);
                }
            	
                //start thread
                currentCalc = calc.run();
            	
                //wait on started thread
                try {
					currentCalc.join();
				} catch (Exception e) {
					System.err.println(ConnectNAgent.AGENT_NAME + " crashed joining calc thread.  Stack Trace:");
					e.printStackTrace(System.err);
				}
            } else {
            	int moveValue;
            	
                // read move
                try{
                	moveValue = Integer.parseInt(input.readLine());
                }catch(Exception e){
                	System.err.println(ConnectNAgent.AGENT_NAME + " errored when parsing opponent move.  Stack trace:");
                	e.printStackTrace(System.err);
                	moveValue = -1;
                }

                // check for end
                if (moveValue < 0){break;}
                
                //Record move
                //TODO send opponent move
            }

            // switch turns
            currentPlayer = -1*currentPlayer;
        }
	}
	
	/**
	 * @return The width of the game board
	 */
	public int getBoardWidth(){return width;}
	
	/**
	 * @return The height of the game board
	 */
	public int getBoardHeight(){return height;}
	
	/**
	 * @return The number in a row required to win
	 */
	public int getNumRequired(){return n;}
	
	/**
	 * @return Returns the number of the current player 
	 */
	public int getCurrentPlayer(){return currentPlayer;}

}
