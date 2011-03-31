package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.Item;

public class LeastBagsRemaining implements ItemHeuristic {

	@Override
	public Item selectItem(Item[] itemSet, Bag[] bagSet) {
		Item toPlace = itemSet[0];
		Bag[] possibleBags = itemSet[0].applyConstraints(bagSet);
		
		//find item to place
		for(int i = 1; i < itemSet.length; i++){
			Bag[] temp = itemSet[i].applyConstraints(bagSet);
			if((temp.length != 0)&&(temp.length < possibleBags.length)){
				toPlace = itemSet[i];
				possibleBags = temp;
			}else if(temp.length == possibleBags.length){
				if(itemSet[i].getNumConstraints() < toPlace.getNumConstraints()){
					toPlace = itemSet[i];
					possibleBags = temp;
				}
			}else if((temp.length != 0)&&(possibleBags.length == 0)){
				toPlace = itemSet[i];
				possibleBags = temp;
			}
		}
		
		return toPlace;
	}

}
