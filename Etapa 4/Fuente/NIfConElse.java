

public class NIfConElse extends NIf{
	//atributos
	protected NSentencia elsee;
	
	//constrcutor
	public NIfConElse(Token token, NExpresion cond, NSentencia then, NSentencia elsee){
		super(token,cond,then);
		this.elsee = elsee;
	}

	//getters
	public NSentencia getElse() {
		return elsee;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("If Con Else");
		cond.imprimir(n+1);
		then.imprimir(n+1);
		elsee.imprimir(n+1);
	}


	//chequear
	public void chequear() throws ExceptionSemantico{
		super.chequear();
		elsee.chequear();		
	}

	


}
