

import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
	private int test;
	private int total_test;
	
	
	
	public Tester(int cant){
		this.total_test = cant;
		test = 1;
		
		this.testear("");	
		
	}
	
	
	public void testear(String bien){
		
		
		
		for(int i = test; i<=this.total_test; i++){
			System.out.println("Test n* "+i+":");
			try {
	        	//creamos el analizador lexico
				AnalizadorLexico alex = new AnalizadorLexico(new Archivo("test/SemanticoII/"+bien+i+".java"));
				try {
					AnalizadorSintactico asin = new AnalizadorSintactico(alex);
					asin.empezar();
					
					//creamos el analizar semantico
					TablaDeSimbolos ts = Principal.ts;
					
					//chequeamos lo que le falto chequear miestras se hacia la TS
					ts.chequearDeclaraciones();
					
					//consolidamos las clases
					ts.consolidar();						
					
					//muestro la tabla de simbolos
					//asin.ts.imprimir();	
					
					if(!ts.hayErrores()){
						
						//muestro la tabla de simbolos	
						//ts.imprimirAST();
						
						ts.chequearSentencias();
						if(!ts.hayErrores())
							System.out.println("No hubo errores léxicos, sintácticos y semánticos (declaración). ");
					}
					
					
				} catch (ExceptionLexico e) {
					System.out.println(e.getMessage());
				} catch (ExceptionSintactico | ExceptionSemantico e) {					
					
					System.out.println(e.getMessage());
					System.out.println("------------------------");
					
					this.test = i+1;
					testear(bien);
					return;
					
				}
				

				System.out.println("------------------------");
				
				
					
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.println("------------------------");
				System.out.println("");
				System.out.println("");
				
				if(!bien.equals("b")){
					this.test = 1;
					testear("b");
				}
				
				return;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return;
			}
		}
	}
	
	
	
}
