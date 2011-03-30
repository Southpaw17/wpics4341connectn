package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

public class UnaryInclusive implements Constraint {

	String[] bags;
	
	@Override
	public ArrayList<Bag> apply(BagHandler currentHandler, Bag[] currentBags) {

		ArrayList<Bag> temp = new ArrayList<Bag>();
		int counter = 0;	// keeps track of the number of items added to the array list
		
		for ( Bag cb : currentBags){
			for (String bag : bags) {
				if ( cb.getBagName().equals( bag ) ) {
					temp.add( currentHandler.getBag( bag ) );
					counter++;
					break;
				}
			}
			
			if ( counter == bags.length )
				break;
		}
		
		
		return temp;
	}
	
	public UnaryInclusive( String[] b ) {
		bags = b;
	}

}
