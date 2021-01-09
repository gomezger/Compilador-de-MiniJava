


public class ExceptionLlamadaMetodoIzq extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionLlamadaMetodoIzq(int fila, int columna){
		super("No puede se le puede asignar un valor a un metodo. ",fila,columna);
	}
	
	public ExceptionLlamadaMetodoIzq(String mensaje){
		super(mensaje);
	}
}
