package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

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

