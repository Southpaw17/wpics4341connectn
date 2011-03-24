package edu.wpi.cs4341.connectn;

//This class stores the state of a game 
public class BoardState {
	
	private int[][] state;			//stores the state
	private BoardState[] children;	//stores the children of this state
	private int indHeuristic;		//stores the (ABSOLUTE) heuristic value of this state
	private int player;				//stores the player who just made the move (use this players perspective, this is result of this player making the move)
	private int[] heightmap;    	//Simple cached map of height of columns to easily identify among a given state's children
	private boolean pruned;			//stores if this state was pruned from cosideration
	private int level;				//stores the level of the tree this state is on
	private int n;					//Number in  row
	private int added;				//column added to to get this state
	
	private static final int NMTWO_MARK = 4;
	private static final int NMONE_MARK = 2*Math.abs(NMTWO_MARK);
	
	/**
	 * Initializes the State class with a predefined state
	 * 
	 * @param newstate - the state you want this state to store and evaluate
	 */
	public BoardState(int[][] newstate , int numinrow, int p, int addCol) {
		added = addCol;
		state = new int[newstate.length][newstate[0].length];
		
		for(int i = 0; i < newstate.length; i++){
			for(int j = 0; j < newstate[0].length; j++){
				state[i][j] = newstate[i][j];
			}
		}
		
		heightmap = new int[state.length];
		children = new BoardState[state.length];
		player = p;
		pruned = false;
		n = numinrow;
		
		heightmap = calculateHeightMap(state);
		level = 0;
		for(int i = 0; i < heightmap.length; i++){
			level += heightmap[i];
			children[i] = null;
		}
		calculateHeuristic();
	}
	
	public int getPlayer(){
		return player;
	}
	
	/**
	 * Calculates a height map
	 */
	protected int[] calculateHeightMap(int[][] board){
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
	
	/**
	 * Calculates and stores the ABSOLUTE heuristic
	 */
	protected void calculateHeuristic(){
		int[] positivePlayer = new int[3];
		int[] negativePlayer = new int[3];
		int[] holder = new int[3];
		int rowZeroCount = 0;
		
		positivePlayer[0] = 0;
		positivePlayer[1] = 0;
		positivePlayer[2] = 0;
		
		negativePlayer[0] = 0;
		negativePlayer[1] = 0;
		negativePlayer[2] = 0;
		
		//create two map and three map, to track number of discrete spots which result in two and threes,
		//and how many moves it takes to get there
		int[][] pMap = new int[state.length][state[0].length];
		int[][] nMap = new int[state.length][state[0].length];
		
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				pMap[i][j] = state[i][j];
				nMap[i][j] = state[i][j];
			}
		}
		
		for (int col = 0; col < state.length; col++){
			rowZeroCount = 0;
			
			for (int row = 0; row < state[0].length; row++){
				if(state[col][row] == 0){rowZeroCount++;}
				
				//short circuit if no more pieces above us
				if(rowZeroCount == state.length){
					col = state.length;
					break;
				}
				
				holder = calcConnectionsFromLocation(col, row, RefInterface.PLAYER, pMap);
				positivePlayer[0] += holder[0];
				positivePlayer[1] += holder[1];
				positivePlayer[2] += holder[2];
				holder = calcConnectionsFromLocation(col, row, RefInterface.OPPONENT, nMap);
				negativePlayer[0] += holder[0];
				negativePlayer[1] += holder[1];
				negativePlayer[2] += holder[2];
			}
		}
		
		int pNMTwos = 0, pNMOnes = 0, nNMTwos = 0, nNMOnes = 0;      //Number of dicrete n-1 and n-2
		int pbNMTwos = 0, pbNMOnes = 0, nbNMTwos = 0, nbNMOnes = 0;  //Number of spaces necessary to build all n-1 and n-2
		
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				if(pMap[i][j] == NMTWO_MARK){
					pNMTwos++;
				}else if(pMap[i][j] == -1*NMTWO_MARK){
					pbNMTwos++;
				}
				
				if(nMap[i][j] == NMTWO_MARK){
					nNMTwos++;
				}else if(nMap[i][j] == -1*NMTWO_MARK){
					nbNMTwos++;
				}

				if(pMap[i][j] == NMONE_MARK){
					pNMOnes++;
				}else if(pMap[i][j] == -1*NMONE_MARK){
					pbNMOnes++;
				}

