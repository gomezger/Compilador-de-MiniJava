


public class ExceptionCondicion extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionCondicion(int fila, int columna){
		super("La condición a evaluar debe ser una expresión booleana. ",fila,columna);
	}
	
	public ExceptionCondicion(String mensaje){
		super(mensaje);
	}
}
