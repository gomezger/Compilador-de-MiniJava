package etapa3Tipos;


import java.util.HashMap;

import etapa1.Principal;
import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionClaseNoExiste;
import etapa3Exp.ExceptionSemantico;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoIdClase extends TipoReferencia {
	
	public TipoIdClase(String nombre){
		super(nombre);
	}
	
	
	//metodos
	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion {
		if(!clases.containsKey(nombre))
			throw new  ExceptionClaseNoExiste(nombre,fila,columna);		
	}
	
	//es compatible
	public boolean esCompatible(TipoBase tipo) throws ExceptionSemantico{
		return (tipo instanceof TipoIdClase && esSubTipo(this.getNombre(),tipo.getNombre())) || tipo instanceof TipoNull;
	}
	
	public boolean esSubTipo(String t1, String t2) throws ExceptionSemantico{

		boolean esta = false;
		
		
		//si no es compatible, me fijo si lo dos son Object
		if(!esta){
			
			esta = t1.equals("Object") && t2.equals("Object");
			
			//si n es compatible, me fijo si el lado izquierdo es Object
			if(!esta)
				esta = t1.equals("Object");

		}
		
		
		//defino las clases de t2 para despues bsucar padres
		EntradaClase c2 = Principal.ts.getClase(t2);

		//mientras la clase sea distinta de object y no se haya encontrado
		while(!t2.equals("Object") && !esta){
			
			//me fijo si t2 es igual a t1
			esta = t1.equals(t2);
			
			//si no son iguales
			if(!esta){
				//pido el padre de c2
				c2 = Principal.ts.getClase(c2.getHerencia());
				//creo el nuevo tipo de t2
				t2 = c2.getNombre();
			}
		}
		
		
		
		return esta;
		
	}
	
	public void imprimir(){
		System.out.print(nombre);
	}
}
