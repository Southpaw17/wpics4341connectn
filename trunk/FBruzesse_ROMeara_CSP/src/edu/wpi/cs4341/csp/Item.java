package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;

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
	 * @author Frank Bruzzese
	 * 
	 */
	public void addConstraint( Constraint con ) {
		constraints.add( con );
	}
	
	public void applyConstraints( BagHandler bagHandler ) {
		
	}
}
