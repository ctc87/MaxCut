package maxcut;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>MultiBoot</h1> This class represents a mthauristic concept called ¿?¿MULTIBOoot?. 
 * The objective of this procedure its alternate the nest steps:
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
		Grasp2 grasp = null;
		Graph graph = null;
		try {
			grasp = new Grasp2("set1/g3.rud",10);
			graph = new Graph("set1/g3.rud",10);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> solution, bestSolution;
		int actualObjetiveValue, bestObjetiveValue ;
		solution = grasp.execute(); 
		bestSolution = new ArrayList<Integer>(solution);
		int i = 0;
		while(i < maxCylesNotImproved) {
			solution = MetaheuristicTools.localsearch(solution, NEIGHBOR_DISTANCE);
			actualObjetiveValue = MetaheuristicTools.function(solution, graph);
			bestObjetiveValue = MetaheuristicTools.function(bestSolution, graph);
			if(actualObjetiveValue > bestObjetiveValue ) {
				bestSolution = new ArrayList<Integer>(solution);
				i = 0;
			} else {
				i++;
			}
			solution = grasp.execute(); 
		}
		System.out.println(solution);
		
	}

}
