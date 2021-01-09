

public class ExceptionSemanticoChequeo extends ExceptionSemantico {

	private static final long serialVersionUID = 1L;

	public ExceptionSemanticoChequeo(String mensaje, int columna, int fila){
		super("# Error Semántico II: "+mensaje+" Error en la posición "+fila+" de la linea "+columna+". ");
	}
	
	public ExceptionSemanticoChequeo(String mensaje){
		super("# Error Semántico II: "+mensaje);
	}
}
