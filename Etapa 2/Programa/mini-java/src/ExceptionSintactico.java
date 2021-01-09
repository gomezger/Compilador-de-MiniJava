


public class ExceptionSintactico extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionSintactico(String mensaje, int fila, int columna, String solucion){
		super(mensaje+" Error en la posici�n "+fila+" de la linea "+columna+". "+solucion);
	}
	public ExceptionSintactico(String lexema, String mensaje, int fila, int columna){
		super("Se encontr� '"+lexema+"'. "+mensaje+" en la posici�n "+fila+" de la linea "+columna+".");
	}
	
	public ExceptionSintactico(String mensaje){
		super(mensaje);
	}
}
