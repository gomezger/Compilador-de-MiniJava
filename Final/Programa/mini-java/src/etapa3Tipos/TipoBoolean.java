package etapa3Tipos;

import java.util.HashMap;

import etapa3Entradas.EntradaClase;
import etapa3Exp.ExceptionSemanticoDeclaracion;

public class TipoBoolean extends TipoPrimitivo {
	//atributos
	public boolean esTrue;
	
	//constrcutores
	public TipoBoolean() {
		super("boolean");
	}
	public TipoBoolean(boolean esTrue) {
		super("boolean");
		this.esTrue = esTrue;
	}
	
	//metodos
	public boolean esCompatible(TipoBase tipo) {
		return tipo.getNombre().equals(this.nombre);
	}
	

	//imprimir
	public void imprimir(){
		System.out.print("TipoBoolean");
	}
	
	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {}
	
	
	public TipoArreglo tipoArreglo() {
		return new TipoArregloBoolean();
	}
}
