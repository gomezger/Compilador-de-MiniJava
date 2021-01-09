



public class ExceptionSemantico extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionSemantico(String mensaje, int columna, int fila){
		super("# Error Sem�ntico: "+mensaje+" Error en la posici�n "+fila+" de la linea "+columna+". ");
	}
	
	public ExceptionSemantico(String mensaje){
		super("# Error Sem�ntico: "+mensaje);
	}
}
