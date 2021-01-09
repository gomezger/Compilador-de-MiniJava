package etapa3Exp;




public class ExceptionAtributoRedefinido extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionAtributoRedefinido(String nombreClase, int fila, int columna){
		super("Ya existe un atributo con el nombre '"+nombreClase+"' en un clase ancestro. ",fila,columna);
	}
	
	public ExceptionAtributoRedefinido(String mensaje){
		super(mensaje);
	}
}
