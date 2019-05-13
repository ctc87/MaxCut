package P4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class VNS.
 */
public class VNS {
	
	/** The sol. */
	public Solucion sol;
	
	/** The sol G. */
	public Solucion solG;
	
	/** The nodo. */
	private int nodo;
	
	/** The arco. */
	private int arco;
	
	/** The distancias. */
	private int [][] distancias;
	
	/** The weight. */
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> coneccion = new ArrayList<ArrayList<Integer>>();
	
	/** The k. */
	private int k;
	
	/**
	 * Gets the sol.
	 *
	 * @return the sol
	 */
	public Solucion getSol() {
		return sol;
	}

	/**
	 * Sets the sol.
	 *
	 * @param sol the new sol
	 */
	public void setSol(Solucion sol) {
		this.sol = sol;
	}

	/**
	 * Gets the nodo.
	 *
	 * @return the nodo
	 */
	public int getNodo() {
		return nodo;
	}

	/**
	 * Sets the nodo.
	 *
	 * @param nodo the new nodo
	 */
	public void setNodo(int nodo) {
		this.nodo = nodo;
	}

	/**
	 * Gets the arco.
	 *
	 * @return the arco
	 */
	public int getArco() {
		return arco;
	}

	/**
	 * Sets the arco.
	 *
	 * @param arco the new arco
	 */
	public void setArco(int arco) {
		this.arco = arco;
	}

	/**
	 * Gets the distancias.
	 *
	 * @return the distancias
	 */
	public int[][] getDistancias() {
		return distancias;
	}

	/**
	 * Sets the distancias.
	 *
	 * @param distancias the new distancias
	 */
	public void setDistancias(int[][] distancias) {
		this.distancias = distancias;
	}

	/**
	 * Gets the k.
	 *
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * Sets the k.
	 *
	 * @param k the new k
	 */
	public void setK(int k) {
		this.k = k;
	}

