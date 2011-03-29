package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Constraint;

public class BagHandler {
	ArrayList<Bag> bags;					//list of bags
	ArrayList<Constraint> validChecks;		//List of checks.  Each one will return list of bags which do  
											//satisify the constraint.  Imdividual constraints will include in
											//constructor which bag to check
	
	
	public Bag getBag(String bagLabel){
		//TODO implement
		return null;
	}
	
	public Bag[] getAllBags(){
		return null;
	}
}
