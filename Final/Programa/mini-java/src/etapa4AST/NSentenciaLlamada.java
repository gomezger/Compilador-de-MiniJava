package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoVoid;

public class NSentenciaLlamada  extends NSentencia{
	//atributos
	protected NPrimario prim;
	protected TipoBase tipo;
	
	//constrcutor
	public NSentenciaLlamada(Token token, NPrimario prim){
		super(token);
		this.prim = prim;
	}

	//GETTERS
	public NPrimario getPrim() {
		return prim;
	}
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Sentencia Llamada");
		prim.imprimir(n+1);
	}

	
	public void chequear() throws ExceptionSemantico {
		tipo = prim.chequear();		
	}

	public void generar() {
		prim.generar();		
		
		//si no es tipo void, pierdo el valor de retorno y hay q borrarlo
		if(!(tipo instanceof TipoVoid))
			Principal.gen.gen("POP", "si no es void, hay q sacar el retorno");	
		
	}
	
	
}
