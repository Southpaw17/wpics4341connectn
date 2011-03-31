package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which states that if a given item is in either of two bags,
 * a different item cannot be in the bag in the stated two bag state that
 * the first item is not in
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class MutuallyExclusive implements Constraint {
	private String item1, item2, bag1, bag2;
	
	/**
	 * @param i1 First item
	 * @param i2 Second item
	 * @param b1 First bag
	 * @param b2 Second bag
	 */
	public MutuallyExclusive(String i1, String i2, String b1, String b2){
		item1 = i1;
		item2 = i2;
		bag1 = b1;
		bag2 = b2;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState,
			Bag[] currentValidSet) {
		//Check if "active" (either of bags even possible)
		for(Bag b : currentValidSet){
			if(b.getName().equals(bag1)||b.getName().equals(bag2)){
				ArrayList<Bag> valid = new ArrayList<Bag>();
				for(Bag a : currentValidSet){
					if(a.getName().equals(bag1)){
						Bag c = currentState.getBag(bag2);
						
						if(!(c.containsItem(item1)||c.containsItem(item2))){
							valid.add(a);
						}
					}else if(a.getName().equals(bag2)){
						Bag c = currentState.getBag(bag1);
						
						if(!(c.containsItem(item1)||c.containsItem(item2))){
							valid.add(a);
						}
					}else{
						valid.add(a);
					}
				}
				
				return valid.toArray(new Bag[valid.size()]);
			}
		}
		
		return currentValidSet;
	}

}
