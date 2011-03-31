CSP Agent
By: Ryan O'Meara and Frank Bruzzese

HOW TO RUN:
In UNIX, run the ROMearaFBruzzeseCSP.sh script, passing in the file name for the input.
The file name should be using a relative path, if it is not already in the same directory
as the script.

OUR APPROACH:

We wanted to arrange our program to use states similar to the first assignment.  So we 
designed a programStep, which stores a particular arrangement of bags and items. This
step can perform a heuristic which determines which steps to take next.  These next steps
are simply new programSteps which can be processed in the same way.

Here is some pseudo-code walking through the logic of examining a programStep to find
a valid solution


Add first programStep to array list		// this step represents the starting state, i.e. no items in any bags

loop through array				// this loop walks through the possible programSteps using depth first search
{
	is this step a valid solution?
		return that step
	
	does it look like this step can not be solved?	// this clause performs forward checking (and AC) to determine if
	{						// continuing down this path will yield a valid solution
		remove this step from the array
		continue to loop through the array
	}

	Determine which item to place next			// this performs the item heuristic, and the returned item is used in the bag heuristic below

	get children of first programStep in array (index 0)	// getting the children performs the bag heuristic, which returns the children in the order which they should be expolored
	insert these children into the array immediately after their parent (index 1 through numChildren - 1)
	remove the first programStep in the array (index 0)
{

If you have have not found a solution at this point, then return null because there is none to be found

In the above pseudo-code, the Item heuristic used MRV to determine the next item, and a Degree heuristic to break ties.
The Bag heuristic was an LCV heuristic.  

	

TESTING:
We ran all of the provided constraint tests in order to test our agent.  While some of our
answers were different then the ones provided in the solution file, checking them by hand
has confirmed that that are indeed valid answers.  Overall, our program appears to perform
well and fairly efficiently.

STRENGTHS AND WEAKNESSES:
The use of a Constraint interface and the Item and Bag interfaces allows constraints to be
added, subtracted, or modified easily.  This allows our program to be easily converted to 
handle other constraint satisfaction problems.  The Depth-First Search is done with a single
array list, meaning there is no searching required to find the next item and minimal search
required in order to back track.

