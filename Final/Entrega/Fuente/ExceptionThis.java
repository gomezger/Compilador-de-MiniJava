


public class ExceptionThis extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionThis(int fila, int columna){
		super("'this' no puede estar solo del lado izquierdo, por ejemplo: this = 5. ",fila,columna);
	}
	
	public ExceptionThis(String mensaje){
		super(mensaje);
	}
}
