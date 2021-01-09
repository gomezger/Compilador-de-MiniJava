package etapa3Entradas;

import etapa1.Token;
import etapa3Tipos.Tipo;

public class EntradaVarLocal extends EntradaVarMetodo{
	
	//constrcutor
	public EntradaVarLocal(Tipo tipo, Token token){
		super(token, tipo);
	}
	


	
	public void imprimir(){
		System.out.print(this.getNombre()+" (Tipo: ");
		tipo.imprimir();
		System.out.print(")");	
	}

	
}
