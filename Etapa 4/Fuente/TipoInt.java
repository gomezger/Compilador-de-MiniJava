

import java.util.HashMap;

public class TipoInt extends TipoPrimitivo {
	public TipoInt() {
		super("int");
	}


	//metodos
	public void imprimir(){
		System.out.print("TipoInt");
	}

	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion {}

	public boolean esCompatible(TipoBase tipo) {
		return tipo.getNombre().equals(nombre);
	}


	public TipoArreglo tipoArreglo() {
		return new TipoArregloInt();
	}
	
	
	
}
