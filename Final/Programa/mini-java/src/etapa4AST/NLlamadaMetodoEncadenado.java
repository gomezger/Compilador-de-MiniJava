package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Entradas.EntradaClase;
import etapa3Entradas.EntradaConParams;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.Tipo;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoIdClase;
import etapa4Exp.ExceptionLlamadaMetodoIzq;
import etapa4Exp.ExceptionMetodoNoExiste;
import etapa4Exp.ExceptionNoEsIdClase;

public class NLlamadaMetodoEncadenado extends NEncadenado {
	//atributos
	protected NArgsActuales args;
	protected NEncadenado enc;
	protected boolean li;
	public EntradaConParams metLlamado;
	
	
	//constrcutor
	public NLlamadaMetodoEncadenado(Token token, NArgsActuales args, NEncadenado enc, boolean li){
		super(token);
		this.args = args;
		this.enc = enc;
		this.li = li;
	}
	

	//getters
	public NArgsActuales getArgsActuales() {
		return args;
	}
	public NEncadenado getEnc() {
		return enc;
	}
	
	//metodo sea un metodo de la clase actual
	// chequear los aprametros
	// si enc es vacio, tira erro si estoy en izq, sino le paso el tipo a enc.
	public TipoBase chequear(TipoBase tipoExp) throws ExceptionSemantico {
		
		//verifico que sea un tipo ID clase
		if(tipoExp instanceof TipoIdClase && Principal.ts.existeClase(tipoExp.nombre)){
		
			String nombreMetodo = token.getLexema()+"$"+args.getLista().size();
			
			EntradaClase clase = Principal.ts.getClase(tipoExp.nombre);
			
			//busco el metodo dentro de la clase
			if(clase.getMetodos().containsKey(nombreMetodo)){				
				
				//tengo el metodo
				metLlamado = clase.getMetodos().get(nombreMetodo);
				
				//chequeo que esten bien los argumentos
				args.chequear(metLlamado.getListaParametros());
				
				//me fijo si es un llamado y estoy del lado izquierdo
				if(enc instanceof NEncadenadoVacio && li){
					
					throw new ExceptionLlamadaMetodoIzq(token.getLinea(),token.getColumna());
					
				}else{
					
					//le paso a encadenado el tipo de metodo y encadenado me retorna un tipo
					return enc.chequear(metLlamado.getTipo());
				}
				
				
			}else{
				//no existe el metodo en la clase actual
				throw new ExceptionMetodoNoExiste(token.getLexema(),args.getLista().size(),Principal.ts.getClaseActual().getNombre(),token.getLinea(),token.getColumna());
			}	
		}else
			throw new ExceptionNoEsIdClase(token.getLinea(),token.getColumna());			

	}
	
		

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Metodo Encadenado");
		tabs(n+1); System.out.println("Lado Izq.: "+li); 
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}


	public void generar() {
		//estoy en un metodo dinamico? 
		if(metLlamado.esDinamico()){
		
			//me fijo si tiene retorno
			if(metLlamado.getTipo() instanceof Tipo){
				Principal.gen.gen("RMEM 1", "Reservo lugar para el retorno");
				Principal.gen.gen("SWAP", "");
			}
			
			//parametros
			for(NExpresion exp: args.getLista()){
				exp.generar();
				Principal.gen.gen("SWAP", "");				
			}
			
			Principal.gen.gen("DUP","Duplico this para no perderlo");
			Principal.gen.gen("LOADREF 0", "Cargo la VT de: ");
			Principal.gen.gen("LOADREF "+metLlamado.offset, "Cargo la direccion del metodo "+metLlamado.getNombre());
			Principal.gen.gen("CALL","Llamo al metodo");
		
		}
		
		//si estoy en un metodo estatico? 
		if(!metLlamado.esDinamico()){
			
			Principal.gen.gen("POP", "Es un llamada metodo encadenado estatico y no necesito this");
			
			//me fijo si no es void
			if(metLlamado.getTipo() instanceof Tipo){
				Principal.gen.gen("RMEM 1", "Si no es de tipo void, hay q sacar el ret");
			}
			
			//parametros
			for(NExpresion exp: args.getLista()){
				exp.generar();
			}
			
			//push etiqueta
			Principal.gen.gen("PUSH "+this.metLlamado.getEtiqueta(), "guardo el nombre del metodo a llamar");
			
			//call
				Principal.gen.gen("CALL", "voy al metodo estaticamente");
			}
			
			//generar de cadena
			enc.generar();
		
	}

	


}
