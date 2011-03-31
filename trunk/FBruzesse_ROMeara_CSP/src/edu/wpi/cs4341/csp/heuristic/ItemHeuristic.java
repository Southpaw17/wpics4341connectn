package edu.wpi.cs4341.csp.heuristic;

import edu.wpi.cs4341.csp.BagHandler;
import edu.wpi.cs4341.csp.Item;

public interface ItemHeuristic {
	public abstract Item selectItem(Item[] itemSet, BagHandler bagSet);
}
