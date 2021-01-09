package etapa4Exp;


public class ExceptionTipoVar extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionTipoVar(String varMet, int fila, int columna){
		super(" El tipo del atributo o método '"+varMet+"' no es una arreglo. ",fila,columna);
	}
	
	public ExceptionTipoVar(String mensaje){
		super(mensaje);
	}
}
