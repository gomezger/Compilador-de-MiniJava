

import java.util.LinkedList;

public class NBloqueSystem  extends NBloque{
	
	public NBloqueSystem(Token token, LinkedList<NSentencia> lista, NBloque padre, EntradaConParams unidad) {
		super(token, lista, padre, unidad);
	}
	

	public void chequear(){
		
	}

	//chequear
	public void generar() {
		//busco el nombre del metodo actual
		String nombre = Principal.ts.getMetodoActual().getNombre();  
		
        switch (nombre) {

	        //PRINT I
	        case "printI": 
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("IPRINT", "Imprimo un entero");                
	        		        	
	        break;
	        
	        //READ
	        case "read":          
	        	Principal.gen.gen("READ", "Pido un entero por pantalla"); 	        	
	        	Principal.gen.gen("STORE 3", "Guardo el entero");
	        
	        break;
	                 
	        //printB
	        case "printB":  
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("BPRINT", "Imprimo un boolean");                    
	        
	        
	        break;
	        
	        //PRINT C
	        case "printC":   
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("CPRINT", "Imprimo un char");            
	        
	        break;
	        
	
	        //PRINT S
	        case "printS":  
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("SPRINT", "Imprimo un string");           
	        
	        break;
	        
	
	        //PRINTLN
	        case "println": 
	        	Principal.gen.gen("PRNLN", "Imprimo un salto de linea");                     
	        
	        break;
	        
	
	        //PRINT Bln
	        case "printBln":
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("BPRINT", "Imprimo un boolean");
	        	Principal.gen.gen("PRNLN", "Imprimo un salto de linea");                     
	        
	        break;
	        
	
	        //PRINT C ln
	        case "printCln": 
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("CPRINT", "Imprimo un char");   
	        	Principal.gen.gen("PRNLN", "Imprimo un salto de linea");  
	        
	        break;
	
	
	        //PRINT I ln
	        case "printIln":     
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("IPRINT", "Imprimo un entero"); 
	        	Principal.gen.gen("PRNLN", "Imprimo un salto de linea");   
	        
	        break;
	
	        //PRINT S ln
	        case "printSln":  
	        	Principal.gen.gen("LOAD 3", "Cargo el valor para imprimir");  
	        	Principal.gen.gen("SPRINT", "Imprimo un string"); 
	        	Principal.gen.gen("PRNLN", "Imprimo un salto de linea");                   
	        
	        
	        break;
	                 
	        default:
	        	
	        break;
		
		}
	}
}
