

import java.util.Map;

public class EntradaConstructor extends EntradaConParams{
	
	//constrcutor
	public EntradaConstructor(Token token, EntradaClase clase){
		super(token,clase,new TipoVoid());
	}
	
	

	
	
	
	//extras
	public void imprimir(){
		System.out.println("+ "+this.getCantParams()+" parametros: ");
		for(Map.Entry<String,EntradaParametro> entry: params.entrySet()) {
			System.out.print("-> ");
			entry.getValue().imprimir();
			System.out.println("");
		}
	}
}
