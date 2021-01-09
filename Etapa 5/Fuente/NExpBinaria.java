

public class NExpBinaria extends NExpresion {
	//atributos
	protected NExpresion li;
	protected NExpresion ld;
	
	
	//constrcutor
	public NExpBinaria(Token token, NExpresion li, NExpresion ld){
		super(token);
		this.li = li;
		this.ld = ld;
	}
	
	
	//getters
	public NExpresion getLi() {
		return li;
	}
	public NExpresion getLd() {
		return ld;
	}
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Expresion Binaria");
		tabs(n+1); System.out.println("Valor: "+token.getLexema()+"");
		li.imprimir(n+1);
		ld.imprimir(n+1);
	}

	//chequear
	public TipoBase chequear() throws ExceptionSemantico {
		
		TipoBase tipoLI = li.chequear();
		TipoBase tipoLD = ld.chequear();
		
		String op = token.getLexema();
		
		//son dos enteros y generan dos enteros
		if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")){
			if(esEntero(tipoLI,tipoLD))
				return new TipoInt();
			else
				throw new ExceptionExpBinaria(op,tipoLI.getNombre(), tipoLD.getNombre(), "int", token.getLinea(), token.getColumna());
		
		//son dos enteros y generan un booleano
		}else if(op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=")){
			if(esEntero(tipoLI,tipoLD))
				return new TipoBoolean();
			else
				throw new ExceptionExpBinaria(op,tipoLI.getNombre(), tipoLD.getNombre(), "int", token.getLinea(), token.getColumna());
		
			
		}else if(op.equals("&&") || op.equals("||")){
			if(esBoolean(tipoLI,tipoLD))
				return new TipoBoolean();
			else
				throw new ExceptionExpBinaria(op,tipoLI.getNombre(), tipoLD.getNombre(), "boolean", token.getLinea(), token.getColumna());
		
					
		}else if(op.equals("==") || op.equals("!=")){
			
			if(esIgual(tipoLI,tipoLD))
				return new TipoBoolean();
			else
				throw new ExceptionExpBinaria(op,tipoLI.getNombre(), tipoLD.getNombre(), tipoLI.getNombre()+" o "+tipoLD.getNombre(), token.getLinea(), token.getColumna());

			
		}else{
			System.out.println("Falta un operador en chequear de exp binaria");
			System.exit(5154);
			return null;
		}		
		
	}
	
	
	//buscar si dos tipos son enteros
	private boolean esEntero(TipoBase tipo1, TipoBase tipo2) throws ExceptionSemantico{
		return tipo1.esCompatible(new TipoInt()) && tipo2.esCompatible(new TipoInt());			
	}
	
	//buscar si dos tipos son enteros
	private boolean esBoolean(TipoBase tipo1, TipoBase tipo2) throws ExceptionSemantico{
		return tipo1.esCompatible(new TipoBoolean()) && tipo2.esCompatible(new TipoBoolean());
	}
	
	private boolean esIgual(TipoBase tipo1, TipoBase tipo2) throws ExceptionSemantico{
		return tipo1.esCompatible(tipo2) || tipo2.esCompatible(tipo1);
	}


	@Override
	public void generar() {
		//generar los dos lados
		this.li.generar();
		this.ld.generar();
		
		//ver que hacer
		switch (this.token.getLexema()) {
	    	case "+":  Principal.gen.gen("ADD", "Sumar");
	    	break;
	    	case "-":  Principal.gen.gen("SUB", "Restar");
	    	break;
	    	case "*":  Principal.gen.gen("MUL", "Multiplicar");
	    	break;
	    	case "/":  Principal.gen.gen("DIV", "Dividir");
	    	break;
	    	case "<":  Principal.gen.gen("LT", "Menor (<)");
	    	break;
	    	case ">":  Principal.gen.gen("GT", "Mayor (>)");
	    	break;
	    	case "<=":  Principal.gen.gen("LE", "Menor o igual (<=)");
	    	break;
	    	case ">=":  Principal.gen.gen("GE", "Mayor o igual (>=)");
	    	break;
	    	case "&&":  Principal.gen.gen("AND", "AND (&&)");
	    	break;
	    	case "||":  Principal.gen.gen("OR", "OR (||)");
	    	break;
	    	case "==":  Principal.gen.gen("EQ", "==");
	    	break;
	    	case "!=":  Principal.gen.gen("NE", "!=");
	    	break;
        	default: 
            break;
		}
	}
	
}
