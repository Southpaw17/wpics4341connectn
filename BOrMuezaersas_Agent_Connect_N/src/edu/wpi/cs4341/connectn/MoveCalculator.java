package edu.wpi.cs4341.connectn;

public class MoveCalculator {

	State gameState;
	static int numConnect;
	
	public MoveCalculator( int row, int col, int n ) {
		int[][] temp = new int[col][row];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				temp[j][i] = 0;
		
		gameState = new State( temp, RefInterface.PLAYER );
		numConnect = n;
	}
	
	public CNThread run() {
		
		CNThread currentThread = new CNThread();
		currentThread.run();
		
		return currentThread;
	}

	// TODO NEED getCurrentBestMove() FUNCTION
	public int getCurrentBestMove() {
		return 0;
	}
	
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
	private int player;			// stores the player whose perspective to take this from
	
	public State( ) {
		
	}
	
	/**
	 * Initializes the State class with a predefined state
	 * 
	 * @param newstate - the state you want this state to store and evaluate
	 */
	public State( int[][] newstate , int p) {
		state = newstate;
		children = new State[state.length];
		player = p;
		
		calculateHeuristic();
	}
	
	private void calculateHeuristic(){
		// TODO add functionality to base heuristic on children
		
		int[] us;
		int[] them;
		
		for ( int col = 0; col < state.length; col++ )
		{
			for ( int row = 0; row < state[0].length; row++ )
			{
				us = calcConnectionsFromLocation( col, row, player );
				them = calcConnectionsFromLocation( col, row, -player );
			}
		}
		
		heuristic = 5;
	}
	
	private int[] calcConnectionsFromLocation( int startCol, int startRow, int p)
	{
		int sequence = 0;
		int[] retarr = new int[3];
		
		retarr[0] = 0;
		retarr[1] = 0;
		retarr[2] = 0;
		
		for ( int col = startCol; col < startCol + MoveCalculator.numConnect - 1; col++ ) {
			for ( int row = startRow; row < startRow + MoveCalculator.numConnect - 1; row++ ) {
				if ( col >= state.length || row >= state[0].length || state[col][row] == -p) {
					col = startCol + MoveCalculator.numConnect - 1;
					sequence = 0;
					break;
				}
				
				if (state[col][row] == p)
				{
					sequence++;
				}
			}
		}
		
		if ( sequence == MoveCalculator.numConnect - 2 )
			retarr[0]++;
		else if ( sequence == MoveCalculator.numConnect - 1 )
			retarr[1]++;
		else if (sequence == MoveCalculator.numConnect )
			retarr[2]++;
		
		sequence = 0;
		
		for ( int col = startCol; col < startCol + MoveCalculator.numConnect - 1; col++ ) {
				
			if ( col >= state.length || state[col][startRow] == -p) {
				sequence = 0;
				break;
			}
				
			if (state[col][startRow] == p)
			{
				sequence++;
			}
		}
		
		if ( sequence == MoveCalculator.numConnect - 2 )
			retarr[0]++;
		else if ( sequence == MoveCalculator.numConnect - 1 )
			retarr[1]++;
		else if (sequence == MoveCalculator.numConnect )
			retarr[2]++;
		
		sequence = 0;
		

		for ( int row = startRow; row < startRow + MoveCalculator.numConnect - 1; row++ ) {
			if ( row >= state[0].length || state[startCol][row] == -p) {
				sequence = 0;
				break;
			}
				
			if (state[startCol][row] == p)
			{
				sequence++;
			}
		}

		
		if ( sequence == MoveCalculator.numConnect - 2 )
			retarr[0]++;
		else if ( sequence == MoveCalculator.numConnect - 1 )
			retarr[1]++;
		else if (sequence == MoveCalculator.numConnect )
			retarr[2]++;
		
		sequence = 0;
		
		for ( int col = startCol; col < startCol + MoveCalculator.numConnect - 1; col++ ) {
			for ( int row = startRow; row > startRow - (MoveCalculator.numConnect - 1); row-- ) {
				if ( col >= state.length || row <= 0 || state[col][row] == -p) {
					col = startCol + MoveCalculator.numConnect - 1;
					sequence = 0;
					break;
				}
				
				if (state[col][row] == p)
				{
					sequence++;
				}
			}
		}
		
		if ( sequence == MoveCalculator.numConnect - 2 )
			retarr[0]++;
		else if ( sequence == MoveCalculator.numConnect - 1 )
			retarr[1]++;
		else if (sequence == MoveCalculator.numConnect )
			retarr[2]++;
		
		return retarr;
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
	public void makeBabies( ) {
		
		for (int i = 0; i < state[0].length; i++) {
			children[i] = makeMove( i, -player );
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
				moved[i][col] = player;		
				break;
			}
		}
		
		return new State( moved, player );
	}
}