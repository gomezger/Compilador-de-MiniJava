package etapa3Entradas;

import etapa1.Token;
import etapa3Tipos.Tipo;

public abstract class EntradaVariable extends Entrada {
	//atributos
	protected Tipo tipo;
	public int offset = -1;
	
	//constructor
	protected EntradaVariable(Token token, Tipo tipo){
		super(token);
		this.tipo = tipo;
	}
	
	//getters
	public Tipo getTipo(){
		return this.tipo;
	}

	public void imprimir() {		
	}
	

}
