package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which states the returned set of bags cannot include any of the 
 * bags in a given subset
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class UnaryExclusive implements Constraint {
	private String[] bagNames;
	
	/**
	 * @param notAllowed Array of names of bags not allowed
	 */
	public UnaryExclusive(String[] notAllowed){
		bagNames = notAllowed;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState,
			Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		boolean found = false;
		for(Bag b : currentValidSet){
			found = false;
			for(String s : bagNames){
				if(s.equals(b.getName())){
					found = true;
					break;
				}
			}
			
			if(!found){valid.add(b);}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
