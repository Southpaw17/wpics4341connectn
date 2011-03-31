package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which returns the subset of bags which meet the requirement of
 * being 90% filled
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class PercentFilled implements Constraint {
	
	public PercentFilled(){}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState, Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b : currentValidSet){
			if(((b.getCurrentWeight()*100)/b.getMaximumCapacity()) >= 90){
				valid.add(b);
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
