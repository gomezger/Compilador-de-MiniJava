package etapa4Exp;


public class ExceptionMetodoEstatico extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionMetodoEstatico(String var, String metodo, int fila, int columna){
		super("El metodo "+metodo+" es estático y "+var+" es un atributo de instancia. ",fila,columna);
	}
	
	public ExceptionMetodoEstatico(String metodo, int fila, int columna){
		super("El metodo "+metodo+" es estático y no se puede usar 'this'. ",fila,columna);
	}
	
	
	public ExceptionMetodoEstatico(String metodo, int cantParams, int fila, int columna){
		super("El metodo "+metodo+" es dinamico y no se puede hacer una llamada estática. ",fila,columna);
	}
	
	public ExceptionMetodoEstatico(String mensaje){
		super(mensaje);
	}
}
