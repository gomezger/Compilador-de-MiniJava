

public abstract class Entrada {
	//atributos
	protected Token token;
	
	
	//constructor
	protected Entrada(Token token){
		this.token = token;
	}
	

	//getters
	public int getLinea(){
		return this.token.getLinea();
	}
	public int getColumna(){
		return this.token.getColumna();		
	}
	public String getNombre(){
		return this.token.getLexema();
	}
	public Token getToken(){
		return this.token;
	}

}
