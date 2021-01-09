package etapa4Exp;


public class ExceptionAtributoExiste extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionAtributoExiste(String var,  int fila, int columna){
		super("Ya existe el atributo '"+var+"' en el bloque, método o clase actual. ",fila,columna);
	}
	
	public ExceptionAtributoExiste(String mensaje){
		super(mensaje);
	}
}
