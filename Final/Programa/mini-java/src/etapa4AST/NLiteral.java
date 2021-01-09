package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.Tipo;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoBoolean;
import etapa3Tipos.TipoNull;
import etapa3Tipos.TipoString;
import etapa5.Generador;

public class NLiteral extends NOperando {
	protected Tipo tipo;
	
	
	//constrcutor
	public NLiteral(Token token, Tipo tipo){
		super(token);
		this.tipo = tipo;
	}

	//getters
	public Tipo getTipo() {
		return tipo;
	}
	
	

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Literal");
		tabs(n+1); System.out.println("   Tipo: "+tipo.nombre+"");
		tabs(n+1); System.out.println("   Valor: "+token.getLexema()+"");
	}

	
	public TipoBase chequear() throws ExceptionSemantico {
		return tipo;
	}

	//pusheo el valor de literal, por ejemplo 5
	public void generar() {
		//es booleando true
		if(tipo instanceof TipoBoolean && ((TipoBoolean) tipo).esTrue)
			Principal.gen.gen("PUSH 1", "Pongo un literal true");
		
		//es un booleano false
		else if(tipo instanceof TipoBoolean && !((TipoBoolean) tipo).esTrue)	
			Principal.gen.gen("PUSH 0", "Pongo un literal false");			
		
		//es un string
		else if(tipo instanceof TipoString){	
			Principal.gen.gen(".DATA", "Data para guardar string");	
			Principal.gen.gen("string_"+(Generador.cant_string)+": DW \""+token.getLexema()+"\",0", "Pongo un string en .data");	
			Principal.gen.gen(".CODE", "SAlgo de guardar string");		
			Principal.gen.gen("PUSH string_"+(Generador.cant_string++)+"", "Pusheo la direccion etiqueta del string");					

		//es un booleano false
		}else if(tipo instanceof TipoNull)	
			Principal.gen.gen("PUSH 0", "Pongo un literal nulo");		
			
		//es otra cosa
		else
			Principal.gen.gen("PUSH "+token.getLexema(), "Pongo el literal en la pila");
				
	}

	
}
