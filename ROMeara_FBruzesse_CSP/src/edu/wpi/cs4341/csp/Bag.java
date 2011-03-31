package edu.wpi.cs4341.csp;

import java.util.ArrayList;

/**
 * Represents a bag in the problem, which can hold {@link Item}s.  A bag has a 
 * name, capacity, and set of contained items
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class Bag {
	private String name;			//The name of the bag
	private int capacity;			//The capacity of the bag
	private ArrayList<Item> items;	//The items in the bag
	
	/**
	 * Creates a new empty bag
	 * @param bagName The name of the new bag
	 * @param bagCapacity The capacity of the new bag
	 */
	public Bag(String bagName, int bagCapacity){
		name = bagName;
		capacity = bagCapacity;
		items = new ArrayList<Item>();
	}
	
	/**
	 * Copies the given bag as a new, independent bag
	 * @param copyBag The bag to make a copy of
	 */
	public Bag(Bag copyBag){
		this(copyBag.name, copyBag.capacity);
		
		for(Item i : copyBag.items){items.add(new Item(i));}
	}
	
	/** @return The name of this bag */
	public String getName(){return name;}
	
	/** @return The maximum possible capacity for this bag */
	public int getMaximumCapacity(){return capacity;}
	
	/** @return The current weight of the items in the bag */
	public int getCurrentWeight(){
		int weight = 0;
		
		for(Item i : items){weight += i.getWeight();}
		
		return weight;
	}
	
	/** @return The maximum additional weight which can be added to the bag */
	public int getRemainingCapacity(){
		return getMaximumCapacity() - getCurrentWeight();
	}
	
	/** @return true if an item with the same name is in this bag */
	public boolean containsItem(String i){
		for(Item it : items){
			if(it.getName().equals(i)){return true;}
		}
		
		return false;
	}
	
	/** @return An array of all the items currently in the bag */
	public Item[] getContainedItems(){
		return items.toArray(new Item[items.size()]);
	}
	
	/** @return Result of adding item to the bag (success/failure) */
	public boolean addItem(Item i){return items.add(i);}
}
