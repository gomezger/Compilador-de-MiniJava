



public class ExceptionParametro extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionParametro(String nombreClase, int fila, int columna){
		super("Ya existe un parámetro con el nombre '"+nombreClase+"'. ",fila,columna);
	}
	
	public ExceptionParametro(String mensaje){
		super(mensaje);
	}
}
