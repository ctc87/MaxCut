package maxcut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grasp {
	
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	private int n_nodes;
	private int n_arcs;

	public static void main(String[] args) {
		
		
	}
	
	public Grasp(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String w = "";
		w = reader.readLine();
		this.setN_nodes(Integer.parseInt(w));
		while(reader.ready()) {
			w = reader.readLine();
		}
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

}
