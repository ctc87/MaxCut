package maxcut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class VND.
 */
public class VND {
	
	/** The sol. */
	public Solucion sol;
	
	/** The sol G. */
	public Solucion solG;
	
	/** The nodo. */
	private int nodo;
	
	/** The arco. */
	private int arco;
	
	/** The weight. */
	ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * Leer.
	 *
	 * @param filename the filename
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void leer(String filename) throws NumberFormatException, IOException {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String w = "";
			String[] token;
			w = reader.readLine();
			token = w.split("\\s+");
			this.nodo = Integer.parseInt(token[0]);
			this.arco = Integer.parseInt(token[1]);
			for(int i = 0; i < this.arco; i++) {
				ArrayList<Integer> dummy = new ArrayList<Integer>();
				for(int j = 0; j < this.arco; j++) {
					dummy.add(0);
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
			
		System.out.println("Fichero leído");
	}
	
	/**
	 * Instantiates a new vnd.
	 *
	 * @param filename the filename
	 * @param k the k
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public VND (String filename, int k,Solucion sols) throws NumberFormatException, IOException {
		this.leer(filename);
		sol = sols;	
		int i = 1;		
		do {
			Solucion aux = sol;
			aux = localSearch(aux);
			if(evaluear(aux) < evaluear(sol)) {
				sol = aux;
				i = 1;
			}else {
				i+=1;
			}
		}while(i < k);
	}
	
	/**
	 * Random solucion.
	 *
	 * @return the solucion
	 * Metodos que genera una solucion aleatoria
	 */
	private Solucion randomSolucion() {
		Random ran = new Random();
		Solucion aux = new Solucion(this.nodo);
		for(int i=0;i<this.getNodo();i++) {
			aux.getSol()[i]= ran.nextBoolean();
		}
		return aux;
	}
	
	/**
	 * Local search.
	 *
	 * @param sol_ the sol
	 * @return the solucion
	 * Metodos de busqueda local 
	 * 
	 */
	private Solucion localSearch(Solucion sol_) {
		ArrayList <Solucion> listadevecinos = new ArrayList <Solucion>(); // array de soluciones
		Solucion mejor = new Solucion (sol_);			 // solucion 	
		Solucion inicial = new Solucion (sol_);
		do {
			inicial = mejor;  
			listadevecinos = generarVecino(inicial); //generar los vecinos proximos
			for(Solucion vecino : listadevecinos) { // recorror el array de los vecino generados
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
	 * Metodos que genera los vecinos proximos
	 * array tipo << 1 1 0 0 0> < 0 1 0 0 0> < 0 1 1 1 1> etc >
	 */
	private ArrayList<Solucion> generarVecino(Solucion sol_) {
		ArrayList<Solucion> aux = new ArrayList<Solucion>();	 // array de soluciones 	
		for(int i=0;i<this.nodo;i++) {
			Solucion dummy = new Solucion(sol_);// copiar la solucion anterior
			if(dummy.getSol()[i]) { // poner el nodo vecino a 0 
				dummy.movernodo2(i);
			}
			else {
				dummy.movernodo1(i); // poner el nodo vecino a 1
				aux.add(dummy);
			}
		}
		return aux;
	}
	
	/**
	 * Evaluear.
	 *
	 * @param sol_ the sol
	 * @return the int
	 */
	/*Metodos que hace las sumas de los valores de la matriz de resultado
	 * */
	public int evaluear(Solucion sol_) {		
		 sol = sol_;
		    int suma = 0;
		    for (int i = 0; i < sol.getSol().length; i++){
		      if (sol.getSol()[i]){
		        for (int j = i + 1 ; j < sol.getSol().length; j++){
		          if (sol.getSol()[j]){
		        	  //System.out.println(weight.get(i).get(j));
		            suma += this.weight.get(i).get(j);
		          }
		        }
		      }
		    }
		    return suma;
	}

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
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public ArrayList<ArrayList<Integer>> getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(ArrayList<ArrayList<Integer>> weight) {
		this.weight = weight;
	
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
	
	/**
	 * Valor M.
	 *
	 * @return the int
	 */
	public int valorM(){
		return evaluear(sol);
	}
}
