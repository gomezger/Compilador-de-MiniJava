


public class ExceptionCondicion extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionCondicion(int fila, int columna){
		super("La condici�n a evaluar debe ser una expresi�n booleana. ",fila,columna);
	}
	
	public ExceptionCondicion(String mensaje){
		super(mensaje);
	}
}
