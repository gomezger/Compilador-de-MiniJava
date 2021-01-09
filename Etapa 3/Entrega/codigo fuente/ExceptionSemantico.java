



public class ExceptionSemantico extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionSemantico(String mensaje, int columna, int fila){
		super("# Error Semántico: "+mensaje+" Error en la posición "+fila+" de la linea "+columna+". ");
	}
	
	public ExceptionSemantico(String mensaje){
		super("# Error Semántico: "+mensaje);
	}
}
