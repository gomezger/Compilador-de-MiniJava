


public class ExceptionTipoExpresion extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionTipoExpresion(String tipoEsperado, String tipoRecibido, int fila, int columna){
		super("Se esperaba una expresión de tipo "+tipoEsperado+" y se recibió "+tipoRecibido+". ",fila,columna);
	}
	
	public ExceptionTipoExpresion(String mensaje){
		super(mensaje);
	}
}
