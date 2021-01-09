package etapa3Exp;




public class ExceptionConstructor extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionConstructor(int cant_params, int fila, int columna){
		super("Ya existe un constructor con '"+cant_params+"' parametros en la clase. ",fila,columna);
	}
	
	public ExceptionConstructor(String mensaje){
		super(mensaje);
	}
}
