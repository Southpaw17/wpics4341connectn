package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraint.Constraint;
import edu.wpi.cs4341.csp.heuristic.BagHeuristic;
import edu.wpi.cs4341.csp.heuristic.ItemHeuristic;

/**
 * Represents a step on the path to the problem solution.  Has the steps it 
 * represents, the items it still has to place, the bags in the problem,
 * the overall constraints on all the bags, and the heuristics to use to
 * move forward
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class ProblemStep {
	ArrayList<String> stepsTaken;			//The steps taken which led to this step
	ArrayList<Item> itemsToPlace;			//Items left to place
	ArrayList<Bag> bags;					//Bags in the problem
	ArrayList<Constraint> bagConstraints;	//Constraints on the bags
	ItemHeuristic itemChoice;				//Heuristic to use to choose item to place
	BagHeuristic bagOrdering;				//Heuristic to use to choose order to check bags
	
	/**
	 * Creates a new problem step
	 * @param items The items to place
	 * @param bagSet The bags in the problem
	 * @param iH The item heuristic to use
	 * @param bH The bag heuristic to use
	 */
	public ProblemStep(Item[] items, Bag[] bagSet, ItemHeuristic iH, BagHeuristic bH){
		itemChoice = iH;
		bagOrdering = bH;
		itemsToPlace = new ArrayList<Item>();
		bags = new ArrayList<Bag>();
		bagConstraints = new ArrayList<Constraint>();
		stepsTaken = new ArrayList<String>();
		
		for(Item i : items){itemsToPlace.add(i);}
		for(Bag b : bagSet){bags.add(b);}
		
		
	}
	
	public void updateAllowed(){
		//update allowed moves
		for(Item i : itemsToPlace){i.updateAllowedBags(this);}
	}
	
	/**
	 * Creates an independent copy of the given problem step
	 * @param copyFrom The problem step to copy
	 */
	public ProblemStep(ProblemStep copyFrom){
		itemChoice = copyFrom.itemChoice;
		bagOrdering = copyFrom.bagOrdering;
		itemsToPlace = new ArrayList<Item>();
		bags = new ArrayList<Bag>();
		bagConstraints = new ArrayList<Constraint>();
		stepsTaken = new ArrayList<String>();
		
		for(Item i : copyFrom.itemsToPlace){itemsToPlace.add(new Item(i));}
		for(Bag b : copyFrom.bags){bags.add(new Bag(b));} 
		
		for(Constraint c : copyFrom.bagConstraints){bagConstraints.add(c);} 
		for(String s : copyFrom.stepsTaken){stepsTaken.add(s);} 
	}
	
	/** @return The item heuristic to use */
	public ItemHeuristic getItemHeuristic(){return itemChoice;}
	
	/** @return The bag heuristic to use */
	public BagHeuristic getBagHeuristic(){return bagOrdering;}
	
	public int getPossibleChoices(){
		int moves = 0;
		
		for(Item i : itemsToPlace){
			moves += i.getAllowedBags().length;
		}
		
		return moves;
	}
	
	/**
	 * Adds the given constraint to the step
	 * @param toAdd The constraint to add
	 * @return true if added successfully, false otherwise
	 */
	public boolean addConstraint(Constraint toAdd){
		return bagConstraints.add(toAdd);
	}
	
	/**
	 * Adds the given step record to the problem step
	 * @param step The step to add
	 * @return true if added successfully, false otherwise
	 */
	public boolean addStep(String step){
		return stepsTaken.add(step);
	}
	
	/** @return An array of all items left to place */
	public Item[] getItemsToPlace(){
		return itemsToPlace.toArray(new Item[itemsToPlace.size()]);
	}
	
	/**
	 * Retrieves the item with the given name
	 * @param itemName The item to find
	 * @return The item object with the given name
	 */
	public Item getItem(String itemName){
		for(Item i : itemsToPlace){
			if(i.getName().equals(itemName)){return i;}
		}
		
		return null;
	}
	
	/** @return An array of every bag in the problem */
	public Bag[] getAllBags(){
		return bags.toArray(new Bag[bags.size()]);
	}
	
	/**
	 * Retrieves the bag with the given name
	 * @param bagName The bag to find
	 * @return The bag object with the given name
	 */
	public Bag getBag(String bagName){
		for(Bag b : bags){
			if(b.getName().equals(bagName)){return b;}
		}
		
		return null;
	}
	
	/**
	 * Creates a following problem step by placing the given item in the given 
	 * bag
	 * @param itemName Name of the item to place
	 * @param bagName Name of the bag to place the item in
	 * @return The problem step, or null if that move is illegal
	 */
	public ProblemStep placeItemInBag(String itemName, String bagName){
		//Create a copy to return
		ProblemStep copy = new ProblemStep(this);
		
		//Find the bag and item
		Bag addTo = copy.getBag(bagName);
		Item add = copy.getItem(itemName);
		
		//if either were not found, then return null
		if((addTo == null)||(add == null)){return null;}
		
		String[] allowed = add.getAllowedBags();
		
		//Check that move is allowed, and if it is make it
		for(String s : allowed){
			if(s.equals(bagName)){
				//Add the item and step, remove item from list to be placed
				addTo.addItem(add);
				copy.addStep(itemName + " -> " + bagName);
				copy.itemsToPlace.remove(add);
				add.setCurrentBag(bagName);
				
				//update allowed moves
				for(Item i : copy.itemsToPlace){i.updateAllowedBags(copy);}
				
				if(copy.isSolvable()){return copy;}
				
				return null;
			}
		}
		
		return null;
	}
	
	/** True if all items have been placed */
	public boolean isComplete(){return (itemsToPlace.size() == 0);}
	
	/** @return true if this step is a complete solution which meets all constraints */
	public boolean isValidSolution(){
		//Must be complete to be valid solution
		if(!isComplete()){return false;}
		
		//Apply/check all constraints on bags
		Bag[] validBags = new Bag[bags.size()];
		
		for(int i = 0; i < bags.size(); i++){validBags[i] = bags.get(i);}
		
		for(Constraint c : bagConstraints){
			//As soon as a bag is eliminated, this is false
			validBags = c.updateConstraints(this, validBags);
			if(validBags.length < bags.size()){return false;}
		}
		
		return true;
	}
	
	/** return true if this step can still place items or is solved */
	public boolean isSolvable(){
		//Check available moves
		for(Item i : itemsToPlace){
			if(i.getAllowedBags().length < 1){return false;}
		}
		
		return true;
	}
	
	public String toString(){
		String output = "";
		int wastedSpace = 0;
		
		for(Bag b : bags){wastedSpace += b.getRemainingCapacity();}
		
		output += "Wasted Space: " + wastedSpace + "\n\n";
		
		for(Bag b : bags){
			output += b.getName() + "\n";
			for(Item i : b.getContainedItems()){
				output += "\t" + i.getName() + "\n";
			}
		}
		
		output += "\n\n";
		
		for(String s : stepsTaken){
			output += s + "\n";
		}
		
		return output;
	}

}
