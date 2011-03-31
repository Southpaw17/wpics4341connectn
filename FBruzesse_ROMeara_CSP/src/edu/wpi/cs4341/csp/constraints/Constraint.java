package edu.wpi.cs4341.csp.constraints;



import edu.wpi.cs4341.csp.Bag;

/**
 * Represents a given constraint for the bags
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public interface Constraint {
	
	/**
	 * Applies this constraint to the set of bags using in given bag handler
	 * Note: This function assumes the item is not currently in a bag
	 * @param allBags The current bag handler (and set of bags)
	 * @param currentSubsetBags TODO
	 * @return The allowable set of bags
	 */
	public abstract Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags);
}