	/**
	 * Leer.
	 *
	 * @param filename the filename
	 */
	public void leer(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String w = "";
			String[] token;
			w = reader.readLine();
			token = w.split("\\s+");
			this.nodo = Integer.parseInt(token[0]);
			this.arco = Integer.parseInt(token[1]);
			//System.out.println(arco + "ss");
			distancias = new int [arco][arco];
			for(int i = 0; i < this.arco; i++) {
				ArrayList<Integer> dummy = new ArrayList<Integer>();
				ArrayList<Integer> cone = new ArrayList<Integer>();
				for(int j = 0; j < this.arco; j++) {
					dummy.add(0);
					cone.add(1);
				}
				weight.add(new ArrayList<Integer>(dummy));
				coneccion.add(cone);
			}
			int actual_node = 0;
			while(reader.ready()) {
				w = reader.readLine();
				token = w.split("\\s+");
				int start = Integer.parseInt(token[0]) - 1;
				int end = Integer.parseInt(token[1]) - 1;
				//System.out.println("start - > " + start);
				int peso = Integer.parseInt(token[2]);
				weight.get(start).set(end, peso);
				weight.get(end).set(start, peso);
				//System.out.println(peso);
				
				boolean v = true; 
				for(int i=0;i<arco;i++) {
					for(int j=i + 1;j<arco;j++) {
					
						if(i== start && j == end) {
							distancias[i][j] = distancias[j][i] = peso;
							//System.out.println(distancias[i][j]);
							
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * Instantiates a new vns.
	 *
	 * @param filename the filename
	 * @param kmax_ the kmax
	 */
	public VNS (String filename, int kmax_) {			
		this.leer(filename);
		sol = randomSolucion();	
		this.k = kmax_;
		solG = new Solucion(sol);
		int k =0;
		do {
			
			sol = new Solucion(solG);
			k = 1;
			Solucion aux;
			do {
				
				aux = shake(k , solG);
				aux = localSearch(aux);
				if(evaluear(aux) > evaluear(solG)) {
					solG = new Solucion(aux);
					k = 1;
				}else {
					k++;
				}
				
			}while(k <= kmax_);
			
		}while(!sol.igual(solG));
	}
	
	/**
	 * Shake.
	 *
	 * @param k the k
	 * @param solG the sol G
	 * @return the solucion
	 */
	//Metodo que devuelve los vecinos aleatorios
	private Solucion shake(int k, Solucion solG) {
		
		Random ran = new Random();
		ArrayList<Solucion> aux = new ArrayList<Solucion>();
		Solucion auxsol = new Solucion(solG); 
		for(int i=0;i<k;i++) {
			for(int j=0;j<this.getNodo();j++) { //Generar los vecinos auxsol
				if(auxsol.getSol()[i]) {
					auxsol.movernodo2(i);
					aux.add(auxsol);
				}else {
					auxsol.movernodo1(i);
					aux.add(auxsol);
				}
			}
			// actualiza auxsol con sus nodos vecinos
			auxsol = aux.get(ran.nextInt(aux.size()));
		}
		return auxsol;
	}

	/**
	 * Evaluear.
	 *
	 * @param sol_ the sol
	 * @return the int
	 * Metodos que evaluar la solocuion
	 */
	public int evaluear(Solucion sol_) {
		
		 sol = sol_;
		    int contador = 0;
		    int suma = 0;

		    for (int i = 0; i < sol.getSol().length; i++){
		      if (sol.getSol()[i]){
		        contador++;
		        for (int j = i + 1 ; j < sol.getSol().length; j++){
		          if (sol.getSol()[j]){
		            suma += this.weight.get(i).get(j);
		          }
		        }
		      }
		    }
		    return suma;
	}
	
	/**
	 * Local search.
	 *
	 * @param sol_ the sol
	 * @return the solucion
	 */
	private Solucion localSearch(Solucion sol_) {
		ArrayList <Solucion> listadevecinos = new ArrayList <Solucion>();
		Solucion mejor = new Solucion (sol_);				
		Solucion inicial = new Solucion (sol_);
		do {
			inicial = mejor; 
			listadevecinos = generarVecino(inicial); //generar los vecinos proximos
			for(Solucion vecino : listadevecinos) {
				if(evaluear(vecino) > evaluear(mejor)) { // comprobar si es mejor o no 
					mejor = vecino;
				}
			}
		}while(!mejor.igual(inicial));
		return mejor;
	}
	
	/**
	 * Generar vecino.
	 *
	 * @param sol_ the sol
	 * @return the array list
	 */
	/*Metodo que genera vecinos 
	 * 1 1 0 0 0 1 
	 * si encuentra con un 1 lo intercabia con el cero 
	 * */
	private ArrayList<Solucion> generarVecino(Solucion sol_) {
		ArrayList<Solucion> aux = new ArrayList<Solucion>();		
		for(int i=0;i<this.nodo;i++) {
			Solucion dummy = new Solucion(sol_);// copiar la solucion anterior
			if(dummy.getSol()[i]) {
				dummy.movernodo2(i);
			}
			else {
				dummy.movernodo1(i);
				aux.add(dummy);
			}
		}
		return aux;
	}
	
	/**
	 * Random solucion.
	 *
	 * @return the solucion
	 */
	private Solucion randomSolucion() {
		Random ran = new Random();
		Solucion aux = new Solucion(this.nodo);
		for(int i=0;i<this.getNodo();i++) {
			aux.getSol()[i]= ran.nextBoolean();
		}
		return aux;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String a ="";
		
		for(int i=0;i<this.nodo;i++) {
			if(sol.getSol()[i]) {
				a += " , " + 1 ;
			}else {
				a += ", " + 0;
			}
		}
			
		return a;
	}
	/*
	public Boolean isConecNodo(Solucion sol, ArrayList<ArrayList<Integer>> array) {
		int [] conected ;
		for(int i=0;i<sol.getSol().length;i++) {
			if(sol.g)
		}
	}
	*/
	/**
	 * F obj.
	 *
	 * @return the int
	 */
	public int valorMax(){
		return evaluear(sol);
	}
	
}
