package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoArregloInt extends TipoArreglo {
		
	public TipoArregloInt() {
		super("ArregloInt","int");
	}

	public void imprimir(){
		System.out.print("TipoArregloInt");
	}

	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {
	}

	public boolean esCompatible(TipoBase tipo) {
		return tipo instanceof TipoArregloInt || tipo instanceof TipoNull;
	}

	@Override
	public TipoBase getTipoPrimitivo() {
		return new TipoInt();
	}
	
	
	

}
