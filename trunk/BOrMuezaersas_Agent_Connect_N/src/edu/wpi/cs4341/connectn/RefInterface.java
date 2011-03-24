package edu.wpi.cs4341.connectn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.cs4341.connectn.MoveCalculator.CNThread;

/**
 * Class which interfaces with the referee to send and receive moves and trigger the generation of moves
 * 
 * @author Ryan O'Meara
 */
public class RefInterface{
	public static final int PLAYER = 1;
	public static final int OPPONENT = -1*PLAYER;
	public static final int WRAPUP_TIME = 500;	//Milliseconds to assume necessary to wrap up move
	
	//Game data
	private int width;
	private int height;
	private int n;
	protected int currentPlayer;			//the current player.  1
	private int timeLimit;				//Time limit in milliseconds
	
	private BufferedReader input;
	private Timer moveExpiration;
	protected CNThread currentCalc;
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
	        //Get move
			int moveValue = calc.getCurrentBestMove();
			//Register our move with us
	        calc.recieveMove(moveValue, PLAYER);
	        //Wait for computations to finish
	        try{currentCalc.join();}catch(Exception e){}
	        //Send move
	        System.out.print(String.valueOf(moveValue) + "\n");
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
		calc = new MoveCalculator(height, width, n, currentPlayer);  
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
                
                currentCalc.start();
            	
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
                }catch(NumberFormatException n){
                	System.err.println(ConnectNAgent.AGENT_NAME + " program ended unexpectedly on opponent's turn");
                	moveValue = -1;
                }catch(Exception e){
                	System.err.println(ConnectNAgent.AGENT_NAME + " errored when parsing opponent move.  Stack trace:");
                	e.printStackTrace(System.err);
                	moveValue = -1;
                }

                // check for end
                if (moveValue < 0){return;}
                
                //Record move
                calc.recieveMove(moveValue, RefInterface.OPPONENT);
                
            }
            
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

}
