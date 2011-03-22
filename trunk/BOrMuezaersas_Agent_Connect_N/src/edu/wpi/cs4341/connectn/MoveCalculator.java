package edu.wpi.cs4341.connectn;

public class MoveCalculator implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	// TODO NEED PUBLIC REGISTER_MOVE FUNCTION
	// TODO NEED GetCurrentBestMove() FUNCTION
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
		
		CalculateHeuristic();
	}
	
	private void CalculateHeuristic(){
		// TODO add functionality to base heuristic on children
		
		heuristic = 5;
	}
	
	/**
	 * Returns the heuristic value for this state
	 * 
	 * @return returns the heuristic
	 */
	public int GetHeuristic() {
		return heuristic;
	}
	
	/**
	 * Creates the children for this state.  These are assigned
	 * to an array since there will always be the same number of
	 * children (equal to the branching factor) which can be sent
	 * to the move calculator to determine which should be persued
	 * further.
	 */
	public void MakeBabies() {
		// TODO create children and store them in the array
		
		CalculateHeuristic();	// recalculates the heuristic for this state based on the heuristic values of its children
	}
	
}