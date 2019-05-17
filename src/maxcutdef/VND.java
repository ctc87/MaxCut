package maxcutdef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VND {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	private int n_nodes;
	private int n_arcs;
	private int k;
	
	public static void main(String[] args) throws IOException {
		VND v = new VND("set1/g11.rud",50);
		ArrayList<Integer> solution;
		solution = v.execute("set1/g11.rud", 50);
		
	}
	
	public ArrayList<Integer> execute(String filename, int k) throws IOException{
		Boolean no_improvement = false;
		Grasp g = new Grasp(filename, 10);
		ArrayList<Integer> solution = g.execute();
		while(!no_improvement) {
			ArrayList<Integer> first_sol = new ArrayList<Integer>(solution);
			int l = 1;
			while(l <= k) {
				ArrayList<Integer> old_sol = new ArrayList<Integer>(solution);
				solution = localsearch(solution,l);
				
				if(function(solution) <= function(old_sol)) {
					l++;
					solution = new ArrayList<Integer>(old_sol);
				}else {
					l = 1;
				}
				System.out.println("VAL: " + function(solution) + " --> size: " + Collections.frequency(solution, 1) + " ---- " + solution);
			}
			if(solution.containsAll(first_sol)) {
				no_improvement = true;
			}
			
		}
		
		return solution;
		
	}
	
	public VND(String filename, int k) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String w = "";
		String[] token;
		w = reader.readLine();
		token = w.split("\\s+");
		this.setN_nodes(Integer.parseInt(token[0]));
		this.setN_arcs(Integer.parseInt(token[1]));
		for(int i = 0; i < this.getN_arcs(); i++) {
			ArrayList<Integer> dummy = new ArrayList<Integer>();
			for(int j = 0; j < this.getN_arcs(); j++) {
				dummy.add(null);
			}
			weight.add(dummy);
		}
		while(reader.ready()) {
			w = reader.readLine();
			token = w.split("\\s+");
			int start = Integer.parseInt(token[0]) - 1;
			int end = Integer.parseInt(token[1]) - 1;
			
			int weightt = Integer.parseInt(token[2]);
			weight.get(start).set(end, weightt);
			weight.get(end).set(start, weightt);
			
			
		}
		
		this.setK(k);

	}
	
	public ArrayList<Integer> localsearch(ArrayList<Integer> solution, int k){
		ArrayList<Integer> best_solution;
		ArrayList<ArrayList<Integer>> neighbour = new ArrayList<ArrayList<Integer>>();
		best_solution = new ArrayList<Integer>(solution);
		neighbour.add(new ArrayList<Integer>(solution));
		for(int i = 0; i < this.getN_nodes(); i++) {
			ArrayList<Integer> aux = new ArrayList<Integer>(solution);
			if(aux.get(i) == 1) {
				if(i >= k){				
					if(aux.get(i - k) == 0) {
						aux.set(i - k, 1);
						aux.set(i, 0);
						if(!neighbour.contains(aux)) {
							neighbour.add(new ArrayList<Integer>(aux));
							
						}
						aux.set(i - k, 0);
						aux.set(i, 1);
					}
				}
				if(i <= this.getN_nodes() - k - 1){
					if(aux.get(i + k) == 0) {
						aux.set(i + k, 1);
						aux.set(i, 0);
						if(!neighbour.contains(aux)) {
							neighbour.add(new ArrayList<Integer>(aux));
							
						}
						aux.set(i + k, 0);
						aux.set(i, 1);
					}
				}
			}
			
		}
		
		int max = 0;
		for(int i = 0; i < neighbour.size(); i++) {
			if(function(neighbour.get(i)) > max) {
				max = function(neighbour.get(i));
				
				best_solution = new ArrayList<Integer>(neighbour.get(i));
			}
		}
		
		return best_solution;
	}
	
	public int function(ArrayList<Integer> sol) {
		/*if(!sol.contains(1)) {
			return -99999999;
		}*/
		int sum = 0;
		for(int i = 0; i < sol.size(); i++) {
			if(sol.get(i) == 1) {
				for(int j = 0; j < sol.size(); j++) {
					if(sol.get(j) != 1) {
						if(weight.get(i).get(j) != null) {
							sum += weight.get(i).get(j);
						}
					}
				}
				
			}
		}
		return sum;
	}
	

	public int getN_nodes() {
		return n_nodes;
	}

	public void setN_nodes(int n_nodes) {
		this.n_nodes = n_nodes;
	}

	public int getN_arcs() {
		return n_arcs;
	}

	public void setN_arcs(int n_arcs) {
		this.n_arcs = n_arcs;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}


}
