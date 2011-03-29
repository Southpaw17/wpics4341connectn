package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;

public class BagHandler {
	ArrayList<Bag> bags;					//list of bags
	ArrayList<Constraint> validChecks;		//List of checks.  Each one will return list of bags which do  
											//satisify the constraint.  Imdividual constraints will include in
											//constructor which bag to check
	
	public BagHandler(){
		bags = new ArrayList<Bag>();
		validChecks = new ArrayList<Constraint>();
	}
	
	/**
	 * @param newBag he Bag to add to this handler
	 */
	public void addBag(Bag newBag){bags.add(newBag);}
	
	/** 
	 * @param con The Constraint to add to this handler
	 */
	public void addConstraint(Constraint con){validChecks.add(con);}
	
	/** @return An independent copy of this bag handler */
	public BagHandler copyHandler(){
		BagHandler ret = new BagHandler();
		
		for(Constraint c : validChecks){ret.addConstraint(c);}
		for(Bag b : bags){ret.addBag(b.copyBag());}
		
		return ret;
	}
	
	/**
	 * @param bagLabel Label of the bag to look for
	 * @return The Bag with the given label, or null if could not be found
	 */
	public Bag getBag(String bagLabel){
		for(Bag b : bags){if(b.getBagName().equals(bagLabel)){return b;}}
		
		return null;
	}
	
	/** @return An array of all the bags in this bag handler */
	public Bag[] getAllBags(){return bags.toArray(new Bag[bags.size()]);}
	
	/** @return whether this bag handler represents a "solved" bag set */
	public boolean isValidSolution(){
		Bag[] currentBags = new Bag[bags.size()];
		Bag[] filteredBags;
		for(int i = 0; i < bags.size(); i++){currentBags[i] = bags.get(i);}
		
		for(Constraint c : validChecks){
			filteredBags = c.apply(this, currentBags);
			if(filteredBags.length < currentBags.length){return false;}
		}
		
		return true;
	}
}
