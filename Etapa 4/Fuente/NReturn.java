

public class NReturn  extends NSentencia{
	//atributos
	protected NExpresion exp;
	
	//constrcutor
	public NReturn(Token token, NExpresion exp){
		super(token);
		this.exp = exp;
	}

	//getters
	public NExpresion getExp() {
		return exp;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Return");
		exp.imprimir(n+1);
	}

	
	/**
	 * Primero pido el tipo de expresion
	 * @throws ExceptionSemantico 
	 */
	public void chequear() throws ExceptionSemantico {
		
		//si no es constrcutor puede haber return
		if(!Principal.ts.getMetodoActual().esConstructor()){
		
			TipoBase tipoExp = exp.chequear();
			TipoBase tipoMetodo = Principal.ts.getMetodoActual().getTipo();
			
			//si es compatible esta todo bien
			if(tipoMetodo.esCompatible(tipoExp)){
							
			//error porque se esperaba otro tipo de retorno		
			}else{
				throw new ExceptionTipoExpresion(tipoMetodo.getNombre(),tipoExp.getNombre(), token.getLinea(), token.getColumna());
			}
		
		}else{
			throw new ExceptionSemanticoChequeo("No puede haber un return en un constructor.",token.getLinea(),token.getColumna());
		}
	}
}
