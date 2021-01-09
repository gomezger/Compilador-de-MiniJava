package etapa4AST;

import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoBoolean;
import etapa4Exp.ExceptionCondicion;

public abstract class NIf extends NSentencia{
	//atributos
	protected NExpresion cond;
	protected NSentencia then;
	
	//constrcutor
	public NIf(Token token, NExpresion cond, NSentencia then){
		super(token);
		this.then = then;
		this.cond = cond;
	}

	//getters	
	public NExpresion getCond() {
		return cond;
	}

	public NSentencia getThen() {
		return then;
	}
	
	
	//chequear
	public void chequear() throws ExceptionSemantico{
		
		TipoBase tipoCond = cond.chequear();
		
		//la condicion es booleana
		if(tipoCond.esCompatible(new TipoBoolean())){
			
			then.chequear();
			
		}else{
			throw new ExceptionCondicion(token.getLinea(),token.getColumna());
		}
		
	}
	

	
	

}
