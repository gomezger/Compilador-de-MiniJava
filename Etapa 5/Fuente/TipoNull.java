

import java.util.HashMap;

public class TipoNull extends TipoReferencia {

	
	public TipoNull() {
		super("null");
	}


	//metodos
	public void imprimir(){
		System.out.print("TipoNull");
	}

	
	//Null es compatible con String, IdClase, ArregloChar, ArregloInt, arregloBoolean
	public boolean esCompatible(TipoBase tipo){
		return 
				tipo.getNombre().equals("tipoString") || 
				tipo instanceof TipoIdClase || 
				tipo instanceof TipoArregloChar ||
				tipo instanceof TipoArregloInt ||
				tipo instanceof TipoArregloBoolean ||
				tipo instanceof TipoNull;
	}

	
	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {}



}

