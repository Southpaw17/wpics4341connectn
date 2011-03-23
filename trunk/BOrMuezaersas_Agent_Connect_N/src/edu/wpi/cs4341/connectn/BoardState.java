package edu.wpi.cs4341.connectn;

//This class stores the state of a game 
public class BoardState {
	
	private int[][] state;		// stores the state
	private BoardState[] children;	// stores the children of this state
	private int heuristic;		// stores the (absolute) heuristic value of this state
	private int player;			// stores the player who just made the move (use this players perspective)
	private int[] heightmap;    // Simple cached map of height of columns to easily identify among a given state's children
	
	/**
	 * Initializes the State class with a predefined state
	 * 
	 * @param newstate - the state you want this state to store and evaluate
	 */
	public BoardState( int[][] newstate , int p) {
		state = newstate;
		heightmap = new int[state.length];
		children = new BoardState[state.length];
		player = p;
		
		heightmap = calculateHeightMap(state);
		calculateHeuristic();
	}
	
	/**
	 * Calculates a height map
	 */
	private int[] calculateHeightMap(int[][] board){
		if(board == null){return null;}
		
		int[] tempheightmap = new int[heightmap.length];
		for(int curCol = 0; curCol < tempheightmap.length; curCol++){
			tempheightmap[curCol] = 0;
			for(int curRow = 0; curRow < board[0].length; curRow++){
				if(board[curCol][curRow] == 0){
					tempheightmap[curCol] = curRow;
					break;
				}else if(curRow == board[0].length - 1){
					tempheightmap[curCol] = curRow + 1;
				}
			}
		}
		
		return tempheightmap;
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
		
		// TODO ... (also, remember the heuristic is made relative to a player in the get heuristic function, this is calcing the absolute heuristic)
		heuristic = 5;		
	}
	
	/**
	 * Calculates the number of available sequences available for the given player on the given board
	 * @param startCol
	 * @param startRow
	 * @param p
	 * @return
	 */
	private int[] calcConnectionsFromLocation( int startCol, int startRow, int p)
	{
		//int sequence = 0;
		int[] retarr = new int[3];
		
		retarr[0] = 0;
		retarr[1] = 0;
		retarr[2] = 0;
		
		/*for ( int col = startCol; col < startCol + MoveCalculator.numConnect - 1; col++ ) {
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
			retarr[2]++;*/
		
		int[] holder = new int[3];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 0, 1);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, 1);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, 0);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, -1);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		
		return retarr;
	}
	
	private int[] calcConnectionsInDirection(int startCol, int startRow, int p, int colDir, int rowDir){
		int sequence = 0;
		int[] retarr = new int[3];
		
		retarr[0] = 0;
		retarr[1] = 0;
		retarr[2] = 0;
		
		for ( int col = startCol; col < startCol + colDir*(RefInterface.getNumRequired() - 1); col=col+colDir ) {
			for ( int row = startRow; row < startRow + rowDir*(RefInterface.getNumRequired() - 1); row=row+rowDir ) {
				if ( col >= state.length || col <= 0 || row >= state[0].length || row <= 0 || state[col][row] == -p) {
					col = startCol + colDir*(RefInterface.getNumRequired() - 1);
					sequence = 0;
					break;
				}
				
				if (state[col][row] == p)
				{
					sequence++;
				}
			}
		}
		
		if ( sequence == RefInterface.getNumRequired() - 2 )
			retarr[0]++;
		else if ( sequence == RefInterface.getNumRequired() - 1 )
			retarr[1]++;
		else if (sequence == RefInterface.getNumRequired() )
			retarr[2]++;
		
		return retarr;
	}
	
	/**
	 * Returns the heuristic value for this state
	 * 
	 * @return returns the heuristic
	 */
	public int getHeuristic() {
		return heuristic*player;
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
	
	/**
	 * @return The children of this State
	 */
	public BoardState[] getChildren(){return children;}
	
	/**
	 * Determines if a given move is legal
	 * 
	 * @param col - the column that the move will be placed in
	 * @return - returns true if the move is legal (column has at least one empty space), false if the column is full
	 */
	public boolean checkMove( int col ) {
		if (state[col][state[0].length - 1] == 0)
			return true;
		
		return false;
	}
	
	/**
	 * Makes a move in a given column
	 * 
	 * @param col - the column the move will be made in
	 * @return - returns the state which would exist if the move was made, returns null if the move was illegal (i.e. the column was already full)
	 */
	public BoardState makeMove( int col, int player ) {
		//TODO this should check if state exists/is child and find it
		if ( !checkMove( col ) )
			return null;
		
		int[][] moved = state;
		
		for (int i = 0; i < moved.length; i++) {
			if ( moved[col][i] == 0 )
			{
				moved[col][i] = player;		
				break;
			}
		}
		
		if(children != null){
			for(int i = 0; i < children.length; i++){
				if(children[i] != null){
					if(children[i].simpleStateCompare(moved)){
						return children[i];
					}
				}
			}
		}
		
		return new BoardState( moved, player );
	}
	
	/**
	 * Determines if states have same column height makeup - useful for identifying among a given state's 
	 * children (to advance through the model)
	 * @param compareTo The State to compare with
	 * @return true if height maps are equal, false otherwise
	 */
	public boolean simpleStateCompare(BoardState compareTo){
		if((heightmap == null)||(compareTo.heightmap == null)){return false;}
		
		if(heightmap.length != compareTo.heightmap.length){return false;}
		
		for(int i = 0; i < heightmap.length; i++){
			if(heightmap[i] != compareTo.heightmap[i]){return false;}
		}
		
		return true;
	}
	
	/**
	 * Determines if states have same column height makeup - useful for identifying among a given state's 
	 * children (to advance through the model)
	 * @param compareTo The State array to compare with
	 * @return true if height maps are equal, false otherwise
	 */
	public boolean simpleStateCompare(int[][] compareTo){
		int[] tempMap = calculateHeightMap(compareTo);
		
		if(heightmap.length != tempMap.length){return false;}
		
		for(int i = 0; i < heightmap.length; i++){
			if(heightmap[i] != tempMap[i]){return false;}
		}
		
		return true;
	}
}