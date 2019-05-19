package maxcutdef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Deterministic {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> rcl = new ArrayList<Integer>();
	Map<Integer, Integer> rclmap = new HashMap<>();
	private int n_nodes;
	private int n_arcs;
	private int k;
	private int rcl_size;
	
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		
		Deterministic d = new Deterministic("set2/sg3dl051000.mc");
		ArrayList<Integer> solution = d.execute();
		
	}
	
	public ArrayList<Integer> execute() throws InterruptedException{
		ArrayList<Integer> solution = new ArrayList<Integer>();
		for(int i = 0; i < this.getN_nodes(); i++) {
			solution.add(0);
		}
		
		this.rcl = make_rcl(solution);
		solution = make_solution();
		System.out.println("VAL: " + function(solution) + " ---- " + solution);
		return solution;
	}
	
	public ArrayList<Integer> make_solution() throws InterruptedException{
		ArrayList<Integer> solution = new ArrayList<Integer>(this.getN_nodes());
		for(int i = 0; i < this.getN_nodes(); i++) {
			solution.add(0);
		}
		Boolean end = false;
		int maxitr = 100;
		int count = 0;
		while(!end) {
			Map.Entry<Integer,Integer> ent = rclmap.entrySet().iterator().next();
			make_rcl(solution);
			ArrayList<Integer> actual_sol = new ArrayList<Integer>(solution);
			solution.set(ent.getKey(), 1);
			if(function(solution) <= function(actual_sol)) {
				count++;
				
			}else {
				count = 0;
			}
			if(count == maxitr) {
				end = true;
				break;
			}
		}
			
		return solution;
		
	}
	
	Deterministic(String filename) throws NumberFormatException, IOException{
		
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
		this.setRcl_size(this.getN_nodes()/150);
		
	}
	
	public ArrayList<Integer> make_rcl(ArrayList<Integer> solution){
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
			

		}
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
