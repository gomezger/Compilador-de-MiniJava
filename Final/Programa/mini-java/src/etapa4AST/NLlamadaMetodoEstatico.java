package etapa4AST;

import java.util.LinkedList;

import etapa1.Principal;
import etapa1.Token;
import etapa3Entradas.EntradaClase;
import etapa3Entradas.EntradaConParams;
import etapa3Entradas.EntradaParametro;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoIdClase;
import etapa3Tipos.TipoVoid;
import etapa4Exp.ExceptionMetodoEstatico;
import etapa4Exp.ExceptionMetodoNoExiste;
import etapa4Exp.ExceptionNoEsIdClase;

public class NLlamadaMetodoEstatico extends NPrimario {
	//atributos
	protected TipoIdClase tipo;
	protected Token idMetVar;
	protected NArgsActuales args;
	protected NEncadenado enc;
	protected boolean li;
	protected EntradaConParams metodo;
	protected LinkedList<EntradaParametro> parametros;
	
	
	//constrcutor
	public NLlamadaMetodoEstatico(Token token, TipoIdClase tipo, NLlamadaMetodo llamadaMetodo, NEncadenado enc){
		super(token,enc);
		this.tipo = tipo;
		this.idMetVar = llamadaMetodo.getToken();
		this.args = llamadaMetodo.getArgs();
		this.enc = llamadaMetodo.getEnc();
		this.li = llamadaMetodo.li;
	}

	//getters
	public TipoIdClase getTipo() {
		return tipo;
	}	

	public NArgsActuales getArgs() {
		return args;
	}

	public NEncadenado getEnc() {
		return enc;
	}

	public boolean isLi() {
		return li;
	}

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Metodo etatico");
		System.out.println("   Tipo ("+tipo.getNombre()+")");
		System.out.println("   Lado Izq ("+li+")");
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}


	public TipoBase chequear() throws ExceptionSemantico {
		
		//me fijo si existe la clase
		if(Principal.ts.existeClase(tipo.getNombre())){
			
			EntradaClase clase = Principal.ts.getClase(tipo.getNombre());
			
			String nombreMetodo = idMetVar.getLexema()+"$"+args.getLista().size();
			
			//existe el metodo?
			if(clase.getMetodos().containsKey(nombreMetodo)){
				
				//tengo el metodo
				this.metodo = clase.getMetodos().get(nombreMetodo);
				
				parametros = this.metodo.getListaParametros();
				args.chequear(this.metodo.getListaParametros());
				
				
				//chequeo que sea estatico
				if(!this.metodo.esDinamico()){
					
					return enc.chequear(this.metodo.getTipo());
					
				}else{
					throw new ExceptionMetodoEstatico(this.metodo.getNombre(), this.metodo.getCantParams(), this.metodo.getToken().getLinea(), this.metodo.getToken().getColumna());
				}
				
				
			}else{
				throw new ExceptionMetodoNoExiste(idMetVar.getLexema(),args.getLista().size(),tipo.getNombre(),token.getLinea(),token.getColumna());				
			}			
			
		//no existe la clase	
		}else{
			throw new ExceptionNoEsIdClase(tipo,token.getLinea(),token.getColumna());
		}		
	}
	
	
	public void generar(){
		
		//me fijo si no es void
		if(!(this.metodo.getTipo() instanceof TipoVoid)){
			Principal.gen.gen("RMEM 1", "Si no es de tipo void, hay q sacar el ret");
		}
		
		//parametros
		for(NExpresion exp: args.getLista()){
			exp.generar();
		}
		
		
		//push etiqueta
		Principal.gen.gen("PUSH "+this.metodo.getEtiqueta(), "guardo el nombre del metodo a llamar");
		
		//call
		Principal.gen.gen("CALL", "voy al metodo estaticamente");
			
		//genero el encadenado
		enc.generar();
	}
	
	
}
