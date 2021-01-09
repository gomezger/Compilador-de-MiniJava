




public class ExceptionSemanticoDeclaracion extends ExceptionSemantico {

	private static final long serialVersionUID = 1L;

	public ExceptionSemanticoDeclaracion(String mensaje, int columna, int fila){
		super("# Error Semántico I: "+mensaje+" Error en la posición "+fila+" de la linea "+columna+". ");
	}
	
	public ExceptionSemanticoDeclaracion(String mensaje){
		super("# Error Semántico I: "+mensaje);
	}
}
