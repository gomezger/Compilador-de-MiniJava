package etapa3Exp;




public class ExceptionConstructorMalo extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionConstructorMalo(String constructor, String clase, int fila, int columna){
		super("El nombre del constructor de '"+clase+"' tiene que llamarse '"+clase+"' y no '"+constructor+"'. ",fila,columna);
	}
	
	public ExceptionConstructorMalo(String mensaje){
		super(mensaje);
	}
}
