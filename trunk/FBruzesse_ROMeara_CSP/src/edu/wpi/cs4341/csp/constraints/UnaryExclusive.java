package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

public class UnaryExclusive implements Constraint {

	Bag[] bags;
	
	@Override
	public ArrayList<Bag> apply(BagHandler currentHandler, Bag[] currentBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentBags ){
			for (Bag bag : bags) {
				if ( cb != bag ) {
					temp.add( bag );
					break;
				}
			}
		}
		
		
		return temp;
	}
	
	public UnaryExclusive( Bag[] b ) {
		bags = b;
	}

}
