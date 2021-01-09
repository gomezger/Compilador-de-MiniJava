


public class OperadorOR extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public OperadorOR(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"'.",columna,fila,"El operador OR es '||'.");
	}
}
