package etapa4Exp;


public class ExceptionAtributoPrivado extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionAtributoPrivado(String var, String clase, int fila, int columna){
		super("No existe el atributo público '"+var+"' en la clase "+clase+". ",fila,columna);
	}
	
	public ExceptionAtributoPrivado(String mensaje){
		super(mensaje);
	}
}
