package etapa4AST;

import java.util.LinkedList;

import etapa1.Principal;
import etapa1.Token;
import etapa3Entradas.EntradaParametro;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa4Exp.ExceptionUnidadNoExiste;

public class NArgsActuales {
	//atributos
	protected Token token;
	protected LinkedList<NExpresion> lista;
	
	
	//constrcutor
	public NArgsActuales(Token token, LinkedList<NExpresion> lista){
		this.token = token;
		this.lista = lista;
	}
	
	
	//getters
	public LinkedList<NExpresion> getLista(){
		return this.lista;
	}
	public Token getToken(){
		return this.token;
	}
	public void tabs(int n){
		for(int i = 0; i<n; i++)
			System.out.print("   ");
	}
	
	//chequear
	public void chequear(LinkedList<EntradaParametro> params) throws ExceptionSemantico{
		int i = 0;
		for(NExpresion exp: lista){
			
			TipoBase tipoArg = exp.chequear();
			TipoBase tipoParam = params.get(i).getTipo();
			
			//si no son compatibles
			if(tipoParam.esCompatible(tipoArg)){
				i++;
			
			//tienen distinto tipo
			}else{
				throw new ExceptionUnidadNoExiste(i,tipoParam,tipoArg,exp.getToken().getLinea(),exp.getToken().getColumna());
			}
			
		}
	}

	public void generarConSwap(){
		//parametros
		for(NExpresion exp: this.lista){
			exp.generar();
			Principal.gen.gen("SWAP", "");				
		}
	}
	
	public void generarSinSwap(){
		//parametros
		for(NExpresion exp: this.lista){
			exp.generar();			
		}
	}
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Args Actuales");
		for(NExpresion e: lista)
			e.imprimir(n+1);
	}
	
	
}
