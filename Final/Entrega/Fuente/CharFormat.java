

public class CharFormat extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public CharFormat(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"'.",columna,fila,"Un char contiene un caracter, salto de linea (\\n) o un tab (\\t).");
	}
}
