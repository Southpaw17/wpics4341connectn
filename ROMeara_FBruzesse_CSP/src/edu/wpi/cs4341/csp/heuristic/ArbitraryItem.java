package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.Item;

/**
 * Heuristic which selects an arbitrary item to place next
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class ArbitraryItem implements ItemHeuristic {

	@Override
	public Item selectItem(Item[] choices) {
		if((choices == null)||(choices.length == 0)){return null;}
		
		return choices[0];
	}

}
