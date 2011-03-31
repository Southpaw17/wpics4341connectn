package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.CanFit;
import edu.wpi.cs4341.csp.constraints.Constraint;

/**
 * Class which stores an item for CS4341 Intro to AI Project 2
 * @author Frank Bruzzese
 *
 */
public class Item{

	String label;
	int weight;
	ArrayList<Constraint> constraints;
	
	public Item(String l, int w) {
		label = l;
		weight = w;
		constraints = new ArrayList<Constraint>();
		constraints.add(new CanFit(weight));
	}
	
	public String getLabel(){
		return label;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getNumConstraints(){
		return constraints.size();
	}
	
	/**
	 * Adds a constraint to the item
	 * 
	 * @param con - the constraint to be added
	 */
	public void addConstraint( Constraint con ) {
		constraints.add( con );
	}
	
	/**
	 * Applies the constraints of this item to a list of available bags
	 * 
	 * @param bagHandler - The Bag Handler which stores all of the bags
	 * @return An array of legal bags which this item can be placed in
	 */
	public ArrayList<Bag> applyConstraints( BagHandler bagHandler ) {
		ArrayList<Bag> temp = new ArrayList<Bag>(); 
		
		for ( Bag bag : bagHandler.getAllBags() ){
			temp.add( bag );
		}
		
		Bag[] currentBag = temp.toArray(new Bag[temp.size()]);
		temp.clear();
		for ( int i = 0; i < constraints.size(); i++ ){
			currentBag = constraints.get( i ).apply( bagHandler, currentBag );
		}
		
		for(Bag b : currentBag){
			temp.add(b);
		}
		
		return temp;
	}
}
