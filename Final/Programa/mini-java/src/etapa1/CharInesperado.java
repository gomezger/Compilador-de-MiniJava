package etapa1;

import etapa3Exp.ExceptionLexico;

public class CharInesperado extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public CharInesperado(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"'.",columna,fila,"");
	}
}
