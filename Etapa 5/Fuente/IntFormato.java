

public class IntFormato extends ExceptionLexico {

	private static final long serialVersionUID = 1L;

	public IntFormato(char caracter, int columna, int fila){
		super("Caracter inesperado: '"+caracter+"'.",columna,fila,"No puede haber caracteres alfab�ticos despues de un n�mero.");
	}
}
