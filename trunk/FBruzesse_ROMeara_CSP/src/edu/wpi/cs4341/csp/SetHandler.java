package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;
import edu.wpi.cs4341.csp.heuristic.ItemHeuristic;

public class SetHandler {
	ArrayList<Item> itemSet;
	ArrayList<Bag> bagSet;
	ItemHeuristic iHeuristic;
	ArrayList<String> steps;
	protected ArrayList<Constraint> validChecks;	//List of checks.  Each one will return list of bags which do  
	//satisfy the constraint.  Individual constraints will include in
	//constructor which bag to check

	
	public SetHandler(Item[] items, Bag[] bags, ItemHeuristic iH){
		bagSet = new ArrayList<Bag>();
		itemSet = new ArrayList<Item>();
		steps = new ArrayList<String>();
		validChecks = new ArrayList<Constraint>();
		iHeuristic = iH;
		
		
		for(Item i : items){itemSet.add(i);}
		for(Bag b : bags){bagSet.add(b);}
	}
	
	public SetHandler(SetHandler copy){
		itemSet = new ArrayList<Item>();
		bagSet = new ArrayList<Bag>();
		steps = new ArrayList<String>();
		validChecks = new ArrayList<Constraint>();
		iHeuristic = copy.iHeuristic;
		
		for(Item i : copy.itemSet){itemSet.add(i);}
		for(Bag b : copy.bagSet){bagSet.add(b);}
		for(String s : copy.steps){steps.add(s);}
		for(Constraint c : copy.validChecks){validChecks.add(c);}
	}
	
	public void addStep(String step){steps.add(step);}
	
	public SetHandler[] getChildren(){
		if((itemSet == null)||(itemSet.size() == 0)){return null;}
		
		Item toPlace = iHeuristic.selectItem(itemSet.toArray(new Item[itemSet.size()]), bagSet.toArray(new Bag[bagSet.size()]));
		ArrayList<Bag> possibleBags = toPlace.applyConstraints(bagSet.toArray(new Bag[bagSet.size()]));
		
		ArrayList<Item> newSet = new ArrayList<Item>();
		
		for(Item i : itemSet){
			if(!i.getLabel().equals(toPlace.getLabel())){
				newSet.add(i);
			}
		}
		
		ArrayList<SetHandler> sets = new ArrayList<SetHandler>();
		
		for(Bag b : possibleBags){
			SetHandler newHand = new SetHandler(this);
			newHand.getBag(b.getBagName()).addToBag(toPlace);
			newHand.addStep(toPlace.getLabel() + " -> " + b.getBagName());
			sets.add(newHand);
		}
		
		if(sets.size() == 0){return null;}
		
		return sets.toArray(new SetHandler[sets.size()]);
	}
	
	public Bag getBag(String bagLabel){
		for(Bag b : bagSet){if(b.getBagName().equals(bagLabel)){return b;}}
		
		return null;
	}
	
	public Bag[] getAllBags(){return bagSet.toArray(new Bag[bagSet.size()]);}
	
	/** 
	 * @param con The Constraint to add to this handler
	 */
	public void addConstraint(Constraint con){validChecks.add(con);}
	
	public boolean isValid(){
		boolean isValid = true;
		
		Bag[] currentBags = new Bag[bagSet.size()];
		Bag[] filteredBags;
		for(int i = 0; i < bagSet.size(); i++){currentBags[i] = bagSet.get(i);}
		
		for(Constraint c : validChecks){
			filteredBags = c.apply(bagSet.toArray(new Bag[bagSet.size()]), currentBags);
			if(filteredBags.length < currentBags.length){return false;}
		}
		
		isValid = true;
		
		for(Item i : itemSet){
			if(i.applyConstraints(bagSet.toArray(new Bag[bagSet.size()])).size() == 0){isValid = false;}
		}
		
		return isValid;
	}
	
	public boolean isComplete(){
		
			boolean found = false;
			
			for(Item i : itemSet){
				found = false;
				
				for(Bag b : bagSet){
					if(b.containsItem(i)){
						found = true;
						break;
					}
				}
				
				if(!found){
					//if still possible sets, not complete, exit
					if(i.applyConstraints(bagSet.toArray(new Bag[bagSet.size()])).size() > 0){
						return false;
					}
				}
			}
			
			return true;
	}
	
	/** @return The number of items in bags in this set */
	public int totalItemsInBags(){
		int ret = 0;
		
		for(Bag b : bagSet){ret += b.getNumberItems();}
		
		return ret;
	}
	
	/** @return The total weight of the bags in this set */
	public int totalWeightInBags(){
		int ret = 0;
		
		for(Bag b : bagSet){ret += (b.getPossibleCapacity() - b.getRemainingCapacity());}
		
		return ret;
	}
	
	/** @return The units of unfilled weight in this set */
	public int wastedWeight(){
		int ret = 0;
		
		for(Bag b : bagSet){ret += b.getRemainingCapacity();}
		
		return ret;
	}
	
	public String toString(){
		String output = "";
		
		output += "Wasted Space: " + wastedWeight() + "\n\n";
		
		for(Bag b : bagSet){
			output += b.getBagName() + ", Num Items: " + b.getNumberItems() + ", Total Weight: " + (b.getPossibleCapacity() - b.getRemainingCapacity()) + "\n";
			for(Item i : b.getItems()){
				output += "\t" + i.getLabel() + "\n";
			}
		}
		
		output += "\nSteps\n";
		
		for(String s : steps){
			output += "\t" + s + "\n";
		}
		
		return output;
	}
}
