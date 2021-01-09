package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa5.Generador;

public class NIfConElse extends NIf{
	//atributos
	protected NSentencia elsee;
	
	//constrcutor
	public NIfConElse(Token token, NExpresion cond, NSentencia then, NSentencia elsee){
		super(token,cond,then);
		this.elsee = elsee;
	}

	//getters
	public NSentencia getElse() {
		return elsee;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("If Con Else");
		cond.imprimir(n+1);
		then.imprimir(n+1);
		elsee.imprimir(n+1);
	}


	//chequear
	public void chequear() throws ExceptionSemantico{
		super.chequear();
		elsee.chequear();		
	}


	public void generar() {
		//agrego uno a la cantidad de if para generar etiqueta con su numero indicado 
		int c_if = Generador.cant_if++;
		
		//genera condicion
		cond.generar();
		
		//generar codigo apra saltar a etiqueta else
		Principal.gen.gen("BF else_"+ c_if, "Si es false, salto a la etiqueta else");
		
		//genero codigo del then
		then.generar();	
		
		//termino el then y salto al fin del if para no hacer el else
		Principal.gen.gen("JUMP finIf_"+c_if, "");		
		
		//empieza el else
		Principal.gen.gen("else_"+c_if+": NOP", "Empieza el codigo del else_"+c_if);
		elsee.generar();
		
		//fin de if
		Principal.gen.gen("finIf_"+c_if+": NOP", "Termina el if_"+c_if);
		
	}
	


}
