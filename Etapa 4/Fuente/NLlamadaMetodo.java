

public class NLlamadaMetodo extends NPrimario {
	//atributos
	protected NArgsActuales args;
	protected boolean li;
	public boolean at; // para ver si se usa en un atributo
	
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
		
		//verificar si se quiere usar un this en asignacion inline de atributo instancia
		if(!this.at){
		
			
			String nombreMetodo = token.getLexema()+"$"+args.getLista().size();
	
			
			//busco el metodo dentro de la clase
			if(Principal.ts.getClaseActual().getMetodos().containsKey(nombreMetodo)){			
				
				//tengo el metodo
				EntradaConParams metLlama = Principal.ts.getClaseActual().getMetodos().get(nombreMetodo);
				EntradaConParams metActual = Principal.ts.getMetodoActual();
				
				
				//si estoy en un metodo statico y llamo a un metodo dinamico hay error
				if(!metActual.esDinamico() && metLlama.esDinamico()){
					
					throw new ExceptionSemanticoChequeo("No se puede acceder a un método dinámico desde un método estático",token.getLinea(),token.getColumna());
				
				//estoy en un metodo esatico	
				}else{
				
					//chequeo que esten bien los argumentos
					args.chequear(metLlama.getListaParametros());		
					
					//me fijo si es un llamado y estoy del lado izquierdo
					if(enc instanceof NEncadenadoVacio && li)
						
						throw new ExceptionLlamadaMetodoIzq(token.getLinea(),token.getColumna());
						
					else
						//le paso a encadenado el tipo de metodo y encadenado me retorna un tipo
						return enc.chequear(metLlama.getTipo());
					
				}	
				
				
			}else{
				//no existe el metodo en la clase actual
				throw new ExceptionMetodoNoExiste(token.getLexema(),args.getLista().size(),Principal.ts.getClaseActual().getNombre(),token.getLinea(),token.getColumna());
			}	
			
		//se quiere usar this en atributo
		}else{
			throw new ExceptionSemanticoChequeo("No se puede hacer una llamada a un metodo en una asignación inline de un atributo de isntancia. ",token.getLinea(),token.getColumna());
		}
	}
	
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Metodo");
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}

	
}
