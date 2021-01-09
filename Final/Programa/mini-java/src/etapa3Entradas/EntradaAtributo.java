package etapa3Entradas;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.Tipo;
import etapa3Tipos.TipoBase;
import etapa4AST.NExpresion;
import etapa4AST.NExpresionVacia;
import etapa4Exp.ExceptionAsignacion;

public class EntradaAtributo extends EntradaVariable{
	protected boolean publico;
	protected NExpresion exp;
	
	//constrcutor
	public EntradaAtributo(boolean publico, Tipo tipo, Token token){
		super(token,tipo);
		this.publico = publico;
	}
	
	
	public void addExpresion( NExpresion exp){
		this.exp = exp;
	}
	
	public boolean esPublico(){
		return this.publico;
	}
	public void chequear() throws ExceptionSemantico{
		try{
			//si es una expresion chequeo
			if(!(exp instanceof NExpresionVacia)){ 
				TipoBase tipoExp = exp.chequear();
				
				//me fijo si es compatible
				if(tipo.esCompatible(tipoExp)){
					
				//error	
				}else{
					throw new ExceptionAsignacion(tipo.getNombre(),tipoExp.getNombre(),exp.getToken().getLinea(),exp.getToken().getColumna());
				}
			}
		}catch(ExceptionSemantico e){
			Principal.ts.setHayErrores();
			
			if(!Principal.ts.errores_detalles.contains(e.getMessage())){
				Principal.ts.errores_detalles.add(e.getMessage());
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void generar(){
		//si es una expresion chequeo
		if(!(exp instanceof NExpresionVacia)){ 
			
			exp.generar();
			Principal.gen.gen("LOAD 3", "Cargo el this");	
			Principal.gen.gen("SWAP", "Intercambio valores de la pila");
			Principal.gen.gen("STOREREF "+this.offset, "Guardo valor en la pila");
			
		}
	}
	
	
	//extras
	public void imprimir(){
		System.out.print(this.getNombre()+" (Tipo: ");
		tipo.imprimir();
		if(publico)
			System.out.print(". Publico)");
		else
			System.out.print(". Privado)");		
	}
	
	
	public void imprimirAST(){
		System.out.println(token.getLexema()+"");
		exp.imprimir(1);
	}
	
}
