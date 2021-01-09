

public class NLlamadaMetodo extends NPrimario {
	//atributos
	protected NArgsActuales args;
	protected boolean li;
	public boolean at; // para ver si se usa en un atributo
	public EntradaConParams metActual;
	public EntradaConParams metLlamado;
	
	//constructor
	public NLlamadaMetodo(Token token, NArgsActuales args, NEncadenado enc, boolean li, boolean at){
		super(token,enc);
		this.args = args;
		this.enc = enc;
		this.at = at;
	}

	//getters
	public NArgsActuales getArgs() {
		return args;
	}
	
	//chequear nombre metodo y cant de args, y
	//chequear los tipos de parametro, tipo de retorno de metodo se lo paso a enc y retorno el resultado
	//ver lo de static
	public TipoBase chequear() throws ExceptionSemantico {
		
		String nombreMetodo = token.getLexema()+"$"+args.getLista().size();
		metLlamado = Principal.ts.getClaseActual().getMetodos().get(nombreMetodo);
		
		//verificar si se quiere usar un this en asignacion inline de atributo instancia
		if(!this.at || (this.at && metLlamado.esDinamico())){	
			
			//busco el metodo dentro de la clase
			if(Principal.ts.getClaseActual().getMetodos().containsKey(nombreMetodo)){			
				
				//tengo el metodo
				metActual = Principal.ts.getMetodoActual();
				
				//si estoy en un metodo statico y llamo a un metodo dinamico hay error
				if(!metActual.esDinamico() && metLlamado.esDinamico()){
					
					throw new ExceptionSemanticoChequeo("No se puede acceder a un método dinámico desde un método estático",token.getLinea(),token.getColumna());
				
				//estoy en un metodo esatico	
				}else{
				
					//chequeo que esten bien los argumentos
					args.chequear(metLlamado.getListaParametros());		
					
					//me fijo si es un llamado y estoy del lado izquierdo
					if(enc instanceof NEncadenadoVacio && li)
						
						throw new ExceptionLlamadaMetodoIzq(token.getLinea(),token.getColumna());
						
					else
						//le paso a encadenado el tipo de metodo y encadenado me retorna un tipo
						return enc.chequear(metLlamado.getTipo());
					
				}	
				
				
			}else{
				//no existe el metodo en la clase actual
				throw new ExceptionMetodoNoExiste(token.getLexema(),args.getLista().size(),Principal.ts.getClaseActual().getNombre(),token.getLinea(),token.getColumna());
			}	
			
		//se quiere usar this en atributo
		}else{
			throw new ExceptionSemanticoChequeo("No se puede hacer una llamada a un metodo dinámico en una asignación inline de un atributo de instancia. ",token.getLinea(),token.getColumna());
		}
	}
	
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Metodo");
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}

	
	public void generar() {
		
		//estoy en un metodo dinamico? Apilo This
		if(metLlamado.esDinamico()){
			Principal.gen.gen("LOAD 3", "Cargo el this porque es un metodo dinamico");
		
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
			Principal.gen.gen("LOADREF "+metLlamado.offset, "Cargo la direccion del metodo");
			Principal.gen.gen("CALL","Llamo al metodo");
		
		}
		
		//si estoy en un metodo estatico? 
		if(!metLlamado.esDinamico()){
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
