

import java.util.HashMap;

public class TipoBoolean extends TipoPrimitivo {
	//atributos
	protected boolean esTrue;
	
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
