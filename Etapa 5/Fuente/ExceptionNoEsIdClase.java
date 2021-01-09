

public class ExceptionNoEsIdClase extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionNoEsIdClase(String var, int fila, int columna){
		super("El atributo '"+var+"' no es de tipo clase o la clase no existe . ",fila,columna);
	}
	
	public ExceptionNoEsIdClase(int fila, int columna){
		super("El atributo no es de tipo clase o la clase no existe. ",fila,columna);
	}
	
	public ExceptionNoEsIdClase(TipoBase tipo, int fila, int columna){
		super("No existe la clase "+tipo.getNombre()+". ",fila,columna);
	}
	
	public ExceptionNoEsIdClase(String mensaje){
		super(mensaje);
	}
}
