package edu.wpi.cs4341.connectn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TimerTask;

public class RefInterface extends TimerTask {
	public static final int PLAYER = 1;
	public static final int OPPONENT = -1;
	public static final int WRAPUP_TIME = 500;	//Milliseconds to assume necessary to wrap up move
	
	private int width;
	private int height;
	private int n;
	private int currentPlayer;			//the
	private int timeLimit;				//Time limit in milliseconds
	private BufferedReader input;
	
	public RefInterface(int inWidth, int inHeight, int inNumtoWin, int inPlayerNumber, int inTimeLimit){
		width = inWidth;
		height = inHeight;
		n = inNumtoWin;
		if(inPlayerNumber == 0){
			currentPlayer = 1;
		}else{
			currentPlayer = -1;
		}
		timeLimit = 1000*inTimeLimit - WRAPUP_TIME;
		
		input = new BufferedReader(
	            new InputStreamReader(System.in));
	}
	
	public void runGame(){
		while (true) {
            if (currentPlayer == PLAYER) {
                //Setup backup move
                
                //Setup timertask to expire and send
                
                //start thread
            	
                //wait on started thread
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
            }

            // switch turns
            currentPlayer = -1*currentPlayer;
        }
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Run timer task

        // send move
		int moveValue = 0;
        System.out.println(String.valueOf(moveValue));
        System.out.flush();

	}

}
