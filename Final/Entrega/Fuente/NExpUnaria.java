

public class NExpUnaria extends NExpresion {
	//atributos
	protected NExpresion ld;
	
	
	//constrcutor
	public NExpUnaria(Token token, NExpresion ld){
		super(token);
		this.ld = ld;
	}


	
	
	//getters
	public NExpresion getLd() {
		return ld;
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Expresion Unaria");
		tabs(n+1); System.out.println("Token: "+token.getLexema()+"");
		ld.imprimir(n+1);
	}


	public TipoBase chequear() throws ExceptionSemantico {
			
		TipoBase tipo = ld.chequear();
		String op = token.getLexema(); 
		
		//ver que operador es
		if(op.equals("!")){
			
			if(tipo.esCompatible(new TipoBoolean()))
				return new TipoBoolean();
			else
				throw new ExceptionTipoExpresion("boolean",tipo.getNombre(),token.getLinea(),token.getColumna());
			
		//ver que operador es	
		}else if(op.equals("+") || op.equals("-")){
			
			if(tipo.esCompatible(new TipoInt())){
				return new TipoInt();
			}else
				throw new ExceptionTipoExpresion("int",tipo.getNombre(),token.getLinea(),token.getColumna());
			
		//error
		}else{
			System.out.println("falto un operando");
			System.exit(45);
			return null;
		}
	}




	public void generar() {
		//generar los dos lados
		this.ld.generar();
		
		//ver que hacer
		switch (this.token.getLexema()) {
	    	case "+":  //Principal.gen.gen("", "Operador unario: +");
	    	break;
	    	case "-":  Principal.gen.gen("NEG", "Operador unario: -");
	    	break;
	    	case "!":  Principal.gen.gen("NOT", "Operador unario: !");
	    	break;
        	default: 
            break;
		}
	}

	
}
