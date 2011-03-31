package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;


import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;
import edu.wpi.cs4341.csp.Item;

public class Equality implements Constraint {

	Item first, second;
	
	@Override
	public Bag[] apply(BagHandler currentHandler, Bag[] currentBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentBags){
			if ( cb.containsItem( first )  || cb.containsItem( second )) {
				temp.clear();
				temp.add( cb );
				return temp.toArray(new Bag[temp.size()]);
			} 
				
			temp.add( cb );
		}
		
		
		return temp.toArray(new Bag[temp.size()]);
	}

	public Equality( Item a, Item b ) {
		first = a;
		second = b;
	}
}
