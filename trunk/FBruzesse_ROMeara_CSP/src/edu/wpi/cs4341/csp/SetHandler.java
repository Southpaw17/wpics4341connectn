package edu.wpi.cs4341.csp;

import java.util.ArrayList;

public class SetHandler {
	ArrayList<Item> itemSet;
	BagHandler bagSet;
	
	public SetHandler(Item[] items, BagHandler bags){
		bagSet = bags;
		itemSet = new ArrayList<Item>();
		
		for(Item i : items){
			itemSet.add(i);
		}
	}
	
	public SetHandler(SetHandler copy){
		bagSet = copy.bagSet.copyHandler();
		
		itemSet = new ArrayList<Item>();
		
		for(Item i : copy.itemSet){
			itemSet.add(i);
		}
	}
	
	public SetHandler[] getChildren(){
		if((itemSet == null)||(itemSet.size() == 0)){return null;}
		
		Item toPlace = itemSet.get(0);
		ArrayList<Bag> possibleBags = itemSet.get(0).applyConstraints(bagSet);
		
		//find item to place
		for(int i = 1; i < itemSet.size(); i++){
			ArrayList<Bag> temp = itemSet.get(i).applyConstraints(bagSet);
			if((temp.size() != 0)&&(temp.size() < possibleBags.size())){
				toPlace = itemSet.get(i);
				possibleBags = temp;
			}else if(temp.size() == possibleBags.size()){
				if(itemSet.get(i).getNumConstraints() < toPlace.getNumConstraints()){
					toPlace = itemSet.get(i);
					possibleBags = temp;
				}
			}else if((temp.size() != 0)&&(possibleBags.size() == 0)){
				toPlace = itemSet.get(i);
				possibleBags = temp;
			}
		}
		
		ArrayList<Item> newSet = new ArrayList<Item>();
		
		for(Item i : itemSet){
			if(!i.getLabel().equals(toPlace.getLabel())){
				newSet.add(i);
			}
		}
		
		ArrayList<SetHandler> sets = new ArrayList<SetHandler>();
		
		for(Bag b : possibleBags){
			BagHandler newHand = bagSet.copyHandler();
			newHand.getBag(b.getBagName()).addToBag(toPlace);
			sets.add(new SetHandler(newSet.toArray(new Item[newSet.size()]), newHand));
		}
		
		if(sets.size() == 0){return null;}
		
		return sets.toArray(new SetHandler[sets.size()]);
	}
	
	public BagHandler getBagHandler(){return bagSet;}
	
	public boolean isValid(){
		boolean isValid = bagSet.isValidSolution();
		
		for(Item i : itemSet){
			if(i.applyConstraints(bagSet).size() == 0){isValid = false;}
		}
		
		return isValid;
	}
	
	public boolean isComplete(){
		bagSet.checkComplete(itemSet.toArray(new Item[itemSet.size()]));
		return bagSet.isComplete();
	}
	
	public String toString(){
		String output = "";
		
		output += "Wasted Space: " + bagSet.wastedWeight() + "\n\n";
		
		for(Bag b : bagSet.getAllBags()){
			output += b.getBagName() + ", Num Items: " + b.getNumberItems() + ", Total Weight: " + (b.getPossibleCapacity() - b.getRemainingCapacity()) + "\n";
			for(Item i : b.getItems()){
				output += "\t" + i.getLabel() + "\n";
			}
		}
		
		return output;
	}
}
