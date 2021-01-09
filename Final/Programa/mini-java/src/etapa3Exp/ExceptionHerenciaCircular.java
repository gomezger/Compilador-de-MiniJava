package etapa3Exp;




public class ExceptionHerenciaCircular extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionHerenciaCircular(String nombreClase, String herencia, int fila, int columna){
			super("Existe herencia circular con la clase "+herencia+" en los ancestros de '"+nombreClase+"'. ",fila,columna);
	}
	
	public ExceptionHerenciaCircular(String mensaje){
		super(mensaje);
	}
}
