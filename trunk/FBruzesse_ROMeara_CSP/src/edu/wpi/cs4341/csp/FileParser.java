package edu.wpi.cs4341.csp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraints.Equality;
import edu.wpi.cs4341.csp.constraints.FitLimit;
import edu.wpi.cs4341.csp.constraints.Inequality;
import edu.wpi.cs4341.csp.constraints.MutuallyExclusivity;
import edu.wpi.cs4341.csp.constraints.UnaryExclusive;
import edu.wpi.cs4341.csp.constraints.UnaryInclusive;
import edu.wpi.cs4341.csp.heuristic.ItemHeuristic;

/**
 * Parses a file into useable objects
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class FileParser {
	SetHandler createdHandler;
	ArrayList<Item> createdItems;
	ArrayList<Bag> createdBags;
	
	public FileParser(String setupFile, ItemHeuristic iH){
		//parse the file
		createdItems = new ArrayList<Item>();
		createdBags = new ArrayList<Bag>();
		
		try{
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(setupFile);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    
		    int currentSection = 0;
		    
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		    	if(strLine.trim().startsWith("#####")){
		    		//new section
		    		currentSection++;
		    	}else{
		    		switch(currentSection){
		    		case 1:
		    			processItemNameWeight(strLine);
		    			break;
		    		case 2:
		    			processBagNameCap(strLine);
		    			createdHandler = new SetHandler(createdItems.toArray(new Item[createdItems.size()]), createdBags.toArray(new Bag[createdBags.size()]), iH);
		    			break;
		    		case 3:
		    			processFitLimits(strLine);
		    			break;
		    		case 4:
		    			processInclusiveUnary(strLine);
		    			break;
		    		case 5:
		    			processExclusiveUnary(strLine);
		    			break;
		    		case 6:
		    			processEqualBinary(strLine);
		    			break;
		    		case 7:
		    			processNotEqualBinary(strLine);
		    			break;
		    		case 8:
		    			processMutualExclusive(strLine);
		    			break;
		    		default:
		    			System.err.println("Indexing for processing failed");
		    			break;
		    		}
		    	}
		    }
		    
		    //Close the input stream
		    in.close();
		    
		    //create the set handler
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/** @return The created bag handler */
	public SetHandler getCreatedHandler(){return createdHandler;}
	
	protected void processItemNameWeight(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			Item add = new Item(inputs[0], Integer.parseInt(inputs[1]));
			createdItems.add(add);
		}catch(Exception e){/*If second one is not int*/}
	}
	
	protected void processBagNameCap(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			Bag add = new Bag(inputs[0], Integer.parseInt(inputs[1]));
			createdBags.add(add);
		}catch(Exception e){/*If second one is not int*/}
	}

	protected void processFitLimits(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			int low = Integer.parseInt(inputs[0]);
			int high = Integer.parseInt(inputs[1]);
			
			createdHandler.addConstraint(new FitLimit(low, high));
		}catch(Exception e){/*If items were not ints*/}
	}

	protected void processInclusiveUnary(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length < 2){return;}
		
		try{
			String[] bags = new String[inputs.length - 1];
			String item = inputs[0];
			for(int i = 1; i < inputs.length; i++){
				bags[i-1] = inputs[i];
			}
			
			for(Item i: createdItems){
				if(i.getLabel().equals(item)){
					i.addConstraint(new UnaryInclusive(bags));
					return;
				}
			}
		}catch(Exception e){/*If items were not ints*/}
	}

	protected void processExclusiveUnary(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length < 2){return;}
		
		try{
			String[] bags = new String[inputs.length - 1];
			String item = inputs[0];
			for(int i = 1; i < inputs.length; i++){
				bags[i-1] = inputs[i];
			}
			
			for(Item i: createdItems){
				if(i.getLabel().equals(item)){
					i.addConstraint(new UnaryExclusive(bags));
					return;
				}
			}
		}catch(Exception e){/*If items were not ints*/}
	}

	protected void processEqualBinary(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		Item[] items = new Item[2];
		
		int i = 0;
		
		for(Item it : createdItems){
			if(it.getLabel().equals(inputs[0])||it.getLabel().equals(inputs[1])){
				items[i] = it;
				i++;
				if(i == 2){break;}
			}
		}
		
		items[0].addConstraint(new Equality(items[0], items[1]));
		items[1].addConstraint(new Equality(items[0], items[1]));
	}

	protected void processNotEqualBinary(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		Item[] items = new Item[2];
		
		int i = 0;
		
		for(Item it : createdItems){
			if(it.getLabel().equals(inputs[0])||it.getLabel().equals(inputs[1])){
				items[i] = it;
				i++;
				if(i == 2){break;}
			}
		}
		
		items[0].addConstraint(new Inequality(items[0], items[1]));
		items[1].addConstraint(new Inequality(items[0], items[1]));
	}

	protected void processMutualExclusive(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 4){return;}
		
		Item[] items = new Item[2];
		
		int i = 0;
		
		for(Item it : createdItems){
			if(it.getLabel().equals(inputs[0])||it.getLabel().equals(inputs[1])){
				items[i] = it;
				i++;
				if(i == 2){break;}
			}
		}
		
		items[0].addConstraint(new MutuallyExclusivity(items[0], items[1], inputs[2], inputs[3]));
		items[1].addConstraint(new MutuallyExclusivity(items[0], items[1], inputs[2], inputs[3]));
	}
}
