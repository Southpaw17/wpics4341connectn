package edu.wpi.cs4341.csp;

import java.util.ArrayList;

import edu.wpi.cs4341.csp.heuristic.ArbitraryBag;
import edu.wpi.cs4341.csp.heuristic.ArbitraryItem;
import edu.wpi.cs4341.csp.heuristic.LCV;
import edu.wpi.cs4341.csp.heuristic.MRVDegree;

/**
 * Runs the main program an parses the file to solve the problem
 * 
 * @author Ryan O'Meara, Frank Bruzesse
 */
public class Main_CSP {

	/**
	 * @param args Command line arguments.  args[0] is the file name containing
	 * the parameters for the CSP problem
	 */
	public static void main(String[] args) {
		long fullArbAvg = 0, smartItemAvg = 0, smartAvg = 0;
		
		//go through each file parser (and therefore each heuristic) 5 times,
		//record mean time.  output answer on last run of each heuristic only
		ProblemStep solution = null;
		
		for(int j = 0; j < 3; j++){
			for(int i = 0; i < 5; i++){
				long start, end;
				start = System.currentTimeMillis();
				
				
				if((args != null)&&(args.length > 0)){
					if(j == 0){
						solution = solveProblem((new FileParser(new ArbitraryItem(), new ArbitraryBag())).parseFile(args[0]));
					}else if(j ==1){
						solution = solveProblem((new FileParser(new MRVDegree(), new ArbitraryBag())).parseFile(args[0]));
					}else{
						solution = solveProblem((new FileParser(new MRVDegree(), new LCV())).parseFile(args[0]));
					}
				}
				
				end = System.currentTimeMillis();
				
				if(j == 0){
					fullArbAvg += (end - start);
				}else if(j == 1){
					smartItemAvg += (end - start);
				}else{
					smartAvg += (end - start);
				}
			}
		}
		
		fullArbAvg /= 5;
		smartItemAvg /= 5;
		smartAvg /= 5;
		
		if(solution != null){
			System.out.println(solution);
		}else{
			System.out.println("No way, no how.  Sorry.  Least you have a chance to escape the law");
		}
		
		System.out.println("\nTime to calculate in milliseconds, avg of 5 runs with each: ");
		System.out.println("BT Only\t\t|BT + H\t\t|BT + H + (FC/AC)");
		System.out.println("---------------------------------------------------");
		System.out.println(fullArbAvg + "    \t\t|" + smartItemAvg + "   \t\t|" + smartAvg);
		
		/*ProblemStep solution = null;
		
		if((args != null)&&(args.length > 0)){
			solution = solveProblem(smart.parseFile(args[0]));
		}
		
		if(solution != null){
			System.out.println(solution);
		}else{
			System.out.println("No way, no how.  Sorry.  Least you have a chance to escape the law");
		}*/
	}
	
	public static ProblemStep solveProblem(ProblemStep initialStep){
		//Create arraylist of steps
		ArrayList<ProblemStep> allSteps = new ArrayList<ProblemStep>();
		initialStep.updateAllowed();
		allSteps.add(initialStep);
		
		//go through steps.  create children, add to arraylist right after parent, remove parent
		//process step at index zero, until solved.  remove any steps outright which are not
		//solvable
		while(allSteps.size() > 0){
			//Check step for being complete/solved
			if(allSteps.get(0).isValidSolution()){return allSteps.get(0);}
			
			if(!allSteps.get(0).isSolvable()){
				allSteps.remove(0);
				continue;
			}
			
			int insert = 1;
			
			if(allSteps.size() == 1){insert = 0;}
			
			Item toProcess = allSteps.get(0).getItemHeuristic().selectItem(allSteps.get(0).getItemsToPlace());
			
			if(toProcess != null){
				ProblemStep[] nextSteps = allSteps.get(0).getBagHeuristic().determineNextStep(allSteps.get(0), toProcess);
				
				if(nextSteps != null){
					for(ProblemStep s : nextSteps){
						if(s != null){
							if(insert > 0){
								allSteps.add(insert, s);
								insert++;
							}else{
								allSteps.add(s);
							}
						}
					}
				}
			}
			
			allSteps.remove(0);
		}
		
		return null;
	}

}
