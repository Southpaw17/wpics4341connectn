package edu.wpi.cs4341.csp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.wpi.cs4341.csp.constraint.BinaryEquality;
import edu.wpi.cs4341.csp.constraint.BinaryInequality;
import edu.wpi.cs4341.csp.constraint.CapacityRemaining;
import edu.wpi.cs4341.csp.constraint.InCapacityRange;
import edu.wpi.cs4341.csp.constraint.MutuallyExclusive;
import edu.wpi.cs4341.csp.constraint.PercentFilled;
import edu.wpi.cs4341.csp.constraint.SpaceRemaining;
import edu.wpi.cs4341.csp.constraint.UnaryExclusive;
import edu.wpi.cs4341.csp.constraint.UnaryInclusive;
import edu.wpi.cs4341.csp.heuristic.BagHeuristic;
import edu.wpi.cs4341.csp.heuristic.ItemHeuristic;

/**
 * Parses the given problem definition file into usuable parts
 * 
 * @author Ryan O'Meara
 */
public class FileParser {
	private ArrayList<Item> items;		//The items read from the file
	private ArrayList<Bag> bags;		//The bags read from the file
	private ItemHeuristic useItemH;		//The heuristics to instruct the 
	private BagHeuristic useBagH;		//problem steps to use
	
	/**
	 * Creates a file parser which will parse a file with the given heuristics
	 * for solving
	 * @param iH The item heuristic to use
	 * @param bH The bag heuristic to use
	 */
	public FileParser(ItemHeuristic iH, BagHeuristic bH){
		useBagH = bH;
		useItemH = iH;
		items = new ArrayList<Item>();
		bags = new ArrayList<Bag>();
	}
	
	/**
	 * Parses the file and gives a problem step
	 * @param fileToParse The file name/path to parse
	 * @return The first problem step, with all empty bags and unassigned items
	 */
	public ProblemStep parseFile(String fileToParse){
		try{
			ProblemStep firstStep = null;
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(fileToParse);
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
		    			processItems(strLine);
		    			break;
		    		case 2:
		    			processBags(strLine);
		    			//Create problem step as soon as possible
		    			firstStep = new ProblemStep(items.toArray(new Item[items.size()]),
		    					bags.toArray(new Bag[bags.size()]),
		    					useItemH,
		    					useBagH);
		    			firstStep.addConstraint(new PercentFilled());
		    			break;
		    		case 3:
		    			processCapRange(strLine, firstStep);
		    			break;
		    		case 4:
		    			processUnaryInclusive(strLine, firstStep);
		    			break;
		    		case 5:
		    			processUnaryExclusive(strLine, firstStep);
		    			break;
		    		case 6:
		    			processBinaryEquality(strLine, firstStep);
		    			break;
		    		case 7:
		    			processBinaryInequality(strLine, firstStep);
		    			break;
		    		case 8:
		    			processMutuallyExclusive(strLine, firstStep);
		    			break;
		    		default:
		    			System.err.println("Indexing for processing failed");
		    			break;
		    		}
		    	}
		    }
		    
		    //Close the input stream
		    in.close();
		    
		    //ret the problem step
		    return firstStep;
		}catch (Exception e){System.err.println("Error: " + e.getMessage());}
	
		return null;
	}
	
	/**
	 * Processes lines which represent items
	 * @param line The line to process
	 */
	protected void processItems(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			Item add = new Item(inputs[0], Integer.parseInt(inputs[1]));
			add.addConstraint(new CapacityRemaining(add.getWeight()));
			items.add(add);
		}catch(Exception e){/*If second one is not int*/}
	}
	
	/**
	 * Processes lines which represent bags
	 * @param line The line to process
	 */
	protected void processBags(String line){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			Bag add = new Bag(inputs[0], Integer.parseInt(inputs[1]));
			bags.add(add);
		}catch(Exception e){/*If second one is not int*/}
	}
	
	/**
	 * Processes lines which represent capacity ranges
	 * @param line The line to process
	 */
	protected void processCapRange(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		try{
			int low = Integer.parseInt(inputs[0]);
			int high = Integer.parseInt(inputs[1]);
			
			firstStep.addConstraint(new InCapacityRange(low, high));
			for(Item i : firstStep.getItemsToPlace()){
				i.addConstraint(new SpaceRemaining(high));
			}
			
		}catch(Exception e){/*If items were not ints*/}
	}
	
	/**
	 * Processes lines which represent unary inclusive constraints
	 * @param line The line to process
	 */
	protected void processUnaryInclusive(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length < 2){return;}
		
		try{
			String[] bags = new String[inputs.length - 1];
			String item = inputs[0];
			for(int i = 1; i < inputs.length; i++){bags[i-1] = inputs[i];}
			
			firstStep.getItem(item).addConstraint(new UnaryInclusive(bags));
		}catch(Exception e){/*If items were not ints*/}
	}
	
	/**
	 * Processes lines which represent unary exclusive constraints
	 * @param line The line to process
	 */
	protected void processUnaryExclusive(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length < 2){return;}
		
		try{
			String[] bags = new String[inputs.length - 1];
			String item = inputs[0];
			for(int i = 1; i < inputs.length; i++){
				bags[i-1] = inputs[i];
			}
			
			firstStep.getItem(item).addConstraint(new UnaryExclusive(bags));
		}catch(Exception e){/*If items were not ints*/}
	}
	
	/**
	 * Processes lines which represent binary equality constraints
	 * @param line The line to process
	 */
	protected void processBinaryEquality(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		firstStep.getItem(inputs[0]).addConstraint(new BinaryEquality(inputs[0], inputs[1]));
		firstStep.getItem(inputs[1]).addConstraint(new BinaryEquality(inputs[0], inputs[1]));
	}
	
	/**
	 * Processes lines which represent binary inequality constraints
	 * @param line The line to process
	 */
	protected void processBinaryInequality(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 2){return;}
		
		firstStep.getItem(inputs[0]).addConstraint(new BinaryInequality(inputs[0], inputs[1]));
		firstStep.getItem(inputs[1]).addConstraint(new BinaryInequality(inputs[0], inputs[1]));
	}
	
	/**
	 * Processes lines which represent mutual exclusion constraints
	 * @param line The line to process
	 */
	protected void processMutuallyExclusive(String line, ProblemStep firstStep){
		String[] inputs = line.split(" ");
		
		if(inputs.length != 4){return;}
		
		firstStep.getItem(inputs[0]).addConstraint(new MutuallyExclusive(inputs[0], inputs[1], inputs[2], inputs[3]));
		firstStep.getItem(inputs[1]).addConstraint(new MutuallyExclusive(inputs[0], inputs[1], inputs[2], inputs[3]));
	}
}
