

public class NWhile  extends NSentencia{
	//atributos
	protected NExpresion cond;
	protected NSentencia cuerpo;
	
	//constrcutor
	public NWhile(Token token, NExpresion cond, NSentencia cuerpo){
		super(token);
		this.cond = cond;
		this.cuerpo = cuerpo;
	}

	//getters
	public NExpresion getCond() {
		return cond;
	}
	public NSentencia getCuerpo() {
		return cuerpo;
	}
	
	
	//chequear
	public void chequear() throws ExceptionSemantico{
		TipoBase tc = cond.chequear();
		
		//verifico que la condicion sea boolean
		if(tc.esCompatible(new TipoBoolean())){
			
			//chequeo el cuerpo
			cuerpo.chequear();
			
		}else{
			throw new ExceptionCondicion(cond.getToken().getLinea(),cond.getToken().getColumna());
		}
	}
	
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("While");
		cond.imprimir(n+1);
		cuerpo.imprimir(n+1);
	}


	public void generar() {
		//hay un where nuevo
		int c_while = Generador.cant_while++;
		
		//genero etiqueta de donde empieza el while
		Principal.gen.gen("while_"+c_while+": NOP", "Empieza el while");	
		
		//genero la condicion
		cond.generar();
		
		//veo si ya termino el while
		Principal.gen.gen("BF finWhile_"+c_while, "Condicion while");
		
		//genero codigo del while
		cuerpo.generar();

		//evaluo la condicion de vuelta
		Principal.gen.gen("JUMP while_"+c_while+"", "Vuelvo a evaluar la condicion");
		
		//fin de while
		Principal.gen.gen("finWhile_"+c_while+": NOP", "Fin del while");		
		
		
	}
	
}
