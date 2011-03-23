package edu.wpi.cs4341.connectn;

import java.util.ArrayList;

/**
 * Calculates a model to base moves off of using min-max and alpha-beta pruning
 * 
 * @author Frank Bruzesse, Ryan O'Meara
 */
public class MoveCalculator {

	private BoardState gameState;			//The current state of the game
	private static int currentBestMove;
	
	public MoveCalculator( int row, int col, int n ) {
		int[][] temp = new int[col][row];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				temp[j][i] = 0;
		
		currentBestMove = 0;
		
		gameState = new BoardState(temp, RefInterface.PLAYER);
	}
	
	/** 
	 * Starts a thread which updates the model and calculates the best move 
	 * @return A reference to the CNThread that was started
	 */
	public CNThread run() {
		
		CNThread currentThread = new CNThread(gameState);
		currentThread.run();
		
		return currentThread;
	}

	/**
	 * @return The column/move deemed "best" based on the current model
	 */
	public int getCurrentBestMove() {return currentBestMove;}
	
	/**
	 * Receives a move and updates the state
	 * 
	 *  @param col		 - the column the move is made in
	 *  @param player	 - which player made the move
	 */
	public void recieveMove( int col, int player) {
		gameState = gameState.makeMove(col, player);
	}
	
	class CNThread extends Thread{
		
		private boolean canRun;
		private ArrayList<BoardState> states;
		
		public CNThread(BoardState gameState) {
			super();
			
			canRun = true;
			states = new ArrayList<BoardState>();
			states.add(gameState);
		}
		
		@Override
		public void run() {
			int firstmove = -1;
			
			firstmove = isEmptyBoard();
			
			while (canRun){
				// TODO This is where the thread does stuff.  This should be as short as possible so canRun gets checked often
				//Pick state to expand next
			
				//expand state (or just get its children if expanded previously)
				
				//if all states on this level expanded, prune
				
				//if the move hasn't already been decided (first move given to us)
				if(firstmove == -1){
					//update currentBestMove (make sure to check each state's pruning status as we go)
					//this should amount to comparing global hueristics
					int currentChoice = -1;
					
					//TODO loop through the GLOBAL heuristics of the game state's children and find max
					
					if(currentChoice == -1){
						//TODO loop through game state "moves" and see if any legal, otherwise set = 0
					}
					
					currentBestMove = currentChoice;
				}else{
					currentBestMove = firstmove;
				}
			}
		}
		
		public void CNstop() {
			canRun = false;
		}
		
		/**
		 * Determines if current board is empty, and best move if it is
		 * @return -1 if not empty board
		 */
		private int isEmptyBoard(){
			if(!gameState.boardEmpty()){return -1;}
			
			//TODO make this determine best move on that board
			return -1;
		}
	}

}



