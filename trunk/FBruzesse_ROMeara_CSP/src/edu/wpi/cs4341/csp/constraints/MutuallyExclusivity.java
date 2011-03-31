package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.Item;

public class MutuallyExclusivity implements Constraint {

	Item first, second;
	String firstBag, secondBag;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {
		
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for ( Bag curr : currentSubsetBags ) {
			temp.add( curr );
		}
		
		for ( Bag cb : allBags ){
			if ( cb.getBagName().equals( firstBag )  && ( cb.containsItem( first ) || cb.containsItem( second ) ) ) {
				for ( Bag bag : currentSubsetBags ) {
					if ( bag.getBagName().equals( second ) ) {
						temp.remove( bag );
						return temp.toArray(new Bag[temp.size()]);
					}
				}
				
				return temp.toArray(new Bag[temp.size()]);
			} 
			
			if ( cb.getBagName().equals( second )  && ( cb.containsItem( first ) || cb.containsItem( second ) ) ) {
				for ( Bag bag : currentSubsetBags ) {
					if ( bag.getBagName().equals( first ) ) {
						temp.remove( bag );
						return temp.toArray(new Bag[temp.size()]);
					}
				}
				
				return temp.toArray(new Bag[temp.size()]);
			}	
			
		}
		
		
		return temp.toArray(new Bag[temp.size()]);
	}

	public MutuallyExclusivity( Item f, Item s, String p, String q ) {
		first = f;
		second = s;
		firstBag = p;
		secondBag = q;
	}
}
