package edu.wpi.cs4341.csp.heuristic;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Item;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Heuristic which selects the steps to try in the order of how much they 
 * constrain other items, from least to greatest
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class LCV implements BagHeuristic {

	@Override
	public ProblemStep[] determineNextStep(ProblemStep currentStep, Item toPlace) {
		if((currentStep == null)||(toPlace == null)){return null;}
		
		ArrayList<ProblemStep> steps = new ArrayList<ProblemStep>();
		
		for(String b : toPlace.getAllowedBags()){
			if(steps.size() == 0){
				ProblemStep hold = currentStep.placeItemInBag(toPlace.getName(), b);
				if(hold != null){steps.add(hold);}
			}else{
				//place in correct order
				ProblemStep hold = currentStep.placeItemInBag(toPlace.getName(), b);
				if(hold != null){
					for(int i = 0; i < steps.size(); i++){
						if(steps.get(i).getPossibleChoices() < hold.getPossibleChoices()){
							steps.add(i, hold);
							break;
						}else if(i == (steps.size() - 1)){
							steps.add(hold);
							break;
						}
					}
				}
			}
		}
		
		if(steps.size() == 0){return null;}
		
		return steps.toArray(new ProblemStep[steps.size()]);
	}

}
