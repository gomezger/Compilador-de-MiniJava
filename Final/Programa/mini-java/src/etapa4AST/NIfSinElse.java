package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa5.Generador;

public class NIfSinElse extends NIf{
	//atributos
	
	//constrcutor
	public NIfSinElse(Token token, NExpresion cond, NSentencia then){
		super(token,cond,then);
	}


	public void imprimir(int n){
		tabs(n); 
		System.out.println("IF Sin Else");
		cond.imprimir(n+1);
		then.imprimir(n+1);
	}

	public void generar() {
		
		int c_if = Generador.cant_if++;
	
		//genera condicion
		cond.generar();
		
		//generar codigo apra saltar a etiqueta else
		Principal.gen.gen("BF else_"+ c_if, "Si es false, salto a la etiqueta else");
		
		//genero codigo del then
		then.generar();	
		
		//empieza el else
		Principal.gen.gen("else_"+c_if+": NOP", "Empieza el codigo del else_"+c_if);
		
		//fin de if
		Principal.gen.gen("finIf_"+c_if+": NOP", "Termina el if_"+c_if);
		
	}
}
