package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which returns the set of bags which are within the given range
 * of items
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class InCapacityRange implements Constraint {
	private int minCap, maxCap;
	
	/**
	 * @param min Minimum number of items
	 * @param max Maximum number of items
	 */
	public InCapacityRange(int min, int max){
		minCap = min;
		maxCap = max;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState, Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b: currentValidSet){
			int cur = b.getContainedItems().length;
			
			if((cur >= minCap)&&(cur <= maxCap)){
				valid.add(b);
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
