package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.Item;

public class Inequality implements Constraint {

	Item first, second;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {
		
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag cb : currentSubsetBags){
			
			if ( !( cb.containsItem( first )  || cb.containsItem( second ) ) ) {
				temp.add( cb );
			} 
				
		}
		
		return temp.toArray(new Bag[temp.size()]);
	}
	
	public Inequality( Item a, Item b ) {
		first = a;
		second = b;
	}

}
