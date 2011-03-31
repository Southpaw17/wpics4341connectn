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
}
