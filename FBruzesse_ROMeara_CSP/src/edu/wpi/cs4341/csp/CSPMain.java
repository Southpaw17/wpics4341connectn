package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.heuristic.LeastBagsRemaining;

public class CSPMain {
	public static void main(String[] args){
		
		FileParser file;
		
		if ( args != null && args.length > 0 )
		{
			file = new FileParser(args[0], new LeastBagsRemaining());
			
			SetHandler sHandler = file.getCreatedHandler();
			
			SetHandler setHandler = determineSolution(sHandler);
			
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
	
	private static SetHandler determineSolution( SetHandler setHandler ) {
		ArrayList<SetHandler> handlers = new ArrayList<SetHandler>();
		
		for (SetHandler sh : setHandler.getChildren() ) {handlers.add( sh );}
		
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
			
			for(SetHandler fool : toRemove){handlers.remove(fool);}
			
			toRemove.clear();
			
			for(SetHandler fool : toAdd){handlers.add(fool);}
			
			toAdd.clear();
		}
		
		return null;
	}
}
