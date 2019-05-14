package maxcut;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>MultiBoot</h1> This class represents a mthauristic concept called ¿?¿MULTIBOoot?. 
 * The objective of this procedure its alternate the next steps:
 *  <ul>
 *  	<li>Generate solution</li>
 *  	<li>Improve this solution</li>
 *  </ul>
 *  
 * This is repeated while a previously selected stop condition is met.
 * 
 * @version 1.0
 * @date 12 may. 2019
 */
public class MultiBoot {
	
	final static int NEIGHBOR_DISTANCE = 1;
	
	static void execute(int maxCylesNotImproved) {
		Grasp grasp = null;
		try {
			grasp = new Grasp("set1/g3.rud",10);
			//graph = new Graph("set1/g3.rud",10);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> solution, bestSolution;
		int actualObjetiveValue, bestObjetiveValue ;
		solution = grasp.execute(); 
		bestSolution = new ArrayList<Integer>(solution);
		int i = 0;
		while(i < maxCylesNotImproved) {
			//solution = grasp.localsearch(solution, NEIGHBOR_DISTANCE);
			solution = grasp.localsearch(solution);
			actualObjetiveValue = grasp.function(solution);
			bestObjetiveValue = grasp.function(bestSolution);
//			System.out.println( grasp.function(solution));
//			System.out.println( grasp.function(bestSolution));
			if(actualObjetiveValue > bestObjetiveValue ) {
				bestSolution = new ArrayList<Integer>(solution);
				System.out.println( grasp.function(bestSolution));
				i = 0;
			} else {
				i++;
			}
			solution = grasp.execute(); 
		}
		System.out.println( grasp.function(bestSolution));
		
	}

}
