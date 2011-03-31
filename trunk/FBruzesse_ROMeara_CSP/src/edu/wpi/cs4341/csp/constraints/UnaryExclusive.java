package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

public class UnaryExclusive implements Constraint {

	String[] bags;
	
	@Override
	public Bag[] apply(BagHandler currentHandler, Bag[] currentBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentBags ){
			for (String bag : bags) {
				if ( !( cb.getBagName().equals( bag ) ) ) {
					temp.add( currentHandler.getBag( bag ) );
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
