package etapa4Exp;

import etapa3Tipos.TipoBase;

public class ExceptionUnidadNoExiste extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionUnidadNoExiste(int cant, String clase, int fila, int columna){
		super("No existe un constructor/metodo con '"+cant+"' parámetros en la clase: "+clase+". ",fila,columna);
	}
	public ExceptionUnidadNoExiste(int pos, TipoBase tipoParam, TipoBase tipoArg, int fila, int columna){
		super("Se esperaba el tipo "+tipoParam.getNombre()+" en la posición "+(pos+1)+" y se recibió "+tipoArg.getNombre()+". ",fila,columna);
	}
	
	public ExceptionUnidadNoExiste(String mensaje){
		super(mensaje);
	}
}
