package edu.wpi.cs4341.csp.heuristic;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.Bag;
import edu.wpi.cs4341.csp.BagHandler;
import edu.wpi.cs4341.csp.Item;

public class LeastBagsRemaining implements ItemHeuristic {

	@Override
	public Item selectItem(Item[] itemSet, BagHandler bagSet) {
		Item toPlace = itemSet[0];
		ArrayList<Bag> possibleBags = itemSet[0].applyConstraints(bagSet);
		
		//find item to place
		for(int i = 1; i < itemSet.length; i++){
			ArrayList<Bag> temp = itemSet[i].applyConstraints(bagSet);
			if((temp.size() != 0)&&(temp.size() < possibleBags.size())){
				toPlace = itemSet[i];
				possibleBags = temp;
			}else if(temp.size() == possibleBags.size()){
				if(itemSet[i].getNumConstraints() < toPlace.getNumConstraints()){
					toPlace = itemSet[i];
					possibleBags = temp;
				}
			}else if((temp.size() != 0)&&(possibleBags.size() == 0)){
				toPlace = itemSet[i];
				possibleBags = temp;
			}
		}
		
		return toPlace;
	}

}
