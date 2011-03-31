package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;

public class UnaryExclusive implements Constraint {

	String[] bags;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentSubsetBags ){
			for (String bag : bags) {
				if ( !( cb.getBagName().equals( bag ) ) ) {
					temp.add( cb );
					break;
				}
			}
		}
		
		
		return temp.toArray(new Bag[temp.size()]);
	}
	
	public UnaryExclusive( String[] b ) {
		bags = b;
	}

}
