package etapa3Exp;




public class ExceptionClaseNoExiste extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionClaseNoExiste(String nombreClase, int fila, int columna){
		super("No existe la clase '"+nombreClase+"'. ",fila,columna);
	}
	
	public ExceptionClaseNoExiste(String mensaje){
		super(mensaje);
	}
}
