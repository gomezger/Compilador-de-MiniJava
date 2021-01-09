

import java.util.HashMap;

public class TipoChar extends TipoPrimitivo {
	
	public TipoChar() {
		super("char");
	}

	//metodos	
	public void imprimir(){
		System.out.print("TipoChar");
	}

	public void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna)
			throws ExceptionSemanticoDeclaracion {}

	public boolean esCompatible(TipoBase tipo) {
		return tipo.getNombre().equals(nombre);
	}
	
	public TipoArreglo tipoArreglo() {
		return new TipoArregloChar();
	}
}
