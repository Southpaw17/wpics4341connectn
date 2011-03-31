package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;

public class FitLimit implements Constraint {
	int lower, higher;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for(Bag b : currentSubsetBags){
			if((b.getNumberItems() >= lower)&&(b.getNumberItems() <= higher)){
				temp.add(b);
			}
		}
		
		return temp.toArray(new Bag[temp.size()]);
	}
	
	public FitLimit(int low, int high){
		lower = low;
		higher = high;
	}

}
