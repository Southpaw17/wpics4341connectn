package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.Item;

/**
 * Determines the next item to place uses a variety of methods
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public interface ItemHeuristic {
	/**
	 * Selects the next item to be placed
	 * @param choices The possible choices for the algorithm
	 * @return The item to place, or null if no choices presented
	 */
	public abstract Item selectItem(Item[] choices);
}
