package edu.wpi.cs4341.csp.constraints;



import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

/**
 * Represents a given constraint for the bags
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public interface Constraint {
	
	/**
	 * Applies this constraint to the set of bags using in given bag handler
	 * Note: This function assumes the item is not currently in a bag
	 * @param currentHandler The current bag handler (and set of bags)
	 * @param currentBags TODO
	 * @return The allowable set of bags
	 */
	public abstract Bag[] apply(BagHandler currentHandler, Bag[] currentBags);
}
