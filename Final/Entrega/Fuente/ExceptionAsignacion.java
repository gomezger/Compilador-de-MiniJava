


public class ExceptionAsignacion extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionAsignacion(String tipoI, String tipoD, int fila, int columna){
		super("Se esperaba una expresion de tipo '"+tipoI+"' y se recibio una de tipo '"+tipoD+"'. ",fila,columna);
	}
	
	public ExceptionAsignacion(String mensaje){
		super(mensaje);
	}
}
