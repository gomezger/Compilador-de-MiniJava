


public abstract class Tipo extends TipoBase{

	public Tipo(String nombre){
		super(nombre);
	}

	public boolean esCompatible(Tipo tipo) {
		return false;
	}
	
}
