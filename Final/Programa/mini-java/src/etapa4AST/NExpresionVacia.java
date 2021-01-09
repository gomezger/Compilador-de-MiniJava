package etapa4AST;

import etapa1.Token;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoVoid;

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
