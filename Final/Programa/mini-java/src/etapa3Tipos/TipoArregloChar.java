package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoArregloChar extends TipoArreglo {
	
	public TipoArregloChar() {
		super("ArregloChar","char");
	}


	public void imprimir(){
		System.out.print("TipoArregloChar");
	}

	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {}

	public boolean esCompatible(TipoBase tipo) {
		return tipo instanceof TipoArregloChar || tipo instanceof TipoNull;
	}


	@Override
	public TipoBase getTipoPrimitivo() {
		return new TipoChar();
	}
}
