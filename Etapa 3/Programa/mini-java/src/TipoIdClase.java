

import java.util.HashMap;

public class TipoIdClase extends TipoReferencia {
	
	public TipoIdClase(String nombre){
		super(nombre);
	}
	
	
	//metodos
	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion {
		if(!clases.containsKey(nombre))
			throw new  ExceptionClaseNoExiste(nombre,fila,columna);		
	}
	public void imprimir(){
		System.out.print(nombre);
	}
}
