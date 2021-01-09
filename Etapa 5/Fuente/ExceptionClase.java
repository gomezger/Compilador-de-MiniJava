




public class ExceptionClase extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionClase(String nombreClase, int fila, int columna){
		super("Ya existe una clase con el nombre '"+nombreClase+"'. ",fila,columna);
	}
	
	public ExceptionClase(String mensaje){
		super(mensaje);
	}
}
