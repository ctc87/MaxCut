package maxcut;

import java.io.IOException;
import java.util.Locale;

public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		 long startTime = System.currentTimeMillis();
		 System.out.println("");
		String file = "set1/g11.rud";
		  System.out.println("VNS - >");
		VNS vns = new VNS(file,10);
		 System.out.println("Solucion:\n" + vns.toString() + "\n Valor :");
		 System.out.format(Locale.ENGLISH," %d ", vns.valorMax());
		 
		 System.out.println("");
		 System.out.println(" | --------------------------------------------- | ");
		 System.out.println("VND - >");
		  VND vnd = new VND(file,10,vns.getSol());
		//  System.out.println("Solucion:\n" + vnd.toString() + "\n Valor :");
		  System.out.format(Locale.ENGLISH," %d ", vnd.valorM());
	}
	
}
