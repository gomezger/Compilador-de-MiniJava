

public class NSentenciaLlamada  extends NSentencia{
	//atributos
	protected NPrimario prim;
	
	//constrcutor
	public NSentenciaLlamada(Token token, NPrimario prim){
		super(token);
		this.prim = prim;
	}

	//GETTERS
	public NPrimario getPrim() {
		return prim;
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Sentencia Llamada");
		prim.imprimir(n+1);
	}

	
	public void chequear() throws ExceptionSemantico {
		prim.chequear();		
	}
	
	
}
