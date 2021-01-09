

public abstract class EntradaVariable extends Entrada {
	//atributos
	protected Tipo tipo;
	
	//constructor
	protected EntradaVariable(Token token, Tipo tipo){
		super(token);
		this.tipo = tipo;
	}
	
	//getters
	public Tipo getTipo(){
		return this.tipo;
	}

	public void imprimir() {		
	}
	

}
