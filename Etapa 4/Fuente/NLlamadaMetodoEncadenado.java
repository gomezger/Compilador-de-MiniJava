

public class NLlamadaMetodoEncadenado extends NEncadenado {
	//atributos
	protected NArgsActuales args;
	protected NEncadenado enc;
	protected boolean li;
	
	
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
				EntradaConParams metActual = clase.getMetodos().get(nombreMetodo);
				
				//chequeo que esten bien los argumentos
				args.chequear(metActual.getListaParametros());
				
				//me fijo si es un llamado y estoy del lado izquierdo
				if(enc instanceof NEncadenadoVacio && li){
					
					throw new ExceptionLlamadaMetodoIzq(token.getLinea(),token.getColumna());
					
				}else{
					
					//le paso a encadenado el tipo de metodo y encadenado me retorna un tipo
					return enc.chequear(metActual.getTipo());
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

	


}
