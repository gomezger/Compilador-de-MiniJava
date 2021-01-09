

public class NLlamadaMetodoEstatico extends NPrimario {
	//atributos
	protected TipoIdClase tipo;
	protected Token idMetVar;
	protected NArgsActuales args;
	protected NEncadenado enc;
	protected boolean li;
	
	
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
				EntradaConParams met = clase.getMetodos().get(nombreMetodo);
				
				args.chequear(met.getListaParametros());
				
				//chequeo que sea estatico
				if(!met.esDinamico()){
					
					return enc.chequear(met.getTipo());
					
				}else{
					throw new ExceptionMetodoEstatico(met.getNombre(), met.getCantParams(), met.getToken().getLinea(), met.getToken().getColumna());
				}
				
				
			}else{
				throw new ExceptionMetodoNoExiste(idMetVar.getLexema(),args.getLista().size(),tipo.getNombre(),token.getLinea(),token.getColumna());				
			}			
			
		//no existe la clase	
		}else{
			throw new ExceptionNoEsIdClase(tipo,token.getLinea(),token.getColumna());
		}		
	}
	
}
