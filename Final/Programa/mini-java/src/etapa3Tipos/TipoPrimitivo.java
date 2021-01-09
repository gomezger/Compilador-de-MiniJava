package etapa3Tipos;


public abstract class TipoPrimitivo extends Tipo {
	
	public TipoPrimitivo(String nombre){
		super(nombre);
	}
	
	
	public abstract TipoArreglo tipoArreglo();
	
}
