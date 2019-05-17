package maxcutdef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

public class Taboo {

	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> rcl = new ArrayList<Integer>();
	Map<Integer, Integer> rclmap = new HashMap<>();
	private int n_nodes;
	private int n_arcs;
	private int k;
	String filename;

	public static void main(String[] args) throws IOException {
		
		Taboo g = new Taboo("set1/g11.rud",100);
		ArrayList<Integer> solution = g.execute(10);
		System.out.println(g.function(solution) + " --- " + solution);
	}

	public Taboo(String filename, int k) throws IOException {
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
		this.filename = filename;

	}

	public ArrayList<Integer> execute(int timeout) throws IOException {
		ArrayList<Integer> best_solution = new ArrayList<Integer>(this.getN_nodes());
		ArrayList<Integer> actual_solution = new ArrayList<Integer>(this.getN_nodes());
		Queue<ArrayList<Integer>> cola = new ArrayDeque<ArrayList<Integer>>(timeout);
		Grasp g = new Grasp(this.filename,10);
		best_solution = g.execute();
		for (int i = 0; i < this.getK(); i++) {
			System.out.println(i);
			ArrayList<Integer> newsol = localsearch(best_solution,1);
			System.out.println("VAL: " + function(newsol) + " ---- " + newsol);
			ArrayList<Integer> actual_taboo = taboo_element(best_solution, newsol);
			if ((!cola.contains(actual_taboo)) && (function(newsol) > function(best_solution))) {
				best_solution = new ArrayList<Integer>(newsol);
				System.out.println("VAL: " + function(best_solution) + " ---- " + best_solution);
			}
			cola.add(actual_taboo);
			System.out.println(cola);
			if(cola.size() == timeout) {
				cola.poll();
			}
			
			
			
		}

		System.out.println(best_solution);
		return best_solution;
	}
	
	public ArrayList<Integer> taboo_element(ArrayList<Integer> solution, ArrayList<Integer> neighbour) {
		ArrayList<Integer> final_element = new ArrayList<Integer>();
		for(int i = 0; i < this.getN_nodes();i++) {
			if(solution.get(i) == neighbour.get(i)) {
				final_element.add(null);
			}else {
				final_element.add(neighbour.get(i));
			}
			
		}
		return final_element;
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
				System.out.println(best_solution);
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
