package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoVoid extends TipoBase {
	
	public TipoVoid() {
		super("void");
	}
	
	
	//comparar tipos
	public boolean esCompatible(TipoBase tipo){
		return tipo.getNombre().equals("void");
	}


	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion{}

	public void imprimir(){}
	
	
}
