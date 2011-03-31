package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which states that two given items cannot be in the same bag
 * 
 * @author Ryan O'Meara
 */
public class BinaryInequality implements Constraint {
	private String i1, i2;
	
	/**
	 * Creates a new binary inequality state
	 * @param itemOne The first item in the set
	 * @param itemTwo The second item in the set
	 */
	public BinaryInequality(String itemOne, String itemTwo){
		i1 = itemOne;
		i2 = itemTwo;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState,
			Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b : currentValidSet){
			if(!(b.containsItem(i1)||b.containsItem(i2))){
				valid.add(b);
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
