package edu.wpi.cs4341.csp.heuristic;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Item;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Bag addition ordering heuristic which returns an arbitrary order
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class ArbitraryBag implements BagHeuristic {

	@Override
	public ProblemStep[] determineNextStep(ProblemStep currentStep, Item toPlace) {
		if((currentStep == null)||(toPlace == null)){return null;}
		
		ArrayList<ProblemStep> steps = new ArrayList<ProblemStep>();
		
		String[] bags = toPlace.getAllowedBags();
		
		for(String b : bags){
			steps.add(currentStep.placeItemInBag(toPlace.getName(), b));
		}
		
		if(steps.size() == 0){return null;}
		
		return steps.toArray(new ProblemStep[steps.size()]);
	}

}
