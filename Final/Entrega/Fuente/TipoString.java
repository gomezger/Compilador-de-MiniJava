

import java.util.HashMap;

public class TipoString extends TipoReferencia {

	
	public TipoString() {
		super("String");
	}


	//metodos
	public void imprimir(){
		System.out.print("TipoString");
	}

	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion {}

	public boolean esCompatible(TipoBase tipo) {
		return tipo.getNombre().equals(nombre) || tipo.getNombre().equals("null");
	}




}

