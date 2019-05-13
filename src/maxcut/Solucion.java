package maxcut;

public class Solucion {

	private boolean[] sol;
		
	 public Solucion(int N) {
		   sol = new boolean[N];
		 }
	
	public Solucion(Solucion sol_) {
		sol = new boolean[sol_.sol.length];
		for(int i=0;i<sol.length;i++) {
			sol[i] = sol_.sol[i];
		}
	}
	
	public boolean [] getSol(){
	    return sol;
	  }
	
	public boolean igual(Solucion sol_) {
		boolean iguales = true;
		  for (int i = 0; i < sol_.getSol().length; i ++)
		      if (sol_.getSol()[i] != sol[i] && iguales){
		        iguales = false;
		      }
		    return iguales;
		  
	}
	
	public void movernodo1(int nodo) {
		sol[nodo] = true;
	}
	
	public void movernodo2(int nodo) {
		sol[nodo] = false;
	}
}
