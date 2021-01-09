

import java.util.HashMap;

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
