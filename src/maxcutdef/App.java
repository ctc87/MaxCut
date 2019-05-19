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
	static int k_neightbor = 100;
	static int candiatatSize = 1000;
	static int maxIterNotImprved = 1000;
	static int cola = 100;
	static int timeout = 10;
	static ArrayList<Integer> solution;
	
	
	
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
            System.out.println("2. GVNS.");
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
	
	// ------------------GRASP---------------------------
	
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
 
                System.out.println("Escribe una opcion.");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                		sn = new Scanner(System.in);
                		System.out.println("Escribe el numero de veces a ejecutar el algoritmo.\n");
                		candiatatSize = sn.nextInt();
                        
                    case 2:
                		sn = new Scanner(System.in);
                		System.out.println("Escribe el tamaño de la lista de candidatos.\n");
                		k = sn.nextInt();
                      
                        break;
                    case 3:
                		System.out.println("Ejecutando...\n");
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
	
	// ------------------GVNS---------------------------

	public static void gvnsMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1.Cambiar el númerto de veces a jecutar el algoritmo  (" + k + " actualmente.)" );
            System.out.println("2. Cambiar el tamaño máximo del vencinadario (" + k_neightbor + " actualmente.)");
            System.out.println("3. Ejecutar GVNS con los anteriores parámetros.");
            System.out.println("4. Volver al menu principal.");
 
            try {
 
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
	                case 1:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el numero de veces a ejecutar el algoritmo.\n");
	            		k = sn.nextInt();
	                    
	                case 2:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el tamaño del vencindario de soluciones.\n");
	            		k_neightbor = sn.nextInt();
	                  
	                    break;
	                case 3:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executeGvns();
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
	
	public static void executeGvns() {
		GVNS v;
		try {
			v = new GVNS(constructFIle(),k_neightbor);
			ArrayList<Integer> solution;
			solution = v.execute(constructFIle(), k_neightbor,k);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// ------------------VND---------------------------

	public static void vndMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1 Cambiar el tamaño máximo del vencinadario (" + k_neightbor + " actualmente.)" );
            System.out.println("2. Ejecutar VND con los anteriores parámetros.");
            System.out.println("3. Volver al menu principal.");
 
            try {
 
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
	                case 1:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el tamaño del vencindario de soluciones.\n");
	            		k_neightbor = sn.nextInt();
	                    break;
	                case 2:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executevnd();
	                    System.out.println("Time:" + Timer.stop());
	                    break;
	                case 3:
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
	
	public static void executevnd() {
		VND v;
		try {
			v = new VND(constructFIle(),k_neightbor);
			ArrayList<Integer> solution;
			solution = v.execute(constructFIle(), k_neightbor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	// ------------------BVNS---------------------------

	public static void bvnsMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1.Cambiar el númerto de veces a jecutar el algoritmo  (" + k + " actualmente.)" );
            System.out.println("2. Cambiar el tamaño máximo del vencinadario (" + k_neightbor + " actualmente.)");
            System.out.println("3. Ejecutar BVNS con los anteriores parámetros.");
            System.out.println("4. Volver al menu principal.");
 
            try {
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
	                case 1:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el numero de veces a ejecutar el algoritmo.\n");
	            		candiatatSize = sn.nextInt();
	                    
	                case 2:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el tamaño del vencindario de soluciones.\n");
	            		k = sn.nextInt();
	                  
	                    break;
	                case 3:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executeBvns();
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
	
	public static void executeBvns() {
		BVNS v;
		try {
			 v = new BVNS(constructFIle(),k_neightbor);
			ArrayList<Integer> solution;
			solution = v.execute(constructFIle(), k_neightbor,k);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	// ------------------MULTI ARRANQUE---------------------------

	public static void multiMenu() throws IOException  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1.Cambiar el númerto de veces a jecutar el algoritmo sin mejoras de solución (" + maxIterNotImprved + " actualmente.)" );
            System.out.println("2. Ejecutar Multi Arranque con los anteriores parámetros.");
            System.out.println("3. Volver al menu principal.");
 
            try {
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
	                case 1:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el numero de veces a ejecutar el algoritmo sin mejoras.\n");
	            		maxIterNotImprved = sn.nextInt();
	                case 2:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executeMulti();
	                    System.out.println("Time:" + Timer.stop());
	                    break;
	                case 3:
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
	
	public static void executeMulti() throws IOException {
		solution = MultiBoot.execute(constructFIle(), maxIterNotImprved);
	}
	

	
	// ------------------TABOO---------------------------

	public static void tabooMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
            System.out.println("\n1.Cambiar el númerto de veces a jecutar el algoritmo  (" + k + " actualmente.)" );
            System.out.println("2. Cambiar el tamaño máximo del vencinadario (" + k_neightbor + " actualmente.)");
            System.out.println("3. Ejecutar TABOO con los anteriores parámetros.");
            System.out.println("4. Volver al menu principal.");
 
            try {
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
                	case 1:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el tamaño de la cola del algoritmo. actual(" + cola + ").");
	            		cola = sn.nextInt();
                    break;
                	case 2:
	            		sn = new Scanner(System.in);
	            		System.out.println("Escribe el timeout del algoritmo. actual(" + timeout + "). ");
	            		timeout = sn.nextInt();
                    break;
	                case 3:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executeTaboo();
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
	
	public static void executeTaboo() {
		try {
			Taboo g = new Taboo(constructFIle(),cola);
			solution = g.execute(timeout);
			System.out.println(g.function(solution) + " --- " + solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 // --------------------------DETERMINISTA---------------------------------

	public static void deterMenu()  {
		Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        while (!salir) {
        	
            System.out.println("\nSelecciona una opción.");
    
            System.out.println("\1. Ejecutar Algoritmo Determinista.");
            System.out.println("2. Volver al menu principal.");
 
            try {
                System.out.println("Escribe una opción.");
                opcion = sn.nextInt();
                switch (opcion) {
	                case 1:
	            		System.out.println("Ejecutando...\n");
	                	Timer.start();
	                	executeDeter();
	                    System.out.println("Time:" + Timer.stop());
	                    break;
	                case 2:
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
	
	public static void executeDeter() {
		try {
			Deterministic d = new Deterministic(constructFIle());
			ArrayList<Integer> solution;
			try {
				solution = d.execute();
				System.out.println(d.function(solution) + " --- " + solution);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
