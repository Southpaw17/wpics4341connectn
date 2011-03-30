package edu.wpi.cs4341.csp.constraints;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;

public class CanFit implements Constraint {
	int weight;
	
	@Override
	public ArrayList<Bag> apply(BagHandler currentHandler, Bag[] currentBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for(Bag b : currentBags){
			if(b.getRemainingCapacity() >= weight){
				temp.add(b);
			}
		}
		
		return temp;
	}
	
	public CanFit(int w){
		weight = w;
	}

}
