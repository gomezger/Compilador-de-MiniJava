



public class ExceptionMainEstatico extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionMainEstatico(String nombreClase, int fila, int columna){
		super("Se defini� un m�todo estatico main en '"+nombreClase+"' y ya existia uno. ",fila,columna);
	}
	
	public ExceptionMainEstatico(String mensaje){
		super(mensaje);
	}
}
