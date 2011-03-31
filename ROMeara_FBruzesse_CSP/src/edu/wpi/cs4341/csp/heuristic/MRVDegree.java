package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.Item;

/**
 * Heuristic which selects the most constrained item to place next
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class MRVDegree implements ItemHeuristic {

	@Override
	public Item selectItem(Item[] choices) {
		if((choices == null)||(choices.length == 0)){return null;}
		
		Item currentItem = null;
		
		for(Item i : choices){
			if(currentItem == null){ 
				currentItem = i;
			}else if(currentItem.getAllowedBags().length > i.getAllowedBags().length){
				currentItem = i;
			}else if(currentItem.getAllowedBags().length == i.getAllowedBags().length){
				if(currentItem.getNumberConstraints() < i.getNumberConstraints()){
					currentItem = i;
				}
			}
		}
		
		return currentItem;
	}

}
