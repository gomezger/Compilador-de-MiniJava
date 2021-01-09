


public class ExceptionAtributoNoExiste extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionAtributoNoExiste(String var, String clase, int fila, int columna){
		super("No existe el atributo '"+var+"' en el método y/o clase actual ("+clase+"). ",fila,columna);
	}
	
	public ExceptionAtributoNoExiste(String mensaje){
		super(mensaje);
	}
}
