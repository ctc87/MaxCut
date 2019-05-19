package maxcutdef;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Grasp {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> rcl = new ArrayList<Integer>();
	Map<Integer, Integer> rclmap = new HashMap<>();
	private int n_nodes;
	private int n_arcs;
	private int k;
	private int rcl_size;

	public static void main(String[] args) throws IOException {
		
		Grasp g = new Grasp("set2/sg3dl105000.mc",100);
		ArrayList<Integer> solution = g.execute();
		System.out.println(g.function(solution) + " --- " + solution);
		//System.out.println("FIN");
		//ArrayList<Integer> solution2 = g.tabooSearch(10000);
	}

	public Grasp(String filename, int k) throws IOException {
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
		
		rclmap = sortMapByValues(rclmap);		
		this.setK(k);
		this.setRcl_size(this.getN_nodes()/100);
		System.out.println();

	}

	public ArrayList<Integer> execute() {
		ArrayList<Integer> best_solution = new ArrayList<Integer>(this.getN_nodes());
		ArrayList<Integer> actual_solution = new ArrayList<Integer>(this.getN_nodes());
		
		for(int i = 0; i < this.getK(); i++) {
			System.out.println("Iteración: " + i);
			actual_solution = construct();
			actual_solution = localsearch(actual_solution,1);
			if(function(actual_solution) > function(best_solution)) {
				best_solution = new ArrayList<Integer>(actual_solution);
				System.out.println("VAL: " + function(best_solution) + " --> size: " + Collections.frequency(best_solution, 1) + " ---- " + best_solution);
			}
		}
		return best_solution;
	}
	
	public ArrayList<Integer> construct(){
		ArrayList<Integer> rcl = new ArrayList<Integer>(this.getN_nodes());
		ArrayList<Integer> solution = new ArrayList<Integer>(this.getN_nodes());
		for(int i = 0; i < this.getN_nodes(); i++) {
			solution.add(0);
		}
		Boolean end = false;
		while(!end) {
			rcl = make_rcl(solution);
			int numero;
			numero = get_random_index(rcl);
			ArrayList<Integer> old_sol = new ArrayList<Integer>(solution);
			solution.set(numero, 1);
			if(function(solution) < function(old_sol)) {
				end = true;
				solution.set(numero, 0);
			}
			
		}
		//System.out.println(solution);
		return solution;
	}
	
	public ArrayList<Integer> make_rcl(ArrayList<Integer> solution){
		//--------------- este criterio va mucho mas rapido pero da peores resultados ----------//
		/*
		for(int i = 0; i < this.getN_nodes(); i++) {
			int sum = 0;
			if(solution.get(i) != 1) {
				for(int j = 0; j < this.getN_nodes(); j++) {
					if(solution.get(j) != 1) {
						if(weight.get(i).get(j) != null) {
							sum += weight.get(i).get(j);
						}
					}
				}
			}
			
			rclmap.put(i, sum);
			

		}*/
		//-----------------------------------------------------------------------------------//
		// ------------- este criterio da mejores resultados pero tarda mucho más ------------//
		for(int i = 0; i < this.getN_nodes(); i++) {
			if(solution.get(i) != 1) {
				solution.set(i, 1);
				rclmap.put(i, function(solution));
				solution.set(i, 0);
			}else {
				rclmap.put(i, -9999999);
			}
			
		}
		//-------------------------------------------------------------------------------//
		
		rclmap = sortMapByValues(rclmap);
		ArrayList<Integer> rcl = new ArrayList<Integer>(this.getN_nodes());
		for(int i = 0; i < this.getN_nodes(); i++) {
			rcl.add(0);
		}
		int count = 0;
		for (Map.Entry<Integer, Integer> entry : rclmap.entrySet()) {
			if(solution.get(entry.getKey()) != 1) {
				if(count < this.getRcl_size()) {
					rcl.set(entry.getKey(), 1);
					count++;
				}else {
					break;
				}
			}
		}
		return rcl;
		
	}

	public int get_random_index(ArrayList<Integer> rcl) {
		int numero;
		do {
			numero = (int) (Math.random() * this.getN_nodes() - 1) + 1;
		}while(rcl.get(numero) != 1);
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
                
                return ele2.getValue().compareTo(ele1.getValue());
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
		if(!sol.contains(1)) {
			return -99999999;
		}
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

	public int getRcl_size() {
		return rcl_size;
	}

	public void setRcl_size(int rcl_size) {
		this.rcl_size = rcl_size;
	}

}