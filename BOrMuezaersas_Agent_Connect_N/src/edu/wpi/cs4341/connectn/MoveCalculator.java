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
	private int width;
	public static final int NO_HEURISTIC = -10000;
	public static final int MAX_HEURISTIC = 9999;
	
	public MoveCalculator( int row, int col, int n ) {
		width = col;
		int[][] temp = new int[col][row];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				temp[j][i] = 0;
		
		currentBestMove = 0;
		
		gameState = new BoardState(temp, n, RefInterface.PLAYER, -1);
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
		private int currentLevel;
		
		public CNThread(BoardState gameState) {
			super();
			
			canRun = true;
			states = new ArrayList<BoardState>();
			states.add(gameState);
			currentLevel = gameState.getLevel();
		}
		
		@Override
		public void run() {
			int firstmove = -1;
			
			firstmove = isEmptyBoard();
			
			while (canRun){
				//Pick state to expand next
				//expand state (or just get its children if expanded previously)
				//if all states on this level expanded, prune
				if((states.get(0) != null)&&(currentLevel <= states.get(0).getLevel())){
					if(!states.get(0).isPruned()){
						states.get(0).makeBabies();
						BoardState[] expandedStates = states.get(0).getChildren();
						if(expandedStates != null){
							for(int i = 0; i < expandedStates.length; i++){
								states.add(expandedStates[i]);
							}
						}
					}
					
					states.remove(states.get(0));
				}else{
					//TODO improve prune
					int mean = 0;
					int stdev = -1;
					
					boolean loop = true;	
					int i = 0;
					
					while(loop){
						if(states.get(i) == null){
							states.remove(i);
							continue;
						}
						
						if(states.get(i).getLevel() > currentLevel){
							if(stdev == -1){
								mean /= (i-1);
								i = 0;
								stdev = 0;
							}
							else{loop = false;}	
						}
						
						if(stdev == -1){
							mean += states.get(i).getIndHeuristic();
						}else{
							stdev = Math.abs(states.get(i).getIndHeuristic() - mean);
						}
						
					}
					
					stdev /= (i-1);
					
					for(int j = 0; j < i; j++){
						if((states.get(j) != null)&&(states.get(j).getIndHeuristic() < (mean - stdev))){
							states.get(j).prune();
						}
					}
				}
				
				//if the move hasn't already been decided (first move given to us)
				if(firstmove == -1){
					//update currentBestMove (make sure to check each state's pruning status as we go)
					//this should amount to comparing global hueristics
					int currentChoice = -1;
					
					//loop through the GLOBAL heuristics of the game state's children and find max
					BoardState[] states = gameState.getChildren();
					
					if(states != null){
						int currentHeuristic = NO_HEURISTIC;
						
						for(int i = 0; i < states.length; i++){
							if((states[i] != null)&&(!states[i].isPruned())){
								if(currentHeuristic < states[i].getGlobalHeuristic()){
									currentChoice = states[i].getAddedCol();
								}
							}
						}
					}
					
					if(currentChoice == -1){
						//loop through game state "moves" and see if any legal, otherwise set = 0
						BoardState[] moves = gameState.getChildren();
						
						if(moves != null){
							for(int i = 0; i < moves.length; i++){
								if(moves[i] != null){
									currentChoice = moves[i].getAddedCol();
								}
							}
						}
						
						currentChoice = 0;
					}
					
					currentBestMove = currentChoice;
				}else{
					currentBestMove = firstmove;
				}
				
				currentLevel++;
			}
		}
		
		public void CNstop() {
			canRun = false;
		}
		
		/**
		 * Determines if current board is empty, and best move if it is
		 * @return -1 if not empty board
		 */
		protected int isEmptyBoard(){
			if(!gameState.boardEmpty()){return -1;}
			
			return (width/2) + 1;
		}
	}

}



