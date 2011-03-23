package edu.wpi.cs4341.connectn;

/**
 * Calculates a model to base moves off of using min-max and alpha-beta pruning
 * 
 * @author Frank Bruzesse, Ryan O'Meara
 */
public class MoveCalculator {

	private BoardState gameState;			//The current state of the game
	
	public MoveCalculator( int row, int col, int n ) {
		int[][] temp = new int[col][row];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				temp[j][i] = 0;
		
		gameState = new BoardState(temp, RefInterface.PLAYER);
	}
	
	/** 
	 * Starts a thread which updates the model and calculates the best move 
	 * @return A reference to the CNThread that was started
	 */
	public CNThread run() {
		
		CNThread currentThread = new CNThread();
		currentThread.run();
		
		return currentThread;
	}

	/**
	 * @return The column/move deemed "best" based on the current model
	 */
	public int getCurrentBestMove() {
		//TODO make this not return 0
		return 0;
	}
	
	/**
	 * Receives a move and updates the state
	 * 
	 *  @param col		 - the column the move is made in
	 *  @param player	 - which player made the move
	 */
	public void recieveMove( int col, int player) {
		gameState = gameState.makeMove(col, player);
	}
}

/**
 * Thread class which does the bulk of calculation for the next move
 * 
 * @author Frank Bruzesse, Ryan O'Meara
 */
class CNThread extends Thread{
	
	private boolean canRun;
	
	public CNThread() {
		super();
		
		canRun = true;
	}
	
	@Override
	public void run() {
		
		while (canRun)
		{
			// TODO This is where the thread does stuff.  This should be as short as possible so canRun gets checked often
		}
	}
	
	public void CNstop() {
		canRun = false;
	}
}

