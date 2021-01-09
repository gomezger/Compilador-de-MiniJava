


public class ComentarioAbierto extends ExceptionLexico {
	private static final long serialVersionUID = 1L;

	public ComentarioAbierto(int columna, int fila, int f_emp){
		super("Fin de archivo inesperado. ",columna,fila,"Tiene un comentario multilinea sin cerrar que empieza en la linea "+f_emp+".");
	}
}
