

public class NExpresionParentizada extends NPrimario {
	//atributos
	protected NExpresion exp;
	
	//constructor
	public NExpresionParentizada(Token token, NExpresion exp, NEncadenado enc){
		super(token,enc);
		this.exp = exp;
	}

	//getters
	public NExpresion getExp() {
		return exp;
	}
	
	//chequear
	public TipoBase chequear() throws ExceptionSemantico{
		TipoBase tipoExp = exp.chequear();
		return enc.chequear(tipoExp);
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Expresion parentizada");
		exp.imprimir(n+1);
		enc.imprimir(n+1);
	}

	@Override
	public void generar() {
		exp.generar();
		enc.generar();
	}

	
}
