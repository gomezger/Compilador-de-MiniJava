package etapa3Exp;




public class ExceptionMetodoRedefinido extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionMetodoRedefinido(int fila, int columna){
		super("El tipo de retorno, el modo del m�todo o uno de los par�metros no coincide con el m�todo de la clase padre.",fila,columna);
	}
	
	public ExceptionMetodoRedefinido(String mensaje){
		super(mensaje);
	}
}
