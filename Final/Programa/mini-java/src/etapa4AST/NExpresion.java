package etapa4AST;

import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;

public abstract class NExpresion {
	//atributos
	protected Token token;
	
	
	//constrcutor
	protected NExpresion(Token token){
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

	public abstract TipoBase chequear() throws ExceptionSemantico;
	
	public abstract void generar();
	
	public void imprimir(int n){}
	
}
