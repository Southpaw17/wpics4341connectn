package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;

/**
 * Class which stores an item for CS4341 Intro to AI Project 2
 * @author Frank Bruzzese
 *
 */
public class Item {

	int weight;
	ArrayList<Constraint> constraints;
	
	public Item( int w ) {
		weight = w;
		constraints = new ArrayList<Constraint>();
	}
	
	/**
	 * Adds a constraint to the item
	 * 
	 * @param con - the constraint to be added
	 *
	 * 
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
	public Bag[] applyConstraints( BagHandler bagHandler ) {
		Bag[] temp = bagHandler.getAllBags();
		
		for ( int i = 0; i < constraints.size(); i++ ){
			temp = constraints.get( i ).apply( bagHandler, temp );
		}
		
		return temp;
	}
}
