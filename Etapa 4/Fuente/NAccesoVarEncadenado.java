

public class NAccesoVarEncadenado extends NEncadenado {
	//atributos
	protected NEncadenado enc;
	protected boolean li;
	
	
	//constrcutor
	public NAccesoVarEncadenado(Token token, NEncadenado enc, boolean li){
		super(token);
		this.enc = enc;
		this.li = li;
	}	


	//getters
	public NEncadenado getEnc() {
		return enc;
	}
	
	
	//chequear
	public TipoBase chequear(TipoBase t) throws ExceptionSemantico{
		
		//t es de tipo clase?
		if(t instanceof TipoIdClase){
			
			//existe la clase ?
			if(Principal.ts.existeClase(t.getNombre())){
				
				//recupero la clase
				EntradaClase clase = Principal.ts.getClase(t.getNombre());		
				
				//existe la variable en la clase 
				if(clase.getAtributos().containsKey(token.getLexema())){
					
					EntradaAtributo atr = clase.getAtributos().get(token.getLexema());
					
					//si es public esta todo ok
					if(atr.esPublico()){						
						
						return enc.chequear(atr.getTipo());
						
					//no es publica	
					}else
						throw new ExceptionAtributoPrivado(atr.getNombre(),clase.getNombre(),token.getLinea(),token.getColumna());


				//no existe la variable	
				}else
					throw new ExceptionAtributoNoExiste(token.getLexema(),clase.getNombre(),token.getLinea(),token.getColumna());
				
			//la clase no existe	
			}else
				throw new ExceptionClaseNoExiste(token.getLexema(),token.getLinea(), token.getColumna());
		
		//t no es de tipo clase
		}else
			throw new ExceptionNoEsIdClase(token.getLexema(),token.getLinea(),token.getColumna());			
	}

	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Acceso Var Encadenado");
		tabs(n+1); System.out.println("Var: "+token.getLexema()); 
		tabs(n+1); System.out.println("Lado Izq.: "+li); 
		enc.imprimir(n+1);
	}

	
}
