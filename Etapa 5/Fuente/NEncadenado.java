

public abstract class NEncadenado {
	//atributos
	protected Token token;
	
	
	//constrcutor
	protected NEncadenado(Token token){
		this.token = token;
	}
	
	
	//getters
	public Token getToken(){
		return this.token;
	}

	public void tabs(int n){
		for(int i = 0; i<n; i++)
			System.out.print("   ");
	}
	

	//chequear
	public abstract TipoBase chequear(TipoBase tipoExp) throws ExceptionSemantico;
	
	//generar
	public abstract void generar();
	
	
	//imprimir
	public void imprimir(int n){}
	
}
