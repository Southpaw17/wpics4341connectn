package edu.wpi.cs4341.connectn;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BoardStateTests {
	BoardState testState;
	BoardState emptyState;
	RefInterface inter;
	
	int[][] state;
	int[][] emptyStateMap;

	@Before
	public void setUpBeforeClass() throws Exception {
		inter = new RefInterface(7, 6, 4, 1, 15);
		emptyStateMap = new int[7][6];
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 6; j++){
				emptyStateMap[i][j] = 0;
			}
		}
		
		emptyState = new BoardState(emptyStateMap, 4, RefInterface.PLAYER, -1);
		
		state = new int[7][6];
		
		state[0][0] = RefInterface.PLAYER;
		state[0][1] = RefInterface.OPPONENT;
		state[1][0] = RefInterface.PLAYER;
		state[2][0] = RefInterface.OPPONENT;
		state[2][1] = RefInterface.PLAYER;
		state[3][0] = RefInterface.OPPONENT;
		state[3][1] = RefInterface.PLAYER;
		state[3][2] = RefInterface.OPPONENT;
		state[3][3] = RefInterface.OPPONENT;
		state[4][0] = RefInterface.PLAYER;
		state[4][1] = RefInterface.OPPONENT;
		state[4][2] = RefInterface.OPPONENT;
		state[4][3] = RefInterface.PLAYER;
		state[4][4] = RefInterface.OPPONENT;
		state[5][0] = RefInterface.PLAYER;
		state[5][1] = RefInterface.OPPONENT;
		state[5][2] = RefInterface.PLAYER;
		state[5][3] = RefInterface.PLAYER;
		state[5][4] = RefInterface.OPPONENT;
		state[5][5] = RefInterface.PLAYER;
		
		testState = new BoardState(state, 4, RefInterface.PLAYER, 5);
	}
	
	@Test
	public void testPointing(){
		class TestCase{
			private int i;
			
			public TestCase(){i=0;}
			
			public void setI(int ic){i = ic;}
			
			public int getI(){return i;}
		}
		
		TestCase test = new TestCase();
		
		ArrayList<TestCase> al = new ArrayList<TestCase>();
		
		al.add(test);
		
		al.get(0).setI(2);
		
		assertEquals("Acts as Pointer", 2, test.getI());
	}

	@Test
	public void testCalculateHeightMap() {
		int[] emptyHeightMap = emptyState.calculateHeightMap(emptyStateMap);
		
		for(int i = 0; i < emptyHeightMap.length; i++){
			assertEquals("Col " + i + " Empty Height Map", emptyHeightMap[i], 0);
		}
		
		int[] stateHeightMap = testState.calculateHeightMap(state);
		
		assertEquals("Col 0 Non-Empty State Map", stateHeightMap[0], 2);
		assertEquals("Col 1 Non-Empty State Map", stateHeightMap[1], 1);
		assertEquals("Col 2 Non-Empty State Map", stateHeightMap[2], 2);
		assertEquals("Col 3 Non-Empty State Map", stateHeightMap[3], 4);
		assertEquals("Col 4 Non-Empty State Map", stateHeightMap[4], 5);
		assertEquals("Col 5 Non-Empty State Map", stateHeightMap[5], 6);
		assertEquals("Col 6 Non-Empty State Map", stateHeightMap[6], 0);
	}
	
	@Test
	public void testCalcConnectionsInDirection() {
		int[] connections;
		
		connections = testState.calcConnectionsInDirection(1, 1, RefInterface.OPPONENT, 1, 1);
		assertEquals("Test NE from 1,1, 2", 1, connections[0]);
	}

	@Test
	public void testCalcConnectionsFromLocation() {
		int[] connections = testState.calcConnectionsFromLocation(1, 1, RefInterface.OPPONENT);
		
		assertEquals("Connection Test 1,1, 2 pieces", 1, connections[0]);
		assertEquals("Connection Test 1,1, 3 pieces", 0, connections[1]);
		assertEquals("Connection Test 1,1, 4 pieces", 0, connections[2]);
	}

	@Test
	public void testBoardEmpty() {
		assertTrue("Empty Board", emptyState.boardEmpty());
		assertFalse("Non-Empty Board", testState.boardEmpty());
	}
	
	@Test
	public void testCalculateHeuristic() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIndHeuristic(){
		fail("Not yet implemented");
	}

	@Test
	public void testGetGlobalHeuristic() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeBabies() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChildren() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrune() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnPrune() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPruned() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLevel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimpleStateCompareBoardState() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimpleStateCompareIntArrayArray() {
		fail("Not yet implemented");
	}

}
