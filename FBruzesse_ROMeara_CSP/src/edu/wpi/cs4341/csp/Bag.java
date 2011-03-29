package edu.wpi.cs4341.csp;

import java.util.ArrayList;

/**
 * Class to represent a given bag 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class Bag {
	String bagLetter;					//Bag Label
	ArrayList<Item> items;				//The items currently "in the bag" 
	
	public Bag(String iBagLetter){
		bagLetter = iBagLetter;
		items = new ArrayList<Item>();
	}
	
	public Bag(Bag copyBag){
		bagLetter = copyBag.bagLetter;
		items = new ArrayList<Item>();
		
		for(Item nextItem : copyBag.items){
			items.add(nextItem);
		}
	}
	
	/** @return An independent copy of this bag */
	public Bag copyBag(){return new Bag(this);}
	
	/** @return The number of items in the bag */
	public int getNumberItems(){return items.size();}
	
	/** @return The label for this bag */
	public String getBagName(){return bagLetter;}
	
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
	public boolean containsItem(Item i){return items.contains(i);}
}
