package edu.wpi.cs4341.csp.constraint;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Defines a constraint which determines which bags are valid.
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public interface Constraint {
	/**
	 * Narrows a set of bags to those which meet the given constraint
	 * @param currentState The set of all possible bags
	 * @param currentValidSet The current set of bags to choose from
	 * @return The set of bags that can now be chosen from
	 */
	public abstract Bag[] updateConstraints(ProblemStep currentState, Bag[] currentValidSet);
}
