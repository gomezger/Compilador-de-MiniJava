package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoIdClase;
import etapa4Exp.ExceptionMetodoEstatico;
import etapa4Exp.ExceptionSemanticoChequeo;
import etapa4Exp.ExceptionThis;

public class NAccesoThis extends NAcceso {
	//atributos
	
	//constructor
	public NAccesoThis(Token token, NEncadenado enc, boolean li, boolean at){
		super(token,enc,li,at);
	}

	// es vacio y esta del lado izquierdo es error
	// le paso a enc el tipo de la clase actual
	
	//ver lo de static
	
	
	public TipoBase chequear() throws ExceptionSemantico{
		
		//verificar si se quiere usar un this en asignacion inline de atributo instancia
		if(!this.at){
		
			//es un encadenado vacio y esta del lado izquierdo
			if(enc instanceof NEncadenadoVacio && li){
				throw new ExceptionThis(token.getLinea(),token.getColumna());
			}else{
				
				//estoy en un metodo estatico
				if(!Principal.ts.getMetodoActual().esDinamico()){
					throw new ExceptionMetodoEstatico(Principal.ts.getMetodoActual().getNombre(),token.getLinea(),token.getColumna());
				
				}else{
				
					//creo el tipo id clase con el nomnbre de la clase actual y se lo doy a encadenado
					return enc.chequear(new TipoIdClase(Principal.ts.getClaseActual().getNombre()));
				}
			}
		
		//se quiere usar this en atributo
		}else{
			throw new ExceptionSemanticoChequeo("No se puede usar this en una asignación inline de un atributo de instancia. ",token.getLinea(),token.getColumna());
		}
	}
	

	public void generar() {
		//acceso a this
		Principal.gen.gen("LOAD 3", "Cargo el this");
		
		//genero el encadenado
		enc.generar();
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Acceso This");
		tabs(n+1); System.out.println("Nombre: "+token.getLexema()+"");
		tabs(n+1); System.out.println("Lado Izquierdo: "+li+"");
		enc.imprimir(n+1);
	}


}
