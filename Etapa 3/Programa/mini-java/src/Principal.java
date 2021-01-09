
import java.io.FileNotFoundException;
import java.io.IOException;

public class Principal {
	private static AnalizadorSintactico asin;
	private static AnalizadorLexico alex;
	public static TablaDeSimbolos ts;

	
	public static void main (String [] args) {
		//para testear varios caso de pruebas a la vez
		//Tester t = new Tester(13);
		//return;
		
    	//verificar si tengo los parametros necesarios
    	if(args.length>0){
    		//verificar si pidio ayuda
    		if(args[0].equals("-h")){
        		System.out.println("Estructura general: <PROGRAM_NAME> <IN_FILE> \n\n\t#<PROGRAM_NAME> es el nombre del ejecutable. \n\t#<IN_FILE> es el archivo a analiza. \n\t");
    		
        	//verificar si solo ingreso el archivo y mostramos resultado por pantalla
    		}else if(args[0]!=null && args.length<2){
    	
    	    	
    	        try {
    	        	//creamos el analizador lexico
    	        	alex = new AnalizadorLexico(new Archivo(args[0]));
    				try {
        	        	//creamos el analizador semantico
						asin = new AnalizadorSintactico(alex);
						
						//empezamos a analizar el sintactico
						asin.empezar();
												
						//chequeamos lo que le falto chequear miestras se hacia la TS
						ts.chequear();
						
						//consolidamos las clases
						ts.consolidar();						
						
						//muestro la tabla de simbolos	
						
						
						if(!ts.hayErrores()){
							//ts.imprimir();				
							System.out.println("No hubo errores léxicos, sintácticos y semánticos (declaración). ");
							return;
						}
    				
    				} catch (ExceptionLexico e) {
						System.out.println("Error léxico: "+ e.getMessage());
					} catch (ExceptionSintactico e) {					
						System.out.println("Error sintáctico: "+ e.getMessage());
					} catch (ExceptionSemanticoDeclaracion e) {					
						System.out.println("Error semántico: "+ e.getMessage());						
					}
    				    				
    					
    			} catch (FileNotFoundException e) {
    				System.out.println(e.getMessage());
    			} catch (IOException e) {
    				System.out.println(e.getMessage());
    			}
    	        
    	    //especificÃ³ que quiere que el resultado se guarde en un archivo 
    		}else{
    			//mas parametros de los deseados
    			System.out.println("Ingresó más de un argumento. Utilice -h para ayuda.");
    		}
    	}else{
    		//no paso parametros
    		System.out.println("Parámetros insuficientes. Ingres -h para ayuda.");
    	}
    	 
    }   
}

