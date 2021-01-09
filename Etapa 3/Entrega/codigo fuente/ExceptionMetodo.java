



public class ExceptionMetodo extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionMetodo(String nombreClase, int cant_params, int fila, int columna){
		super("Ya existe un metodo con el nombre '"+nombreClase+"' con "+cant_params+" par�metros. ",fila,columna);
	}
	
	public ExceptionMetodo(String mensaje){
		super(mensaje);
	}
}
