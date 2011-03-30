package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;
import edu.wpi.cs4341.csp.Item;

public class Inequality implements Constraint {

	Item first, second;
	
	@Override
	public ArrayList<Bag> apply(BagHandler currentHandler, Bag[] currentBags) {
		
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentBags){
			
			if ( !( cb.containsItem( first )  || cb.containsItem( second ) ) ) {
				temp.add( cb );
			} 
				
		}
		
		return temp;
	}
	
	public Inequality( Item a, Item b ) {
		first = a;
		second = b;
	}

}
