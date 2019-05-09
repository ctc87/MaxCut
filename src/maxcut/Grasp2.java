package maxcut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Grasp2 {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> rcl = new ArrayList<Integer>();
	Map<Integer, Integer> rclmap = new HashMap<>();
	private int n_nodes;
	private int n_arcs;
	private int k;
	private int rcl_size;
	private int sol_size;

	public static void main(String[] args) throws IOException {
		
		Grasp2 g = new Grasp2("set1/g3.rud",100);
		ArrayList<Integer> solution = g.execute();
		System.out.println(solution);
		
	}
	
	public Grasp2(String filename, int k) throws IOException {
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
			weight.add(new ArrayList<Integer>(dummy));
		}
		int actual_node = 0;
		int count_arcs = 0;
		int count_weight = 0;
		while(reader.ready()) {
			w = reader.readLine();
			token = w.split("\\s+");
			int start = Integer.parseInt(token[0]) - 1;
			int end = Integer.parseInt(token[1]) - 1;
			
			int weightt = Integer.parseInt(token[2]);
			weight.get(start).set(end, weightt);
			weight.get(end).set(start, weightt);
			
			
		}
		
		System.out.println("Fichero leído y parseado");
		
		this.setK(k);
		this.setRcl_size(this.getN_nodes()/3);
		this.setSol_size(this.getN_nodes()/5);

	}
	
	public ArrayList<Integer> execute() {
		ArrayList<Integer> best_solution = new ArrayList<Integer>(this.getN_nodes());
		ArrayList<Integer> actual_solution = new ArrayList<Integer>(this.getN_nodes());
		for(int i = 0; i < this.getK(); i++) {
			System.out.println("Iteración: " + i);
			actual_solution = construct();
			System.out.println("Solución construida");
			actual_solution = localsearch(actual_solution);
			System.out.println("Búsqueda local realizada");
			System.out.println(function(actual_solution));
			System.out.println(function(best_solution));
			if(function(actual_solution) > function(best_solution)) {
				best_solution = new ArrayList<Integer>(actual_solution);
				System.out.println("VAL: " + function(best_solution) + " ---- " + best_solution);
			}
			
		}
		
		return best_solution;
	}
	
	public ArrayList<Integer> construct(){
		ArrayList<Integer> rcl = new ArrayList<Integer>(this.getRcl_size());
		ArrayList<Integer> solution = new ArrayList<Integer>(this.getSol_size());
		for(int i = 0; i < this.getSol_size(); i++) {
			evaluate_rcl(solution);
			rcl = make_rcl();
			//System.out.println("CANDIDATOS \t" + rcl);
			//System.out.println("SOLUTION \t" + solution);
			int numero;
			Boolean is_in = false;
			do{
				is_in = false;
				numero = get_random_index(rcl);
				for(int j = 0; j < solution.size(); j++) {
					if(solution.get(j) == rcl.get(numero)) {
						is_in = true;
					}
				}
			}while(is_in);
			solution.add(rcl.get(numero));
			
		}
		return solution;
	}
	
	public int function(ArrayList<Integer> sol) {
		int sum = 0;
		for(int i = 0; i < sol.size(); i++) {
			for(int j = 0; j < this.getN_nodes(); j++) {
				if(!sol.contains(j)) {
					if(weight.get(sol.get(i)).get(j) != null) {
						sum += weight.get(sol.get(i)).get(j);
					}
				}
			}
		}
		return sum;
	}
	
	public ArrayList<Integer> make_rcl(){
		ArrayList<Integer> rcl = new ArrayList<Integer>(this.getN_nodes());
		int count = 0;
		for (Map.Entry<Integer, Integer> entry : rclmap.entrySet()) {
			if(count < this.getRcl_size()) {
				rcl.add(entry.getKey());
				count++;
			}else {
				break;
			}
		}
		return rcl;
		
	}
	
	public void evaluate_rcl(ArrayList<Integer> solution){
		for(int i = 0; i < this.getN_nodes(); i++) {
			int sum = 0;
			for(int j = 0; j < this.getN_nodes(); j++) {
				Boolean is_in_solution = false;
				for(int k = 0; k < solution.size(); k++) {
					if(solution.get(k) == j) {
						is_in_solution = true;
					}
				}
				if(!is_in_solution) {
					if(weight.get(i).get(j) != null) {
						sum += weight.get(i).get(j);
					}
				}
			}
			rclmap.put(i, sum);
		}
		rclmap = sortMapByValues(rclmap);
	}
	
	public int get_random_index(ArrayList<Integer> rcl) {
		int numero;
		numero = (int) (Math.random() * this.getRcl_size() - 1) + 1;
		return numero;
	}
	
	private static Map<Integer, Integer> sortMapByValues(Map<Integer, Integer> aMap) {
        
        Set<Entry<Integer,Integer>> mapEntries = aMap.entrySet();
        
        
        
        // used linked list to sort, because insertion of elements in linked list is faster than an array list. 
        List<Entry<Integer,Integer>> aList = new LinkedList<Entry<Integer,Integer>>(mapEntries);

        // sorting the List
        Collections.sort(aList, new Comparator<Entry<Integer,Integer>>() {

            @Override
            public int compare(Entry<Integer, Integer> ele1,
                    Entry<Integer, Integer> ele2) {
                
                return ele1.getValue().compareTo(ele2.getValue());
            }
        });
        
        // Storing the list into Linked HashMap to preserve the order of insertion. 
        Map<Integer,Integer> aMap2 = new LinkedHashMap<Integer, Integer>();
        for(Entry<Integer,Integer> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }
        
        // printing values after soring of map
        
        
        return aMap2;
        
    }
	
	public ArrayList<Integer> localsearch(ArrayList<Integer> solution){
		ArrayList<Integer> best_solution;
		ArrayList<ArrayList<Integer>> neighbour = new ArrayList<ArrayList<Integer>>();
		best_solution = new ArrayList<Integer>(solution);
		neighbour.add(new ArrayList<Integer>(solution));
		for(int i = 0; i < this.getSol_size(); i++) {
			ArrayList<Integer> aux = new ArrayList<Integer>(solution);
			if(!(aux.get(i) - 1 < 0)){				
				if(!aux.contains(aux.get(i) - 1)) {
					aux.set(i, aux.get(i) - 1);
					if(!neighbour.contains(aux)) {
						neighbour.add(new ArrayList<Integer>(aux));
						aux.set(i, aux.get(i) + 1);
					}
				}
			}
			if(!(aux.get(i) + 1 > this.getSol_size() - 1)){
				if(!aux.contains(aux.get(i) + 1)) {
					aux.set(i, aux.get(i) + 1);
					if(!neighbour.contains(aux)) {
						neighbour.add(new ArrayList<Integer>(aux));
						aux.set(i, aux.get(i) - 1);
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

	public int getRcl_size() {
		return rcl_size;
	}

	public void setRcl_size(int rcl_size) {
		this.rcl_size = rcl_size;
	}

	public int getSol_size() {
		return sol_size;
	}

	public void setSol_size(int sol_size) {
		this.sol_size = sol_size;
	}

}