				if(nMap[i][j] == NMONE_MARK){
					nNMOnes++;
				}else if(nMap[i][j] == -1*NMONE_MARK){
					nbNMOnes++;
				}
			}
		}
		
		// TODO Imrpove heuristic (also, remember the heuristic is made relative to a player in the get heuristic function, this is calcing the absolute heuristic)
		if((player > 0)&&(positivePlayer[2] > 0)){indHeuristic = MoveCalculator.MAX_HEURISTIC;}
		else if((player < 0)&&(negativePlayer[2] > 0)){indHeuristic = -1*MoveCalculator.MAX_HEURISTIC;}
		else{
			//TODO possible mess with how the discrete number of threes and required moves to get them represented
			//indHeuristic = 10*positivePlayer[0] + 20*positivePlayer[1] - 10*negativePlayer[0] - 20*negativePlayer[1];
			//indHeuristic = indHeuristic + 4*pNMOnes + 2*pNMTwos + 2*nbNMOnes + nbNMTwos;
			//indHeuristic = indHeuristic - 4*nNMOnes - 2*nNMTwos - 2*pbNMOnes - pbNMTwos;
			indHeuristic = 2*positivePlayer[0] + 3*positivePlayer[1]*positivePlayer[1] - 2*negativePlayer[0] - 3*negativePlayer[1]*negativePlayer[1];
			indHeuristic = indHeuristic + pNMOnes*pNMOnes*pNMOnes*pNMOnes/2 + pNMTwos*pNMTwos/2 + 2*nbNMOnes*nbNMOnes + 2*nbNMTwos;
			indHeuristic = indHeuristic - nNMOnes*nNMOnes*nNMOnes*nNMOnes/2 - nNMTwos*nNMTwos/2 - 2*pbNMOnes*pbNMOnes - 2*pbNMTwos;
		}
	}
	
	
	/**
	 * Calculates the connections for a given player from a given position
	 * @param startCol The starting column position 	[0, state.length]
	 * @param startRow The started row position 		[0, state[0].length]
	 * @param p The player to consider for 				(RefInterface.OPPONENT or RefInterface.PLAYER)
	 * @param stateMap The copy of the board layout to mark
	 * @return An array of connections:
	 * 		ret[0] = number of n-2 length sequences which are able to create a sequence of n length
	 * 		ret[1] = number of n-1 length sequences which are able to create a sequence of n length
	 * 		ret[2] = number of n length sequences of n length
	 */
	protected int[] calcConnectionsFromLocation( int startCol, int startRow, int p, int[][] stateMap){
		int[] retarr = new int[3];
		
		retarr[0] = 0;
		retarr[1] = 0;
		retarr[2] = 0;
		
		int[] holder = new int[3];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 0, 1, stateMap);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, 1, stateMap);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, 0, stateMap);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		holder = calcConnectionsInDirection(startCol, startRow, p, 1, -1, stateMap);
		retarr[0] += holder[0];
		retarr[1] += holder[1];
		retarr[2] += holder[2];
		
		
		return retarr;
	}
	
	/**
	 * Calculates the connections in a given direction (direction denoted by positive/negative value in
	 * rowDir and colDir
	 * @param startCol The starting column position 	[0, state.length]
	 * @param startRow The started row position 		[0, state[0].length]
	 * @param p The player to consider for 				(RefInterface.OPPONENT or RefInterface.PLAYER)
	 * @param colDir The direction to move column-wise 	[-1, 0, 1]
	 * @param rowDir The direction to move row-wise 	[-1, 0, 1]
	 * @param stateMap The copy of the board layout to mark
	 * @return An array of connections:
	 * 		ret[0] = number of n-2 length sequences which are able to create a sequence of n length
	 * 		ret[1] = number of n-1 length sequences which are able to create a sequence of n length
	 * 		ret[2] = number of n length sequences of n length
	 */
	protected int[] calcConnectionsInDirection(int startCol, int startRow, int p, int colDir, int rowDir,  int[][] stateMap){
		if(colDir != 0){colDir = colDir/Math.abs(colDir);}
		
		if(rowDir != 0){rowDir = rowDir/Math.abs(rowDir);}
		
		
		int sequence = 0;
		int[] retarr = new int[3];
		
		retarr[0] = 0;
		retarr[1] = 0;
		retarr[2] = 0;
		
		int col = startCol;
		int row = startRow;
		
		for(int itNumber = 0; itNumber < n; itNumber++){
			if ( col >= state.length || col < 0 || row >= state[0].length || row < 0 || state[col][row] == -p) {
				sequence = 0;
				break;
			}
			
			if (state[col][row] == p){sequence++;}
			
			col += colDir;
			row += rowDir;
		}
		
		if (sequence == n - 2){
			retarr[0]++;
			for(int itNumber = 0; itNumber < n; itNumber++){
				markMap(stateMap, NMTWO_MARK, col, row);
				col += colDir;
				row += rowDir;
			}
		}else if ( sequence == n - 1){
			retarr[1]++;
			for(int itNumber = 0; itNumber < n; itNumber++){
				markMap(stateMap, NMONE_MARK, col, row);
				col += colDir;
				row += rowDir;
			}
		}else if (sequence == n){
			retarr[2]++;
		}
		
		return retarr;
	}
	
	/**
	 * Marks a map with n-2 and n-1 spots, giving priority to better spots
	 * @param stateMap The stateMap to mark (is a copy of this state's layout)
	 * @param mark the number ot mark with
	 * @param col The col to start marking at
	 * @param row The row to start marking at
	 * @return -1 if did not mark due to better mark already in place, 0 if did not mark due to actual 
	 * piece/border, 1 if marked
	 */
	private int markMap(int[][] stateMap, int mark, int col, int row){
		try{
			if((stateMap == null)||(row < 0)||(col < 0)||(col > stateMap.length)||(row > stateMap[0].length)){
				return 0;
			}
			
			if((stateMap[col][row] == 0)||((Math.abs(stateMap[col][row]) < Math.abs(mark))&&(Math.abs(stateMap[col][row]) != Math.abs(RefInterface.PLAYER)))){
				int holder = stateMap[col][row];
				stateMap[col][row] = mark;
				int tempMark = mark;
				
				if(mark > 0){tempMark = -mark;}
				
				int retVal = markMap(stateMap, tempMark, col, row-1);
				
				if(retVal == -1){
					stateMap[col][row] = holder;
					return -1;
				}
				
				return 1;
			}else if(stateMap[col][row] > mark){
				return -1;
			}else{
				return 0;
			}
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * @return true if the baord is empty, false otherwise
	 */
	public boolean boardEmpty(){
		for(int i = 0; i < heightmap.length; i++){
			if(heightmap[i] != 0){return false;}
		}
		
		return true;
	}
	
	/**
	 * Returns the heuristic value for this state, adjusted for the player it pertains to
	 * (REALTIVE heuristic)
	 * @return player-realtive heuristic
	 */
	public int getIndHeuristic() {
		return indHeuristic*player;
	}
	
	/**
	 * Returns the global hueristic for this state (mini-maxed hueristic) taking
	 * into account if states are pruned
	 * 
	 * @return The minimaxed hueristic of the state
	 */
	public int getGlobalHeuristic(){
		if(children == null){return indHeuristic;}
		
		if(Math.abs(indHeuristic) == Math.abs(MoveCalculator.MAX_HEURISTIC)){
			return indHeuristic;
		}
		
		int nextLevel = -1;
		
		if(player == RefInterface.PLAYER){
			//minimize
			int min = 0;
			for(int i = 0; i < children.length; i++){
				if(children[i] != null){
					int nextGH = children[i].getGlobalHeuristic();
					if((nextLevel == -1)||(min > nextGH)){
						min = nextGH;
						nextLevel = children[i].getAddedCol();
					}
				}
			}
			
			if(nextLevel != -1){
				return min;
			}
		}else{
			//maximize
			int max = 0;
			
			for(int i = 0; i < children.length; i++){
				if(children[i] != null){
					int nextGH = children[i].getGlobalHeuristic();
					if((nextLevel == -1)||(max < nextGH)){
						max = nextGH;
						nextLevel = i;
					}
				}
			}
			
			if(nextLevel != -1){
				return max;
			}
		}
		
		return indHeuristic;
	}
	
	/**
	 * Creates the children for this state.  These are assigned
	 * to an array since there will always be the same number of
	 * children (equal to the branching factor) which can be sent
	 * to the move calculator to determine which should be persued
	 * further.
	 */
	public void makeBabies( ){
		for (int i = 0; i < state[0].length; i++) {
			if(children[i] == null){
				children[i] = makeMove(i, -player);
			}
		}
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
		if (!checkMove(col))
			return null;
		
		int[][] moved = new int[state.length][state[0].length];
		
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				moved[i][j] = state[i][j];
			}
		}
		
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
		
		return new BoardState(moved, n, player, col);
	}
	
	/**
	 * Marks this states as pruned from consideration
	 */
	public void prune(){pruned = true;}
	
	/**
	 * Unprunes this state, useful if opponent takes state which was pruned from being expanded
	 */
	public void unPrune(){pruned = false;}
	
	/**
	 * @return true if this state is currently pruned, false otherwise
	 */
	public boolean isPruned(){return pruned;}
	
	/**
	 * @return The col added to to get this move.  -1 if board is empty, else [0, board width]
	 */
	public int getAddedCol(){
		return added;
	}
	
	/**
	 * @return The level of the tree this state is on
	 */
	public int getLevel(){return level;}
	
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
