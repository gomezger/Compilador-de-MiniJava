



public class ExceptionAtributo extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionAtributo(String nombreClase, int fila, int columna){
		super("Ya existe un atributo con el nombre '"+nombreClase+"'. ",fila,columna);
	}
	
	public ExceptionAtributo(String mensaje){
		super(mensaje);
	}
}
