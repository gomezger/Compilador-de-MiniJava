

public abstract class NAcceso extends NPrimario {
	//atributos
	protected boolean li;
	public boolean at;
	
	//constructor
	protected NAcceso(Token token, NEncadenado enc, boolean li, boolean at){
		super(token,enc);
		this.li = li;
		this.at = at;
	}

	//getters
	public boolean isLi() {
		return li;
	}
	
	
	
	
}
