

public class NAccesoArregloEncadenado extends NEncadenado {
	//atributos
	protected NExpresion exp;
	protected NEncadenado enc;
	protected boolean li;
	
	
	//constrcutor
	public NAccesoArregloEncadenado(Token token, NExpresion exp, NEncadenado enc, boolean li){
		super(token);
		this.exp = exp;
		this.enc = enc;
		this.li = li;
	}
	

	//getters
	public NExpresion getExp() {
		return exp;
	}
	public NEncadenado getEnc() {
		return enc;
	}
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Acceso Arreglo Encadenado");
		tabs(n+1); System.out.println("Lado Izq.: "+li); 
		exp.imprimir(n+1);
		enc.imprimir(n+1);
	}



	public TipoBase chequear(TipoBase tipo) throws ExceptionSemantico {
		//verificar si es de tipo arreglo la variable que estaba a la izqueirda
		if(tipo instanceof TipoArregloChar || tipo instanceof TipoArregloInt || tipo instanceof TipoArregloBoolean){
			
			//verifico que exp sea de tipo int
			TipoBase tipoExp = this.exp.chequear();			
			if(tipoExp.esCompatible(new TipoInt())){
				
				//no puede haber encadenados en un arreglo primitivo
				if(enc instanceof NEncadenadoVacio){
					
					return ((TipoArreglo)tipo).getTipoPrimitivo();	
					
				}else{
					
					throw new ExceptionSemanticoChequeo("En un arreglo no puede haber encadenado, ya que solo se trabaja con tipos primitivos",token.getLinea(),token.getColumna());
				}
				
			
			}else{
				throw new ExceptionTipoExpresion("int",tipoExp.getNombre(),exp.getToken().getLinea(),exp.getToken().getColumna());
			}			
			
			
		// si tenemos a[5], puede que a no sea de tipo arreglo	
		}else{
			throw new ExceptionTipoVar(token.getLexema(),token.getLinea(),token.getColumna());
		}
	}
	
}
