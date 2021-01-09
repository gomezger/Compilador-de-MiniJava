package etapa3Exp;




public class ExceptionTipoMainEstatico extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionTipoMainEstatico(String nombreClase, int fila, int columna){
		super("El método static main de '"+nombreClase+"' debe ser static, void y no tener parámetros. ",fila,columna);
	}
	
	public ExceptionTipoMainEstatico(String mensaje){
		super(mensaje);
	}
}
