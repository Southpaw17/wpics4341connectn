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
			
			SetHandler setHandler = determineSolution( bagHandler, items );
			
			if ( setHandler != null ) {
				// WE FRIGGEN FOUND IT BABY!
				System.out.println( setHandler.toString() );
			} else{
				// NO SOLUTION
				System.out.println("Our illusterious intellect has determined there is no possible solution.");
			}
			
		}
		else
		{
			System.err.println("Error: args is null.");
		}
		
	}
	
	private static SetHandler determineSolution( BagHandler bagHandler, ArrayList<Item> items ) {
		SetHandler setHandler = new SetHandler( items.toArray(new Item[items.size()]), bagHandler );
		
		ArrayList<SetHandler> handlers = new ArrayList<SetHandler>();
		
		for (SetHandler sh : setHandler.getChildren() ) {
			handlers.add( sh );
		}
		
		ArrayList<SetHandler> toRemove = new ArrayList<SetHandler>();
		ArrayList<SetHandler> toAdd = new ArrayList<SetHandler>();
		
		while(handlers.size() > 0){
			for ( SetHandler sHandle : handlers ) {
				if ( sHandle.isComplete() && sHandle.isValid() ) {
					return sHandle;
				}
				
				SetHandler handle[] = sHandle.getChildren();
				if ( handle != null )
				{
					for (SetHandler sh : handle ) {
						toAdd.add( sh );
					
					}
				}
				
				toRemove.add( sHandle );
	
			}
			
			for(SetHandler fool : toRemove){
				handlers.remove(fool);
			}
			
			for(SetHandler fool : toAdd){
				handlers.add(fool);
			}
		}
		
		return null;
	}
}
