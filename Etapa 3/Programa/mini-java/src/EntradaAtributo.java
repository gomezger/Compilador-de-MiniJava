
public class EntradaAtributo extends EntradaVariable{
	protected boolean publico;
	
	//constrcutor
	public EntradaAtributo(boolean publico, Tipo tipo, Token token){
		super(token,tipo);
		this.publico = publico;
	}
	
	
	
	
	
	//extras
	public void imprimir(){
		System.out.print(this.getNombre()+" (Tipo: ");
		tipo.imprimir();
		if(publico)
			System.out.print(". Publico)");
		else
			System.out.print(". Privado)");		
	}
	
}
