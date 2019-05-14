package maxcut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Graph {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> rcl = new ArrayList<Integer>();
	Map<Integer, Integer> rclmap = new HashMap<>();
	private int n_nodes;
	private int n_arcs;
	
	private int k;
	
	private int rcl_size;
	private int sol_size;
	
	
	public Graph(String filename, int k) throws IOException {
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
		
		rclmap = sortMapByValues(rclmap);		
		
		this.setK(k);
		this.setRcl_size(this.getN_nodes()/3);
		this.setSol_size(this.getN_nodes()/5);

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


	/**
	 * @return the weight
	 */
	public ArrayList<ArrayList<Integer>> getWeight() {
		return weight;
	}


	/**
	 * @param weight the weight to set
	 */
	public void setWeight(ArrayList<ArrayList<Integer>> weight) {
		this.weight = weight;
	}


	/**
	 * @return the rcl
	 */
	public ArrayList<Integer> getRcl() {
		return rcl;
	}


	/**
	 * @param rcl the rcl to set
	 */
	public void setRcl(ArrayList<Integer> rcl) {
		this.rcl = rcl;
	}


	/**
	 * @return the rclmap
	 */
	public Map<Integer, Integer> getRclmap() {
		return rclmap;
	}


	/**
	 * @param rclmap the rclmap to set
	 */
	public void setRclmap(Map<Integer, Integer> rclmap) {
		this.rclmap = rclmap;
	}


	/**
	 * @return the k
	 */
	public int getK() {
		return k;
	}


	/**
	 * @param k the k to set
	 */
	public void setK(int k) {
		this.k = k;
	}


	/**
	 * @return the rcl_size
	 */
	public int getRcl_size() {
		return rcl_size;
	}


	/**
	 * @param rcl_size the rcl_size to set
	 */
	public void setRcl_size(int rcl_size) {
		this.rcl_size = rcl_size;
	}


	/**
	 * @return the sol_size
	 */
	public int getSol_size() {
		return sol_size;
	}


	/**
	 * @param sol_size the sol_size to set
	 */
	public void setSol_size(int sol_size) {
		this.sol_size = sol_size;
	}


	/**
	 * @return the n_nodes
	 */
	public int getN_nodes() {
		return n_nodes;
	}


	/**
	 * @param n_nodes the n_nodes to set
	 */
	public void setN_nodes(int n_nodes) {
		this.n_nodes = n_nodes;
	}


	/**
	 * @return the n_arcs
	 */
	public int getN_arcs() {
		return n_arcs;
	}


	/**
	 * @param n_arcs the n_arcs to set
	 */
	public void setN_arcs(int n_arcs) {
		this.n_arcs = n_arcs;
	}
}
