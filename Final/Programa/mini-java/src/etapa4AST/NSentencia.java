package etapa4AST;

import etapa1.Token;
import etapa3Exp.ExceptionSemantico;

public abstract class NSentencia {
	//atributos
	protected Token token;
	
	
	//constrcutor
	protected NSentencia(Token token){
		this.token = token;
	}
	
	
	//getters
	public Token getToken(){
		return this.token;
	}
	
	public void tabs(int n){
		for(int i = 0; i<n; i++)
			System.out.print("   ");
	}

	//chequear
	public abstract void chequear() throws ExceptionSemantico;

	//imprimir
	public abstract void imprimir(int n);
	
	public abstract void generar();
	
}
