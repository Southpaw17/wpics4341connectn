package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.Item;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Determines which order to attempt to place things in bags
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public interface BagHeuristic {
	/**
	 * Determines what order of problem steps to use
	 * @param currentStep The step the problem is currently on
	 * @param toPlace The item to place
	 * @return An ordered array of the steps to take - take step 0 first, or null if 
	 * no current step or item to place was provided, or the item to place cannot be placed
	 */
	public abstract ProblemStep[] determineNextStep(ProblemStep currentStep, Item toPlace);
}
