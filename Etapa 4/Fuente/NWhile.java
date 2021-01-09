

public class NWhile  extends NSentencia{
	//atributos
	protected NExpresion cond;
	protected NSentencia cuerpo;
	
	//constrcutor
	public NWhile(Token token, NExpresion cond, NSentencia cuerpo){
		super(token);
		this.cond = cond;
		this.cuerpo = cuerpo;
	}

	//getters
	public NExpresion getCond() {
		return cond;
	}
	public NSentencia getCuerpo() {
		return cuerpo;
	}
	
	
	//chequear
	public void chequear() throws ExceptionSemantico{
		TipoBase tc = cond.chequear();
		
		//verifico que la condicion sea boolean
		if(tc.esCompatible(new TipoBoolean())){
			
			//chequeo el cuerpo
			cuerpo.chequear();
			
		}else{
			throw new ExceptionCondicion(cond.getToken().getLinea(),cond.getToken().getColumna());
		}
	}
	
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("While");
		cond.imprimir(n+1);
		cuerpo.imprimir(n+1);
	}
	
}
