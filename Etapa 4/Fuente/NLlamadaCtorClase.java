

public class NLlamadaCtorClase extends NLlamadaCtor {
	//atributos
	protected NArgsActuales args;
	protected TipoIdClase tipo;
	
	//constrcutor
	public NLlamadaCtorClase(Token token,TipoIdClase tipo, NArgsActuales args, NEncadenado enc){
		super(token,enc);
		this.args = args;
		this.enc = enc;
		this.tipo = tipo;
	}
	
	//getters
	public NArgsActuales getArgs() {
		return args;
	}
	public TipoIdClase getTipo() {
		return tipo;
	}
	

	//chequear
	public TipoBase chequear() throws ExceptionSemantico{
		
		// ... = new nombreClase(...)
		String nombreClase = token.getLexema();
		
		//me devuelve la clase con ese nombre, si no lanza excepsion
		if(Principal.ts.existeClase(nombreClase)){
		
			EntradaClase clase = Principal.ts.getClase(nombreClase);
				
			//pido el contructor con la cantidad de argumentos
			EntradaConParams ctor = clase.getConstructores().get(args.getLista().size());
			
			//verifico si es distinto a nulo
			if(ctor!=null){
				
				//chequeamos los parametros
				args.chequear(ctor.getListaParametros());
				
				//chequeamos el encadenado
				return enc.chequear(tipo);
				
			}else{
				throw new ExceptionUnidadNoExiste(args.getLista().size(),nombreClase, token.getLinea(), token.getColumna());
			}
		}else{
			throw new ExceptionNoEsIdClase(nombreClase,token.getLinea(),token.getColumna());
		}
		
	}
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Ctor Clase");
		System.out.println("   Tipo: "+tipo.nombre+"");
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}

}
