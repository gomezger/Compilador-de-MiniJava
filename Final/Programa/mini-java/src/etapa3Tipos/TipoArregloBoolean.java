package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoArregloBoolean extends TipoArreglo {
	
	public TipoArregloBoolean() {
		super("ArregloBoolean","boolean");
	}

	
	//metodos	
	public void imprimir(){
		System.out.print("TipoArregloBoolean");
	}
	
	public boolean esCompatible(TipoBase tipo) {
		return tipo instanceof TipoArregloBoolean || tipo instanceof TipoNull;
	}
	


	@Override
	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {}


	@Override
	public TipoBase getTipoPrimitivo() {
		return new TipoBoolean();
	}

}
