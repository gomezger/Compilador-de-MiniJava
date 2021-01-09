



public class StringAbierto extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public StringAbierto(int columna, int fila){
		super("Salto de linea inesperado o falta comilla de cierre en un String. Utilice "+(char)92+"n para hacer salto de linea. El String comienza en la posición "+columna+" de la linea "+fila+".");
	}
}
