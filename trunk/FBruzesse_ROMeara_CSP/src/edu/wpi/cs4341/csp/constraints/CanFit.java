package edu.wpi.cs4341.csp.constraints;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;

public class CanFit implements Constraint {
	int weight;
	
	@Override
	public Bag[] apply(Bag[] allBags, Bag[] currentSubsetBags) {
		ArrayList<Bag> temp = new ArrayList<Bag>();
		
		for(Bag b : currentSubsetBags){
			if(b.getRemainingCapacity() >= weight){
				temp.add(b);
			}
		}
		
		return temp.toArray(new Bag[temp.size()]);
	}
	
	public CanFit(int w){
		weight = w;
	}

}
