

public class NEncadenadoVacio extends NEncadenado {
	
	//constrcutor
	public NEncadenadoVacio(Token token){
		super(token);
	}
	
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Encadenado Vacio");
	}


	//chequear
	public TipoBase chequear(TipoBase tipoExp) {
		return tipoExp;
	}


	@Override
	public void generar() {
		// TODO Auto-generated method stub
		
	}

	
}
