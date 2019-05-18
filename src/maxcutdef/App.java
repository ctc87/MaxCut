package maxcutdef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import test.timer.Timer;


public class App {
	
	final static String SET_DITECROTY = "set/";
	
	final static int MAX_SET_NUMBER = 54;

	static int file_local = 1;
	static String  actual_alg = "VNS";
	static int k = 100;
	static int candiatatSize = 1000;
	
	
	static void menu() {
		  
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nBienvenido selecciones una opción.");
            System.out.println("\n1.Selecciona el algoritmo a ejecutar: actual -> " + actual_alg);
            System.out.println("2. Seleciona el set de entrada ejecutar actual -> " + constructFIle() +".");
            System.out.println("3. Execute "+ actual_alg +".");
            System.out.println("4. Exit.");
 
            try {
 
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                    	alghMenu();
                    	salir = true;
                        break;
                    case 2:
                		sn = new Scanner(System.in);
                		boolean correct_set = false;
                		while(!correct_set) {
                			System.out.println("Escribe el número de set de 1 a " + MAX_SET_NUMBER + ".\n");
                			int set = sn.nextInt();
                			if(set >= 1 && set <= MAX_SET_NUMBER) {
                				correct_set = true;
                				file_local = set;
                				System.out.println("Set " + set + " Seleccionado.");
                			} else {
                				System.out.println("Set " + set + " iválido.");
                			}
                		}
                        break;
                    case 3:
                    	Timer.start();
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Only options 1,2,3 or 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You migth insert number.");
                sn.next();
            }
        }
	}
	
	public static String constructFIle() {
		return SET_DITECROTY + file_local + ".rud"; 
	}
	
	public static void alghMenu() {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona el algoritmo a ejecutar.");
            System.out.println("\n1.GRASP.");
            System.out.println("2. VNS.");
            System.out.println("3. VND.");
            System.out.println("4. BVNS.");
            System.out.println("5. MULTI ARRANQUE.");
            System.out.println("6. DETERMINISTA.");
            System.out.println("7.TABOO.");
            System.out.println("8.Volver.");
 
            try {
 
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                		System.out.println("Grasp sleccionado");
                		graspMenu();
                		salir = true;
                    case 2:
                		System.out.println("Sin implementar");
                        break;
                    case 3:
                		System.out.println("Sin implementar");
                        break;
                    case 4:
                		System.out.println("Sin implementar");
                        break;
                    case 5:
                		System.out.println("Sin implementar");
                        break;
                    case 6:
                		System.out.println("Sin implementar");
                        break;
                    case 7:
                		System.out.println("Sin implementar");
                        break;
                    case 8:
                        salir = true;
                        break;
                    default:
                }
            } catch (InputMismatchException e) {
                System.out.println("You migth insert number.");
                sn.next();
            }
        }
	}
	
	public static void graspMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1.Cambiar el númerto de veces a jecutar el algoritmo  (" + k + " actualmente.)" );
            System.out.println("2. Cambiar el tamaño de la lista de candidatos (n/" + candiatatSize + " actualmente.)");
            System.out.println("3. Ejecutar VNS con los anteriores parámetros.");
            System.out.println("4. Volver al menu principal.");
 
            try {
 
                System.out.println("Write one option.");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 2:
                		sn = new Scanner(System.in);
                		System.out.println("Write the name of the file 'file.ext' to read K-means instance, and press enter.\n");
                        
                    case 1:
                		sn = new Scanner(System.in);
                		System.out.println("Write the number of K parameter, and press enter.\n");
                      
                        break;
                    case 3:
                    	Timer.start();
                    	executeGrasp();
                        System.out.println("Time:" + Timer.stop());
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                }
            } catch (InputMismatchException e) {
                System.out.println("You migth insert number.");
                sn.next();
            }
        }
	}
	
	public static void executeGrasp() {
		// TODO: CAMBIAR EL TAMAÑO DE LA LISTA DE CANSIDATOIS PARA EL CONSTRUCTOR
		Grasp g = null;
		try {
			g = new Grasp(constructFIle(),k);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Integer> solution = g.execute();
		System.out.println(g.function(solution) + " --- " + solution);
	}

}
