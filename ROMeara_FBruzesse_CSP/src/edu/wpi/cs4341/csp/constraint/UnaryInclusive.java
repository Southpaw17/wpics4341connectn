package edu.wpi.cs4341.csp.constraint;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.ProblemStep;

/**
 * Constraint which states that the returned set of bags must only consist of 
 * bags in a certain subset
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class UnaryInclusive implements Constraint {
	private String[] bagNames;
	
	/**
	 * @param allowedNames Array of allowed bags
	 */
	public UnaryInclusive(String[] allowedNames){
		bagNames = allowedNames;
	}

	@Override
	public Bag[] updateConstraints(ProblemStep currentState,
			Bag[] currentValidSet) {
		ArrayList<Bag> valid = new ArrayList<Bag>();
		
		for(Bag b : currentValidSet){
			for(String s : bagNames){
				if(s.equals(b.getName())){
					valid.add(b);
					break;
				}
			}
		}
		
		return valid.toArray(new Bag[valid.size()]);
	}

}
