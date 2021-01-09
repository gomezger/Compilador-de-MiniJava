

public class NPuntoComa  extends NSentencia{
	//atributos
	
	//constrcutor
	public NPuntoComa(Token token){
		super(token);
	}

		

	public void imprimir(int n){
		tabs(n); 
		System.out.println("Punto y Coma");
	}

	
	/**
	 * Primero pido el tipo de expresion
	 * @throws ExceptionSemantico 
	 */
	public void chequear() throws ExceptionSemantico {
		
	}


	public void generar() {		
	}
}
