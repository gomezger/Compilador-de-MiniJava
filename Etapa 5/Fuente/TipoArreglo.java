


public abstract class TipoArreglo extends TipoReferencia {
	protected String tipoPrimitivo;
	
	
	public TipoArreglo(String nombre, String tipoPrimitivo) {
		super(nombre);
		this.tipoPrimitivo = tipoPrimitivo;
	}

	
	public abstract TipoBase getTipoPrimitivo();
	
	
	
}
