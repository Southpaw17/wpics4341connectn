package edu.wpi.cs4341.csp.constraint;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which states that two given items must be in the same bag
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class BinaryEquality implements Constraint {
	private String i1, i2;
	
	/**
	 * Creates a new binary equality constraint 
	 * @param itemOne The first item name in the set
	 * @param itemTwo The second item name in the set
	 */
	public BinaryEquality(String itemOne, String itemTwo){
		i1 = itemOne;
		i2 = itemTwo;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState, Bag[] currentValidSet) {
		Bag[] valid = new Bag[1];
		
		//Determine if constraint "active"
		for(Bag b : currentState.getAllBags()){
			if(b.containsItem(i1) || b.containsItem(i2)){
				//Constraint "active"
				for(Bag c : currentValidSet){
					if(c.containsItem(i1)||c.containsItem(i2)){
						valid[0] = c;
						return valid;
					}
				}
				
				//Not found in current valid set
				return new Bag[0];
			}
		}
		
		return currentValidSet;
	}

}
