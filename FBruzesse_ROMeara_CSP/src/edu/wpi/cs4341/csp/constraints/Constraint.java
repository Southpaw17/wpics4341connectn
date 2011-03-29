package edu.wpi.cs4341.csp.constraints;

import java.util.ArrayList;

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
	 * @param currentHandler The current bag handler (and set of bags)
	 * @return The allowable set of bags
	 */
	public abstract ArrayList<Bag> apply(BagHandler currentHandler);
}
