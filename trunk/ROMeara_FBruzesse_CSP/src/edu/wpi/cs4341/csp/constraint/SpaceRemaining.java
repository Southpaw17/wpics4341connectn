package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which returns only the bags which can contain the given amount
 * of items 
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class SpaceRemaining implements Constraint {
	private int maxItems;
	
	/**
	 * @param max Maximum number of items that can be put in the bag
	 */
	public SpaceRemaining(int max){
		maxItems = max;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState,
			Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b : currentValidSet){
			if(b.getContainedItems().length < maxItems){
				valid.add(b);
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
