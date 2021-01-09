package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoInt;
import etapa3Tipos.TipoPrimitivo;
import etapa4Exp.ExceptionTipoExpresion;

public class NLlamadaCtorArreglo extends NLlamadaCtor {
	//atributos
	protected NExpresion exp;
	protected TipoPrimitivo tipo;
	
	//constrcutor
	public NLlamadaCtorArreglo(Token token, TipoPrimitivo tipo, NExpresion exp, NEncadenado enc){
		super(token,enc);
		this.exp = exp;
		this.enc = enc;
		this.tipo = tipo;
	}
	
	//getters
	public NExpresion getExp() {
		return exp;
	}

	public TipoPrimitivo getTipo() {
		return tipo;
	}

	//chequear
	/**
	 * me tengo que fijar que:
	 * 1. expresion sea de tipo int
	 * @return 
	 * @throws ExceptionSemantico 
	 */
	public TipoBase chequear() throws ExceptionSemantico{
		//pido el tipo de exp
		TipoBase tipoExp = exp.chequear();
				
		//si es tipo int, esta todo bien
		if(tipoExp.esCompatible(new TipoInt())){
			
			//si no hay encadenado retorno 
			if(enc instanceof NEncadenadoVacio)
				return tipo.tipoArreglo();
			else			
				return enc.chequear(tipo);
						
		//tira error porque esperaba un int	
		}else{
			throw new ExceptionTipoExpresion(new TipoInt().getNombre(),tipoExp.getNombre(),exp.getToken().getLinea(),exp.getToken().getColumna());
		}
		
	}

	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Ctor arreglo");
		tabs(n+1); System.out.println("   Tipo: "+tipo.nombre+"");
		exp.imprimir(n+1);
		enc.imprimir(n+1);
	}

	@Override
	public void generar() {
		
		Principal.gen.gen("RMEM 1", "");

		//genero el largo de array
		this.exp.generar();
		
		Principal.gen.gen("PUSH simple_malloc", "Reservo espacio para el objeto");
		Principal.gen.gen("CALL", "llamo a simple malloc");
		
		//genero codigo del encadenado
		enc.generar();
	}

	
}
