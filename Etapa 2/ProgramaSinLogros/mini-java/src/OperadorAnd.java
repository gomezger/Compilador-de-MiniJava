


public class OperadorAnd extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public OperadorAnd(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"'.",columna,fila,"El operador AND es '&&'.");
	}
}
