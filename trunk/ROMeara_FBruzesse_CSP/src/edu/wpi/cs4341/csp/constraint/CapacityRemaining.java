package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which returns only the bags which can contain the given amount
 * of weight 
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class CapacityRemaining implements Constraint {
	private int checkWeight;
	
	/**
	 * Creates a new capacity remaining constraint
	 * @param weight The weight to check
	 */
	public CapacityRemaining(int weight){
		checkWeight = weight;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState, Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b : currentValidSet){
			if(b.getRemainingCapacity() >= checkWeight){
				valid.add(b);
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
