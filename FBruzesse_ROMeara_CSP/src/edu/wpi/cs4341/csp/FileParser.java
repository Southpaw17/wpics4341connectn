package edu.wpi.cs4341.csp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FileParser {
	BagHandler createdHandler;
	ArrayList<Item> createdItems;
	
	public FileParser(String setupFile){
		//parse the file
		createdHandler = new BagHandler();
		createdItems = new ArrayList<Item>();
		
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
		    	if(strLine.equals("#####")){
		    		//new section
		    		currentSection++;
		    	}else{
		    		switch(currentSection){
		    		case 1:
		    			processItemNameWeight(strLine);
		    			break;
		    		case 2:
		    			processBagNameCap(strLine);
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
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public BagHandler getCreatedHandler(){
		//TODO implement
		return null;
	}
	
	public ArrayList<Item> getItems(){
		//TODO implement
		return null;
	}
	
	protected void processItemNameWeight(String line){
		
	}
	
	protected void processBagNameCap(String line){
		
	}

	protected void processFitLimits(String line){
	
	}

	protected void processInclusiveUnary(String line){
	
	}

	protected void processExclusiveUnary(String line){
	
	}

	protected void processEqualBinary(String line){
	
	}

	protected void processNotEqualBinary(String line){
	
	}

	protected void processMutualExclusive(String line){
	
	}
}
