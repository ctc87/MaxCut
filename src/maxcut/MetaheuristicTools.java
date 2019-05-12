package maxcut;

import java.util.ArrayList;

public class MetaheuristicTools {
	
	
	public static int function(ArrayList<Integer> sol, Graph g) {
		/*if(!sol.contains(1)) {
			return -99999999;
		}*/
		int sum = 0;
		for(int i = 0; i < sol.size(); i++) {
			for(int j = 0; j < sol.size(); j++) {
				if(sol.get(i) == 1) {
					if(sol.get(j) != 1) {
						if(g.weight.get(i).get(j) != null) {
							sum += g.weight.get(i).get(j);
						}
					}
				}
		
			}
		}
		return sum;
	}

	public static ArrayList<Integer> localsearch(ArrayList<Integer> solution, int K, Graph g){
		ArrayList<Integer> best_solution;
		ArrayList<ArrayList<Integer>> neighbour = new ArrayList<ArrayList<Integer>>();
		best_solution = new ArrayList<Integer>(solution);
		neighbour.add(new ArrayList<Integer>(solution));
		for(int i = 0; i < solution.size(); i++) {
			ArrayList<Integer> aux = new ArrayList<Integer>(solution);
			
			if(aux.get(i) == 1) {
				if(i >= K){				
					if(aux.get(i - K) == 0) {
						aux.set(i - K, 1);
						aux.set(i, 0);
						if(!neighbour.contains(aux)) {
							neighbour.add(new ArrayList<Integer>(aux));
							
						}
						aux.set(i - K, 0);
						aux.set(i, 1);
					}
				}
				if(i <= solution.size() - 1 - K){
					if(aux.get(i + K) == 0) {
						aux.set(i + K, 1);
						aux.set(i, 0);
						if(!neighbour.contains(aux)) {
							neighbour.add(new ArrayList<Integer>(aux));
							
						}
						aux.set(i + K, 0);
						aux.set(i, 1);
					}
				}
			}
			
		}
		
		int max = 0;
		for(int i = 0; i < neighbour.size(); i++) {
			if(MetaheuristicTools.function(neighbour.get(i), g) > max) {
				max = function(neighbour.get(i), g);
				
				best_solution = new ArrayList<Integer>(neighbour.get(i));
			}
		}
		
		return best_solution;
	}
}
