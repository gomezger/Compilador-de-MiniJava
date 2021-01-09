

public abstract class NPrimario extends NOperando {
	protected NEncadenado enc;
	
	
	//constrcutor
	protected NPrimario(Token token, NEncadenado enc){
		super(token);
		this.enc = enc;
	}


	//getters
	public NEncadenado getEnc() {
		return enc;
	}

	
}
