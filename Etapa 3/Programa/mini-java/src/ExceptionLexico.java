



public class ExceptionLexico extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionLexico(String mensaje, int fila, int columna, String solucion){
		super(mensaje+" Error en la posición "+fila+" de la linea "+columna+". "+solucion);
	}
	
	public ExceptionLexico(String mensaje){
		super(mensaje);
	}
}
