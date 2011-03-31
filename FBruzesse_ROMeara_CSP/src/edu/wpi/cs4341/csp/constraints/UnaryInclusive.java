package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;

public class UnaryInclusive implements Constraint {

	String[] bags;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {

		ArrayList<Bag> temp = new ArrayList<Bag>();
		int counter = 0;	// keeps track of the number of items added to the array list
		
		for ( Bag cb : currentSubsetBags){
			for (String bag : bags) {
				if ( cb.getBagName().equals( bag ) ) {
					temp.add( cb );
					counter++;
					break;
				}
			}
			
			if ( counter == bags.length )
				break;
		}
		
		
		return temp.toArray(new Bag[temp.size()]);
	}
	
	public UnaryInclusive( String[] b ) {
		bags = b;
	}

}
