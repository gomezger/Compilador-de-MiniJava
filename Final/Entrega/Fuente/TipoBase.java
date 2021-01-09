


import java.util.HashMap;

public abstract class TipoBase {
	//atributos
	public String nombre;
	
	//constrcutor
	protected TipoBase(String nombre){
		this.nombre = nombre;
	}
	
	//getters
	public String getNombre(){
		return this.nombre;
	}
	
	public abstract  void verificarTipo(HashMap<String, EntradaClase> clases, int fila, int columna) throws ExceptionSemanticoDeclaracion;
	
	public abstract boolean esCompatible(TipoBase tipo) throws ExceptionSemantico;

	public abstract void imprimir();
	
}
