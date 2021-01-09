

public class CharAscii extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public CharAscii(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"' ("+(int)caracter+").",columna,fila,"Solo se permiten caracteres pertenecientes al código ASCII.");
	}
}
