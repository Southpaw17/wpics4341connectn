package edu.wpi.cs4341.csp;

import java.util.ArrayList;

/**
 * Class to represent a given bag 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class Bag {
	protected String bagLetter;					//Bag Label
	protected int capacity;						//bag capacity
	protected ArrayList<Item> items;			//The items currently "in the bag" 
	
	/**
	 * Creates a new, empty bag
	 * @param iBagLetter The label for this bag
	 * @param cap The weight capacity for this bag
	 */
	public Bag(String iBagLetter, int cap){
		bagLetter = iBagLetter;
		capacity = cap;
		items = new ArrayList<Item>();
	}
	
	/**
	 * Copy constructor for this bag.  Completely copies the bag
	 * @param copyBag The bag to copy
	 */
	public Bag(Bag copyBag){
		bagLetter = copyBag.bagLetter;
		capacity = copyBag.capacity;
		items = new ArrayList<Item>();
		
		for(Item nextItem : copyBag.items){
			items.add(nextItem);
		}
	}
	
	/** @return An array of the items in this bag*/
	public Item[] getItems(){
		return items.toArray(new Item[items.size()]);
	}
	
	/** @return An independent copy of this bag */
	public Bag copyBag(){return new Bag(this);}
	
	/** @return The number of items in the bag */
	public int getNumberItems(){return items.size();}
	
	/** @return The label for this bag */
	public String getBagName(){return bagLetter;}
	
	/** @return The maximum capacity of the bag */
	public int getPossibleCapacity(){return capacity;}
	
	/** @return The remaining capacity of the bag */
	public int getRemainingCapacity(){
		int used = 0;
		
		for(Item i : items){used += i.getWeight();}
		
		return getPossibleCapacity() - used; 
	}
	
	/**
	 * Attempts to add a given item to this bag
	 * @param i The Item to add
	 * @return true if added successfully, false otherwise
	 */
	public boolean addToBag(Item i){return items.add(i);}
	
	/**
	 * Attempts to remove an item from the bag
	 * @param i The Item to remove
	 * @return true if removed, false if failed to remove
	 */
	public boolean removeFromBag(Item i){return items.remove(i);}
	
	/**
	 * @param i The Item to search for
	 * @return true if this bag currently contains this item
	 */
	public boolean containsItem(Item i){
		for(Item j : items){
			if(j.getLabel().equals(i.getLabel())){return true;}
		}
		
		return false;
	}
}
