

public class NLiteral extends NOperando {
	protected Tipo tipo;
	
	
	//constrcutor
	public NLiteral(Token token, Tipo tipo){
		super(token);
		this.tipo = tipo;
	}

	//getters
	public Tipo getTipo() {
		return tipo;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Literal");
		tabs(n+1); System.out.println("   Tipo: "+tipo.nombre+"");
		tabs(n+1); System.out.println("   Valor: "+token.getLexema()+"");
	}


	public TipoBase chequear() throws ExceptionSemantico {
		return tipo;
	}

	
}
