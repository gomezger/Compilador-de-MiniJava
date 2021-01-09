


import java.util.Map;

public class EntradaMetodo extends EntradaConParams{
	
	//atributos
	protected boolean dinamico; //si es dinamyc true, sino false y es static
	
	//constructor
	public EntradaMetodo(boolean dinamico, TipoBase tipoRetorno, Token token, EntradaClase clase){
		super(token,clase,tipoRetorno);	
		this.dinamico = dinamico;
	}
	
	
	/// GETTERS
	public boolean esDinamico(){
		return this.dinamico;
	}
	public boolean esEstatico(){
		return !this.dinamico;
	}
	

	//extras
	public void imprimir(){
		System.out.print("+  "+token.getLexema()+" (Tipo: ");
		tipoRetorno.imprimir();
		System.out.println(")");
		for(Map.Entry<String,EntradaParametro> entry: params.entrySet()) {
			System.out.print("-> ");
			entry.getValue().imprimir();
			System.out.println("");
		}
	}
	public void imprimirAST() {	
		System.out.print("+  "+token.getLexema()+" (Tipo: ");
		tipoRetorno.imprimir();
		System.out.println(")");
		if(bloque!=null) this.bloque.imprimir(0);
	}

	public boolean esConstructor() {
		return false;
	}

}
