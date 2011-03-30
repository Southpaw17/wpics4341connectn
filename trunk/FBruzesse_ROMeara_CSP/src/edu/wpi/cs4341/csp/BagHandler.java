package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;

public class BagHandler {
	protected boolean complete;
	ArrayList<Bag> bags;					//list of bags
	ArrayList<Constraint> validChecks;		//List of checks.  Each one will return list of bags which do  
											//satisfy the constraint.  Individual constraints will include in
											//constructor which bag to check
	
	public BagHandler(){
		complete = false;
		bags = new ArrayList<Bag>();
		validChecks = new ArrayList<Constraint>();
	}
	
	/**
	 * Checks if this set is complete
	 * @param items The items being put in bags
	 */
	public void checkComplete(Item[] items){
		boolean found = false;
		
		for(Item i : items){
			found = false;
			
			for(Bag b : bags){
				if(b.containsItem(i)){
					found = true;
					break;
				}
			}
			
			if(!found){
				//if still possible sets, not complete, exit
				if(i.applyConstraints(this).size() > 0){
					complete = false;
					return;
				}
			}
		}
		
		complete = true;
	}
	
	/** @return true if this is a complete set, false otherwise */
	public boolean isComplete(){return complete;}
	
	/** @return The number of items in bags in this set */
	public int totalItems(){
		int ret = 0;
		
		for(Bag b : bags){ret += b.getNumberItems();}
		
		return ret;
	}
	
	/** @return The total weight of the bags in this set */
	public int totalWeight(){
		int ret = 0;
		
		for(Bag b : bags){ret += (b.getPossibleCapacity() - b.getRemainingCapacity());}
		
		return ret;
	}
	
	/** @return The units of unfilled weight in this set */
	public int wastedWeight(){
		int ret = 0;
		
		for(Bag b : bags){ret += b.getRemainingCapacity();}
		
		return ret;
	}
	
	/** @return The standard deviation of weight in bags in this set */
	public int weightEvenness(){
		int mean = totalWeight()/bags.size();
		int meanDev = 0;
		
		for(Bag b : bags){
			meanDev += Math.abs(mean - (b.getPossibleCapacity() - b.getRemainingCapacity()));
		}
		
		meanDev /= bags.size();
		
		return meanDev;
	}
	
	/** @return The standard deviation of items in bags in this set */
	public int itemEvenness(){
		int mean = totalItems()/bags.size();
		int meanDev = 0;
		
		for(Bag b : bags){
			meanDev += Math.abs(mean - b.getNumberItems());
		}
		
		meanDev /= bags.size();
		
		return meanDev;
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
		ArrayList<Bag> filteredBags;
		for(int i = 0; i < bags.size(); i++){currentBags[i] = bags.get(i);}
		
		for(Constraint c : validChecks){
			filteredBags = c.apply(this, currentBags);
			if(filteredBags.size() < currentBags.length){return false;}
		}
		
		return true;
	}
}
