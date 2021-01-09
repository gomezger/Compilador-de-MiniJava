


public class ExceptionMetodoNoExiste extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionMetodoNoExiste(String metodo, int cantParams, String clase, int fila, int columna){
		super("No existe el metodo '"+metodo+"' con "+cantParams+" argumentos en la clase: "+clase+" o ancestros. ",fila,columna);
	}
	
	public ExceptionMetodoNoExiste(String mensaje){
		super(mensaje);
	}
}
