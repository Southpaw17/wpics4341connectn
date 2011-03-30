package edu.wpi.cs4341.csp.constraints;


import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

public class FitLimit implements Constraint {
	int lower, higher;
	
	@Override
	public ArrayList<Bag> apply(BagHandler currentHandler, Bag[] currentBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for(Bag b : currentBags){
			if((b.getNumberItems() >= lower)&&(b.getNumberItems() <= higher)){
				temp.add(b);
			}
		}
		
		return temp;
	}
	
	public FitLimit(int low, int high){
		lower = low;
		higher = high;
	}

}
