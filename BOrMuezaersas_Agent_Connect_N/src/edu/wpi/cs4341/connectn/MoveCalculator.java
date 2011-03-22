package edu.wpi.cs4341.connectn;

public class MoveCalculator {

	State gameState;
	
	public MoveCalculator( int row, int col ) {
		int[][] temp = new int[row][col];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				temp[i][j] = 0;
		
		gameState = new State( temp );
	}
	
	public CNThread run() {
		
		CNThread currentThread = new CNThread();
		currentThread.run();
		
		return currentThread;
	}

	// TODO NEED PUBLIC REGISTER_MOVE FUNCTION
	// TODO NEED GetCurrentBestMove() FUNCTION
	
	/*
	 * Receives a move and updates the state
	 * 
	 *  @param col		 - the column the move is made in
	 *  @param player	 - which player made the move
	 */
	public void recieveMove( int col, int player) {
		gameState = gameState.makeMove( col, player );
	}
}

class CNThread extends Thread {
	
	private boolean canRun;
	
	public CNThread() {
		super();
		
		canRun = true;
	}
	
	@Override
	public void run() {
		
		while ( canRun )
		{
			// TODO This is where the thread does stuff.  This should be as short as possible so canRun gets checked often
		}
	}
	
	public void CNstop() {
		canRun = false;
	}
}

// This class stores the state of a game 
class State {
	
	private int[][] state;		// stores the state
	private State[] children;	// stores the children of this state
	private int heuristic;		// stores the heuristic value of this state
	
	public State( ) {
		
	}
	
	/**
	 * Initializes the State class with a predefined state
	 * 
	 * @param newstate - the state you want this state to store and evaluate
	 */
	public State( int[][] newstate ) {
		state = newstate;
		
		calculateHeuristic();
	}
	
	private void calculateHeuristic(){
		// TODO add functionality to base heuristic on children
		
		heuristic = 5;
	}
	
	/**
	 * Returns the heuristic value for this state
	 * 
	 * @return returns the heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}
	
	/**
	 * Creates the children for this state.  These are assigned
	 * to an array since there will always be the same number of
	 * children (equal to the branching factor) which can be sent
	 * to the move calculator to determine which should be persued
	 * further.
	 */
	public void makeBabies( int player ) {
		
		for (int i = 0; i < state[0].length; i++) {
			children[i] = makeMove( i, player * -1 );
		}
		
		calculateHeuristic();	// recalculates the heuristic for this state based on the heuristic values of its children
	}
	
	/*
	 * Determines if a given move is legal
	 * 
	 * @param col - the column that the move will be placed in
	 * @return - returns true if the move is legal (column has at least one empty space), false if the column is full
	 */
	public boolean checkMove( int col ) {
		if (state[state.length - 1][col] == 0)
			return true;
		
		return false;
	}
	
	/*
	 * Makes a move in a given column
	 * 
	 * @param col - the column the move will be made in
	 * @return - returns the state which would exist if the move was made, returns null if the move was illegal (i.e. the column was already full)
	 */
	public State makeMove( int col, int player ) {
		if ( !checkMove( col ) )
			return null;
		
		int[][] moved = state;
		
		for (int i = 0; i < moved.length; i++) {
			if ( moved[i][col] == 0 )
			{
				moved[i][col] = player;		// TODO update to create a value based on which player's turn it is
				break;
			}
		}
		
		return new State( moved );
	}
}