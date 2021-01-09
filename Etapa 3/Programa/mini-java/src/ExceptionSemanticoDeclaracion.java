



public class ExceptionSemanticoDeclaracion extends ExceptionSemantico {

	private static final long serialVersionUID = 1L;

	public ExceptionSemanticoDeclaracion(String mensaje, int columna, int fila){
		super(""+mensaje+" Error en la posici�n "+fila+" de la linea "+columna+". ");
	}
	
	public ExceptionSemanticoDeclaracion(String mensaje){
		super(""+mensaje);
	}
}
