


public class ExceptionExpBinaria extends ExceptionSemanticoChequeo {

	private static final long serialVersionUID = 1L;

	public ExceptionExpBinaria(String operador, String op1, String op2, String esperado, int fila, int columna){
		super("Para el operador '"+operador+"' se esperaban dos operandos de tipo "+esperado+", y se recibio uno de tipo "+op1+" y otro "+op2+". ",fila,columna);
	}
	
	
	public ExceptionExpBinaria(String mensaje){
		super(mensaje);
	}
}
