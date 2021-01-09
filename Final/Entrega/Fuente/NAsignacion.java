

public class NAsignacion  extends NSentencia{
	//atributos
	protected NAcceso li;
	protected NExpresion ld;
	
	//constrcutor
	public NAsignacion(Token token, NAcceso li, NExpresion ld){
		super(token);
		this.li = li;
		this.ld = ld;
	}

	//getters
	public NAcceso getLi() {
		return li;
	}

	public NExpresion getLd() {
		return ld;
	}


	//chequear
	public void chequear() throws ExceptionSemantico{
		
		//dejar en este 
		TipoBase tipoLD = ld.chequear();
		TipoBase tipoLI = li.chequear();
		
		if(tipoLI.esCompatible(tipoLD)){
			//no hacer nada
		
		//error
		}else{
			throw new ExceptionAsignacion(tipoLI.getNombre(),tipoLD.getNombre(),token.getLinea(),token.getColumna());
		}
		
	}
	
	@Override
	public void generar() {
		ld.generar();
		li.generar();
	}
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Asignacion");
		li.imprimir(n+1);
		ld.imprimir(n+1);
	}

	
	
}
