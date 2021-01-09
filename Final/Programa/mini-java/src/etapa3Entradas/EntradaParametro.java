package etapa3Entradas;

import etapa1.Token;
import etapa3Tipos.Tipo;

public class EntradaParametro extends EntradaVarMetodo{
	protected int pos;
	
	//constrcutor
	public EntradaParametro(Tipo tipo, Token token){
		super(token, tipo);
		this.pos = 0;
	}
	
	
	//SETTERS
	public void setPos(int pos){
		this.pos = pos;
	}
	
	//getters
	public int getPos(){
		return this.pos;
	}
	


	
	public void imprimir(){
		System.out.print(this.getNombre()+" (Tipo: ");
		tipo.imprimir();
		System.out.print(")");	
	}

	
}
