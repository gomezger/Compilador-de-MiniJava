

public class NExpresionVacia extends NExpresion {	
	
	//constrcutor
	public NExpresionVacia(Token token){
		super(token);
	}
	
	
	public TipoBase chequear(){
		return new TipoVoid();
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Expresion Vacia");
	}


	public void generar() {		
	}

	
}
