package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraint.Constraint;

/**
 * Represents an item in the problem, which are placed in {@link Bag}s.  Items
 * have a name, weight, set of allowed bags, currentBag, and set of constraints
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class Item {
	private String name;						//The name of the item
	private int weight;							//The weight of the item
	private ArrayList<String> allowedBags;		//The bag names this item can be in
	private String currentBag;					//The bag name this item is in if any
	private ArrayList<Constraint> constraints;	//The constraints on this item
	
	/**
	 * Creates a new item
	 * @param itemName The name of the item
	 * @param itemWeight The weight of the item
	 */
	public Item(String itemName, int itemWeight){
		name = itemName;
		weight = itemWeight;
		allowedBags = null;
		currentBag = null;
		constraints = new ArrayList<Constraint>();
	}
	
	/**
	 * Creates an independent copy of this item
	 * @param copyItem The item to copy
	 */
	public Item(Item copyItem){
		this(copyItem.name, copyItem.weight);
		
		if(copyItem.allowedBags == null){
			allowedBags = null;
		}else{
			allowedBags = new ArrayList<String>();
			for(String s : copyItem.allowedBags){allowedBags.add(s);}
		}
		
		currentBag = copyItem.currentBag;
		
		constraints = new ArrayList<Constraint>();
		for(Constraint c : copyItem.constraints){constraints.add(c);}
	}
	
	/** @return The name of this item  */
	public String getName(){return name;}
	
	/** @return The weight of this item */
	public int getWeight(){return weight;}
	
	/** @return The number of constraints on this item */
	public int getNumberConstraints(){return constraints.size();}
	
	/** @return An array of the names of the bags which this item can be placed in*/
	public String[] getAllowedBags(){
		return allowedBags.toArray(new String[allowedBags.size()]);
	}
	
	/** @return The name of the bag this item is in, or null if not yet placed */
	public String getCurrentBag(){return currentBag;}
	
	/**
	 * Sets the bag that this item is in
	 * @param cB The name of the bag the item is being put in
	 */
	public void setCurrentBag(String cB){
		currentBag = cB;
		allowedBags.clear();
	}
	
	/**
	 * Adds a constraint to this item
	 * @param toAdd the constraint to add
	 * @return true if added successfully, false otherwise
	 */
	public boolean addConstraint(Constraint toAdd){
		return constraints.add(toAdd);
	}
	
	/**
	 * Updates the currently allowed bags based on the current problem step
	 * @param currentStep The current problem step
	 * @return The number of bags this item can still be put in. -1 if placed
	 * previously
	 */
	public int updateAllowedBags(ProblemStep currentStep){
		if(currentBag != null){return -1;}
		if(allowedBags == null){
			//If this is first step, set of allowed bags (before first culling)
			//is every bag
			allowedBags = new ArrayList<String>();
			for(Bag b : currentStep.getAllBags()){allowedBags.add(b.getName());}
		}
		
		Bag[] bags = new Bag[allowedBags.size()];
		
		//find the current reference to all bags this item can be placed in
		for(int i = 0; i < allowedBags.size(); i++){
			bags[i] = currentStep.getBag(allowedBags.get(i));
		}
		
		//apply all constraints on this item
		for(Constraint c : constraints){
			bags = c.updateConstraints(currentStep, bags);
		}
		
		allowedBags.clear();
		
		for(Bag b : bags){allowedBags.add(b.getName());}
		
		return bags.length;
	}
}
