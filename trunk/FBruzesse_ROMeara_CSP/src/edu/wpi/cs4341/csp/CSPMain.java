package edu.wpi.cs4341.csp;

import java.util.ArrayList;

public class CSPMain {
	public static void main(String[] args){
		
		FileParser file;
		
		if ( args != null && args.length > 0 )
		{
			file = new FileParser(args[0]);
			
			ArrayList<Item> items = file.getItems();
			BagHandler bagHandler = file.getCreatedHandler();
			
			determineSolution( bagHandler, items );
			
		}
		else
		{
			System.err.print("Error: args is null.");
		}
		
		
			
		
		//create starting set
		
		
		//loop through all possible sets
			//check if complete set
				//if not, advance to next set
					//update sets of items not placed to reflect this set
					//choose next item to place
					//place item
					//check if complete set, if yes, mark bag handler as complete
		
		
		//compare and choose
			//first, eliminate any which have both less weight and items
			//next, choose based on % wasted space
			//if still tie, pick based on evenness of weight distribution
			//if STILL tie, pick based on evenness of item distribution
			//if STILL tie, just pick one (honestly, they are essentially exactly equivalent at this point)
	}
	
	private static SetHandler determineSolution( BagHandler bagHandler, ArrayList<Item> items ) {
		SetHandler setHandler = new SetHandler( ((Item[])items.toArray()), bagHandler );
		
		ArrayList<SetHandler> handlers = new ArrayList<SetHandler>();
		
		for (SetHandler sh : setHandler.getChildren() ) {
			handlers.add( sh );
		}
		
		for ( SetHandler sHandle : handlers ) {
			if ( sHandle.isComplete() && sHandle.isValid() ) {
				return sHandle;
			}
			
			SetHandler handle[] = sHandle.getChildren();
			if ( handle != null || handle.length == 0 )
			{
				for (SetHandler sh : handle ) {
					handlers.add( sh );
				
				}
			}
			
			handlers.remove( sHandle );

		}
		
		return null;
	}
}
