

public class NIfSinElse extends NIf{
	//atributos
	
	//constrcutor
	public NIfSinElse(Token token, NExpresion cond, NSentencia then){
		super(token,cond,then);
	}


	public void imprimir(int n){
		tabs(n); 
		System.out.println("IF Sin Else");
		cond.imprimir(n+1);
		then.imprimir(n+1);
	}

}
