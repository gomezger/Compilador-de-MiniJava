package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Entradas.EntradaConParams;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa4Exp.ExceptionSemanticoChequeo;
import etapa4Exp.ExceptionTipoExpresion;

public class NReturn  extends NSentencia{
	//atributos
	protected NExpresion exp;
	public int offset = 0;
	public boolean dinamico;	
	public EntradaConParams miMetodo;
	
	//constrcutor
	public NReturn(Token token, NExpresion exp){
		super(token);
		this.exp = exp;
	}

	//getters
	public NExpresion getExp() {
		return exp;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Return");
		exp.imprimir(n+1);
	}

	
	/**
	 * Primero pido el tipo de expresion
	 * @throws ExceptionSemantico 
	 */
	public void chequear() throws ExceptionSemantico {
		
		//si no es constrcutor puede haber return
		if(!Principal.ts.getMetodoActual().esConstructor()){
		
			TipoBase tipoExp = exp.chequear();
			TipoBase tipoMetodo = Principal.ts.getMetodoActual().getTipo();
			
			//si es compatible esta todo bien
			if(tipoMetodo.esCompatible(tipoExp)){
						
				//guardo el modificador del metodo
				this.dinamico = Principal.ts.getMetodoActual().esDinamico();
				this.miMetodo = Principal.ts.getMetodoActual();
				
				
			//error porque se esperaba otro tipo de retorno		
			}else{
				throw new ExceptionTipoExpresion(tipoMetodo.getNombre(),tipoExp.getNombre(), token.getLinea(), token.getColumna());
			}
		
		}else{
			throw new ExceptionSemanticoChequeo("No puede haber un return en un constructor.",token.getLinea(),token.getColumna());
		}
	}

	public void generar() {
		//genero la expresion
		exp.generar();
		
		//la guardo en memoria 
		if(this.dinamico)
			Principal.gen.gen("STORE "+(4+this.miMetodo.getCantParams()), "Guardo la expresion de retorno (metodo dinamico)");
		else
			Principal.gen.gen("STORE "+(3+this.miMetodo.getCantParams()), "Guardo la expresion de retorno (metodo dinamico)");	
		
		//FMEN 
		Principal.gen.gen("FMEM "+miMetodo.varsLocales, "Elimino las variables usadas hasta el momento");
		
		//storefp
		Principal.gen.gen("STOREFP", "");
		
		//ret
		if(miMetodo.esDinamico())
			Principal.gen.gen("RET "+(miMetodo.getCantParams()+1)," Libero espacio de var params");
		else
			Principal.gen.gen("RET "+(miMetodo.getCantParams())," Libero espacio de var params");
		
	}
}
